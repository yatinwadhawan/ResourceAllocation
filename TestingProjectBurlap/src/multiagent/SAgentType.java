package multiagent;

import java.util.ArrayList;
import java.util.List;

import QLearning.MAction;
import QLearning.MainClass;
import burlap.mdp.core.action.ActionType;
import burlap.mdp.core.action.UniversalActionType;
import burlap.mdp.stochasticgames.agent.SGAgentType;

public class SAgentType extends SGAgentType {

	private String typeName;
	private List<ActionType> actionsAvailableToType;
	private int agentNum;

	public SAgentType(String typeName, int num, ArrayList<ActionType> arrayList) {
		super(typeName, arrayList);
		this.typeName = typeName;
		this.agentNum = num;
		this.actionsAvailableToType = arrayList;
	}

	public void clearActions() {
		this.actionsAvailableToType.clear();
	}

	// For Attacker
	public void updateActionList(int index) {
		WorldForMultiAgent.attackerNode = MainClass.nlist.get(index).copy();
		for (int i = 0; i < MainClass.nlist.get(index).getAdj().size(); i++) {
			MAction s = new MAction(MainClass.nlist.get(index).getAdj().get(i)
					.getName(), MainClass.ACTION_SCAN);
			MAction p = new MAction(MainClass.nlist.get(index).getAdj().get(i)
					.getName(), MainClass.ACTION_HACK);
			this.addAction(s);
			this.addAction(p);
		}
	}

	public void addAction(MAction a) {
		for (int i = 0; i < this.actionsAvailableToType.size(); i++) {
			UniversalActionType u = (UniversalActionType) this.actionsAvailableToType
					.get(i);
			MAction temp = (MAction) u.action;
			if (a.getActionName().equals(temp.getActionName()))
				return;
		}
		this.actionsAvailableToType.add(new UniversalActionType(a));
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public List<ActionType> getActionsAvailableToType() {
		return actionsAvailableToType;
	}

	public void setActionsAvailableToType(
			List<ActionType> actionsAvailableToType) {
		this.actionsAvailableToType = actionsAvailableToType;
	}

	public int getAgentNum() {
		return agentNum;
	}

	public void setAgentNum(int agentNum) {
		this.agentNum = agentNum;
	}

}
