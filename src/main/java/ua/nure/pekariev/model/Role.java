package ua.nure.pekariev.model;

import java.io.Serializable;

public class Role implements Serializable {

	private static final long serialVersionUID = 3126603328684836749L;

	private int id;
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
