package edu.iastate.cs228.hw1;

/**
 * @author Aaron Jacobson ajacob1
 * Geotask that tracks the number of concurrent visitors
  */
public class CounterGeotask extends Geotask {

    /**
     * The number of visitors
     */
    private int numberOfVisitors;

    /**
     * Constructs a CounterGeotask
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @throws java.lang.IllegalArgumentException if x or y is less than 0
     */
    public CounterGeotask(int x, int y) 
    {
    	super(x,y);
    	this.numberOfVisitors = 0;
    }

    @Override
    public void moveIn(MobileObject mo) 
    {
    	numberOfVisitors++;
    }

    @Override
    public void moveOut(MobileObject mo) 
    {
    	return;
    }

    @Override
    public void printType() 
    {
    	System.out.println("This is a Counter Geotask.");
    }

    /**
     * Gets the number of visitors
     */
    public int getNumberOfVisitors() 
    {
    	return numberOfVisitors;
    }
}
