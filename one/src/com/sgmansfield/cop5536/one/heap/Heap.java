package com.sgmansfield.cop5536.one.heap;

/**
 *
 * @author Scott
 */
public interface Heap
{
    /**
     * Inserts a number into the heap
     *
     * @param num the number to insert
     */
    public void insert(int num);

    /**
     * Removes and returns the minimum element from the heap
     *
     * @return the minimum number as of the moment before this function is called
     */
    public Integer deleteMin();
}
