package edu.iastate.cs228.hw3;

import java.util.AbstractSequentialList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;



/**
 * A doubly linked list that stores data in Nodes with varying size arrays as
 * the backing store.
 * Important Note: Your index-based methods remove(int pos), add(int pos, E
 * item) and listIterator(int pos) must not traverse every element in order to
 * find the node and offset for a given index pos (see spec for more details)
 */

public class DoublingList<E> extends AbstractSequentialList<E> {

	/**
	 * Node to keep track of the head (beginning of the list)
	 */
	private Node<E> head;

	/**
	 * Node to keep track of the tail (end of the list)
	 */
	private Node<E> tail;
	
	/**
	 * The number of actual nodes in the list
	 */
	private int numNodes;
	
	/**
	 * A count of the number of data elements in the list
	 */
	private int size;
	
	/**
	 * The total capacity available for the elements;
	 */
	private int totalCap = (int) Math.pow(2, numNodes) - 1;

	
	/**
	 * Constructs an empty DoublingList
	 */
	public DoublingList() 
	{
		head = new Node<E>(null);
		tail = new Node<E>(null, head, null);
		Object[] obj = new Object[1];
		E[] data = (E[]) obj;
		Node<E> first = new Node<E>(tail, head, data);
		tail.setPrev(first);
		head.setNext(first);
		size = 0;
		numNodes = 1;
	}
	
	/**
	 * A constructor to be called by the tests when it's necessary to manually create
	 * the internal structure of the list. 
	 * 
	 * NOTE: In real life you would never have this constructor. It is simply used so
	 * we can test your remove methods without relying on the add methods working properly
	 * 
	 * @param head
	 * @param tail
	 * @param numNodes
	 * @param size
	 */
	public DoublingList(Node<E> head, Node<E> tail, int numNodes, int size) {
		this.head = head;
		this.tail = tail;
		this.numNodes = numNodes;
		this.size = size;
		
		/*
		 * TODO any additional initialization code you need.
		 * It is not necessarily the case that you need anything here. 
		 */
	}
	
	/**
	 * Returns the head node of the list.
	 * 
	 *  NOTE: Again, in real life you would never have this method. It is just used 
	 *  in the tests so we don't need to rely on your get method.
	 * @return
	 * 		the head node of the list
	 */
	public Node<E> getHeadNode() {
		return head;
	}
	
	/**
	 * Returns the tail node of the list.
	 * 
	 *  NOTE: Again, in real life you would never have this method. It is just used 
	 *  in the tests so we don't need to rely on your get method.
	 * @return
	 * 		the tail node of the list
	 */
	public Node<E> getTailNode() {
		return tail;
	}
	
	/**
	 * Removes the element with the given logical position, following the rules
	 * for removing an element.
	 */
	@Override
	public E remove(int pos) {
		NodeInfo info = find(pos);
		E data = info.node.getData()[info.offset];
		info.node.getData()[info.offset] = null;
		size --;
		if(binaryBreakdown(size) < binaryBreakdown(totalCap))
		{
			//copy all the data items into one big array
			Object[] obj = new Object[size];
			E[] copy = (E[]) obj;
			Node<E> curs = head.getNext();
			int count = 0;
			//add them to the big array
			while(curs.getNext().getData() != null)
			{
				for(int i = 0; i < curs.getData().length; i++)
				{
					if(curs.getData()[i] != null)
					{
						copy[count] = curs.getData()[i];
						count++;
						curs.getData()[i] = null;
					}
				}
				curs = curs.getNext();
			}
			// redirect the nodes and set the head and tail of the node to null (remove it)
			curs.getPrev().setNext(tail);
			curs.getNext().setPrev(curs.getPrev());
			curs.setNext(null);
			curs.setPrev(null);
			numNodes--;
			
			//add all the data items in the big array back into the list
			curs = head.getNext();
			count = 0;
			while(curs.getNext().getData() != null)
			{
					for(int i = 0; i < curs.getData().length; i++)
					{
						curs.getData()[i] = copy[count];
						count ++;
					}
					curs = curs.getNext();	
			}
		}
		return data;
	}

	/**
	 * Adds the given item to have the given logical position. Adds a new Node
	 * if necessary. Follows the rules stated by leftward and rightward shift.
	 */
	@Override
	public void add(int pos, E item) {
		if(item == null) {throw new NullPointerException();}
		//check if there's space. if not, add an array and update the capacity.
		if(size ==  0)
		{
			head.getNext().getData()[0] = item;
			size++;
		}
		else if(size >= totalCap)
		{
			NodeInfo end = find(totalCap -1);
			int sizeOfArray = end.node.getData().length * 2;
			Object[] objR = new Object[sizeOfArray];
			E[] data = (E[])  objR;
			Node<E> newN = new Node<E>(getTailNode(),end.node, data);
			end.node.setNext(newN);
			numNodes++;
		}
		//find the logical position in the arrays.
		NodeInfo toAdd = find(pos);
		if(toAdd.node.getData()[toAdd.offset] == null)
		{
				toAdd.node.getData()[toAdd.offset] = item;
				size++;
		}

		//if not, check for space to the left and then left shift
		else if(isSpaceLeft(toAdd) != null)
		{
			leftShift(isSpaceLeft(toAdd), toAdd);
			toAdd.node.getData()[toAdd.offset] = item;
			size ++;
		}
		else if(isSpaceRight(toAdd) != null)//if there's not space to the left, check for right space and shift 
		{
			rightShift(isSpaceRight(toAdd), toAdd);
			toAdd.node.getData()[toAdd.offset] = item;
			size++;
		}
		
	}
	

	/**
	 * Shifts all data to the left until it hits the passed in stopping point.
	 * @param info The empty space to the left
	 * @param stop The stopping point
	 */
	private void leftShift(NodeInfo info, NodeInfo stop)
	{
		int pos = info.offset;
		
		while(info != stop)
		 {
			 while(pos < info.node.getData().length -1)
			 { 
				 if(pos == 0)
				 {
					 info.node.getPrev().getData()[info.node.getPrev().getData().length - 1] = info.node.getData()[pos];
				 }
				 info.node.getData()[pos]  = info.node.getData()[pos + 1];	
				 pos++;
			 }
			 NodeInfo newIn = new NodeInfo(info.node.getNext(), 0);
			 leftShift(newIn, stop);
		 }
	}

	/**
	 * Shifts all data items to the right by one space until it hits the passed in stopping point.
	 * @param space The empty space that everything is shifted to.
	 * @param stop Where the shifting needs to stop. 
	 */
	private void rightShift(NodeInfo space, NodeInfo stop)
	{
		int pos = space.offset;
		while(space != stop)
		 {
			 while(pos > 0)
			 { 
				 if(pos == space.node.getData().length -1 )
				 {
					 space.node.getNext().getData()[0] = space.node.getData()[space.node.getData().length - 1];
				 }
				 space.node.getData()[pos]  = space.node.getData()[pos - 1];	
				 pos--;
			 }
			 NodeInfo newIn = new NodeInfo(space.node.getPrev(), space.node.getPrev().getData().length -1);
			 rightShift(newIn, stop);
		 }
	}
	
	/**
	 * Adds the given item to the end of the list. Creates a new Node if
	 * Necessary. Return true if the add was successful, false otherwise.
	 * 
	 * @throws NullPointerException 
	 * 				If the item is null.
	 */
	@Override
	public boolean add(E item) throws NullPointerException
	{
		if(item == null) throw new NullPointerException();
		add(size, item);
		return true;
	}

	/**
	 * Returns a ListIterator for this DoublingList at the given position (I.E.
	 * a call to next should return the element with the logical index equal to
	 * the index given)
	 */
	@Override
	public ListIterator<E> listIterator(int index) 
	{
		DoublingListIterator dListIter = new DoublingListIterator(index);
		return dListIter;
	}

	/**
	 * Returns a ListIterator for this DoublingList starting from the beginning
	 */
	@Override
	public ListIterator<E> listIterator() 
	{
		DoublingListIterator dListIter = new DoublingListIterator();
		return dListIter;
	}

	/**
	 * Returns an Iterator for this DoublingList starting from the beginning
	 */
	@Override
	public Iterator<E> iterator() {
		Iterator<E> iterator = new DoublingIterator();

		return iterator;
	}

	/**
	 * Returns the size of the list. It is acceptable to use the size instance
	 * variable and update it during add / remove so you can just return that
	 * variable here.
	 */
	@Override
	public int size() 
	{
		return size;
	}

	/**
	 *  Finds the node and offset in the node's data array of the data at logical position pos.
	 * @param pos
	 * @return
	 */
	private NodeInfo find(int pos)
	{
		Node<E> curs = head.getNext();
		int found = -1;
		while(found<pos)
		{
			found+= curs.getSize();
			if(found>=pos)
			{
				break;
			}	
			else
			{
				curs = curs.getNext();
			}
		}
		found -= curs.getSize();
		int index=0;
		while(found<pos)
		{
			if(curs.getData()[index]!=null)
			{
				found++;
			}
			
			if(found>=pos)
			{
				break;
			}
			index++;
		}
		NodeInfo nodeInfoOfPos = new NodeInfo(curs, index);
		return nodeInfoOfPos;
		
	}
	
	public int binaryBreakdown(int b)
	{
		int toRet = 0;
		while(b > 0)
		{
			b = b/2;
			toRet++;
		}
		return toRet;
	}
	
	/**
	 * Checks for a null space, starting at the passed in starting point and moving left
	 * @param start The starting space. In the add method, this is the space you want to add the item.
	 * @return the NodeInfo of the null space. Returns null is there is no null space.
	 */
	private NodeInfo isSpaceLeft(NodeInfo start)
	{
		NodeInfo empty = null;
		Node<E> trav = head.getNext();
		int nextC = 0;
		while(trav != start.node && nextC != start.offset)
		{
			for(int i = 0; i < trav.getData().length; i++)
			{
				if(trav.getData()[i] == null)
				{
					empty = new NodeInfo(trav, i);
				}
			}
			
			trav = trav.getNext();
		}
		return empty;
	}
	
	/**
	 * Searches to the right of the passed in spot for a null space.
	 * @param start Starting spot of the search. In add method, this is the space you want to add an item.
	 * @return The NodeInfo of the null space. Returns null if there is no right space.
	 */
	private NodeInfo isSpaceRight(NodeInfo start)
	{
		NodeInfo empty = null;
		int curs = start.offset;
		Node<E> cursor = start.node;
		while(cursor.getNext() != getTailNode())
		{
			for(int i = curs; i < cursor.getData().length; i++)
			{
				if(start.node.getData()[i] == null)
				{
					NodeInfo toRet = new NodeInfo(start.node, i);
					return toRet;
				}
			}
			cursor = cursor.getNext();
			curs = 0;
		}
		return null;
	}
	
	/**
	 * ListIterator class. Please reference the ListIterator API to see how
	 * methods handle errors (no next element, null arguments, etc.)
	 * 
	 * API: http://docs.oracle.com/javase/6/docs/api/java/util/ListIterator.html
	 */
	private class DoublingListIterator implements ListIterator<E> {
		
		
		/**
		 * True if next() was called
		 */
		private boolean nextCalled;
		
		/**
		 * True if previous() was called. Should be opposite nextCalled.
		 */
		private boolean prevCalled;
		
		/**
		 * The position in the list
		 */
		private int position;
		
		/**
		 * True if set(), add(), or remove() were just called. 
		 */
		private boolean setAddRevCall;
		
		/**
		 * The last NodeInfo returned by next or previous.
		 */
		private NodeInfo lastCalled;
		
		
		
		/**
		 * Creates a DoublingListIterator at the beginning of the list
		 */
		public DoublingListIterator()
		{
			position = -1;
			nextCalled = false;
			prevCalled = false;
			setAddRevCall = false;
			lastCalled = find(0);
			
		}
		
		/**
		 * Creates a DoublingListIterator at the given index.
		 * @param index
		 */
		public DoublingListIterator(int index)
		{
			position = index;
			nextCalled = false;
			prevCalled = false;
			lastCalled = find(index);
			setAddRevCall = false;
		}
		
		/**
		 * Adds the given element to the DoublingList following the rules of
		 * add(). DO NOT call the add method you wrote for DoublingList above!
		 * This one needs to run in AMORTIZED O(1) (constant time).
		 */
		@Override
		public void add(E data) 
		{
			if(setAddRevCall)
			{
				throw new IllegalStateException("Whoops. You can't call add, remove or set twice in a row");
			}
			if(data == null) {throw new NullPointerException();}
			
			//check if there's space. if not, add a Node and an array and update the capacity.
			if(size >= totalCap)
			{
				NodeInfo end = find(totalCap -1);
				int sizeOfArray = end.node.getData().length * 2;
				Object[] objR = new Object[sizeOfArray];
				E[] newData = (E[])  objR;
				Node<E> newN = new Node<E>(null,end.node, newData);
				end.node.getPrev().setNext(newN);
				totalCap += sizeOfArray;
				numNodes++;
			}
			//find the logical position in the arrays.
			NodeInfo toAdd = find(position);

			//if not, check for space to the left and then left shift
			if(isSpaceLeft(toAdd) != null)
			{
				leftShift(isSpaceLeft(toAdd), toAdd);
				toAdd.node.getData()[toAdd.offset] = data;
				size ++;
			}
			//if there's not space to the left, check for right space and shift 
			else
			{
				position++;
				rightShift(isSpaceRight(toAdd), toAdd);
				toAdd.node.getData()[toAdd.offset] = data;
				size++;
			}
			setAddRevCall = true;
		}

		/**
		 * Returns true if this list iterator has more elements when traversing
		 * the list in the forward direction. (In other words, returns true if
		 * next would return an element rather than throwing an exception.)
		 */

		@Override
		public boolean hasNext() {
			if(lastCalled.node.getNext().getData() == null)
			{
				return false;
			}
			for(int i = lastCalled.offset; i < lastCalled.node.getData().length; i++)
			{
				if(lastCalled.node.getData()[i] != null)
				{
					return true;
				}
			}
			Node<E> cursor = lastCalled.node;
			int i = 0;
			while(lastCalled.node.getNext().getData() != null)
			{
				while(i < cursor.getData().length)
				{
					if(cursor.getData()[i] != null)
					{
						return true;
					}
					i++;
				}
				i = 0;
				cursor = cursor.getNext();
			}
			return false;
			
		}

		/**
		 * Returns true if this list iterator has more elements when traversing
		 * the list in the reverse direction. (In other words, returns true if
		 * previous would return an element rather than throwing an exception.)
		 */

		@Override
		public boolean hasPrevious() {
			if(lastCalled.node.getPrev().getData() == null)
			{
				return false;
			}
			for(int i = lastCalled.offset; i > 0; i--)
			{
				if(lastCalled.node.getData()[i] != null)
				{
					return true;
				}
			}
			Node<E> cursor = lastCalled.node;
			int i = 0;
			while(lastCalled.node.getPrev().getData() != null)
			{
				while(i > 0)
				{
					if(cursor.getData()[i] != null)
					{
						return true;
					}
					i++;
				}
				cursor = cursor.getNext();
				i = cursor.getData().length-1;
			}
			return false;
		}

		/**
		 * Returns the next element in the list. This method may be called
		 * repeatedly to iterate through the list, or intermixed with calls to
		 * previous to go back and forth. (Note that alternating calls to next
		 * and previous will return the same element repeatedly.)
		 */
		@Override
		public E next() throws NoSuchElementException 
		{
			if(prevCalled)
			{
				return lastCalled.node.getData()[lastCalled.offset];
			}
			if(!hasNext())
			{
				throw new NoSuchElementException();
			}
			NodeInfo toCall = find(position+1);
			position++;
			nextCalled = true;
			prevCalled = false;
			setAddRevCall = false;
			lastCalled = toCall;
			return toCall.node.getData()[toCall.offset];
		}

		/**
		 * Returns the index of the element that would be returned by a
		 * subsequent call to next. (Returns list size if the list iterator is
		 * at the end of the list.)
		 */
		@Override
		public int nextIndex() {
			if(!hasNext())
			{
				return size;
			}
			return position;
		}

		/**
		 * 
		 * Returns the previous element in the list. This method may be called
		 * repeatedly to iterate through the list backwards, or intermixed with
		 * calls to next to go back and forth. (Note that alternating calls to
		 * next and previous will return the same element repeatedly.)
		 */

		@Override
		public E previous() {
			if(!hasPrevious())
			{
				throw new NullPointerException();
			}
			if(nextCalled)
			{
				return lastCalled.node.getData()[lastCalled.offset];
			}
			NodeInfo toCall = find(position-1);
			position--;
			nextCalled = false;
			prevCalled = true;
			setAddRevCall = false;
			lastCalled = toCall;
			return toCall.node.getData()[toCall.offset];
		}

		/**
		 * Returns the index of the element that would be returned by a
		 * subsequent call to previous. (Returns -1 if the list iterator is at
		 * the beginning of the list.)
		 */

		@Override
		public int previousIndex() {
			if(!hasPrevious())
			{
				return -1;
			}
			return position - 1;
		}

		/**
		 * Removes from the list the last element that was returned by next or
		 * previous (optional operation). This call can only be made once per
		 * call to next or previous. It can be made only if ListIterator.add has
		 * not been called after the last call to next or previous. DO NOT call
		 * the remove method you wrote for DoublingList above! This one should
		 * run in AMORTIZED O(1) (constant time)
		 */
		@Override
		public void remove() 
		{
			if(setAddRevCall)
			{
				throw new IllegalStateException("Whoops. Can't call add, remove, or set twice in a row.");
			}
			lastCalled.node.getData()[lastCalled.offset] = null;
			if(nextCalled)
			{
				position--;
			}
			size--;
			if(binaryBreakdown(size) < binaryBreakdown(totalCap))
			{
				//copy all the data items into one big array
				Object[] obj = new Object[size];
				E[] copy = (E[]) obj;
				Node<E> curs = head.getNext();
				int count = 0;
				//add them to the big array
				while(curs.getNext().getData() != null)
				{
					for(int i = 0; i < curs.getData().length; i++)
					{
						if(curs.getData()[i] != null)
						{
							copy[count] = curs.getData()[i];
							count++;
							curs.getData()[i] = null;
						}
					}
					curs = curs.getNext();
				}
				// redirect the nodes and set the head and tail of the node to null (remove it)
				curs.getPrev().setNext(tail);
				curs.getNext().setPrev(curs.getPrev());
				curs.setNext(null);
				curs.setPrev(null);
				numNodes--;
				
				//add all the data items in the big array back into the list
				curs = head.getNext();
				count = 0;
				while(curs.getNext().getData() != null)
				{
						for(int i = 0; i < curs.getData().length; i++)
						{
							curs.getData()[i] = copy[count];
							count ++;
						}
						curs = curs.getNext();	
				}
			}
			nextCalled = false;
			prevCalled = false;
			setAddRevCall = true;
			
		}

		/**
		 * Replaces the last element returned by next or previous with the
		 * specified element (optional operation). This call can be made only if
		 * neither ListIterator.remove nor ListIterator.add have been called
		 * after the last call to next or previous.
		 */
		@Override
		public void set(E data) 
		{
			if(setAddRevCall)
			{
				throw new IllegalStateException("Whoops. Can't call set, add, remove twice in a row.");
			}
			lastCalled.node.getData()[lastCalled.offset] = data;
			setAddRevCall = true;
		}
		
		public int getPosition()
		{
			return position;
		}
		
	}

	/**
	 * Iterator to be used for traversing a DoublingList. This iterator is
	 * optional if you fully implement the ListIterator but is easier and
	 * partial point will be awarded if the one is correct and your ListIterator
	 * is wrong.

	 * API: http://docs.oracle.com/javase/6/docs/api/java/util/Iterator.html
	 */
	private class DoublingIterator implements Iterator<E> 
	{
		/**
		 * Returns true if the iteration has more elements. (In other words,
		 * returns true if next would return an element rather than throwing an
		 * exception.)
		 */
		@Override
		public boolean hasNext() 
		{
			try
			{
				this.next();
			}
			catch(Exception e)
			{
				return false;
			}
			return true;
		}

		/**
		 * Returns the next element in the iteration.
		 */
		@Override
		public E next() throws NoSuchElementException
		{
			if(!hasNext())
			{
				throw new NoSuchElementException();
			}
			
			
			return null;
		}

		/**
		 * You do not need to implement this method
		 */

		@Override
		public void remove() throws UnsupportedOperationException
		{
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * NodeInfo class that you may find useful to use. Again, feel free to add
	 * methods / constructors / variables that you find useful in here.
	 */
	private class NodeInfo 
	{
		public Node<E> node;
		public int offset;

		public NodeInfo(Node<E> node, int offset) 
		{
			this.node = node;
			this.offset = offset;
		}
	}

	/**
	 * Returns a string representation of this list showing the internal
	 * structure of the nodes.
	 */

	public String toStringInternal() 
	{
		return toStringInternal(null);
	}

	/**
	 * Returns a string representation of this list showing the internal
	 * structure of the nodes and the position of the iterator.
	 * @param iter
	 *            an iterator for this list
	 */

	public String toStringInternal(ListIterator<E> iter) 
	{
		int count = 0;
		int position = -1;
		if (iter != null) 
		{
			position = iter.nextIndex();
		}

		StringBuilder sb = new StringBuilder();
		sb.append('[');

		Node<E> current = head.getNext();
		while (current != tail)
		{
			sb.append('(');
			E data = current.getData()[0];
			if (data == null) 
			{
				sb.append("-");
			} else 
			{
				if (position == count) 
				{
					sb.append("| ");
					position = -1;
				}
				sb.append(data.toString());
				++count;
			}

			for (int i = 1; i < current.getData().length; ++i) 
			{
				sb.append(", ");
				data = current.getData()[i];
				if (data == null) 
				{
					sb.append("-");
				} else {
					if (position == count)
					{
						sb.append("| ");
						position = -1;
					}

					sb.append(data.toString());
					++count;

					// iterator at end
					if (position == size() && count == size()) 
					{
						sb.append(" |");
						position = -1;
					}
				}
			}
			sb.append(')');
			current = current.getNext();
			if (current != tail)
				sb.append(", ");
		}
		sb.append("]");
		return sb.toString();

	}

	public static void main(String[] arg0)
	{
		DoublingList<String> list = new DoublingList();
//		ListIterator<String> iter = list.listIterator();
		list.add("BEWBS");
		System.out.println(DoublingListUtil.toStringInternal(list));
		list.add(1, "BOOBS");
//		System.out.println(DoublingListUtil.toStringInternal(list, iter));
	}
}