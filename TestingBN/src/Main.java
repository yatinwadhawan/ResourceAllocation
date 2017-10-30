import norsys.netica.*;
import norsys.neticaEx.aliases.Node;

public class Main {
	public static void main(String[] args) {
		try {
			Environ env = new Environ(null);
			DrawNet dn = new DrawNet(
					args.length == 0 ? "Data Files/ChestClinic_WithVisuals.dne" : args[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
