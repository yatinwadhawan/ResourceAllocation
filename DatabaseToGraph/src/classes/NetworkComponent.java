package classes;

import java.util.ArrayList;

public class NetworkComponent {

	private String name;
	private String details;
	private double compromiseProb;
	private ArrayList<Vulnerability> vulnerabilityList;
	private ArrayList<NetworkComponent> networkComponentList;

	public NetworkComponent() {
		System.out.println("Network Component Created");
		this.name = "";
		this.details = "";
		this.compromiseProb = 0.0;
		this.vulnerabilityList = new ArrayList<Vulnerability>();
		this.networkComponentList = new ArrayList<NetworkComponent>();
	}

	public NetworkComponent(String name, String details, double cprob,
			ArrayList<Vulnerability> vlist, ArrayList<NetworkComponent> nclist) {
		this.name = name;
		this.details = details;
		this.compromiseProb = cprob;
		this.vulnerabilityList = vlist;
		this.networkComponentList = nclist;
	}

	public void print() {
		System.out.println("Name: " + this.name);
		System.out.println("Function: " + this.details);
		System.out.println("Compromised Probability: " + this.compromiseProb);
		System.out.println("Vulnerability List: " + this.vulnerabilityList);
		System.out.println("Network Components: " + this.networkComponentList);
	}

	public static void print(NetworkComponent nc) {
		System.out.println("Name: " + nc.name);
		System.out.println("Function: " + nc.details);
		System.out.println("Compromised Probability: " + nc.compromiseProb);
		System.out.println("Vulnerability List: " + nc.vulnerabilityList);
		System.out.println("Network Components: " + nc.networkComponentList);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public double getCompromiseProb() {
		return compromiseProb;
	}

	public void setCompromiseProb(double compromiseProb) {
		this.compromiseProb = compromiseProb;
	}

	public ArrayList<Vulnerability> getVlist() {
		return vulnerabilityList;
	}

	public void setVlist(ArrayList<Vulnerability> vlist) {
		this.vulnerabilityList = vlist;
	}

	public ArrayList<NetworkComponent> getNclist() {
		return networkComponentList;
	}

	public void setNclist(ArrayList<NetworkComponent> nclist) {
		this.networkComponentList = nclist;
	}

}
