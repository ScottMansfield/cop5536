package com.sgmansfield.cop5536.one.heap.binomial;

/**
 *
 * @author Scott
 */
public class BinomialNode
{
    private int data;

    public int getData()
    {
        return data;
    }

    public void setData(int data)
    {
        this.data = data;
    }

    private int degree;

    public int getDegree()
    {
        return degree;
    }

    public void setDegree(int degree)
    {
        this.degree = degree;
    }

    private BinomialNode next;

    public BinomialNode getNext()
    {
        return next;
    }

    public void setNext(BinomialNode next)
    {
        this.next = next;
    }

    private BinomialNode child;

    public BinomialNode getChild()
    {
        return child;
    }

    public void setChild(BinomialNode child)
    {
        this.child = child;
    }

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
