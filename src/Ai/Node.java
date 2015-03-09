package Ai;

import java.awt.Point;


public class Node implements Comparable {
		private Point coor;
		private Node lastNode;
		private float fromB, toE;
		
		
		public Node(Point p, Node lastNode,float fromB,float toE)
		{
			coor = p;
			this.lastNode = lastNode;
			this.fromB = fromB;
			this.toE = toE;
		}
		
		public Point getCoor() {
			return coor;
		}
		
		public float getTotal()
		{
			return fromB + toE;
		}

		@Override
		public int compareTo(Object o) {
			return (int) (this.getTotal() - ((Node) o).getTotal());
		}
	
}
