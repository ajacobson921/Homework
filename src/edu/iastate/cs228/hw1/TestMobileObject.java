package edu.iastate.cs228.hw1;

import static org.junit.Assert.*;

import org.junit.*;

/**
 * Test class for MobileObject
 * @author Aaron Jacobson ajacob1
 *
 */

public class TestMobileObject 
{
	private MobileObject obj;
	
	private Ground ground;
	
	public TestMobileObject()
	{
		
	}
	
	@Before
	public void init()
	{
		ground = new Ground(10,10);
		obj = new MobileObject(0, 3, 5, 1, 5, ground);
	}
	
	@Test
	public void testGetX()
	{
		assertEquals(obj.getCurrentX(), 3);
	}
	
	
	@Test
	public void testGetY()
	{
		assertEquals(obj.getCurrentY(), 5);
	}
	
	@Test
	public void testMove()
	{
		obj.move();
		assertEquals(obj.getCurrentX(), 4);
		assertEquals(obj.getCurrentY(), 6);
	}
	
	@Test
	public void testNorthWall()
	{
	 	MobileObject northObj = new MobileObject(6, 0, 0, 1, 7, ground);
		northObj.move();
		assertEquals(northObj.getDirection(), 5);
	}
	 
	@Test
	public void testSouthWall()
	{
		MobileObject southObj = new MobileObject(3, 10, 10, 1, 1, ground);
		southObj.move();
		assertEquals(southObj.getDirection(), 0);
	}
	
	@Test
	public void testEastWall()
	{
		MobileObject eastObj = new MobileObject(9, 10, 5, 1, 2, ground);
		eastObj.move();
		assertEquals(eastObj.getDirection(), 3);
	}
	
	@Test
	public void testWestWall()
	{
		MobileObject westObj = new MobileObject(8, 0, 0, 1, 3, ground);
		westObj.move();
		assertEquals(westObj.getDirection(),2);
	}
	
	
}
