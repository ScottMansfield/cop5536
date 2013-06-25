package com.sgmansfield.cop5536.one.heap.leftisttree;

/**
 *
 * @author Scott
 */
public class LeftistNode
{
    private int data;
    
    /**
     * Gets the data value for this node
     *
     * @return the data value for this node
     */
    public int getData()
    {
        return data;
    }
    
    private LeftistNode right;
    
    /**
     * Retrieves the right child node of this node.
     * 
     * @return the right child
     */
    public LeftistNode getRight()
    {
        return right;
    }

    /**
     * Sets the right node of this node.
     *
     * @param right the node that will become the right node of this node.
     */
    public void setRight(LeftistNode right)
    {
        this.right = right;
    }

    private LeftistNode left;
    
    /**
     * Retrieves the left child node of this node.
     *
     * @return the left child
     */
    public LeftistNode getLeft()
    {
        return left;
    }

    /**
     * Sets the left node of this node.
     *
     * @param left the node that will become the left node of this node.
     */
    public void setLeft(LeftistNode left)
    {
        this.left = left;
    }

    private int rightDistance;
    
    /**
     * The distance (going to the right only) to an external node.
     *
     * @return The distance to an external node
     */
    public int getRightDistance()
    {
        return rightDistance;
    }

    /**
     * Sets the distance from this node (going to the right only) to an external node.
     *
     * @param rightDistance the distance to an external node
     */
    public void setRightDistance(int rightDistance)
    {
        this.rightDistance = rightDistance;
    }

    /**
     * Constructs a new LeftistNode with the given right and left children and right distance
     *
     * @param data the data value for this node
     * @param right the right child of this node.
     * @param left the left child of this node.
     * @param rightDistance the distance to an external node
     */
    public LeftistNode(int data, LeftistNode right, LeftistNode left, int rightDistance)
    {
        this.data = data;
        this.right = right;
        this.left = left;
        this.rightDistance = rightDistance;
    }
}
