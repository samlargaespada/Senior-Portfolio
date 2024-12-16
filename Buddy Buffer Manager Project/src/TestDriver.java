//Sam Largaespada
//CISC 310
//Assignment 8
//5/21/24

//Driver class which test out a bunch of inputs and outputs from the Buffer Manager

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TestDriver {
    public static void main(String[] args) {
        String filePath = "output.txt";

        BufferManager test = new BufferManager();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("Sam Largaespada, 5/21/24, Assignment 8\n\n\n\n");
            writer.write("Initializing buffers\n");
            writer.write(" Expected values: 10 512 size buffers, Status Ok\n\n");
            writer.write(test.debug());
            writer.write("\nStatus: " + test.getStatus());

            writer.write("\n-----------------------------------------\n");

            writer.write("\n\nRequesting 700\n");
            writer.write("Expected values:\n");
            writer.write("Assigned address: -2\n\n");
            writer.write("Actual = Assigned address: " + test.BufferAssign(700));

            writer.write("\n\nRequesting buffer size 7\n");
            writer.write("Expected values: 9 511 size buffers, 1 255 size buffer, 1 127 size buffer,\n" +
                    "1 62 size buffer, 1 30 size buffer, 1 14 size buffer and 1 6 size buffer,\n" +
                    "Status OK\n\n");
            writer.write("Actual = Assigned address: " + test.BufferAssign(7) + "\n\n");
            writer.write(test.debug());
            writer.write("\nStatus: " + test.getStatus());

            writer.write("\n-----------------------------------------\n");

            writer.write("\n\nReturn buffer size 7\n\n");
            writer.write("Expected values: 10 511 size buffers, Status OK\n\n");
            test.returnBuffer(1);
            writer.write("Actual = \n");
            writer.write(test.debug());
            writer.write("\nStatus: " + test.getStatus());

            writer.write("\n-----------------------------------------\n");

            writer.write("\n\nRequesting 10 511 buffers\n");
            writer.write("Expected values: 0 511 buffers, 0 for all buffers, Status Tight\n\n");
            for (int i = 0; i < 10; i++) {
                writer.write("Actual = Assigned address: "
                        + test.BufferAssign(511) + "\n");
            }
            writer.write("\n" + test.debug());
            writer.write("\nStatus: " + test.getStatus());

            writer.write("\n-----------------------------------------\n");

            writer.write("\n\nRequest additional buffer\n");
            writer.write("Expected values: ");
            writer.write("\nAssigned address: -1");
            writer.write("\n0 511 buffers, Status Tight\n\n");
            writer.write("Actual = Assigned address: "
                    + test.BufferAssign(7) + "\n");
            writer.write("\n" + test.debug());
            writer.write("\nStatus: " + test.getStatus());

            writer.write("\n-----------------------------------------\n");

            writer.write("\n\nReturn 10 511 buffers\n");
            writer.write("Expected values: \n");
            writer.write("10 511 buffers, Status OK\n\n");
            writer.write("Actual = \nDebug output: ");
            for (int i = 0; i < 10; i++) {
                test.returnBuffer(1 + i*512);
            }
            writer.write("\n" + test.debug());
            writer.write("\nStatus: " + test.getStatus());

            writer.write("\n-----------------------------------------\n");

            writer.write("\n\nRequesting 19 255 buffers\n");
            writer.write("Expected values: \n");
            writer.write("0 511 buffers, 1 255 size buffers, 0 127 size buffers,\n" +
                    "0 63 size buffers, 0 31 size buffers, 0 15 size buffers, 0 7 size\n"+
                    "Status Tight\n\n");
            for (int i = 0; i < 19; i++) {
                writer.write("Actual = Assigned address: " + test.BufferAssign(255) + "\n");
            }
            writer.write("\n" + test.debug());
            writer.write("\nStatus: " + test.getStatus());

            writer.write("\n-----------------------------------------\n");

            writer.write("\n\nReturn 19 255 buffers\n");
            writer.write("Expected values: \n");
            writer.write("10 511 buffers, Status OK\n\n");
            writer.write("Actual = \nDebug output: ");
            for (int i = 0; i < 19; i++) {
                test.returnBuffer(1 + i*256);
            }
            writer.write("\n" + test.debug());
            writer.write("\nStatus: " + test.getStatus());

            writer.write("\n-----------------------------------------\n");

            writer.write("\n\nRequesting multiple buffers: 5 size 7, 2 size 255, 2 size 127, 7 size 511");
            writer.write("\nExpected values: \n");
            writer.write("1 511 size buffers, 0 255 size buffer, 1 127 size buffer,\n" +
                    "1 62 size buffer, 0 30 size buffer, 1 14 size buffer and 1 7 size \n");
            writer.write("\n\nAddresses for size 7\n----------\n");
            for (int i = 0; i < 5; i++) {
                writer.write("Assigned address: " + test.BufferAssign(7) + "\n");
            }
            writer.write("\n\nAddresses for size 255\n----------\n");
            for (int i = 0; i < 2; i++) {
                writer.write("Assigned address: " + test.BufferAssign(255) + "\n");
            }
            writer.write("\n\nAddresses for size 127\n----------\n");
            for (int i = 0; i < 2; i++) {
                writer.write("Assigned address: " + test.BufferAssign(127) + "\n");
            }
            writer.write("\n\nAddresses for size 511\n----------\n");
            for (int i = 0; i < 7; i++) {
                writer.write("Assigned address: " + test.BufferAssign(511) + "\n");
            }
            writer.write("\n" + test.debug());
            writer.write("\nStatus: " + test.getStatus());

            writer.write("\n-----------------------------------------\n");

            writer.write("\n\nReturn 1st size 7 buffer at address 1\n");
            test.returnBuffer(1);
            writer.write("\n" + test.debug());
            writer.write("\nStatus: " + test.getStatus());

            writer.write("\n-----------------------------------------\n");

            writer.write("\n\nReturn 2nd size 7 buffer at address 9\n");
            test.returnBuffer(9);
            writer.write("\n" + test.debug());
            writer.write("\nStatus: " + test.getStatus());

            writer.write("\n-----------------------------------------\n");

            writer.write("\n\nReturn 3rd size 7 buffer at address 17\n");
            test.returnBuffer(17);
            writer.write("\n" + test.debug());
            writer.write("\nStatus: " + test.getStatus());

            writer.write("\n-----------------------------------------\n");

            writer.write("\n\nReturn 4th size 7 buffer at address 25\n");
            test.returnBuffer(25);
            writer.write("\n" + test.debug());
            writer.write("\nStatus: " + test.getStatus());

            writer.write("\n-----------------------------------------\n");

            writer.write("\n\nReturn 5th size 7 buffer at address 33\n");
            test.returnBuffer(33);
            writer.write("\n" + test.debug());
            writer.write("\nStatus: " + test.getStatus());

            writer.write("\n-----------------------------------------\n");

            writer.write("\n\nReturn 1st size 255 buffer at address 257\n");
            test.returnBuffer(257);
            writer.write("\n" + test.debug());
            writer.write("\nStatus: " + test.getStatus());

            writer.write("\n-----------------------------------------\n");

            writer.write("\n\nReturn 2nd size 255 buffer at address 513\n" +
                    "Note: Due to how these buffers were assigned the two size 255 buffers" +
                    " belong to different buffer chains so will not recombine");
            test.returnBuffer(513);
            writer.write("\n" + test.debug());
            writer.write("\nStatus: " + test.getStatus());

            writer.write("\n-----------------------------------------\n");

            writer.write("\n\nReturn 1st size 127 buffer at address 129\n");
            test.returnBuffer(129);
            writer.write("\n" + test.debug());
            writer.write("\nStatus: " + test.getStatus());

            writer.write("\n-----------------------------------------\n");

            writer.write("\n\nReturn 2nd size 127 buffer at address 769\n");
            test.returnBuffer(769);
            writer.write("\n" + test.debug());
            writer.write("\nStatus: " + test.getStatus());
        } catch (IOException e) {
            e.printStackTrace();
        }






//        int ad = test.BufferAssign(7);
//        test.debug();
//        System.out.println("Assigned address: " + ad);
//
//        test.returnBuffer(1);
//        test.debug();

    }
}