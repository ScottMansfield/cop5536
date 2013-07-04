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
            fileInputTest(args);
        }
        // Bad args.
        else
        {
            printUsage();
            System.exit(-1);
        }
	}

    private static void randomTest()
    {
        // From the instructions: These are the number of elements to use
        // TODO: Restore real sizes array
        int[] sizes = {100, 500, 1000, 2000, 3000, 4000, 5000};
        //int[] sizes = {5};

        // From the instructions: Run at least 5 times to get an average.
        // might as well make it 10
        int numRuns = 10;

        for (int size : sizes)
        {
            double binomialAcc = 0;
            double leftistAcc = 0;

            // From the instructions: 5000 random operations to perform on the heaps
            List<Instruction> instructions = RandomListGen.randomInstructions(5000, size);

            for (int j = 0; j < numRuns; j++)
            {
                // From the instructions: Generate a random permutation of 0 to n-1
                List<Integer> items = RandomListGen.randomIntegerPermutation(size);

                Heap heap = new BinomialHeap();
                fillHeap(heap, items);
                binomialAcc += timeInstructions(heap, instructions);

                heap = new LeftistTreeHeap();
                fillHeap(heap, items);
                leftistAcc += timeInstructions(heap, instructions);
            }

            binomialAcc = binomialAcc / numRuns;
            double binomialAvgTimeMs = binomialAcc / 1000000.0;
            System.out.println("Binomial heap average time for size " + size + ": " + binomialAvgTimeMs + " ms");

            leftistAcc = leftistAcc / numRuns;
            double leftistAvgTimeMs = leftistAcc / 1000000.0;
            System.out.println("Leftist Tree heap average time for size " + size + ": " + leftistAvgTimeMs + " ms");
        }
    }

    private static void fillHeap(Heap heap, List<Integer> items)
    {
        for (Integer item : items)
        {
            heap.insert(item);
        }
    }

    private static double timeInstructions(Heap heap, List<Instruction> instructions)
    {
        long startTime = System.nanoTime();

        runInstructons(instructions, heap);

        double duration = System.nanoTime() - startTime;

        return duration;
    }

    private static void fileInputTest(String[] args) throws FileNotFoundException
    {
        List<Instruction> instructions = FileParser.parseFile(args[1]);

        Heap heap = null;

        if (args[0].charAt(1) == 'b')
        {
            heap = new BinomialHeap();
        }
        else
        {
            heap = new LeftistTreeHeap();
        }

        runInstructons(instructions, heap);

        System.out.println(heap.toString());
    }

    private static void runInstructons(List<Instruction> instructions, Heap heap)
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
