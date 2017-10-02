package classes;

import java.util.ArrayList;
import java.util.Objects;

public class Node {
	// Node represents the function node that is displayed on the dashboard. The
	// responsibility of the defender is to perform resource allocation on these
	// node. For example scan node, patch node etc.

	private String symbol;
	private String name;
	private String function;
	private String status;
	private double compromiseProb;
	private int reward;
	private ArrayList<Node> adjList;
	private ArrayList<NetworkComponent> networkComponentList;

	@Override
	public boolean equals(Object obj) {
		Node that = (Node) obj;
		if (this.symbol == that.symbol && this.name == that.name
				&& this.status == that.status)
			return true;
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(symbol, name, function, status, compromiseProb,
				reward, adjList, networkComponentList);
	}

	public Node copy() {
		Node copy = new Node();
		copy.symbol = this.symbol;
		copy.name = this.name;
		copy.function = this.function;
		copy.status = this.status;
		copy.compromiseProb = this.compromiseProb;
		copy.reward = this.reward;
		copy.adjList = (ArrayList<Node>) this.adjList.clone();
		copy.networkComponentList = (ArrayList<NetworkComponent>) this.networkComponentList
				.clone();
		return copy;
	}

	public Node() {
		// System.out.println("Node created");
		this.symbol = "";
		this.name = "";
		this.function = "";
		this.status = NodeStatus.UNKNOWN;
		this.compromiseProb = 0.0;
		this.adjList = new ArrayList<Node>();
		this.networkComponentList = new ArrayList<NetworkComponent>();
	}

	public Node(String symbol, String name, String function, String status,
			String pstatus, double prob, ArrayList<Node> adj,
			ArrayList<NetworkComponent> nclist, int reward) {
		this.symbol = symbol;
		this.name = name;
		this.function = function;
		this.status = status;
		this.compromiseProb = prob;
		this.adjList = adj;
		this.networkComponentList = nclist;
		this.reward = reward;
	}

	public void print() {
		System.out.println("Symbol - " + this.symbol);
		System.out.println("Name - " + this.name);
		System.out.println("Function - " + this.function);
		System.out.println("Status - " + this.status);
		System.out.println("Probability - " + this.compromiseProb);
		System.out.println("Reward - " + this.reward);
		System.out.println("Adjacency List - " + this.adjList);
		System.out.println("Network Components List - "
				+ this.networkComponentList);
	}

	public static void print(Node n) {
		System.out.println("Symbol - " + n.symbol);
		System.out.println("Name - " + n.name);
		System.out.println("Function - " + n.function);
		System.out.println("Status - " + n.status);
		System.out.println("Probability - " + n.compromiseProb);
		System.out.println("Reward - " + n.reward);
		System.out.println("Adjacency List - " + n.adjList);
		System.out.println("Network Components List - "
				+ n.networkComponentList);
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getCompromiseProb() {
		return compromiseProb;
	}

	public void setCompromiseProb(double compromiseProb) {
		this.compromiseProb = compromiseProb;
	}

	public ArrayList<Node> getAdj() {
		return adjList;
	}

	public void setAdj(ArrayList<Node> adj) {
		this.adjList = adj;
	}

	public ArrayList<NetworkComponent> getNclist() {
		return networkComponentList;
	}

	public void setNclist(ArrayList<NetworkComponent> nclist) {
		this.networkComponentList = nclist;
	}

	public int getReward() {
		return reward;
	}

	public void setReward(int reward) {
		this.reward = reward;
	}

	public ArrayList<Node> getAdjList() {
		return adjList;
	}

	public void setAdjList(ArrayList<Node> adjList) {
		this.adjList = adjList;
	}

	public ArrayList<NetworkComponent> getNetworkComponentList() {
		return networkComponentList;
	}

	public void setNetworkComponentList(
			ArrayList<NetworkComponent> networkComponentList) {
		this.networkComponentList = networkComponentList;
	}

}
