package edu.iastate.cs228.hw5;

import java.util.ArrayList;

/**
 *  
 * @author Aaron Jacobson ajacob1
 *
 */


public class TreeSort<E extends Comparable<? super E>>
{
	private Node<E> cursor;
	
	/**
	 * Sorts an input array by first creating a binary search tree to store its elements, and then perform an 
	 * inorder traversal of the tree to visit the elements in order and put them back in the array. 
	 * @param eleArray
	 * @throws TreeStructureException 
	 */
	public static <E> void sort(E[] eleArray) throws TreeStructureException
	{
		Node<E> root = new Node<E>(eleArray[0]);
		BST b = new BST(root);
		for(int i = 1; i < eleArray.length; i++)
		{
			b.add(new Node(eleArray[i]));
		}
		ArrayList<E> list = new ArrayList<E>();
		b.getInorderSequence(list);
		for(int i = 0; i < list.size(); i ++)
		{
			eleArray[i] = list.get(i);
		}
	}
}
