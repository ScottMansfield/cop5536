package com.sgmansfield.cop5536.one.heap.binomial;

/**
 * The node used in a Binomial Heap. It keeps track of the data, degree, next node, and child node.
 *
 * @author Scott
 */
public class BinomialNode
{
    private int data;

    /**
     * Gets the data held by this node.
     *
     * @return The data held by this node.
     */
    public int getData()
    {
        return data;
    }

    /**
     * Sets the data held by this node.
     *
     * @param data The data to hold in this node.
     */
    public void setData(int data)
    {
        this.data = data;
    }

    private int degree;

    /**
     * Gets the degree of this node
     *
     * @return The degree of this node.
     */
    public int getDegree()
    {
        return degree;
    }

    /**
     * Sets the degree of this node.
     * The data is mutable because of the structure of the BinomialHeap.
     * When a node is deleted, the data from the next is copied into the previous
     * to avoid a traversal of the entire list.
     *
     * @param degree The new degree of this node.
     */
    public void setDegree(int degree)
    {
        this.degree = degree;
    }

    private BinomialNode next;

    /**
     * Gets the next BinmialNode in the linked list.
     *
     * @return The next node in the linked list
     */
    public BinomialNode getNext()
    {
        return next;
    }

    /**
     * Sets the next node in the linked list
     *
     * @param next The next node in the linked list
     */
    public void setNext(BinomialNode next)
    {
        this.next = next;
    }

    private BinomialNode child;

    /**
     * Gets the first node of the linked list of children of this node
     *
     * @return The first node of the linked list of children of this node
     */
    public BinomialNode getChild()
    {
        return child;
    }

    /**
     * Sets first node of the linked list of children of this node
     *
     * @param child The new first node of the linked list of children of this node
     */
    public void setChild(BinomialNode child)
    {
        this.child = child;
    }

    /**
     * Creates a new BinomialNode with the given data, degree, next node, and child list pointer.
     *
     * @param data The data held in this node
     * @param degree The degree of this node
     * @param next The next node in the linked list of nodes
     * @param child The first node of the list of children of this node.
     */
    public BinomialNode(int data, int degree, BinomialNode next, BinomialNode child)
    {
        this.data = data;
        this.degree = degree;
        this.next = next;
        this.child = child;
    }

    @Override
    public BinomialNode clone()
    {
        return new BinomialNode(this.getData(), this.getDegree(), this.getNext(), this.getChild());
    }
}
