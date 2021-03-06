package com.src.classes;

import java.util.ArrayList;

public class NetworkComponent {

	String name;
	String details;
	ArrayList<Vulnerability> vlist;
	ArrayList<NetworkComponent> nclist;

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Vulnerability> getVlist() {
		return vlist;
	}

	public void setVlist(ArrayList<Vulnerability> vlist) {
		this.vlist = vlist;
	}

	public ArrayList<NetworkComponent> getNclist() {
		return nclist;
	}

	public void setNclist(ArrayList<NetworkComponent> nclist) {
		this.nclist = nclist;
	}

}
