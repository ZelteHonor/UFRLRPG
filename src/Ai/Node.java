package Ai;

import java.awt.Point;


/**
 * @author gabriel
 * Classe de l'objet Node telle que définis par l'algorithme A*
 */
public class Node implements Comparable<Node> {
		private Point coor;
		private Node lastNode;
		private float fromB, toE;
		
		
		/**
		 * Constructeur de node
		 * @param p
		 * 	Position X et Y du node
		 * @param lastNode
		 * 	Le node parent
		 * @param fromB
		 * 	Distance depuis le début du chemin
		 * @param toE
		 * 	Distance "estimé" jusqu'a l'objectif
		 */
		public Node(Point p, Node lastNode,float fromB,float toE)
		{
			coor = p;
			this.lastNode = lastNode;
			this.fromB = fromB;
			this.toE = toE;
		}
		
		/**
		 * @return
		 * 	L'objet point contenant les coordoners du Node
		 */
		public Point getCoor() {
			return coor;
		}
		
		/**
		 * @return
		 */
		public float getTotal()
		{
			return fromB + toE;
		}
		
		/**
		 * @return
		 * 	Retourne le premier node
		 */
		public Node getFirst(){
			Node temp = this;
			while(this.lastNode != null){
				temp = temp.lastNode;
			}
			return temp;
		}
		
		/**
		 * @return
		 * 	Retourne le premier Node et le retire
		 */
		public Node getDestructiveFirst(){
			Node temp = this;
			while(temp.lastNode != null){
				if(this.lastNode.lastNode == null){
					temp = this.lastNode;
					this.lastNode = null;
				}
			}
			return temp;
		}
		
		/** 
		 *Compare 2 node selon leur coût total
		 */
		@Override
		public int compareTo(Node o) {
			return (int) (this.getTotal() - o.getTotal());
		}
	
}
