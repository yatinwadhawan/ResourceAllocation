package UI;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.src.classes.Functions;
import com.src.classes.NetworkComponent;

public class CButton extends Button {

	Functions function;
	NetworkComponent nc;
	Shell shell;
	int x;
	int y;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public CButton(Composite parent, int style, Functions f) {
		super(parent, style);
		this.function = f;
		this.shell = (Shell) parent;
	}

	public CButton(Composite parent, int style, NetworkComponent nc) {
		super(parent, style);
		this.nc = nc;
		this.shell = (Shell) parent;
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public Functions getFunction() {
		return function;
	}

	public void setFunction(Functions function) {
		this.function = function;
	}

	public NetworkComponent getNc() {
		return nc;
	}

	public void setNc(NetworkComponent nc) {
		this.nc = nc;
	}

}
