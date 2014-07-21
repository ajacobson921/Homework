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
	private String[] ranked;
	private int[] floatToInt;
	private Item[] itemsByScore;
	private Item[] itemsByRank;
	private Item[] itemsByName;
	
	/*
	 * Returns the number of items in the ranking. Must run in O(1) time.
	 */
	public int getNumItems()
	{		
		return ranked.length;
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
		
		
	}
	
	/*
	 * Constructs a ranking sigma of the strings in the array names, where sigma(names[i]) = rank[i]
	 * for each i between 0 and names.length - 1. Throws a NullPointerException if names
	 * is null or if rank is null. Throws an IllegalArgumentException if names.length !=
	 * rank.length, if names contains duplicate strings or one or more null strings, or if 
	 * rank does not consist of distinct elements between 1 and rank.length. Must run in
	 * O(n log n) time, where n = names.length.
	 */
	public Ranking(String[] names, int[] rank)
	{	
		if(rank == null || names == null) throw new NullPointerException();
		if(names.length != rank.length) throw new IllegalArgumentException();
		ranked = names;
		
		itemsByRank = createItemArray(ranked, rank);
		Comparator<Item> compareRank = new ItemCompareByRank();
		itemsByRank = mergeSort(itemsByRank, compareRank);
		itemsByName = createItemArray(ranked, rank);
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
		ranked = names;
		
		
		//TODO
	}
	
	/*
	 * Constructs a random ranking of the strings in the array names. Throws a NullPointerException
	 * if names is null. Throws an IllegalArgumentException if names contains duplicates or one
	 * or more null strings. Must run in O(n log n) time, where n = names.length.
	 */
	public Ranking(String[] names, long seed)
	{
		if(names == null) throw new NullPointerException();
		Random rand = new Random(seed);
		ranked = names;
		
		
		
		
	}

	/*
	 * Returns a string representation of this ranking. The string should be a comma-separated
	 * list of the names in the ranking, ordered by rank, starting at rank 1.
	 */
	@Override
	public String toString()
	{
		

	}
			
	/*
	 * Returns true if r1 and r2 are rankings of the same set of strings; otherwise, returns false.
	 * Throws a NullPointerException if either r1 or r2 is null. Must run in O(n) time, where
	 * n is the number of elements in r1 (or r2).
	 */
	public static boolean sameNames(Ranking r1, Ranking r2)
	{
		//TODO
	}
	
	/*
	 * Returns true if this and other are rankings of the same set of strings; otherwise, returns
	 * false. Throws a NullPointerException if other is null. Must run in O(n) time, where
	 * n is the number of elements in this (or other).
	 */
	public boolean sameNames(Ranking other)
	{
		if(other == null) throw new NullPointerException();
		//TODO
	}
	
	/*
	 * Returns the Kemeny distance between r1 and r2. Throws a NullPointerException if
	 * either r1 or r2 is null. Throws an IllegalArgumentException if r1 and r2 rank different
	 * sets of strings. Must run in O(n log n) time, where n is the number of elements in r1 (or r2).
	 */
	public static int kemeny(Ranking r1, Ranking r2) throws NullPointerException, IllegalArgumentException
	{
		if(r1 == null || r2 == null) throw new NullPointerException();
		
		//TODO
	}
	
	/*
	 * Returns the Kemeny distance between this and other. Throws a NullPointerException
	 * if other is null. Throws an IllegalArgumentException if this and other rank different
	 * sets of strings. Must run in O(n log n) time, where n is the number of elements in this (or
	 * other).
	 */
	public int kemeny(Ranking other)
	{
		//TODO
	}
	
	/*
	 * Returns the footrule distance between r1 and r2. Throws a NullPointerException if either
	 * r1 or r2 is null. Throws an IllegalArgumentException if r1 and r2 rank different sets
	 * of strings. Must run in O(n) time, where n is the number of elements in r1 (or r2).
	 */
	public static int footrule(Ranking r1, Ranking r2)
	{
		//TODO
	}
	
	/*
	 * Returns the footrule distance between this and other. Throws a NullPointerException
	 * if other is null. Throws an IllegalArgumentException if this and other rank different
	 * sets of strings. Must run in O(n) time, where n is the number of elements in this (or other).
	 */
	public int footrule(Ranking other) throws NullPointerException, IllegalArgumentException
	{
		if(other == null) throw new NullPointerException();
		//TODO
	}
	
	private Item[] createItemArray(String[] names, int[] ranks) throws IllegalArgumentException
	{
		if(names.length != ranks.length) throw new IllegalArgumentException();
		Item[] items = new Item[names.length];
		for(int i = 0; i < names.length; i++)
		{
			items[i] = new Item(names[i], ranks[i]);
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
