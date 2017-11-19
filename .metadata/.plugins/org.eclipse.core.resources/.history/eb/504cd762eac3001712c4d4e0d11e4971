package multiagent;

import java.util.List;

import burlap.mdp.auxiliary.DomainGenerator;
import burlap.mdp.core.Domain;
import burlap.mdp.core.action.ActionType;
import burlap.mdp.stochasticgames.SGDomain;

public class JointWorldGenerator implements DomainGenerator {

	@Override
	public Domain generateDomain() {
		SGDomain domain = new SGDomain();
		StateWorld statemodel = new StateWorld();
		domain.setJointActionModel(statemodel);
		return domain;
	}

	public static List<ActionType> getDefenderActionList() {
		return null;

	}

	public static List<ActionType> getAtatckerActionList() {
		return null;

	}

}
