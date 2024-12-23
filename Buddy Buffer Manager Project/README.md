Buddy Buffer Manager

Sam Largaespada

Source Course: CISC310

The purpose of this project was to simulate a memory allocation strategy called the buddy system. This strategy takes blocks of memory and divides them into “buddies” recursively until we get a buffer that is of a correct size. Further rounds of allocation will prioritize using chunks of memory that have already been split and that are of an appropriate size. Say we have a block of memory that is size 1028. If we want to allocate a chunk of size 256 we would first split the block into two 512 sized buffers, then split one of those into two 256 sized buffers and consider one of those buffers in-use.

I was a big fan of this project. I got to combine knowledge from algorithms, data structures, and operating systems into one moderately sized program. My code used an array of binary trees, where each tree is a full-sized block of memory, to simulate the buddy system. This meant that I could use recursive methods to both split and rejoin memory buffers as needed.

To actually use this project you would want to run the TestDriver code, which calls methods from the BufferManager class and creates a text file as output. Unfortunately, the instructions of this assignment were to not allow user input, so all the test cases are hard coded. There is a wide variety of test cases to look at, but I think having user input would have been more interesting and dynamic. There are a few main methods that a user would use to interact with the data structure. First would be assignBuffer(), which lets the user ask for a buffer of a particular size. The second, returnBuffer(), takes an in-use buffer and recursively joins it back up with its buddies until it hits another in-use buffer, or is back to full size. Finally, debug(), prints out a list of buffers that are available for use. My TestDriver class uses repeated combinations of  these functions to simulate multiple buffer assignments and rejoins, and also prints out the available buffers after each method call. 
