package edu.iastate.cs228.hw5;

/**
 * 
 * @author Aaron Jacobson ajacob1
 *
 */


/**
 * 
 * This class represents a tree node.  The class is public but in the BST class 
 * the root node will be protected. 
 *
 */

// also okay to use the following. 
//public class Node<E extends Comparable<? super E>>
public class Node<E> 
{
	private E data; 
	private Node<E> parent; 
	private Node<E> left; 
	private Node<E> right; 
	
	
	public Node(E dat)
	{
		data = dat;
		left = null;
		right = null;
		parent = null;
	}

	public Node(E dat, Node<E> left, Node<E> right)
	{
		data = dat;
		this.left = left;
		this.right = right;
		parent = null;
	}
	
	/**
	 * Write the value of the instance variable named data.
	 */
	public String toString()
	{
		String ret = data.toString();
		return ret; 
	}

	public E getData()
	{
		return data; 
	}
	
	public void setData(E dat)
	{
		data = dat;
	}
	
	public Node<E> getParent()
	{	
		return parent; 
	}
	
	public void setParent(Node<E> parent)
	{
		this.parent = parent;
	}
	
	public Node<E> getLeft()
	{
		return left; 
	}
	
	public void setLeft(Node<E> left)
	{
		this.left = left;
	}
	
	public Node<E> getRight()
	{
		return right; 
	}
	
	public void setRight(Node<E> right)
	{
		this.right = right;
	}
	
//	public int compareTo(Node<E> comp)
//	{
//		if(comp == this)
//		{
//			return 0;
//		}
//		if(this.getData().compareTo(comp.getData()) == -1)
//		{
//			return -1;
//		}
//		else if(this.getData().compareTo(comp.getData()) == 0)
//		{
//			return 0;
//		}
//		else
//			return 1;
//	}
	
	
}
