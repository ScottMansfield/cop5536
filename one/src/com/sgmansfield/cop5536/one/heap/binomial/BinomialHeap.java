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
    private BinomialNode minNode = null;
    
    @Override
    public void insert(int num)
    {
        BinomialNode newNode = new BinomialNode(num, 0, null, null);
        
        if (minNode == null)
        {
            minNode = newNode;
            minNode.setNext(minNode);
        }
        else
        {
            newNode.setNext(minNode.getNext());
            minNode.setNext(newNode);
        }
    }

    @Override
    public Integer deleteMin()
    {
        if (minNode == null)
        {
            return -1;
        }
        
        int retval = minNode.getData();
        
        // A couple of special cases:
        // 1) The minNode is the only node
        // 2) The minNode is the only top-level node, so its children become the
        //      list. This works because of the properties of binomial trees
        if (minNode == minNode.getNext())
        {
            if (minNode.getChild() == null)
            {
                // delete the root node!
                minNode = null;
            }
            else
            {
                // find the min node of the children and point to that
                minNode = minNode.getChild();
                BinomialNode start = minNode;
                BinomialNode current = start.getNext();
                
                while (current != start)
                {
                    if (current.getData() < minNode.getData())
                    {
                        minNode = current;
                    }
                    
                    current = current.getNext();
                }
            }
            
            return retval;
        }
        
        // And now the general case where there are multiple nodes in the main
        // linked list that all potentially have children.
        // take the min node out and insert the linked list of children
        // into the main loop
        
        // This will insert the children of minNode into the main top-level list
        if (minNode.getChild() != null)
        {
            // get the ends of the child list
            BinomialNode firstChild = minNode.getChild();
            BinomialNode lastChild = minNode.getChild().getNext();
            
            while (lastChild.getNext() != firstChild)
            {
                lastChild = lastChild.getNext();
            }
            
            // insert the list into the main list
            BinomialNode nodeAfterInsertedSection = minNode.getNext();
            
            // by first copying data over the minNode values
            minNode.setChild(firstChild.getChild());
            minNode.setData(firstChild.getData());
            minNode.setDegree(firstChild.getDegree());
            minNode.setNext(firstChild.getNext());
            
            // then connecting the last child to the previous next node
            lastChild.setNext(nodeAfterInsertedSection);
        }
        
        Map<Integer, BinomialNode> nodes = new HashMap<>();
        
        // merge trees here. Make sure to keep a reference to the original node
        // to prevent an infinite loop
        BinomialNode originalNode = minNode;
        
        BinomialNode currentNode = minNode.getNext();
        
        // run through the list and merge all of the possible trees together
        // TODO: FIX THIS LOGIC. The node that is beign combind should not be currentNode
        // it should be tracked separately.
        while (currentNode != originalNode)
        {
            while (nodes.containsKey(currentNode.getDegree()))
            {
                BinomialNode otherNode = nodes.get(currentNode.getDegree());
                
                BinomialNode master = null;
                BinomialNode slave = null;
                
                // Figure out the parent node and the child node
                if (currentNode.getData() > otherNode.getData())
                {
                    master = currentNode;
                    slave = otherNode;
                }
                else
                {
                    master = otherNode;
                    slave = currentNode;
                }
                
                // insert node into the child list
                // If null, the one node is the entire child list
                if (master.getChild() == null)
                {
                    master.setChild(slave);
                    slave.setNext(slave);
                }
                // if not null, insert after the first node since the order
                // doesnt matter and the min isn't kept track of
                else
                {
                    BinomialNode child = master.getChild();
                    slave.setNext(child.getNext());
                    child.setNext(slave);
                }
                
                // Increase the degree since another child tree was just added
                master.setDegree(master.getDegree() + 1);
            }
            
            nodes.put(currentNode.getDegree(), currentNode);
        }
        
        // iterate over the hashmap and serialize to a list
        //   while maintaining a min to use as the minNode element.
        
        return retval;
    }
}
