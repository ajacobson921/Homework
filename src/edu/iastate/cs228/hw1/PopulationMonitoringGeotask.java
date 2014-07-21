package edu.iastate.cs228.hw1;

/**
 * @author Aaron Jacobson ajacob1
 * 
 * Geotask that monitors the current population of mobile units and alerts when
 * the number goes above or below a threshold
 */
public class PopulationMonitoringGeotask extends Geotask {

    /**
     * The population threshold to alert the user
     */
    private int alertThreshold;

    /**
     * The current population
     */
    private int population ;
    
    /**
     * If population is crowded
     */
    private boolean crowded;

    /**
     * Constructs a new PopulationMonitoringGeotask
     *
     * @param x              the x coordinate
     * @param y              the y coordinate
     * @param alertThreshold the threshold of which to alert the user
     * @throws java.lang.IllegalArgumentException if x or y is less than 0
     */
    public PopulationMonitoringGeotask(int x, int y, int alertThreshold) 
    {
    	super(x,y);
    	population = 0;
    	this.alertThreshold = alertThreshold;
    }

    @Override
    public void moveIn(MobileObject mo) 
    {
    	population++;
    	if(population >= alertThreshold)
    	{
    		System.out.println("Too crowded");
    		crowded = true;
    	}
    }

    @Override
    public void moveOut(MobileObject mo) 
    {
    	int temp = population;
    	population--;
    	if(population < alertThreshold)
    	{
    		crowded = false;
    	}
    	if(temp >= alertThreshold && crowded == false)
    	{
    		System.out.println("No longer crowded.");
    	}
    }
}