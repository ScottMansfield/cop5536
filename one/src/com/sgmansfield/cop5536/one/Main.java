package com.sgmansfield.cop5536.one;

import com.sgmansfield.cop5536.one.heap.Heap;
import com.sgmansfield.cop5536.one.heap.Instruction;
import com.sgmansfield.cop5536.one.heap.Operation;
import com.sgmansfield.cop5536.one.heap.binomial.BinomialHeap;
import com.sgmansfield.cop5536.one.heap.leftisttree.LeftistTreeHeap;
import java.io.FileNotFoundException;
import java.util.List;

/**
 *
 * @author Scott
 */
public class Main
{

	/**
     * The entry point of the program.
     *
     * Note: Most of the methods are really only public to show them in the
     * generated javadoc.
     *
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws FileNotFoundException
	{
        // Random values with timing information
        if (args.length == 1 &&
            args[0].equals("-r"))
        {
            randomTest();
        }
        // Specific heap type with user input from a file
        else if (args.length == 2 &&
                 args[0].startsWith("-i"))
        {
            fileInputTest(args[0], args[1]);
        }
        // Bad args.
        else
        {
            printUsage();
            System.exit(-1);
        }
	}

    /**
     * Performs a random test of both kinds of {@link Heap}
     */
    public static void randomTest()
    {
        // From the instructions: These are the number of elements to use
        // TODO: Restore real sizes array
        int[] sizes = {100, 500, 1000, 2000, 3000, 4000, 5000};

        // From the instructions: Run at least 5 times to get an average.
        // might as well make it 10
        int numRuns = 10;

        for (int size : sizes)
        {
            // Accumulators for the total time taken for each kind of min heap
            double binomialAcc = 0;
            double leftistAcc = 0;

            // From the instructions: 5000 random operations to perform on the heaps
            List<Instruction> instructions = RandomListGen.randomInstructions(5000, size);

            for (int j = 0; j < numRuns; j++)
            {
                // From the instructions: Generate a random permutation of 0 to n-1
                List<Integer> items = RandomListGen.randomIntegerPermutation(size);

                // Test the binomial heap first.
                // Note only the random instructions are timed and not the initial inserts.
                Heap heap = new BinomialHeap();
                fillHeap(heap, items);
                binomialAcc += timeInstructions(heap, instructions);

                // Then test the leftist tree with the same elements and set of instructions
                heap = new LeftistTreeHeap();
                fillHeap(heap, items);
                leftistAcc += timeInstructions(heap, instructions);
            }

            // Divide by number of runs to get the average time taken per run
            // then divide by 1 million to get the time in milliseconds (from nanoseconds)
            binomialAcc = binomialAcc / numRuns;
            double binomialAvgTimeMs = binomialAcc / 1000000.0;
            System.out.println("Binomial heap average time for size " + size + ": " + binomialAvgTimeMs + " ms");

            leftistAcc = leftistAcc / numRuns;
            double leftistAvgTimeMs = leftistAcc / 1000000.0;
            System.out.println("Leftist Tree heap average time for size " + size + ": " + leftistAvgTimeMs + " ms");
        }
    }

    /**
     * Fills the provided {@link Heap} with the provided list of numbers.
     *
     * @param heap The {@link Heap} to fill
     * @param items The items to put into the heap
     */
    public static void fillHeap(Heap heap, List<Integer> items)
    {
        for (Integer item : items)
        {
            heap.insert(item);
        }
    }

    /**
     * Times how long the list of {@link Instruction}s takes to run on the given {@link Heap}
     *
     * @param heap The {@link Heap} on which to perform the instructions
     * @param instructions The list of {@link Instruction}s to perform
     * @return The time taken in nanoseconds
     */
    public static double timeInstructions(Heap heap, List<Instruction> instructions)
    {
        long startTime = System.nanoTime();

        runInstructons(instructions, heap);

        double duration = System.nanoTime() - startTime;

        return duration;
    }

    /**
     * Runs instructions from a file on a {@link Heap} specified in the command line arguments
     *
     * @param cmdSwitch the full command switch from the command line
     * @param fileName the file to read instructions from
     * @throws FileNotFoundException when the given file does not exist
     */
    public static void fileInputTest(String cmdSwitch, String fileName) throws FileNotFoundException
    {
        List<Instruction> instructions = FileParser.parseFile(fileName);

        Heap heap = null;

        // if -ib, use binomial heap
        // if -il, use leftist tree
        if (cmdSwitch.charAt(2) == 'b')
        {
            heap = new BinomialHeap();
        }
        else
        {
            heap = new LeftistTreeHeap();
        }

        // we don't care about the time it takes here
        runInstructons(instructions, heap);

        // Each heap knows how to print itself row-wise
        System.out.println(heap.toString());
    }

    /**
     * Runs the given {@link Instruction}s on the given {@link Heap}
     *
     * @param heap The {@link Heap} on which to perform the instructions
     * @param instructions The list of {@link Instruction}s to perform
     */
    public static void runInstructons(List<Instruction> instructions, Heap heap)
    {
        for (Instruction instruction : instructions)
        {
            if (instruction.getOperation() == Operation.INSERT)
            {
                heap.insert(instruction.getNumber());
            }
            else
            {
                heap.deleteMin();
            }
        }
    }

    /**
     * Prints the usage of the program to standard out
     */
    private static void printUsage()
    {
        System.out.println("Usage:");
        System.out.println("    (1) Random Mode");
        System.out.println("        run with a sequence of insert and delete-min operations generated by a random sequence generator.");
        System.out.println("            $ heap -r");
        System.out.println("    (2) User Input Mode: user input mode - run with the operation sequence from user");
        System.out.println("        (i) user input mode using min leftist tree scheme");
        System.out.println("            $ heap -il file-name // read the input from a file file-name");
        System.out.println("        (ii) user input mode using min binomial heap scheme");
        System.out.println("            $ heap -ib file-name // read the input from a file file-name");
    }
}
