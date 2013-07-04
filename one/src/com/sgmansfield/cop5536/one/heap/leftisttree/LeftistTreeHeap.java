package com.sgmansfield.cop5536.one.heap.leftisttree;

import com.sgmansfield.cop5536.one.heap.Heap;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author Scott
 */
public class LeftistTreeHeap implements Heap
{
    private LeftistNode root;

    @Override
    public void insert(int num)
    {
        // Create a single-node leftist tree and meld to insert
        LeftistNode nodeToInsert = new LeftistNode(num, null, null, 1);
        this.root = meld(this.root, nodeToInsert);
    }

    @Override
    public Integer deleteMin()
    {
        // If all the elements have been removed, there is nothing to return,
        if (root == null)
        {
            return null;
        }

        // Meld both subtrees to get the new tree, then return the former root
        int retval = this.root.getData();
        this.root = meld(root.getLeft(), root.getRight());
        return retval;
    }

    private static LeftistNode meld(LeftistNode node1, LeftistNode node2)
    {
        // First, take care of some base cases.
        // if both are null... well I guess the result is null
        // This implicitly takes care of the deleteMin case where the tree
        //   becomes empty.
        if (node1 == null &&
            node2 == null)
        {
            return null;
        }

        // if one is null and the other is not, return the not null one.
        // this is the base case for the recursion.
        if (node1 != null &&
            node2 == null)
        {
            return node1;
        }

        if (node1 == null &&
            node2 != null)
        {
            return node2;
        }

        // now for the meld operation
        // at this point both nodes should be not null
        LeftistNode master = null;
        LeftistNode slave = null;

        if (node1.getData() < node2.getData())
        {
            master = node1;
            slave = node2;
        }
        else
        {
            master = node2;
            slave = node1;
        }

        // The right subtree of node1 is melded with all of node2 and the
        //   result is reattached as the right subtree of node 1
        master.setRight(meld(master.getRight(), slave));

        // now the leftist tree properties have to be maintained.
        // First, check to see the subtree with the lesser right values
        //   is on the right. If not, swap.
        if ((master.getLeft() == null &&
             master.getRight() != null) ||
            (master.getLeft().getRightDistance() < master.getRight().getRightDistance()))
        {
            LeftistNode temp = master.getLeft();
            master.setLeft(master.getRight());
            master.setRight(temp);
        }

        // Second, make sure the right distance is correct
        if (master.getRight() == null)
        {
            master.setRightDistance(1);
        }
        else
        {
            master.setRightDistance(master.getRight().getRightDistance() + 1);
        }

        return master;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        LeftistNode sentinel = new LeftistNode(-1, null, null, -1);
        int level = 1;

        Queue<LeftistNode> q = new LinkedBlockingQueue<>();
        LeftistNode currentNode = null;
        boolean first = true;

        q.add(root);
        q.add(sentinel);

        while (!(q.peek() == sentinel))
        {
            sb.append("Level ").append(level).append(": [");

            first = true;

            while ((currentNode = q.remove()) != sentinel)
            {
                if (!first)
                {
                    sb.append(", ");
                }

                first = false;

                sb.append(currentNode.getData());

                if (currentNode.getLeft() != null)
                {
                    q.add(currentNode.getLeft());
                }

                if (currentNode.getRight() != null)
                {
                    q.add(currentNode.getRight());
                }
            }

            sb.append("]\n");

            q.add(sentinel);
            level++;
        }

        return sb.toString();
    }
}
