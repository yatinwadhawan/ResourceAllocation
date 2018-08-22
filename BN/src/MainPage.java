import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.JFrame;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import com.src.classes.Functions;
import com.src.classes.NetworkComponent;
import com.src.classes.Vulnerability;

import UI.CButton;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;

public class MainPage {

	protected static Shell shell;
	static Label ncname;
	static ArrayList<Vulnerability> vlist = new ArrayList<Vulnerability>();
	static HashMap<String, Vulnerability> vmap = new HashMap<String, Vulnerability>(); // name
	static ArrayList<NetworkComponent> nclist = new ArrayList<NetworkComponent>();
	static HashMap<String, NetworkComponent> ncmap = new HashMap<String, NetworkComponent>(); // name
	static ArrayList<Functions> flist = new ArrayList<Functions>();
	static HashMap<String, Functions> fmap = new HashMap<String, Functions>(); // symbol
	static HashMap<String, CButton> mapButton = new HashMap<String, CButton>();
	static HashMap<String, CButton> mapNetwork = new HashMap<String, CButton>();
	static HashMap<String, Label> vulNetwork = new HashMap<String, Label>();
	private Label lblVulnerabilities;
	private Label lblRsmartGridResilience;

	public static void main(String[] args) {

		try {
			MainPage window = new MainPage();
			updateDatabase();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateDatabase() {
		UploadDatabase.loadVulnerabilities();
		UploadDatabase.loadNetworkComponent();
		UploadDatabase.loadComponentToComponent();
		UploadDatabase.loadNetworkComponentVul();
		UploadDatabase.loadFunctions();
		UploadDatabase.loadFunctionToFunctions();
		UploadDatabase.loadFuntionToComponent();
	}

	public void open() {
		Display display = Display.getDefault();
		createContents();
		createfunctions();
		shell.open();
		shell.layout();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

	protected void createContents() {
		shell = new Shell();
		shell.setSize(1280, 938);
		shell.setText("BAGS TOOL-DEMO");
	}

	private void createfunctions() {
		final Label fname = new Label(shell, SWT.NONE);
		fname.setFont(SWTResourceManager.getFont(
				".Helvetica Neue DeskInterface", 14, SWT.BOLD));
		fname.setBounds(90, 10, 501, 38);
		fname.setText("Function:");

		ncname = new Label(shell, SWT.NONE);
		ncname.setFont(SWTResourceManager.getFont(
				".Helvetica Neue DeskInterface", 13, SWT.BOLD));
		ncname.setBounds(90, 309, 415, 25);
		ncname.setText("Network Component:");

		lblVulnerabilities = new Label(shell, SWT.NONE);
		lblVulnerabilities.setText("Vulnerabilities");
		lblVulnerabilities.setFont(SWTResourceManager.getFont(
				".Helvetica Neue DeskInterface", 13, SWT.BOLD));
		lblVulnerabilities.setBounds(90, 491, 415, 25);

		lblRsmartGridResilience = new Label(shell, SWT.NONE);
		lblRsmartGridResilience.setFont(SWTResourceManager.getFont(
				".Helvetica Neue DeskInterface", 13, SWT.BOLD));
		lblRsmartGridResilience.setBounds(812, 11, 346, 251);
		lblRsmartGridResilience
				.setText("R,Smart Grid Resilience\nS1,Quality of Smart Meter and Electric Vehicle Reads\nS2,Quality of Smart Sync Head\nS3,Billing System Performance\nS4,Performance of Outage Management System\nS6,Quality of Data captured by Vendor\nS7,Performance of Electricity Energy Control Center\nS8,Meter Data Management\nS9,Power Generation");

		int j = 0, k = 0;
		int length = MainPage.flist.size();
		for (int i = 0; i < length; i++) {
			Functions f = MainPage.flist.get(i);
			final CButton btnNewButton = new CButton(shell, SWT.PUSH, f);
			btnNewButton.setText(f.getSymbol());
			if (i < length / 3) {
				btnNewButton.setBounds(200 + i * 150, 40, 50, 50);
				btnNewButton.setX(200 + i * 150 + 25);
				btnNewButton.setY(40 + 25);
			} else if (i <= (2 * length) / 3) {
				btnNewButton.setBounds(200 + j * 200, 130, 50, 50);
				btnNewButton.setX(200 + j * 200 + 25);
				btnNewButton.setY(130 + 25);
				j++;
			} else {
				btnNewButton.setBounds(200 + k * 250, 220, 50, 50);
				btnNewButton.setX(200 + k * 250 + 25);
				btnNewButton.setY(220 + 25);
				k++;
			}

			btnNewButton.addListener(SWT.MouseDown, new Listener() {

				public void handleEvent(Event arg0) {
					// TODO Auto-generated method stub
					if (mapNetwork.size() > 0) {
						Set s = mapNetwork.entrySet();
						Iterator itr = s.iterator();

						while (itr.hasNext()) {
							Map.Entry m = (Map.Entry) itr.next();
							CButton c = (CButton) m.getValue();
							c.dispose();
						}
						mapButton.clear();
					}
					if (vulNetwork.size() > 0) {
						Set s = vulNetwork.entrySet();
						Iterator itr = s.iterator();

						while (itr.hasNext()) {
							Map.Entry m = (Map.Entry) itr.next();
							Label c = (Label) m.getValue();
							c.dispose();
						}
						vulNetwork.clear();
					}
					ncname.setText("Network Component: ");
					Functions f = (Functions) btnNewButton.getFunction();
					fname.setText("Function - " + f.getSymbol() + ": "
							+ f.getName());
					drawNetworkBN(f);
				}
			});
			mapButton.put(f.getSymbol(), btnNewButton);
		}
		drawLinesFunction();
	}

	private static void drawNetworkBN(Functions f) {
		int j = 0;
		ArrayList<NetworkComponent> l = f.getNclist();
		int length = l.size();
		for (int i = 0; i < length; i++) {
			NetworkComponent c = l.get(i);
			final CButton btnNewButton = new CButton(shell, SWT.PUSH, c);
			btnNewButton.setText(c.getName());
			if (i < length / 2) {
				btnNewButton.setBounds(200 + i * 330, 350, 250, 50);
				btnNewButton.setX(200 + i * 330 + 125);
				btnNewButton.setY(350 + 25);
			} else {
				btnNewButton.setBounds(200 + j * 330, 420, 250, 50);
				btnNewButton.setX(200 + j * 330 + 125);
				btnNewButton.setY(420 + 25);
				j++;
			}
			btnNewButton.addListener(SWT.MouseDown, new Listener() {

				public void handleEvent(Event arg0) {
					// TODO Auto-generated method stub
					if (vulNetwork.size() > 0) {
						Set s = vulNetwork.entrySet();
						Iterator itr = s.iterator();

						while (itr.hasNext()) {
							Map.Entry m = (Map.Entry) itr.next();
							Label c = (Label) m.getValue();
							c.dispose();
						}
					}
					vulNetwork.clear();
					NetworkComponent nc = btnNewButton.getNc();
					ncname.setText("Network Component: " + nc.getName());
					drawVulnerability(nc);
				}
			});
			mapNetwork.put(c.getName(), btnNewButton);
		}
		drawLinesNetwork(l);
	}

	private static void drawVulnerability(NetworkComponent nc) {
		int j = 0;
		ArrayList<Vulnerability> l = nc.getVlist();
		int length = l.size();
		for (int i = 0; i < length; i++) {
			Vulnerability v = l.get(i);
			final Label vname = new Label(shell, SWT.WRAP);
			vname.setFont(SWTResourceManager.getFont(
					".Helvetica Neue DeskInterface", 13, SWT.NORMAL));
			vname.setBounds(200, 500 + i * 140, 600, 120);
			vname.setText("NAME: " + v.getName() + "\nDETAILS: "
					+ v.getDetails() + "\nCVSS: " + v.getScore()
					+ "\nPROBABILITY OF COMPROMISE: " + v.getProbability());
			vulNetwork.put(v.getName(), vname);
		}
	}

	private static void drawLinesFunction() {
		int length = MainPage.flist.size();
		GC gc = new GC(shell);
		for (int i = 0; i < length; i++) {
			Functions f = MainPage.flist.get(i);
			final CButton b = mapButton.get(f.getSymbol());
			ArrayList<Functions> ls = f.getFlist();
			if (ls != null) {
				int size = ls.size();
				gc.setLineWidth(2);
				for (int k = 0; k < size; k++) {
					final CButton b2 = mapButton.get(ls.get(k).getSymbol());
					if (b2 != null) {
						shell.addPaintListener(new PaintListener() {
							public void paintControl(PaintEvent e) {
								e.gc.drawLine(b.getX(), b.getY(), b2.getX(),
										b2.getY());
							}
						});
						shell.setSize(150, 150);
					}
				}
			}
		}
		gc.dispose();
	}

	private static void drawLinesNetwork(ArrayList<NetworkComponent> l) {
		int length = l.size();
		GC gc = new GC(shell);
		for (int i = 0; i < length; i++) {
			NetworkComponent f = l.get(i);
			final CButton b = mapNetwork.get(f.getName());
			ArrayList<NetworkComponent> ls = f.getNclist();
			if (ls != null) {
				int size = ls.size();
				gc.setLineWidth(2);
				for (int k = 0; k < size; k++) {
					final CButton b2 = mapNetwork.get(ls.get(k).getName());
					if (b2 != null) {
						shell.addPaintListener(new PaintListener() {
							public void paintControl(PaintEvent e) {
								e.gc.drawLine(b.getX(), b.getY(), b2.getX(),
										b2.getY());
							}
						});
						shell.setSize(150, 150);
					}
				}
			}
		}
		gc.dispose();
	}

	// private static void doShowMessageBox(String name) {
	// int style = SWT.ICON_INFORMATION | SWT.OK;
	// MessageBox dia = new MessageBox(shell, style);
	// dia.setText("Information");
	// dia.setMessage(name);
	// dia.open();
	// }
}
