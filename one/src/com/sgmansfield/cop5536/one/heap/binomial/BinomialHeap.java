package com.sgmansfield.cop5536.one.heap.binomial;

import com.sgmansfield.cop5536.one.heap.Heap;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Scott
 */
public class BinomialHeap implements Heap
{
    private BinomialNode root = null;
    
    @Override
    public void insert(int num)
    {
        BinomialNode newNode = new BinomialNode(num, 0, null, null);
        
        if (root == null)
        {
            root = newNode;
            root.setNext(root);
        }
        else
        {
            newNode.setNext(root.getNext());
            root.setNext(newNode);
        }
    }

    @Override
    public Integer deleteMin()
    {
        if (root == null)
        {
            return -1;
        }
        
        int retval = root.getData();
        
        if (root == root.getNext())
        {
            // delete the root node!
            root = null;
            return retval;
        }
        
        Map<Integer, BinomialNode> nodes = new HashMap<>();
        
        // merge trees here. Make sure to keep a reference to the original node
        // so there's no infinite loop
        BinomialNode originalNode = root;
        
        BinomialNode currentNode = root.getNext();
        
        // run through the list and merge all of the possible trees together
        while (currentNode != originalNode)
        {
            // do stuff
            // need a while loop here to say while the nodes map doesn't have
            //   an entry, merge trees
        }
        
        // iterate over the hashmap and serialize to a list
        //   while maintaining a min to use as the root element.
        
        return retval;
    }
}
