package edu.iastate.cs228.hw2;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/*
 * A Ranking object represents a ranking of a set of items numbered 1 through n, for some n. 
 * The Ranking class contains various methods to construct rankings. Once a ranking is constructed,
 * you can determine how many items it ranks and obtain the rank of some specific item. You can
 * also compute distances between rankings. Ranking objects are immutable; that is, once created,
 * they cannot be modified.
 * 
 * @author Aaron Jacobson ajacob1 
 */

public class Ranking
{
	private Item[] itemsByScore;
	private Item[] itemsByRank;
	private Item[] itemsByName;
	private static int count;
	
	/*
	 * Returns the number of items in the ranking. Must run in O(1) time.
	 */
	public int getNumItems()
	{		
		return itemsByRank.length;
	}
	
	/*
	 * Returns the string of rank i. Throws an IllegalArgumentException if i is not between 1
	 * and this.getNumItems(). Must run in O(1) time.
	 */
	public String getStringOfRank(int i)
	{	
		return itemsByRank[i-1].getName();
	}
	
	/*  binary search
	 * 	Returns the rank of name. Throws an IllegalArgumentException if name is not present in
	 * 	the ranking. Must run in O(log n) time, where n = this.getNumItems().
	 */
	public int getRankOfString(String name)
	{	
		return binarySearch(name, 0, itemsByRank.length -1);
	}
	
	/*
	 * Constructs a ranking sigma of the strings in the array names, where sigma(names[i]) = rank[i]
	 * for each i between 0 and names.length - 1. Throws a NullPointerException if names
	 * is null or if rank is null. Throws an IllegalArgumentException if names.length !=
	 * rank.length, if names contains duplicate strings or one or more null strings, or if 
	 * rank does not consist of distinct elements between 1 and rank.length. Must run in
	 * O(n log n) time, where n = names.length.
	 */
	public Ranking(String[] names, int[] rank) throws NullPointerException, IllegalArgumentException
	{	
		if(rank == null || names == null) throw new NullPointerException();
		if(names.length != rank.length) throw new IllegalArgumentException();
		
		itemsByRank = createItemArrayInt(names, rank);
		Comparator<Item> compareRank = new ItemCompareByRank();
		itemsByRank = mergeSort(itemsByRank, compareRank);
		itemsByName = createItemArrayInt(names, rank);
		Comparator<Item> compareName = new ItemCompareByString();
		itemsByName = mergeSort(itemsByName, compareName);
		for(int i = 0; i < itemsByName.length - 1; i++)
		{
			if(itemsByName[i].getName().contains(itemsByName[i+1].getName()))
			{
				throw new IllegalArgumentException();
			}
		}
		for(int j = 0; j < itemsByRank.length - 1; j++)
		{
			if(itemsByRank[j].getRank() == itemsByRank[j+1].getRank())
			{
				throw new IllegalArgumentException();
			}
		}
	}
	

	/*
	 * Constructs a ranking sigma of the strings in the array names, where sigma(names[i]) = k if and only
	 * if scores[i] is the kth largest element in the array scores. Throws a NullPointerException
	 * if names is null or if scores is null. Throws an IllegalArgumentException if names.length !=
	 * scores.length, if names contains duplicate strings or one or more null strings, or if scores
	 * contains duplicate values. Must run in O(n log n) time, where n = names.length.
	 */
	public Ranking(String[] names, float[] scores) throws NullPointerException, IllegalArgumentException
	{
		if(names == null || scores == null) throw new NullPointerException();
		
		//make an array, pairing the names and float scores, and then mergesorting them by scores
		Comparator<Item> compareScores = new ItemCompareByScore();
		itemsByScore = createItemArrayFloat(names, scores);
		itemsByScore = mergeSort(itemsByScore, compareScores);
		
		
		//make new array of ints for ranks
		int[] ranks = new int[scores.length];
		for(int i = 0; i < scores.length; i++)
		{
			ranks[i] = i+1;
		}
		
		//make an array of names that are in order of the scored ranking
		String[] newNames = new String[names.length];
		for(int j = 0; j < names.length; j++)
		{
			newNames[j] = itemsByScore[j].getName();
		}
		
		//make a new array that has them in order of rank, then mergesort them
		itemsByRank = createItemArrayInt(newNames, ranks);
		Comparator<Item> compareRank = new ItemCompareByRank();
		itemsByRank = mergeSort(itemsByRank, compareRank);
		
		//now compare them by names
		Comparator<Item> compareName = new ItemCompareByString();
		itemsByName = createItemArrayInt(names, ranks);
		itemsByName = mergeSort(itemsByName, compareName);
		
		for(int i = 0; i < itemsByName.length - 1; i++)
		{
			if(itemsByName[i].getName().contains(itemsByName[i+1].getName()))
			{
				throw new IllegalArgumentException();
			}
		}
		for(int i = 0; i < itemsByScore.length - 1; i++)
		{
			if(itemsByScore[i].getScore() == itemsByName[i+1].getScore())
			{
				throw new IllegalArgumentException();
			}
		}
		
		
		
	}
	
	/*
	 * Constructs a random ranking of the strings in the array names. Throws a NullPointerException
	 * if names is null. Throws an IllegalArgumentException if names contains duplicates or one
	 * or more null strings. Must run in O(n log n) time, where n = names.length.
	 */
	public Ranking(String[] names, long seed) throws NullPointerException, IllegalArgumentException
	{
		if(names == null) throw new NullPointerException();
		Random rand = new Random(seed);
		int[] ranks = new int[names.length];
		for(int i = 0; i < ranks.length; i++)
		{
			ranks[i] = rand.nextInt();
		}
		itemsByRank = createItemArrayInt(names, ranks);
		Comparator<Item> compareName = new ItemCompareByString();
		Comparator<Item> compareRank = new ItemCompareByRank();
		itemsByName = createItemArrayInt(names, ranks);
		itemsByName = mergeSort(itemsByName, compareName);
		itemsByRank = mergeSort(itemsByRank, compareRank);
	}

	/*
	 * Returns a string representation of this ranking. The string should be a comma-separated
	 * list of the names in the ranking, ordered by rank, starting at rank 1.
	 */
	@Override
	public String toString()
	{
		String toRet = "";
		for(int i = 0; i < itemsByRank.length; i++)
		{
			toRet += itemsByRank[i].getName();
			{
				if(i < itemsByRank.length - 1)
				{
					toRet += ", ";
				}
			}
		}
		return toRet;
	}
			
	/*
	 * Returns true if r1 and r2 are rankings of the same set of strings; otherwise, returns false.
	 * Throws a NullPointerException if either r1 or r2 is null. Must run in O(n) time, where
	 * n is the number of elements in r1 (or r2).
	 */
	public static boolean sameNames(Ranking r1, Ranking r2) throws NullPointerException
	{
		if(r1 == null || r2 == null) throw new NullPointerException();
		if(r1 == r2) return true;
		for(int i = 0; i < r1.itemsByName.length; i++)
		{
			if(r1.itemsByName[i] != r2.itemsByName[i])
			{
				return false;
			}
		}
		return true;
	}
	
	/*
	 * Returns true if this and other are rankings of the same set of strings; otherwise, returns
	 * false. Throws a NullPointerException if other is null. Must run in O(n) time, where
	 * n is the number of elements in this (or other).
	 */
	public boolean sameNames(Ranking other) throws NullPointerException
	{
		if(other == null) throw new NullPointerException();
		return sameNames(this, other);
	}
	
	/*
	 * Returns the Kemeny distance between r1 and r2. Throws a NullPointerException if
	 * either r1 or r2 is null. Throws an IllegalArgumentException if r1 and r2 rank different
	 * sets of strings. Must run in O(n log n) time, where n is the number of elements in r1 (or r2).
	 */
	public static int kemeny(Ranking r1, Ranking r2) throws NullPointerException, IllegalArgumentException
	{
		if(r1 == null || r2 == null) throw new NullPointerException();
		if(!sameNames(r1,r2)) throw new IllegalArgumentException();
		if(r1 == r2) return 0;
		count = 0;
		
		//make the second list, assuming r1 is the "standard order" list
		int[] kem = new int[r2.itemsByName.length];
		for(int i = 0; i < r1.itemsByRank.length; i++)
		{
			kem[i] = r2.getRankOfString(r1.itemsByRank[i].getName());
		}
		int[] merged = sortAndCount(kem);		
		return count;
	}
	
	/*
	 * Returns the Kemeny distance between this and other. Throws a NullPointerException
	 * if other is null. Throws an IllegalArgumentException if this and other rank different
	 * sets of strings. Must run in O(n log n) time, where n is the number of elements in this (or
	 * other).
	 */
	public int kemeny(Ranking other) throws NullPointerException, IllegalArgumentException
	{
		if(other == null) throw new NullPointerException();
		return kemeny(this, other);
	}
	
	/*
	 * Returns the footrule distance between r1 and r2. Throws a NullPointerException if either
	 * r1 or r2 is null. Throws an IllegalArgumentException if r1 and r2 rank different sets
	 * of strings. Must run in O(n) time, where n is the number of elements in r1 (or r2).
	 */
	public static int footrule(Ranking r1, Ranking r2) throws NullPointerException, IllegalArgumentException
	{
		if(r1 == null || r2 == null) throw new NullPointerException();
		if(!sameNames(r1,r2))throw new IllegalArgumentException();
		if (r1==r2) return 0;
		int foot = 0;
		for(int i = 0; i < r1.itemsByName.length; i++)
		{
			if(r1.itemsByName[i].getRank() > r2.itemsByName[i].getRank())
			{
				foot += r1.itemsByName[i].getRank() - r2.itemsByName[i].getRank();
			}
			else
			{
				foot += r2.itemsByName[i].getRank() - r1.itemsByName[i].getRank();
			}
		}
		return foot;
		
	}
	
	/*
	 * Returns the footrule distance between this and other. Throws a NullPointerException
	 * if other is null. Throws an IllegalArgumentException if this and other rank different
	 * sets of strings. Must run in O(n) time, where n is the number of elements in this (or other).
	 */
	public int footrule(Ranking other) throws NullPointerException, IllegalArgumentException
	{
		if(other == null) throw new NullPointerException();
		return footrule(this, other);
	}
	
	private Item[] createItemArrayInt(String[] names, int[] ranks) throws IllegalArgumentException
	{
		if(names.length != ranks.length) throw new IllegalArgumentException();
		Item[] items = new Item[names.length];
		for(int i = 0; i < names.length; i++)
		{
			items[i] = new Item(names[i], ranks[i]);
		}
		return items;
	}
	
	private Item[] createItemArrayFloat(String[] names, float[] scores) throws IllegalArgumentException
	{
		if(names.length != scores.length) throw new IllegalArgumentException();
		Item[] items = new Item[names.length];
		for(int i = 0; i < names.length; i++)
		{
			items[i] = new Item(names[i], scores[i]);
		}
		return items;
		
		
 	}
	
	private static Item[] mergeSort(Item[] arr, Comparator<Item> compare)
	{
		if(arr.length <= 1)
		{
			return arr;
		}
		int mid = arr.length / 2;
		Item[] first = Arrays.copyOfRange(arr, 0, mid);
		Item[] last = Arrays.copyOfRange(arr, mid, arr.length);
		mergeSort(first, compare); 
		mergeSort(last, compare);
		return merge(first, last, compare);
	}
	
	private static Item[] merge(Item[] arr1, Item[] arr2, Comparator<Item> compare)
	{
		int left = 0;
		int right = 0;
		int cursor = 0;
		Item[] items = new Item[arr1.length + arr2.length];
		while(left < arr1.length && right < arr2.length)
		{
			if(compare.compare(arr1[left], arr2[right]) <= 0)
			{
				items[cursor] = arr1[left];
				cursor++;
				left++;
			}
			else if(compare.compare(arr1[left], arr2[right]) == 1)
			{
				items[cursor] = arr2[right];
				cursor++;
				right++;
			}

		}
		if(right < arr2.length)
		{
			for(int i = right; i < arr2.length; i++)
			{
				items[cursor] = arr2[i];
				cursor++;
			}
		}
		else if(left < arr1.length)
		{
			for(int j = left; j < arr1.length; j++)
			{
				items[cursor] = arr1[j];
				cursor++;
			}
		}
		return items;
		}
		
	private int binarySearch(String name, int first, int last)
	{
		int mid = (last-first) / 2;
        
        if (itemsByRank[mid].getName().equals(name)) 
        {
            return mid;
        } 
        else if(itemsByRank[mid].getName().compareTo(name) > 0) 
        {
            return binarySearch(name, 0, mid - 1);
        } 
        else 
        {
            return binarySearch(name, mid+1, itemsByRank.length);
        }
	
	}
	
	private static int[] sortAndCount(int[] left)
	{
		
		int middle;
		if(left.length == 1)
		{
			return left;
		}
		else	
			middle = left.length / 2;
			int[] first = Arrays.copyOfRange(left, 0, middle);
			int[] last = Arrays.copyOfRange(left, middle, left.length);
			sortAndCount(first); 
			sortAndCount(last);
			
		
		return mergeAndCount(first, last);
	}
	
	private static int[] mergeAndCount(int[] inv1, int[] inv2)
	{
		int left = 0;
		int right = 0;
		int cursor = 0;
		int[] merged = new int[inv1.length + inv2.length];
		while(left < inv1.length && right < inv2.length)
		{
			if(inv1[cursor] < inv2[cursor] )
			{
				merged[cursor] = inv1[left];
				cursor++;
				left++;
			}
			else if(inv1[cursor] > inv2[cursor])
			{
				merged[cursor] = inv2[right];
				cursor++;
				right++;
				count++;
			}

		}
		if(right < inv2.length)
		{
			for(int i = right; i < inv2.length; i++)
			{
				merged[cursor] = inv2[i];
				cursor++;
			}
		}
		else if(left < inv1.length)
		{
			for(int j = left; j < inv1.length; j++)
			{
				merged[cursor] = inv1[j];
				cursor++;
			}
		}
		return merged;
		
	}
	
	private class Item
	{
		private String name;
		private int rank;
		private float score;
		
		public Item(String name, int rank)
		{
			if(name == null) throw new NullPointerException();
			if(rank <= 0) throw new IllegalArgumentException();
			this.name = name;
			this.rank = rank;
		}
		
		public Item(String name, float score)
		{
			this.name = name;
			this.score = score;
		}
		
		public int getRank()
		{
			return rank;
		}
		
		public String getName()
		{
			return name;
		}
		
		public float getScore()
		{
			return score;
		}
		
	}
	
	private class ItemCompareByRank implements Comparator<Item>
	{

		@Override
		public int compare(Item o1, Item o2) 
		{
			if(o1.getRank() > o2.getRank())
			{
				return 1;
			}
			else if(o1.getRank() == o2.getRank())
			{
				return 0;
			}
			else
				return -1;
		}
			
	}	
	private class ItemCompareByScore implements Comparator<Item>
	{

		@Override
		public int compare(Item o1, Item o2) 
		{
			if(o1.getScore() > o2.getScore())
			{
				return 1;
			}
			else if(o1.getScore() == o2.getScore())
			{
				return 0;
			}
			else 
				return -1;
		}
	}
	
	private class ItemCompareByString implements Comparator<Item>
	{

		@Override
		public int compare(Item o1, Item o2) 
		{	
			return o1.getName().compareTo(o2.getName());
		}
		
	}
}
