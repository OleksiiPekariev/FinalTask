package ua.nure.pekariev.model;

import java.io.Serializable;

public class CarName implements Serializable, Checkable {
	private static final long serialVersionUID = -8018284142588809856L;

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
