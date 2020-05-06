package ua.nure.pekariev.model;

import java.io.Serializable;

public class CarClass implements Serializable, Checkable {

	private static final long serialVersionUID = -5512201977296873005L;

	private String name;
	private Boolean checked;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean isChecked) {
		this.checked = isChecked;
	}

}
