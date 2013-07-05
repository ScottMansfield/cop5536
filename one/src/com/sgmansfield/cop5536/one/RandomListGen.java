package com.sgmansfield.cop5536.one;

import com.sgmansfield.cop5536.one.heap.Instruction;
import com.sgmansfield.cop5536.one.heap.Operation;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Generates a couple types of random lists.
 *
 * One is a random permutation of a sequence from 0 to n-1.
 * The other is a random list of instructions to run on a heap.
 *
 * @author Scott
 */
public class RandomListGen
{

    /**
     * Creates a list of random Insert and DeleteMin {@link Instruction}s with insert values bounded by 0 and maxNum
     *
     * @param size the number of instructions to generate
     * @param maxNum the maximum integer to insert, exclusive
     * @return a list of random {@link Instruction}s
     */
    public static List<Instruction> randomInstructions(int size, int maxNum)
    {
        List<Instruction> insts = new ArrayList<>(size);

        // Only use one random instance to maximize randomness
        // (seriously I googled this. It makes sense.)
        Random r = new Random();

        for (int i = 0; i < size; i++)
        {
            if (r.nextInt(2) == 0)
            {
                insts.add(new Instruction(Operation.INSERT, r.nextInt(maxNum)));
            }
            else
            {
                insts.add(new Instruction(Operation.DELETEMIN, 0));
            }
        }

        return insts;
    }

    /**
     * Creates a list of integers from 0 to size-1 that is randomly permuted.
     *
     * @param size the total size / number of distinct numbers
     * @return a randomly permuted set of integers using 0 to size - 1
     */
    public static List<Integer> randomIntegerPermutation(int size)
    {
        List<Integer> numbers = new ArrayList<>(size);
        Random r = new Random();

        // Fill with 0 to n-1
        for (int i = 0; i < size; i++)
        {
            numbers.add(i);
        }

        // Swap random pairs
        for (int i = 0; i < size; i++)
        {
            swap(numbers, i, r.nextInt(size));
        }

        return numbers;
    }

    private static void swap(List<Integer> numbers, int pos1, int pos2)
    {
        int temp = numbers.get(pos1);
        numbers.set(pos1, numbers.get(pos2));
        numbers.set(pos2, temp);
    }
}
