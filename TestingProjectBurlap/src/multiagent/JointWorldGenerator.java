package multiagent;

import java.util.ArrayList;
import QLearning.MAction;
import QLearning.MainClass;
import burlap.mdp.auxiliary.DomainGenerator;
import burlap.mdp.core.Domain;
import burlap.mdp.core.action.ActionType;
import burlap.mdp.core.action.UniversalActionType;
import burlap.mdp.stochasticgames.SGDomain;

public class JointWorldGenerator implements DomainGenerator {

	@Override
	public Domain generateDomain() {
		SGDomain domain = new SGDomain();
		StateWorld statemodel = new StateWorld();
		domain.setJointActionModel(statemodel);
		return domain;
	}

	public static ArrayList<ActionType> getDefenderActionList() {
		ArrayList<ActionType> output = new ArrayList<ActionType>();
		for (int i = 0; i < MainClass.nlist.size(); i++) {
			MAction ms = new MAction(MainClass.nlist.get(i).getName(),
					MainClass.ACTION_SCAN);
			MAction mp = new MAction(MainClass.nlist.get(i).getName(),
					MainClass.ACTION_PATCH);
			output.add(new UniversalActionType(ms));
			output.add(new UniversalActionType(mp));
		}
		return output;
	}
}
