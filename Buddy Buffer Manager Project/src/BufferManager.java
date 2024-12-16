//Sam Largaespada
//CISC 310
//Assignment 8
//5/21/24

//This program simulates allocation of memory using the "Buddy Buffer" system. We are given
//blocks of memory of a maximum size, which can then be broken down into half sized "buddy" buffers.
//Requests for memory are calculated to find an appropriate sized buffer, at which point either
//a free buffer is found and used, or a larger buffer is broken down until an appropriate sized
//buffer is created.
//If a buffer is no longer being used it can recombine with its buddy and cascade upwards
//until an available maximum sized buffer is reformed.


import java.util.ArrayList;

public class BufferManager {
    Node[] blocks; //Array of binary trees
    boolean tight; //Boolean for keeping track of whether the memory is "tight"
    private int[] freeBuffers; //Array that keeps track of how many free buffers and their size

    //Constructor
    public BufferManager() {
        //Initialize an array of binary trees that represent the blocks of memory
        //The root of each tree is of size 511 and breaks down into smaller halves from there
        blocks = new Node[10];
        for (int i = 0; i < 10; i++) {
            blocks[i] = new Node(511, i*512, null);
        }
        tight = false;
        freeBuffers = new int[]{10, 0 , 0, 0, 0, 0, 0};
    }

    //Method that finds and assigns a free buffer when a block is requested
    public int BufferAssign(int block_size){
        //Check to make sure requested size is valid
        if (block_size > 511 || block_size < 7)
            return -2;

        //This finds the appropriate size block for the request
        int full_size = getGoodSize(block_size);
        //Determines if the requested size buffer
        // already exists or if we gotta start splitting
        int id = getSizeID(full_size);


        int address; //Address of the buffer we eventually want to return

        if (freeBuffers[id] > 0) //If the needed buffer is already available we explore til we find it
            address = exploreMethod(full_size);
        //If the needed buffer isn't available we need to split for it
        else
            address = splitMethod(full_size);

        updateTight(); //After allocating the buffer we update the tight variable

        return address;
    }

    //Method which finds the right buffer if its already available
    public int exploreMethod(int size){
        int address = -1; //Initialize to -1 just in case
        //For-loop to run through the array of trees and find the correct buffer
        for (int i = 0; i < 10; i++) {
            address = explore(blocks[i], size);
            if (address > -1) //If we found a good address break the loop
                break;
        }
        return address;
    }
    //Recursive method that searchs down through the tree til it gets the right size buffer
    public int explore(Node n, int s){
        if (n==null) //Return -1 if n is null since something bad has happened
            return -1;

        //If we found a buffer of good size thats not in use then we can stop recursing
        if (n.size == s && !n.in_use) {
            n.in_use = true;
            freeBuffers[getSizeID(s)]--;
            return n.ad + 1;
        }
        //If the size we want is bigger than the current buffer, and its a "leaf" buffer
        //then we havent found what we want in this branch
        if(s > n.size || n.left == null)
            return -1;

        //If we havent found the right buffer, but we arent in a "leaf" yet then recursively
        //explore the left and right "children" buffers
        int address = explore(n.left, s);
        if (address == -1) {
            address = explore(n.right, s);
        }

        return address;
    }

    //Method for when we have to split to get the correct sized buffer
    public int splitMethod(int size){
        //Run through the array of trees until we got one that isnt already being used
        int address = -1;
        for (int i = 0; i < 10; i++) {
//            if (blocks[i].size >= size)// && !blocks[i].in_use)
            address = split(blocks[i], size);
            if (address > -1)
                break;
        }
        return address;
    }



    //Recursive splitter method that does the actual work
    public int split(Node n, int s){
        if(n==null)
            return -1;
        //If we got the right size and its not being used we can stop splitting
        if (n.size == s && !n.in_use){
            n.in_use = true;
            freeBuffers[getSizeID(s)]--; //Update the freeBuffer list
            return n.ad+1;
        }

        //If the node size is bigger than what we want then its time to split
        if (n.size > s && !n.in_use){
            n.in_use = true; //We say the node is in use since its currently split
            //Create the "child" buffers with updated addresses and the current buffer as
            //their "parent"
            n.left = new Node(n.size / 2, n.ad, n);
            n.right = new Node(n.size / 2, n.ad + n.size / 2 + 1, n);

            //Update the freeBuffer list since stuff has changed
            freeBuffers[getSizeID(n.size)]--;
            freeBuffers[getSizeID(n.size / 2)] += 2;

            //Recursively call split on the left child first looking for the right size
            //If that returns -1 then try the right child
            int address = split(n.left, s);
            if (address == -1)
                address = split(n.right, s);
            return address;
        }
        else{
            int address = split(n.left, s);
            if (address == -1)
                address = split(n.right, s);
            return address;
        }
//        return -1;
    }

    //Method for freeing a buffer then recursively recombining buffers back up the tree
    public void returnBuffer(int address){
        //Run through the array of trees as always
        for (int i = 0; i < 10; i++) {
            if (returnBufferHelper(blocks[i], address - 1)){
                break;
            }
        }

        updateFreeBuffers();
        updateTight();
    }
    //Helper method which finds the node with the correct address
    public boolean returnBufferHelper(Node n, int address){
        //If we get a null then just return false since we've gone too deep
        if (n == null)
            return false;

//        System.out.println("Node size = " + n.size);
//        System.out.println("Node address = " + n.ad);

        //If we found the right address, its in use, and its a leaf then we are in the right spot
        if (n.ad == address && n.in_use && n.left == null){
            n.in_use = false;
            freeBuffers[getSizeID(n.size)]++;
            combine(n.parent); //Start the recursive recombining
            return true;
        }
        //If we didnt get the right spot yet then start checking the child buffers
        return returnBufferHelper(n.left, address) || returnBufferHelper(n.right, address);
    }

    //Recursive combiner method than starts pairing up buddy buffers if they arent in use
    public void combine(Node n){
        //base cases. If its null we have hit the highest size buffer and its time to stop
        if(n == null)
            return;
        //If both children exist and are not in use we can combine them
        //We make sure to update the freeBuffer list too while doing so
        if(n.left != null && !n.left.in_use && n.right != null && !n.right.in_use){
            freeBuffers[getSizeID(n.left.size)] -= 2;
            n.left = null;
            n.right = null;
            n.in_use = false;
            freeBuffers[getSizeID(n.size)]++;
            //After combing the children its time to see if the current node can also be combined
            combine(n.parent);
        }
    }

    //Quick method for updating the tight variable after making changes to the buffers
    public void updateTight(){
        tight = freeBuffers[0] < 2;
    }

    //Essentially just a public getter for the tight status
    public String getStatus(){
        if (tight)
            return "Tight";
        return "Ok";
    }

    //Method which finds the appropriately sized buffer for the request
    public int getGoodSize(int size){
        if (size<=511 && size > 255)
            return 511;
        if (size<=255 && size > 127)
            return 255;
        if (size<=127 && size > 63)
            return 127;
        if (size<=63 && size > 31)
            return 63;
        if (size<=31 && size > 15)
            return 31;
        if (size<=15 && size > 7)
            return 15;
        else return 7;
    }

    //This is how we identify which size goes in each spot of the freeBuffer list
    //Actually pretty critical for updating the list as we add and remove free buffers
    public int getSizeID(int size){
        if (size == 511)
            return 0;
        if (size == 255)
            return 1;
        if (size == 127)
            return 2;
        if (size == 63)
            return 3;
        if (size == 31)
            return 4;
        if (size == 15)
            return 5;
        else return 6;
    }

    //Debug which prints out the freeBuffer list
    public String debug(){

        System.out.println("Free Buffer Count:");
        System.out.println(freeBuffers[0] + " 511 size buffers");
        System.out.println(freeBuffers[1] + " 255 size buffers");
        System.out.println(freeBuffers[2] + " 127 size buffers");
        System.out.println(freeBuffers[3] + " 63 size buffers");
        System.out.println(freeBuffers[4] + " 31 size buffers");
        System.out.println(freeBuffers[5] + " 15 size buffers");
        System.out.println(freeBuffers[6] + " 7 size buffers");

        String output = "Free Buffer Count:\n" +
                freeBuffers[0] + " 511 size buffers\n" +
                freeBuffers[1] + " 255 size buffers\n" +
                freeBuffers[2] + " 127 size buffers\n" +
                freeBuffers[3] + " 63 size buffers\n" +
                freeBuffers[4] + " 31 size buffers\n" +
                freeBuffers[5] + " 15 size buffers\n" +
                freeBuffers[6] + " 7 size buffers\n";
        return output;
    }

    //This is another way to update the freeBuffer list by wiping the old one and recounting
    //what is actually in the binary trees
    public void updateFreeBuffers(){
        freeBuffers = new int[10];
        for (int i = 0; i < 10; i++) {
            count(blocks[i]);
        }
    }

    //Recursive helper method for updateFreeBuffers which essentially does a
    //Depth-First-Search on a binary tree and increments the freeBuffer list when it finds
    //a leaf buffer that is not in use
    public void count(Node n) {
        if(n==null)
            return;
        int s = n.size;
        if (n.left == null && !n.in_use) {
            if (s == 511)
                freeBuffers[0]++;
            else if (s == 255)
                freeBuffers[1]++;
            else if (s == 127)
                freeBuffers[2]++;
            else if (s == 63)
                freeBuffers[3]++;
            else if (s == 31)
                freeBuffers[4]++;
            else if (s == 15)
                freeBuffers[5]++;
            else
                freeBuffers[6]++;
        }
        //Recurse on the left and right child buffers
        else {
            count(n.left);
            count(n.right);
        }
    }

    //Node class which is really how the buffers of various sizes are represented
    private class Node{
        private int ad; //Buffer address
        private int size; //Buffer size
        private boolean in_use; //Whether the buffer is "in-use"
        private boolean chained;
        private Node left, right, parent; //Fields to keep track of "child" and "parent" buffers

        //Basic constuctor with just buffer size as an input
        public Node(int size){
            this.size = size;
            left = null;
            right = null;
            in_use = false;
        }
        //The important constructor which also sets the parent buffer
        public Node(int size, int address, Node p){
            this.size = size;
            left = null;
            right = null;
            in_use = false;
            ad = address;
            parent = p;
        }

    }
}
