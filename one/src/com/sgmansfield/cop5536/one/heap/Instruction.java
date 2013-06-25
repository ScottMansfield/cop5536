package com.sgmansfield.cop5536.one.heap;

/**
 *
 * @author Scott
 */
public class Instruction
{
    private Operation operation;

    public Operation getOperation()
    {
        return operation;
    }

    public int getNumber()
    {
        return number;
    }
    private int number;

    public Instruction(Operation operation, int number)
    {
        this.operation = operation;
        this.number = number;
    }
}
