package com.sgmansfield.cop5536.one.heap.binomial;

import com.sgmansfield.cop5536.one.heap.Heap;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Implements the {@link Heap} interface using a Binomial Heap structure
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

        if (newNode.getData() < minNode.getData())
        {
            minNode = newNode;
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

        // to hold the trees while combining them. Maps degree to tree root.
        Map<Integer, BinomialNode> nodes = new HashMap<>();

        // merge trees here. Make sure to keep a reference to the original node
        // to prevent an infinite loop
        BinomialNode originalNode = minNode;
        BinomialNode currentNode = minNode.getNext();

        // run through the list and merge all of the possible trees together
        while (currentNode != originalNode)
        {
            BinomialNode combineRoot = currentNode.clone();

            while (nodes.containsKey(combineRoot.getDegree()))
            {
                BinomialNode otherNode = nodes.remove(combineRoot.getDegree());

                BinomialNode master = null;
                BinomialNode slave = null;

                // Figure out the parent node and the child node
                if (combineRoot.getData() < otherNode.getData())
                {
                    master = combineRoot;
                    slave = otherNode;
                }
                else
                {
                    master = otherNode;
                    slave = combineRoot;
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

                // reset for the loop condition
                combineRoot = master;
            }

            nodes.put(combineRoot.getDegree(), combineRoot);

            currentNode = currentNode.getNext();
        }

        // iterate over the hashmap and serialize to a list
        //   while maintaining a min to use as the minNode element.

        minNode = null;
        BinomialNode list = null;

        for (Map.Entry<Integer, BinomialNode> entry : nodes.entrySet())
        {
            BinomialNode value = entry.getValue();

            // the start of the process
            if (list == null)
            {
                // Set up a one element list and base the minNode
                list = value;
                list.setNext(list);

                minNode = list;

                continue;
            }

            // this is not the first time around.
            // insert the element into the list and update minNode if necessary
            value.setNext(list.getNext());
            list.setNext(value);

            if (value.getData() < minNode.getData())
            {
                minNode = value;
            }
        }

        // trees are combined and the minNode has been set. Time to return the
        // value retrieved at the very beginning.

        return retval;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("(BinomialHeap)\n");

        // Thee sentinel node marks the end of a level
        BinomialNode sentinel = new BinomialNode(-1, -1, null, null);
        int level = 1;

        // java doesnt have a "regular" queue so we have to use this rediculous one
        Queue<BinomialNode> q = new LinkedBlockingQueue<>();
        BinomialNode currentNode = null;
        boolean first = true;

        // seed the queue
        q.add(minNode);
        q.add(sentinel);

        // While no new nodes were added in the last round
        while (!(q.peek() == sentinel))
        {
            sb.append("Level ").append(level).append(": [");

            first = true;

            // Keep going until the level is cleared
            while ((currentNode = q.remove()) != sentinel)
            {
                BinomialNode firstNode = currentNode;
                BinomialNode loopNode = currentNode;

                // Each nodes is potentially a linked list of many nodes
                // So we need a while loop to traverse the list
                do
                {
                    // the first value printed per level is NOT preceeded by a comma
                    if (!first)
                    {
                        sb.append(", ");
                    }

                    first = false;

                    // Add the data to the output
                    sb.append(loopNode.getData());

                    // Add the child if there is one
                    if (loopNode.getChild() != null)
                    {
                        q.add(loopNode.getChild());
                    }

                    // maintain loop variables
                    loopNode = loopNode.getNext();
                }
                // loop until we get back to the start
                while (loopNode != firstNode);
            }

            sb.append("]\n");

            // Make sure to add the sentinel back in and maintain the level
            q.add(sentinel);
            level++;
        }

        return sb.toString();
    }
}
