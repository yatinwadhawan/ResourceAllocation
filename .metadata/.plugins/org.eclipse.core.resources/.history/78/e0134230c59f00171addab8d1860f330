import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.src.classes.Functions;
import com.src.classes.NetworkComponent;
import com.src.classes.Vulnerability;

public class UploadDatabase {

	public static void loadVulnerabilities() {
		String fileName = "/Users/yatinwadhawan/Documents/android/BN/src/com/src/database/Vulnerability";
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
					v.setScore(v.getProbability() * 10);
					line = bufferedReader.readLine();
					v.setDetails(line);
					MainPage.vlist.add(v);
					MainPage.vmap.put(v.getName(), v);
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
		String fileName = "/Users/yatinwadhawan/Documents/android/BN/src/com/src/database/NetworkComponent";
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
					MainPage.ncmap.put(nc.getName().toUpperCase(), nc);
				}
			}

			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}
	}

	public static void loadNetworkComponentVul() {
		String fileName = "/Users/yatinwadhawan/Documents/android/BN/src/com/src/database/NetworkToVul";
		String line = null;

		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				if (line.length() != 0) {
					NetworkComponent nc = MainPage.ncmap
							.get(line.toUpperCase());
					ArrayList<Vulnerability> l = new ArrayList<Vulnerability>();
					line = bufferedReader.readLine();
					String[] arr = line.split(",");
					for (int i = 0; i < arr.length; i++) {
						String upper = arr[i].toUpperCase();
						Vulnerability v = MainPage.vmap.get(upper);
						l.add(v);
					}
					nc.setVlist(l);
					MainPage.ncmap.put(nc.getName().toUpperCase(), nc);
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
		String fileName = "/Users/yatinwadhawan/Documents/android/BN/src/com/src/database/ComptoComp";
		String line = null;

		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				if (line.length() != 0) {
					String[] arr = line.split(":");
					NetworkComponent nc = MainPage.ncmap.get(arr[0]
							.toUpperCase());
					ArrayList<NetworkComponent> nclist = new ArrayList<NetworkComponent>();
					String[] arr_2 = arr[1].split(",");
					for (int i = 0; i < arr_2.length; i++) {
						nclist.add(MainPage.ncmap.get(arr_2[i].toUpperCase()));
					}
					nc.setNclist(nclist);
					// Updating the network components in the Global list and
					// map interface.
					MainPage.nclist.add(nc);
					MainPage.ncmap.put(nc.getName().toUpperCase(), nc);
				}
			}

			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}
	}

	public static void loadFunctions() {
		String fileName = "/Users/yatinwadhawan/Documents/android/BN/src/com/src/database/Functions";
		String line = null;

		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				if (line.length() != 0) {
					Functions f = new Functions();
					String[] arr = line.split(",");
					f.setSymbol(arr[0]);
					f.setName(arr[1]);
					MainPage.fmap.put(f.getSymbol(), f);
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
		String fileName = "/Users/yatinwadhawan/Documents/android/BN/src/com/src/database/FunctionConnect";
		String line = null;

		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				if (line.length() != 0) {
					String[] arr = line.split(":");
					Functions f = MainPage.fmap.get(arr[0]);
					ArrayList<Functions> flist = new ArrayList<Functions>();
					String[] arr_2 = arr[1].split(",");
					for (int i = 0; i < arr_2.length; i++) {
						flist.add(MainPage.fmap.get(arr_2[i]));
					}
					f.setFlist(flist);
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
					Functions f = MainPage.fmap.get(arr[0]);
					ArrayList<NetworkComponent> nclist = new ArrayList<NetworkComponent>();
					String[] arr_2 = arr[1].split(",");
					for (int i = 0; i < arr_2.length; i++) {
						nclist.add(MainPage.ncmap.get(arr_2[i].toUpperCase()));
					}
					f.setNclist(nclist);
					MainPage.flist.add(f);
					MainPage.fmap.put(arr[0], f);
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
