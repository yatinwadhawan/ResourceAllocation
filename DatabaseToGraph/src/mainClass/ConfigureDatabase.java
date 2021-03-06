package mainClass;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import classes.NetworkComponent;
import classes.Node;
import classes.Vulnerability;

public class ConfigureDatabase {

	public static void loadVulnerabilities() {
		String fileName = MainClass.filename + "Vulnerability";
		String line = null;

		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				if (line.length() != 0) {
					Vulnerability v = new Vulnerability();
					v.setName(line.toUpperCase());
					line = bufferedReader.readLine();
					v.setProbability(Double.parseDouble(line));
					v.setCvssScore(v.getProbability() * 10);
					line = bufferedReader.readLine();
					v.setDetails(line);

					MainClass.vulnerabilityList.add(v);
					MainClass.vulnerabilityMap.put(v.getName(), v);
				}
			}

			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}
	}

	public static void loadNetworkComponent() {
		String fileName = MainClass.filename + "NetworkComponent";
		String line = null;

		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				if (line.length() != 0) {
					NetworkComponent nc = new NetworkComponent();
					nc.setName(line.toUpperCase());
					line = bufferedReader.readLine();
					nc.setDetails(line);

					// Adding Network Components correpsonding their names in
					// the Map
					MainClass.networkMap.put(nc.getName().toUpperCase(), nc);
				}
			}

			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}
	}

	public static void loadNetworkComponentToVul() {
		String fileName = MainClass.filename + "NetworkToVul";
		String line = null;

		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				if (line.length() != 0) {
					NetworkComponent nc = MainClass.networkMap.get(line
							.toUpperCase());
					ArrayList<Vulnerability> l = new ArrayList<Vulnerability>();
					line = bufferedReader.readLine();
					String[] arr = line.split(",");
					for (int i = 0; i < arr.length; i++) {
						String upper = arr[i].toUpperCase();
						Vulnerability v = MainClass.vulnerabilityMap.get(upper);
						l.add(v);
					}
					nc.setVlist(l);

					// Update Network Component with list of vulnerabilities.
					MainClass.networkMap.put(nc.getName().toUpperCase(), nc);
				}
			}

			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}
	}

	public static void loadComponentToComponent() {
		String fileName = MainClass.filename + "ComptoComp";
		String line = null;

		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				if (line.length() != 0) {
					String[] arr = line.split(":");
					NetworkComponent nc = MainClass.networkMap.get(arr[0]
							.toUpperCase());
					ArrayList<NetworkComponent> nclist = new ArrayList<NetworkComponent>();
					String[] arr_2 = arr[1].split(",");
					for (int i = 0; i < arr_2.length; i++) {
						nclist.add(MainClass.networkMap.get(arr_2[i]
								.toUpperCase()));
					}
					nc.setNclist(nclist);

					// Updating the network components in the map interface.
					MainClass.networkMap.put(nc.getName().toUpperCase(), nc);
				}
			}

			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}

		Set set = MainClass.networkMap.keySet();
		Iterator itr = set.iterator();
		while (itr.hasNext()) {
			String name = (String) itr.next();
			MainClass.networkList.add(MainClass.networkMap.get(name));
		}
	}

	public static void loadFunctions() {
		String fileName = MainClass.filename + "Functions";
		String line = null;

		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				if (line.length() != 0) {
					Node f = new Node();
					String[] arr = line.split(",");
					f.setSymbol(arr[0]);
					f.setName(arr[1]);
					f.setFunction(arr[1]);

					MainClass.nodeMap.put(f.getSymbol(), f);
				}
			}

			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}
	}

	public static void loadFunctionToFunctions() {
		String fileName = MainClass.filename + "FunctionConnect";
		String line = null;

		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				if (line.length() != 0) {
					String[] arr = line.split(":");
					Node f = MainClass.nodeMap.get(arr[0]);

					ArrayList<Node> adj = new ArrayList<Node>();
					String[] arr_2 = arr[1].split(",");
					for (int i = 0; i < arr_2.length; i++) {
						adj.add(MainClass.nodeMap.get(arr_2[i]));
					}
					f.setAdj(adj);
				}
			}

			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}
	}

	public static void loadFuntionToComponent() {
		String fileName = "/Users/yatinwadhawan/Documents/android/BN/src/com/src/database/FunctionNetworkComp";
		String line = null;

		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				if (line.length() != 0) {
					String[] arr = line.split(":");
					Node f = MainClass.nodeMap.get(arr[0]);
					ArrayList<NetworkComponent> nclist = new ArrayList<NetworkComponent>();
					String[] arr_2 = arr[1].split(",");
					for (int i = 0; i < arr_2.length; i++) {
						nclist.add(MainClass.networkMap.get(arr_2[i]
								.toUpperCase()));
					}
					f.setNclist(nclist);

					MainClass.nodeMap.put(arr[0], f);
				}
			}

			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}

		// Updating Node List Finally
		Set set = MainClass.nodeMap.keySet();
		Iterator itr = set.iterator();
		while (itr.hasNext()) {
			String name = (String) itr.next();
			MainClass.nodeList.add(MainClass.nodeMap.get(name));
		}
	}

}
