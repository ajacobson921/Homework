package edu.iastate.cs228.hw1;

import static org.junit.Assert.*;

import org.junit.*;
/**
 * Test class for Ground class
 * @author Aaron Jacobson ajacob1
 *
 */
public class TestGround 
{
	private Ground ground;
	
	public TestGround()
	{
		
	}
	
	@Before
	public void init()
	{
		ground = new Ground(10, 10);
	}
	
	@Test
	public void testX()
	{
		assertEquals(ground.getDimensionX(), 10);
	}
	
	@Test
	public void testY()
	{
		assertEquals(ground.getDimensionY(), 10);
	}
	
	
	@Test (expected = IllegalArgumentException.class)
	public void testGeotasks()
	{
		Ground newG = new Ground(0,9);
	}
	
	@Test (expected = NullPointerException.class)
	public void nullTest()
	{
		ground.addGeotask(null);
	}
	
	@Test
	public void testSizeArrayList()
	{
		ground.addGeotask(new WarningGeotask(4,5));
		assertEquals(0, ground.getGeotask(5, 3).size());
	}
	
	
	
	
	
	
	
}
