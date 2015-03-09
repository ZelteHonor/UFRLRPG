package Ai;

import java.awt.Point;


public class Node implements Comparable<Node> {
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
		
		public Node getFirst(){
			Node temp = this;
			while(this.lastNode != null){
				temp = temp.lastNode;
			}
			return temp;
		}
		@Override
		public int compareTo(Node o) {
			return (int) (this.getTotal() - o.getTotal());
		}
	
}
