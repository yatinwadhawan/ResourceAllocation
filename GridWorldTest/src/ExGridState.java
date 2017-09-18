import burlap.mdp.core.state.MutableState;
import burlap.mdp.core.state.StateUtilities;
import burlap.mdp.core.state.UnknownKeyException;
import burlap.mdp.core.state.annotations.DeepCopyState;

import java.util.Arrays;
import java.util.List;

@DeepCopyState
public class ExGridState implements MutableState {

	public int x;
	public int y;

	private final static List<Object> keys = Arrays.<Object> asList(
			ExampleGridWorld.VAR_X, ExampleGridWorld.VAR_Y);

	public ExGridState() {
	}

	public ExGridState(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public MutableState set(Object variableKey, Object value) {
		if (variableKey.equals(ExampleGridWorld.VAR_X)) {
			this.x = StateUtilities.stringOrNumber(value).intValue();
		} else if (variableKey.equals(ExampleGridWorld.VAR_Y)) {
			this.y = StateUtilities.stringOrNumber(value).intValue();
		} else {
			throw new UnknownKeyException(variableKey);
		}
		return this;
	}

	public List<Object> variableKeys() {
		return keys;
	}

	@Override
	public Object get(Object variableKey) {
		if (variableKey.equals(ExampleGridWorld.VAR_X)) {
			return x;
		} else if (variableKey.equals(ExampleGridWorld.VAR_Y)) {
			return y;
		}
		throw new UnknownKeyException(variableKey);
	}

	@Override
	public ExGridState copy() {
		return new ExGridState(x, y);
	}

	@Override
	public String toString() {
		return StateUtilities.stateToString(this);
	}
}
