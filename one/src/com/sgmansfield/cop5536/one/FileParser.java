package com.sgmansfield.cop5536.one;

import com.sgmansfield.cop5536.one.heap.Instruction;
import com.sgmansfield.cop5536.one.heap.Operation;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Parses a file, returning a list of instructions.
 *
 * @author Scott
 */
public class FileParser
{
    /**
     * Parses the given file and returns a list of instructions from it.
     *
     * <p>
     *     The file has three types of lines in these formats:
     *     <ol>
     *         <li>Insert instructions: <code>I ###</code></li>
     *         <li>Delete Min instructions: <code>D</code></li>
     *         <li>End of instructions: <code>*</code></li>
     *     </ol>
     * </p>
     *
     * @param fileName The name of the file to parse
     * @return A list of {@link Instruction}s parsed from the file
     * @throws FileNotFoundException when the file does not exist
     */
    public static List<Instruction> parseFile(String fileName) throws FileNotFoundException
    {
        List<Instruction> retval = new ArrayList<>();
        Scanner s = new Scanner(new File(fileName));

        while (s.hasNext())
        {
            String next = s.nextLine().trim();

            // Empty lines are OK
            if (next.length() == 0)
            {
                continue;
            }

            // End of instructions! stop parsing.
            if (next.equals("*"))
            {
                break;
            }

            String[] parts = next.split(" ");

            if (parts[0].equals("D"))
            {
                retval.add(new Instruction(Operation.DELETEMIN, 0));
            }
            else
            {
                // Let any parsing exceptions bubble up since it's user input.
                int num = Integer.valueOf(parts[1]);
                retval.add(new Instruction(Operation.INSERT, num));
            }
        }

        return retval;
    }
}
