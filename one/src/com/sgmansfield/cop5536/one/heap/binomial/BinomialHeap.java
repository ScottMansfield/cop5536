package com.sgmansfield.cop5536.one.heap.binomial;

import com.sgmansfield.cop5536.one.heap.Heap;

/**
 *
 * @author Scott
 */
public class BinomialHeap implements Heap
{
    //private Node root = null;
    
    public BinomialHeap()
    {
        // initialize heap
    }
    
    @Override
    public void insert(int num)
    {
        System.out.println("Inserting " + num);
    }

    @Override
    public Integer deleteMin()
    {
        System.out.println("Deleting min");
        return 0;
    }
    
    /**
     * Merges this heap with another BinomialHeap.
     * 
     * @param otherHeap 
     */
    public void meld(BinomialHeap otherHeap)
    {
        
    }
}
