package database;

import java.util.ArrayList;

import mainClass.MainClass;
import classes.NetworkComponent;
import classes.Node;
import classes.RewardVariables;
import classes.Vulnerability;

public class InstallDatabase {

	public static void install() {
		ConfigureDatabase.loadVulnerabilities();
		ConfigureDatabase.loadNetworkComponent();
		ConfigureDatabase.loadNetworkComponentToVul();
		ConfigureDatabase.loadComponentToComponent();
		ConfigureDatabase.loadFunctions();
		ConfigureDatabase.loadFunctionToFunctions();
		ConfigureDatabase.loadFuntionToComponent();
		// ConfigureDatabase.loadRewardVariablesDescription();
		// ConfigureDatabase.loadRewardVariableValues();
	}

	public static void print() {
		for (int i = 0; i < MainClass.vulnerabilityList.size(); i++) {
			MainClass.vulnerabilityList.get(i).print();
			System.out.println("");
		}

		for (int i = 0; i < MainClass.networkList.size(); i++) {
			MainClass.networkList.get(i).print();
			System.out.println("");
		}

		for (int i = 0; i < MainClass.nodeList.size(); i++) {
			MainClass.nodeList.get(i).print();
			System.out.println("");
		}
	}

	public static void printReward() {
		for (int i = 0; i < MainClass.rewardList.size(); i++) {
			MainClass.rewardList.get(i).print();
			System.out.println("");
		}

		for (int i = 0; i < MainClass.nodeList.size(); i++) {
			ArrayList<RewardVariables> ls = MainClass.rewardValueMap
					.get(MainClass.nodeList.get(i).getSymbol());
			for (int j = 0; j < ls.size(); j++) {
				ls.get(j).print();
				System.out.println("");
			}

			System.out.println("");
		}
	}

}
