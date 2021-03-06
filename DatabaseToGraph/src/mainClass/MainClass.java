package mainClass;

import java.util.ArrayList;
import java.util.HashMap;

import classes.NetworkComponent;
import classes.Node;
import classes.Vulnerability;

public class MainClass {

	// Path of the database
	static String filename = "/Users/yatinwadhawan/Documents/Projects/DatabaseToGraph/src/database/";

	// Data Structure storing vulnerabilities.
	static ArrayList<Vulnerability> vulnerabilityList = new ArrayList<Vulnerability>();
	static HashMap<String, Vulnerability> vulnerabilityMap = new HashMap<String, Vulnerability>();
	static ArrayList<NetworkComponent> networkList = new ArrayList<NetworkComponent>();
	static HashMap<String, NetworkComponent> networkMap = new HashMap<String, NetworkComponent>();
	static ArrayList<Node> nodeList = new ArrayList<Node>();
	static HashMap<String, Node> nodeMap = new HashMap<String, Node>();

	public static void main(String[] str) {

		ConfigureDatabase.loadVulnerabilities();
		ConfigureDatabase.loadNetworkComponent();
		ConfigureDatabase.loadNetworkComponentToVul();
		ConfigureDatabase.loadComponentToComponent();
		ConfigureDatabase.loadFunctions();
		ConfigureDatabase.loadFunctionToFunctions();
		ConfigureDatabase.loadFuntionToComponent();

		for (int i = 0; i < vulnerabilityList.size(); i++) {
			Vulnerability v = vulnerabilityList.get(i);
			v.print();
			System.out.println("");
		}

		for (int i = 0; i < networkList.size(); i++) {
			NetworkComponent v = networkList.get(i);
			v.print();
			System.out.println("");
		}

		for (int i = 0; i < nodeList.size(); i++) {
			Node v = nodeList.get(i);
			v.print();
			System.out.println("");
		}

	}

}
