package com.sgmansfield.cop5536.one;

import com.sgmansfield.cop5536.one.heap.Instruction;
import com.sgmansfield.cop5536.one.heap.Operation;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Scott
 */
public class FileParser
{
    public static List<Instruction> parseFile(String fileName) throws FileNotFoundException
    {
        List<Instruction> retval = new ArrayList<>();
        Scanner s = new Scanner(new File(fileName));
        
        while (s.hasNext())
        {
            String next = s.nextLine().trim();
            
            if (next.length() == 0)
            {
                continue;
            }
            
            String[] parts = next.split(" ");
            
            if (parts[0].equals("D"))
            {
                retval.add(new Instruction(Operation.DELETEMIN, 0));
            }
            else
            {
                int num = Integer.valueOf(parts[1]);
                retval.add(new Instruction(Operation.INSERT, num));
            }
        }
        
        return retval;
    }
}
