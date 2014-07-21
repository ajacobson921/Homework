package edu.iastate.cs228.hw1;

import java.util.ArrayList;

/**
 * @author Aaron Jacobson ajacob1
 * Class representing the ground where geotasks and mobile objects are placed
 */
public class Ground {

	/**
	 * The x dimension
	 */
	private int dimensionX;
	/**
	 * The y dimension
	 */
	private int dimensionY;
	/**
	 * List of all Geotasks
	 */
	private ArrayList<Geotask> geotasks;

	/**
	 * Constructs a new Ground object
	 * 
	 * @param dimensionX
	 *            the size on the x axis
	 * @param dimensionY
	 *            the size on the y axis
	 * @throws java.lang.IllegalArgumentException
	 *             if either dimension is less than 1
	 */
	public Ground(int dimensionX, int dimensionY) throws IllegalArgumentException
	{
		if(dimensionX < 1 || dimensionY < 1)
		{
			throw new IllegalArgumentException();
		}
		else
		{
			this.dimensionX = dimensionX;
			this.dimensionY = dimensionY;
			ArrayList<Geotask> geotasks = new ArrayList<Geotask>();
		}
		
	}

	/**
	 * Adds a Geotask
	 * 
	 * @param t
	 *            the task to add
	 */
	public void addGeotask(Geotask t) throws NullPointerException
	{
		if(t == null)
		{
			throw new NullPointerException();
		}
		else
		{
			geotasks.add(t);
		}
		
	}

	/**
	 * Gets the x dimension
	 */
	public int getDimensionX() 
	{
		return dimensionX;
	}

	/**
	 * Gets the y dimension
	 */
	public int getDimensionY() 
	{
		return dimensionY;
	}

	/**
	 * Gets the Geotask belonging to a cell
	 * 
	 * @param x
	 *            the x coordinate of the Geotask
	 * @param y
	 *            the y coordinate of the Geotask
	 * @return an ArrayList of geotasks if any is present, otherwise null
	 */
	public ArrayList<Geotask> getGeotask(int x, int y) {
		ArrayList <Geotask> toRet = new ArrayList<Geotask>();
		for(int i = 0; i < geotasks.size(); i++)
		{
			if(geotasks.get(i).getX() == x && geotasks.get(i).getY() == y)
			{
				toRet.add(geotasks.get(i));
			}
		}
		return toRet;
	}

	/**
	 * Gets the Geotask belonging to a mobile object
	 * 
	 * @param mo
	 *            the MobileObject to get Geotasks belonging to
	 * @return an ArrayList of geotasks if any is present, otherwise null
	 */
	public ArrayList<Geotask> getGeotask(MobileObject mo) 
	{
		return getGeotask(mo.getCurrentX(), mo.getCurrentY());
	}
		
	
	
}
