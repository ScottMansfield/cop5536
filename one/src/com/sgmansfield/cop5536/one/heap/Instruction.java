package com.sgmansfield.cop5536.one.heap;

/**
 * Holds the type of operation to perform and the number to use if necessary
 *
 * @author Scott
 */
public class Instruction
{
    private Operation operation;

    /**
     * Gets the {@link Operation} to perform for this instruction
     * @return The operation to perform
     */
    public Operation getOperation()
    {
        return operation;
    }

    private int number;

    /**
     * Gets the number to use in the operation if it is necessary
     * @return The number to use for the associated operation
     */
    public int getNumber()
    {
        return number;
    }

    /**
     * Creates a new Instruction with the given {@link Operation} and number
     * @param operation The {@link Operation} to perform
     * @param number The number to use in the operation
     */
    public Instruction(Operation operation, int number)
    {
        this.operation = operation;
        this.number = number;
    }
}
