package test;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import pathfinding.Node;
import world.Floor;

public class AiTest {
	
	private Node n;
	
	@Before
	public void build(){
		Floor floor = new Floor(-1);
		n = pathfinding.Pathfinding.getPath(new Point(6,6), new Point(8,8), floor);
	}
	
	@Test
	public void testGetPath() {
		assertTrue(n.getDestructiveFirst().getCoor().equals(new Point(7,7)));
		assertTrue(n.getDestructiveFirst().getCoor().equals(new Point(8,8)));
	}

}
