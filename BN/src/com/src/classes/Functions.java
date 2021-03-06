package com.src.classes;

import java.util.ArrayList;

public class Functions {

	String symbol;
	String name;
	ArrayList<Functions> flist;
	ArrayList<NetworkComponent> nclist;

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public ArrayList<NetworkComponent> getNclist() {
		return nclist;
	}

	public void setNclist(ArrayList<NetworkComponent> nclist) {
		this.nclist = nclist;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Functions> getFlist() {
		return flist;
	}

	public void setFlist(ArrayList<Functions> flist) {
		this.flist = flist;
	}

}
