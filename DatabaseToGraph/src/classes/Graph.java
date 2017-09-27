package classes;

import java.util.ArrayList;

public class Graph {

	private int nodes;
	private int edges;
	private Node startNode;
	private Node endNode;
	private ArrayList<Node> nodeList;

	public Graph(int n, int e, ArrayList<Node> l, Node s, Node en) {
		this.nodes = n;
		this.edges = e;
		this.nodeList = l;
		this.startNode = s;
		this.endNode = en;
	}

	public Graph copy() {
		return new Graph(nodes, edges, nodeList, startNode, endNode);
	}

	public static Graph copy(Graph g) {
		return new Graph(g.nodes, g.edges, g.nodeList, g.startNode, g.endNode);
	}

	public void print() {
		int s = this.nodeList.size();
		for (int i = 0; i < s; i++) {
			this.nodeList.get(i).print();
		}
	}

	public int getNodes() {
		return nodes;
	}

	public void setNodes(int nodes) {
		this.nodes = nodes;
	}

	public int getEdges() {
		return edges;
	}

	public void setEdges(int edges) {
		this.edges = edges;
	}

	public ArrayList<Node> getNodelist() {
		return nodeList;
	}

	public void setNodelist(ArrayList<Node> nodelist) {
		this.nodeList = nodelist;
	}

	public Node getStartNode() {
		return startNode;
	}

	public void setStartNode(Node startNode) {
		this.startNode = startNode;
	}

	public Node getEndNode() {
		return endNode;
	}

	public void setEndNode(Node endNode) {
		this.endNode = endNode;
	}

}
