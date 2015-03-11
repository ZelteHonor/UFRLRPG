package test;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import world.Floor;
import Ai.Node;

public class AiTest {
	
	private Node n;
	
	@Before
	public void build(){
		Floor floor = new Floor(0);
		n = Ai.Ai.getPath(new Point(1,1), new Point(3,3), floor);
	}
	
	@Test
	public void testGetPath() {
		
	}

}
