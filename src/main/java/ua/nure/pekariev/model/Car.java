package ua.nure.pekariev.model;

import java.io.Serializable;

public class Car implements Serializable {
	private static final long serialVersionUID = 5117046559315794028L;

	private Long id;
	private String manufacturer;
	private String model;
	private String carClass;
	private String stateNumber;
	private int year;
	private int rentValuePerDay;
	private String equipmentInformation;
	private boolean airConditioner;
	private boolean navigation;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getCarClass() {
		return carClass;
	}

	public void setCarClass(String carClass) {
		this.carClass = carClass;
	}

	public String getStateNumber() {
		return stateNumber;
	}

	public void setStateNumber(String stateNumber) {
		this.stateNumber = stateNumber;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getRentValuePerDay() {
		return rentValuePerDay;
	}

	public void setRentValuePerDay(int rentValuePerDay) {
		this.rentValuePerDay = rentValuePerDay;
	}

	public String getEquipmentInformation() {
		return equipmentInformation;
	}

	public void setEquipmentInformation(String equipmentInformation) {
		this.equipmentInformation = equipmentInformation;
	}

	public boolean isAirConditioner() {
		return airConditioner;
	}

	public void setAirConditioner(boolean air_conditioner) {
		this.airConditioner = air_conditioner;
	}

	public boolean isNavigation() {
		return navigation;
	}

	public void setNavigation(boolean navigation) {
		this.navigation = navigation;
	}

}
