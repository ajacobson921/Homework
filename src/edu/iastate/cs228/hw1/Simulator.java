package edu.iastate.cs228.hw1;

import java.io.*;

import java.util.*;


/**
 * @author Aaron Jacobson ajacob1
 * 
 * Simulates the interaction between objects
 */
public class Simulator {
	


	
    public static void main(String[] args)
    {
    	try
    	{
    		int x = 0;
    		int y = 0;
    		int num = 0;
    		int duration = 1;
    		File file = new File("src/Simulation.configuration");
    		Scanner scan = new Scanner(file);
    		ArrayList<Geotask> gList = new ArrayList<Geotask>(); //total Geotask list
    		while(scan.hasNext())
    		{
    			String line = scan.next();

    			if(line.contains("dimensionX")) //set ground X dimension
    			{
    				x = Integer.parseInt(scan.next());
    				
    			}
    			if(line.contains("dimensionY")) //set ground Y dimension
    			{
    				
    				y = Integer.parseInt(scan.next());

    			}
    			if(line.contains("number")) //set number of objects to create
    			{
    				
    				num = Integer.parseInt(scan.next());

    			}
    			if(line.contains("duration")) //set duration of moves
    			{
    				
    				duration = Integer.parseInt(scan.next());

    			}
    			if(line.contains("WarningGeotask")) //WarningGeotask maker
    			{
    				String subLineX = scan.next();
    				subLineX = subLineX.substring(0, subLineX.length() - 1);
    				int xSpot =Integer.parseInt(subLineX);
    				scan.next();
    				int ySpot = Integer.parseInt(scan.next());
    				WarningGeotask warn = new WarningGeotask(xSpot, ySpot);
    			}
    			if(line.contains("CounterGeotask")) //CounterGeotask maker
    			{
    				String subX = scan.next();
    				subX = subX.substring(0, subX.length() - 1);
    				int xSpot = Integer.parseInt(subX);
    				int ySpot = Integer.parseInt(scan.next());
    				CounterGeotask count = new CounterGeotask(xSpot,ySpot);
    				gList.add(count);
    			}
    			if(line.contains("Population")) //PopulationMonitoringGeotask maker
    			{   
    				String sublineX = scan.next();
    				sublineX = sublineX.substring(0, sublineX.length()-1);
    				int xSpot = Integer.parseInt(sublineX);
    				String sublineY = scan.next();
    				sublineY = sublineY.substring(0, sublineY.length() - 1);
    				int ySpot = Integer.parseInt(sublineY);
    				int alert = Integer.parseInt(scan.next());
    				PopulationMonitoringGeotask pop = new PopulationMonitoringGeotask(xSpot, ySpot, alert);
    				gList.add(pop);
    			}

    		}
    		Ground ground = new Ground(x, y);
    		for(int k = 0; k < gList.size(); k++) //put all Geotasks in the Ground
    		{
    			ground.addGeotask(gList.get(k));
    		}
    		MobileObject[] arr = new MobileObject[num]; //make array of MobileObjects
    		for(int i =0; i < arr.length -1; i++) //initialize all array spots
    		{
    			arr[i] = new MobileObject(i, i % x, i % y , 1, i%8, ground);
    			//System.out.println("New Mobile object made at " + i + " with x-dimension " + i% x + " and y-dimension " + i%y);
    			
    		}
    		
    		for(int i = 0; i < duration; i++) //loop for duration
    		{
    			for(int j = 0; j < arr.length; j++) //loop through array, calling move
    			{
    				arr[j].move();
    				System.out.println(arr[j].getID() + ": (" + arr[j].getCurrentX() + ", " + arr[j].getCurrentY()+ ")");
    			}
    		}
    		for(int f = 0; f < gList.size(); f++) //print all the Geotask printTypes
    		{
    			gList.get(f).printType();
    		}
    		
    	}
    	catch(IOException e)
    	{
    		System.out.println("Sorry, there's not a file there.");
    	}
    	
    	catch(IllegalArgumentException e)
    	{
    		System.out.println("This Ground doesn't have the right dimensions.");
    	}
    	finally
		{

		}
    }
    	
    	
    	
    }

