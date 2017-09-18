import java.util.Arrays;
import java.util.List;

import burlap.mdp.core.state.MutableState;
import burlap.mdp.core.state.State;
import burlap.mdp.core.state.StateUtilities;
import burlap.mdp.core.state.UnknownKeyException;

public class GridState implements MutableState {

	public int x;
	public int y;

	private final static List<Object> keys = Arrays.<Object> asList(
			TestBurlap.VAR_X, TestBurlap.VAR_Y);

	public GridState(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public State copy() {
		// TODO Auto-generated method stub
		return new GridState(x, y);
	}

	@Override
	public Object get(Object variableKeys) {
		if (variableKeys.equals(TestBurlap.VAR_X))
			return x;
		else if (variableKeys.equals(TestBurlap.VAR_Y))
			return y;

		throw new UnknownKeyException(variableKeys);
	}

	@Override
	public List<Object> variableKeys() {
		// TODO Auto-generated method stub
		return keys;
	}

	@Override
	public MutableState set(Object variableKey, Object value) {
		if (variableKey.equals(TestBurlap.VAR_X))
			this.x = StateUtilities.stringOrNumber(value).intValue();
		else if (variableKey.equals(TestBurlap.VAR_Y))
			this.y = StateUtilities.stringOrNumber(value).intValue();
		else
			throw new UnknownKeyException(variableKey);

		return this;
	}

	@Override
	public String toString() {
		return StateUtilities.stateToString(this);
	}

}
