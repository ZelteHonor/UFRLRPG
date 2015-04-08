package test;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import pathfinding.Node;

public class NodeTest {

	private Node n1;
	private Node n2;
	private Node n3;

	@Before
	public void testNode() {
		n1 = new Node(new Point(1,1),null,10,10);
		n2 = new Node(new Point(2,2),n1,20,20);
		n3 = new Node(new Point(3,3),n2,30,30);
	}

	@Test
	public void testGetCoor() {
		assertTrue(n1.getCoor().equals(new Point(1,1)));
		assertTrue(n2.getCoor().equals(new Point(2,2)));
		assertTrue(n3.getCoor().equals(new Point(3,3)));
	}

	@Test
	public void testGetTotal() {
		assertTrue(n1.getTotal()==20);
		assertTrue(n2.getTotal()==40);
		assertTrue(n3.getTotal()==60);
	}

	@Test
	public void testGetFirst() {
		assertTrue(n1.getFirst()==n1);
		assertTrue(n2.getFirst()==n1);
		assertTrue(n3.getFirst()==n1);
	}

	@Test
	public void testCompareTo() {
		assertTrue(n3.compareTo(n1) > 0);
		assertTrue(n3.compareTo(n3) == 0);
		assertTrue(n1.compareTo(n3) < 0);
	}

}
