package edu.iastate.cs228.hw1;

import java.util.ArrayList;

/**
 * @author Aaron Jacobson ajacob1
 * 
 * Mobile Object that traverses the ground
 */
public class MobileObject {

    /**
     * The ground which the MobileObjhect resides on
     */
    private final Ground ground;
    /**
     * The id of the mobileObject
     */
    private final int ID;
    /**
     * The current x coordinate
     */
    private int currentX;
    /**
     * The current y coordinate
     */
    private int currentY;
    /**
     * The speed of the MobileObject
     */
    private int speed;
    /**
     * The direction of the MobileObject
     * 0-7 correlates to N, S, E, W, NE, SE, SW, NW
     */
    private int direction;

    /**
     * Constructs a new MobileObject
     *
     * @param ID        the ID of the object
     * @param currentX  the x coordinate of the object
     * @param currentY  the y coordinate of the object
     * @param speed     the speed of the object
     * @param direction the direction of the object
     * @param ground    the Ground on which the object resides
     * @throws java.lang.IllegalArgumentException if the cell coordinates, speed, or direction is out of bounds
     */
    public MobileObject(int ID, int currentX, int currentY, int speed, int direction, Ground ground) 
    {
    	this.ID = ID;
    	this.currentX = currentX;
    	this.currentY = currentY;
    	this.speed = speed;
    	this.direction = direction;
    	this.ground = ground;
    }

    /**
     * Gets the x coordinate of the mobileObject
     */
    public int getCurrentX() 
    {
    	return currentX;
    }

    /**
     * Gets the y coordinate of the mobileObject
     */
    public int getCurrentY() 
    {
    	return currentY;
    }

    /**
     * Gets the speed of the MobileObject
     */
    public int getSpeed() 
    {
    	return speed;
    }

    /**
     * Gets the direction of the MobileObject
     */
    public int getDirection() 
    {
    	return direction;
    }

    /**
     * Gets the ID of the MobileObject
     */
    public int getID() 
    {
    	return ID;
    }

    /**
     * Moves according to the speed
     */
    public void move() 
    {
    	if(this.currentX == ground.getDimensionX()) //east wall
    	{
    		if(this.direction == 4)
    		{
    			this.direction = 7;
    		}
    		else if(this.direction == 2)
    		{
    			this.direction = 3;
    		}
    		else if(this.direction == 5)
    		{
    			this.direction = 6;
    		}
    	}
    	else if(this.currentY == ground.getDimensionY()) //south wall
    	{
    		if(this.direction == 5)
    		{
    			this.direction = 7;
    		}
    		else if(this.direction == 1)
    		{
    			this.direction = 0;
    		}
    		else if(this.direction == 6)
    		{
    			this.direction = 4;
    		}
    	}
    	else if(this.currentX ==0) //west wall
    	{
    		if(this.direction == 6)
    		{
    			this.direction = 5;
    		}
    		else if(this.direction == 3)
    		{
    			this.direction = 2;
    		}
    		else if(this.direction == 7)
    		{
    			this.direction = 4;
    		}
    		
    	}
    	else if(this.currentY == 0) //north wall
    	{
    		if(this.direction == 7)
    		{
    			this.direction = 6;
    		}
    		else if(this.direction == 0)
    		{
    			this.direction = 1;
    		}
    		else if(this.direction == 4)
    		{
    			this.direction = 5;
    		}
    	}
    		
    	ArrayList<Geotask> alist = ground.getGeotask(this);
    	for(int i = 0; i < alist.size(); i++)
    	{
    		alist.get(i).moveOut(this);
    	} 
    	
    	if(this.direction == 0)
    	{
    		this.currentY -= this.speed;
    	}
    	if(this.direction == 1)
    	{
    		this.currentY -= this.speed;
    	}
    	if(this.direction == 2)
    	{
    		this.currentX += this.speed;
    	}
    	if(this.direction == 3)
    	{
    		this.currentX -= this.speed;
    	}
    	if(this.direction == 4)
    	{
    		this.currentX += this.speed;
    		this.currentY -= this.speed;
    	}
    	if(this.direction == 5)
    	{
    		this.currentX += this.speed;
    		this.currentY += this.speed;
    	}
    	if(this.direction == 6)
    	{
    		this.currentX -= this.speed;
    		this.currentY += this.speed;
    	}
    	if(this.direction == 7)
    	{
    		this.currentX -= this.speed;
    		this.currentY -= this.speed;
    	}
    	alist = ground.getGeotask(this);
    	for(int j = 0; j < alist.size(); j++)
    	{
    		alist.get(j).moveIn(this);
    	}
    	
    	
    	
    }
}
