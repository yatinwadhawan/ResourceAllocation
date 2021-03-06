package database;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import mainClass.MainClass;
import classes.NetworkComponent;
import classes.Node;
import classes.RewardVariables;
import classes.Vulnerability;

public class ConfigureDatabase {

	public static void loadVulnerabilities() {
		String fileName = MainClass.FILENAME + "Vulnerability";
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
		String fileName = MainClass.FILENAME + "NetworkComponent";
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
		String fileName = MainClass.FILENAME + "NetworkToVul";
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
		String fileName = MainClass.FILENAME + "ComptoComp";
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
		String fileName = MainClass.FILENAME + "Functions";
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

					// We are not taking rewards from the function file rather
					// we are computing in the node importance class.
					f.setReward(Double.parseDouble(arr[2]));

					// We have to update this probability via Bayesian Network
					// later on when we implement Bayesian algorithm.
					f.setCompromiseProb(Double.parseDouble(arr[3]));

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
		String fileName = MainClass.FILENAME + "FunctionConnect";
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
					f.setAdjList(adj);
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
		String fileName = MainClass.FILENAME + "FunctionNetworkComp";
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
					f.setNetworkComponentList(nclist);

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

	public static void loadRewardVariablesDescription() {
		String fileName = MainClass.FILENAME + "RewardVariables";
		String line = null;
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			bufferedReader.readLine();
			while ((line = bufferedReader.readLine()) != null) {
				if (line.length() != 0) {
					String[] arr = line.split(",");
					RewardVariables r = new RewardVariables();
					r.setName(arr[0]);
					r.setSymbol(arr[1]);
					r.setDescription(arr[2]);
					r.setValue(0.0);

					MainClass.rewardVariable.put(r.getSymbol(), r);
					MainClass.rewardList.add(r);
				}
			}

			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}
	}

	public static void loadRewardVariableValues() {
		String fileName = MainClass.FILENAME + "RewardValues";
		String line = null;
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			bufferedReader.readLine();
			while ((line = bufferedReader.readLine()) != null) {
				if (line.length() != 0) {
					String[] arr = line.split(":");
					String node = arr[0];
					String[] value = arr[1].split(",");
					ArrayList<RewardVariables> ls = new ArrayList<RewardVariables>();

					RewardVariables av = MainClass.rewardVariable.get("AV")
							.clone();
					av.setValue(Double.parseDouble(value[0]));
					ls.add(av);
					RewardVariables ro = MainClass.rewardVariable.get("RO")
							.clone();
					ro.setValue(Double.parseDouble(value[1]));
					ls.add(ro);
					RewardVariables re = MainClass.rewardVariable.get("RE")
							.clone();
					re.setValue(Double.parseDouble(value[2]));
					ls.add(re);
					RewardVariables poc = MainClass.rewardVariable.get("POC")
							.clone();
					poc.setValue(Double.parseDouble(value[3]));
					ls.add(poc);

					MainClass.rewardValueMap.put(node, ls);
				}
			}

			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}
	}
}
