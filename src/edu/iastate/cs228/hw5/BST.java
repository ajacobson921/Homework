package edu.iastate.cs228.hw5;

/**
 *  
 * @author Aaron Jacobson ajacob1
 *
 */


import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ArrayList; 
import java.lang.IllegalArgumentException; 



/**
 * Binary search tree implementation
 */
public class BST<E extends Comparable<? super E>> extends AbstractSet<E>
{
	private Node<E> root;
	private int size;      
  
	private ArrayList<E>  preorderArr;	// stores the key values from a preorder traversal
	private ArrayList<E>  inorderArr;	// stores the key values from an inorder traversal
	private ArrayList<E>  postorderArr;	// stores the key values from a postorder traversal
  
  
	/*
	 * These tags will be set to false respectively at the ends of calls to traversePreorder(), 
	 * traverseInorder(), and traversePostorder(). They must be set back to true whenever
	 * the binary search tree is modified by add(), remove(), leftRotate(), and rightRotate(). 
	 */
	private boolean redoPreorder = true; 	
	private boolean redoInorder = true; 
	private boolean redoPostorder = true; 
  
	

	// ------------
	// Constructors
	// ------------
  
	/**
	 * Default constructor builds an empty tree. 
	 */
	public BST()
	{
		root = new Node<E>(null);
	}
	
	
	/**
	 * Constructor from an existing tree (manually set up for testing) 
	 * @param root
	 * @param size
	 */
	public BST(Node<E> root, int size) 
	{
		this.root = root;
		this.size = size;
		this.preorderArr = new ArrayList<E>();
		this.inorderArr = new ArrayList<E>();
		this.postorderArr = new ArrayList<E>();
	 }
  

	/**
	 * Constructor over an element array.  Elements must be inserted sequentially in order of 
	 * increasing index from the array.  
	 * 
	 * @param eleArray
	 */
	public BST(E[] eleArray)
	{
		root = new Node<E>(eleArray[0]);
		for(int i = 1; i < eleArray.length; i ++)
		{
			add(eleArray[i]);
		}
			
	}
 
  
	/**
	 * Copy constructor.  It takes a binary tree with a given root as input, and calls isBST() to check 
	 * if it is indeed a binary search tree. If not, throws a TreeStructureException with the message 
	 * "Copying a non-BST tree".  If so, makes a deep copy of the input tree such that the resulting BST
	 * assumes the same structure and has the same key stored at every corresponding node.  
	 * 
	 * @param rt  root of an existing binary tree 
	 */
	public BST(Node<E> root) throws TreeStructureException
	{
		if(!isBST(root))
		{
			throw new TreeStructureException("Copying a non-BST tree");
		}
		else
		{
			this.root = root;
			
		}
	}

	// -------
	// Getters
	// -------
  
	/**
	 * This function is here for grading purpose not as a good programming practice.
	 * @return root of the BST
	 */
	public Node<E> getRoot()
	{
		return root; 
	}
	
	public int size()
	{
		return size; 
	}
	
	/**
	 * 
	 * @return tree height 
	 */
	public int height()
	{
		int count = 0;
		Node<E> curs = root;
		while(curs.getLeft() != null || curs.getRight() != null)
		{
			count ++;
			if(curs.getLeft() == null)
			{
				curs = curs.getRight();
			}
			else if (curs.getRight() == null)
			{
				curs.getLeft();
			}
			else
				curs = curs.getRight(); 
		}
		return count; 
	}
	
	

	/**
	 * This method must be implemented by operating over the tree without using either of 
	 * the array lists preorderArr, inorderArr, and postorderArr. 
	 * 
	 * @return	the minimum element in the tree or null in the case of an empty tree 
	 */
	public E min()
	{
		if(size == 0)
		{
			return null;
		}
		Node<E> node = root;
		while(node.getLeft() != null)
		{
			node = node.getLeft();
		}
		return node.getData(); 
	}
	
	
	/**
	 * This method must be implemented by operating over the tree without using either of 
	 * the array lists preorderArr, inorderArr, and postorderArr. 
	 * 
	 * @return	the maximum element in the tree or null in the case of an empty tree 
	 */
	public E max()
	{
		if(size == 0)
		{
			return null;
		}
		Node<E> node = root;
		while(node.getRight() != null)
		{
			node = node.getRight();
		}
		return node.getData(); 
	}
	
	
	/**
	 * Calls traversePreorder() and copy the content of preorderArr to arr. 
	 * 
	 * @param arr array list to store the sequence 
	 */
	public void getPreorderSequence(ArrayList<E> arr)
	{
			String tmp = traversePreorder();
			arr = preorderArr;
	}
	

	/**
	 * Calls traverseInorder() and copy the content of inorderArr to arr. 
	 * 
	 * @param arr array list to store the sequence 
	 */
	public void getInorderSequence(ArrayList<E> arr)
	{
		String tmp = traverseInorder();
		arr = inorderArr;
	}
	
	
	/**
	 * Calls traversePostorder() and copy the content of postorderArr to arr. 
	 * 
	 * @param arr array list to store the sequence 
	 */
	public void getPostorderSequence(ArrayList<E> arr)
	{
		String tmp = traversePostorder();
		arr = postorderArr;
	}	
	
		
	
	// -----------
	// Comparators 
	// -----------
	
	/**
	 * Returns true if the tree and a second tree o have exactly the same structure, and equal 
	 * elements stored at every pair of corresponding nodes.  
	 */
	@Override
	public boolean equals(Object o) 
	{
		BST b = (BST) o;
		if(this == b) return true;
		ArrayList<E> thisTree = new ArrayList<E>();
		ArrayList<E> thatTree = new ArrayList<E>();
		if(isBST(b.root))
		{
			this.getInorderSequence(thisTree);
			b.getInorderSequence(thatTree);
			if(thisTree.size() != thatTree.size())
			{
				return false;
			}
			else
			{
				for(int i = 0; i < thisTree.size(); i++)
				{
					if(thisTree.get(i) != thatTree.get(i))
					{
						return false;
					}
				}
				return true;
			}
		}
		else
			return false; 
	}
	
	
	
	/** 
	 * Returns true if two binary search trees store the same set of elements, and false otherwise.   
	 * The tree rooted at tree is also a binary search tree.   
	 *    
	 * @param rt
	 * @return
	 */
	public boolean setEquals(BST<E> tree)
	{
		if(tree == this)
		{
			return true;
		}
		ArrayList<E> list = new ArrayList<E>();
		ArrayList<E> list2 = new ArrayList<E>();
		getInorderSequence(list);
		tree.getInorderSequence(list2);
		for(int i = 0; i < list.size(); i++)
		{
			boolean found = false;
			for(int j = 0; j < list2.size(); j++)
			{
				if(list.get(i) == list2.get(j))
				{
					found = true;
				}
			}
			if(!found)
			{
				return false;
			}
		}
		return true; 
	}

	
	
	// ----------
	// Traversals
	// ----------
	
	/**
	 * Performs a preorder traversal of the tree, stores the result in the array list preOrderArr, and also 
	 * write the key values to a string in which they are separated by blanks (exactly one blank  
	 * between two adjacent key values). 
	 *  
	 * No need to perform the traversal if redoPreorder == false. 
	 */
	public String traversePreorder()
	{
		ArrayList<E> list = new ArrayList<E>();
		if(redoPreorder)
		{
			preOrder(this.root, list);
		}
		preorderArr = list;
		String tmp = "";
		for(int i = 0; i < list.size(); i++)
		{
			tmp += list.get(i).toString() + " ";
		}
		
		redoPreorder = false;
		return tmp; 
	}
  
	/**
	 * Recursive preorder algorithm
	 * @param root
	 * @param list
	 */
	private void preOrder(Node<E> root, ArrayList<E> list)
	{
		
		if(root == null) return;
		list.add(root.getData());
		preOrder(root.getLeft(), list);
		preOrder(root.getRight(), list);
	}

  
	/**
	 * Performs an inorder traversal of the tree, and stores the result in the array list inOrderArr. 
	 * No need to perform the traversal if redoInorder == false. 
	 */
	public String traverseInorder()
	{
		ArrayList<E> list = new ArrayList<E>();
		if(redoInorder)
		{
			inOrder(this.root, list);
		}
		inorderArr = list;
		String tmp = "";
		for(int i = 0; i < list.size(); i++)
		{
			tmp += list.get(i).toString() + " ";
		}
		
		redoInorder = false;
		return tmp; 
	}  
	
	/**
	 * Recursive inorder algorithm
	 * @param root
	 * @param list
	 */
	private void inOrder(Node<E> root, ArrayList<E> list)
	{
		
		if(root == null) return;
		inOrder(root.getLeft(), list);
		list.add(root.getData());
		inOrder(root.getRight(), list);
	}

  
  
	/**
	 * Performs a postorder traversal of the tree, and stores the result in the array list preOrderArr. 
	 * No need to perform the traversal if redoPostorder == false. 
	 */   
	public String traversePostorder()
	{
		ArrayList<E> list = new ArrayList<E>();
		if(redoPostorder)
		{
			postOrder(this.root, list);
		}
		postorderArr = list;
		String tmp = "";
		for(int i = 0; i < list.size(); i++)
		{
			tmp += list.get(i).toString() + " ";
		}
		
		redoPostorder = false;
		return tmp; 
	}
	
	/**
	 * Recursive postorder algorithm
	 * @param root
	 * @param list
	 */
	private void postOrder(Node<E> root, ArrayList<E> list)
	{
		
		if(root == null) return;
		postOrder(root.getLeft(), list);
		postOrder(root.getRight(), list);
		list.add(root.getData());
	}

	
  
	// -------------
	// Query Methods
	// -------------
	
	/**
	 * Returns the number of keys with values >= minValue and <= maxValue, and stores these key values 
	 * in the array eleArray[] in the increasing order.  Note that minValue and maxValue may not be any
	 * of the key values stored in the tree. 
	 * 
	 * Exception is thrown if minValue > maxValue. 
	 *  
	 * @param minValue	lower bound for query values 
	 * @param maxValue  upper bound for query values 
	 * @param eleArray	stores elements >= minValue and <= maxValue 
	 * @return			number of elements in the interval [minValue, maxValue]
	 * @throws TreeStructureException 
	 */
	public int rangeQuery(E minValue, E maxValue, E[] eleArray) throws IllegalArgumentException, TreeStructureException 
	{
		if(minValue.compareTo(maxValue) >= 0)
		{
			throw new IllegalArgumentException();
		}
		rangeIter(root,  minValue, maxValue, eleArray, 0);
		TreeSort sorter = new TreeSort();
		sorter.sort(eleArray);
		return eleArray.length; 
	}
	
	
	/**
	 * Recursive range data-finder
	 * @param root
	 * @param minValue
	 * @param maxValue
	 * @param eleArray
	 * @param arrSpot
	 */
	private void rangeIter(Node<E> root, E minValue, E maxValue, E[] eleArray, int arrSpot)
	{
		if(root == null) return;
		boolean full = true;
		for(int j = 0; j < eleArray.length; j++)
		{
			if(eleArray[j] == null)
			{
				full = false;
			}
		}
		if(full)
		{
			eleArray = Arrays.copyOf(eleArray, eleArray.length * 2);
		}	
		if(root.getData().compareTo(minValue) >= 0 && root.getData().compareTo(maxValue) <= 0)
		{
			eleArray[arrSpot] = root.getData();
			arrSpot ++;
		}
		rangeIter(root.getLeft(), minValue, maxValue, eleArray, arrSpot);
		rangeIter(root.getRight(), minValue, maxValue, eleArray, arrSpot);
	}
	
	
	/**
	 * Get the keys that are between the imin-th and the imax-th positions from an inorder traversal. 
	 * The first visited node is at position 0.  Store these keys in eleArray[] in their original order. 
	 * 
	 * Exception is thrown if 1) imax < imin, 2) imin < 0, or 3) imax >= size. 
	 * 
	 * @param imin			minimum index of the keys to be searched for according to inorder
	 * @param imax			maximum index of the keys to be searched for according to inorder
	 * @param eleArray		stores the found keys 
	 * @return
	 */
	public void orderQuery(int imin, int imax, E[] eleArray) throws IllegalArgumentException 
	{
		if(imax < imin) throw new IllegalArgumentException();
		if(imin < 0) throw new IllegalArgumentException();
		if(imax > size) throw new IllegalArgumentException();
		
		
		
		
		
		
	}

	
    
	// --------------------------
	// Operations related to Keys
	// --------------------------
  
	@Override
	public boolean contains(Object obj)
	{
		E data = (E) obj;
		String key = data.toString();
		String order = traverseInorder();
		if(order.contains(key))
		{
			return true;
		}
		else
			return false; 
	}
  

	@Override
	public boolean add(E key)
	{
		Node<E> curs = root;
		boolean left = false;

		while(curs != null)
		{
			if(key.compareTo(curs.getData()) == -1)
			{
				curs = curs.getLeft();
				left = true;
			}
			else
				{
					curs = curs.getRight();
					left = false;
				}
		}
		Node<E> add = new Node<E>(key);
		if(left)
		{
			curs.getParent().setLeft(add);
			add.setParent(curs.getParent());
		}
		else
		{
			curs.getParent().setRight(add);
			add.setParent(curs.getParent());
		}
		size++;
		redoPreorder = false;
		redoInorder = false;
		redoPostorder = false;
		return true; 
	}
  
	@Override
	public boolean remove(Object obj)
	{
		E data = (E) obj;
		Node toR = findEntry(data);
		unlinkNode(toR);
		redoPreorder = true;
		redoInorder = true;
		redoPostorder = true;
		size--;
		return true; 
	}
  
  
	/**
	 * Returns the node containing key, or null if the key is not
	 * found in the tree.
	 * @param key
	 * @return the node containing key, or null if not found
	 */
	protected Node<E> findEntry(E key)
	{
		Node<E> ret = null;
		find(key, root) ;
		
		return ret; 
	}
	
	private void find(E key, Node<E> node)
	{
		if(node.getData() == key || node.getData() == null) return;
		else
		{ 
			find(key, node.getLeft());
			find(key,node.getRight());
		}	 
	}
  
	// -------------------
	// Operations on Nodes
	// -------------------

	/**
	 * Returns the successor of the given node.
	 * @param n
	 * @return the successor of the given node in this tree, 
	 *   or null if there is no successor
	 */
	protected Node<E> successor(Node<E> n)
	{
		E tmp = null;
		traverseInorder();
		int i = 0;
		boolean found = false;
		while(i < inorderArr.size() && !found)
		{
			if(inorderArr.get(i) == n.getData())
			{
				found = true;
				tmp = inorderArr.get(i + 1);
			}
		}
		Node<E> find = findEntry(tmp);
		return find; 
	}
  

	/**
	 * Returns the predecessor of a node.
	 * @param n
	 * @return the predecessor of the given node in this tree, 
	 *   or null if there is no successor
	 */
	public Node<E> predecessor(Node<E> n)
	{
		E tmp = null;
		traverseInorder();
		int i = 0;
		boolean found = false;
		while(i < inorderArr.size() && !found)
		{
			if(inorderArr.get(i) == n.getData())
			{
				found = true;
				tmp = inorderArr.get(i - 1);
			}
		}
		Node<E> find = findEntry(tmp);
		return find; 
	}

	
	/**
	 * Performs left rotation on a node 
	 * Reset tags redoPreorder, redoInorder, redoPostorder 
	 * 
	 * @param n
	 */
	public void leftRotate(Node<E> n)
	{
		Node<E> big = n.getRight();
		n.setParent(big);
		n.setRight(big.getLeft());
		big.setLeft(n);
		redoPreorder = true;
		redoInorder = true;
		redoPostorder = true;
	}
  
	/**
	 * Performs right rotation on a node 
	 * Reset tags redoPreorder, redoInorder, redoPostorder 
	 * 
	 * @param n
	 */
	public void rightRotate(Node<E> n)
	{
		
		Node<E> little = n.getLeft();
		n.setParent(little);
		n.setLeft(little.getRight());
		little.setRight(n);
		redoPreorder = true;
		redoInorder = true;
		redoPostorder = true;
	}
  
		
  
	/**
	 * Removes the given node, preserving the binary search
	 * tree property of the tree.
	 * @param n node to be removed
	 */
	protected void unlinkNode(Node<E> n)
	{
		boolean left = false;
		if(n.getParent().getLeft() == n)
		{
			left = true;
		}
		if(left)
		{
			n.getParent().setLeft(n.getRight());
		}
		else
		{
			n.getParent().setRight(n.getLeft());
		}
		n.setData(null);
		n.setLeft(null);
		n.setParent(null);
		n.setRight(null);
	}
	

	
	// -------------
	// String output
	// -------------
 
	/**
	 * Returns a representation of this tree as a multi-line string.
	 * The tree is drawn with the root at the left and children are
	 * shown top-to-bottom.  Leaves are marked with a "-" and non-leaves
	 * are marked with a "+".
	 */
	@Override
	public String toString()
	{
		String tmp = "";
		Node<E> curs = root;
		while(curs.getLeft() != null || curs.getRight() != null)
		{
			tmp += curs.getData().toString()+ "+\r\n" + print(tmp, curs.getLeft()) + "\r\n" + print(tmp, curs.getRight());
		}
		return tmp; 
	}
	
	private String print(String t, Node<E> node)
	{
		if(node.getRight() == null || node.getLeft() == null)
			{
				t += "-" + node.getData().toString();
				return t;
			}
		else
		{
			return t += "+" + print(t, node.getLeft()) + "\r\n" + "+" + print(t, node.getRight())+ "\r\n";
		}
		
		
	}
 
	/**
	 * Iterator implementation is from BSTSet.java and thus not required here. 
	 */	
	@Override
	public Iterator<E> iterator()
	{
	    return null;
	}

	/**
	 * Checks if the tree with a given root is a binary search tree. 
	 * @param rt
	 */
	private boolean isBST(Node<E> root)
	{
		Node<E> curs = root;
		boolean isL = isBST(curs.getLeft());
		
		boolean isR = isBST(curs.getRight());
		
		boolean toL = true;
		boolean toR = true;
		if(curs.getData().compareTo(curs.getLeft().getData()) != -1)
		{
			toL = false;
		}
		else if(curs.getData().compareTo(curs.getRight().getData()) != 1)
		{
			toR = false;
		}
		if(!toL || !toR || !isR || !isL)
		{
			return false;
		}
		else
		{
			return true;
		}
	}  
}