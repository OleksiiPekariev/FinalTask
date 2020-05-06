package ua.nure.pekariev.dao.impl.rsh;

import java.sql.ResultSet;
import java.sql.SQLException;

import ua.nure.pekariev.model.Car;

public class CarResultSetHandler {

	public Car handle(ResultSet rs) throws SQLException {
		Car result = new Car();
		rs.beforeFirst();
		if (rs.next()) {
			result.setId(rs.getLong("id"));
			result.setManufacturer(rs.getString("name"));
			result.setModel(rs.getString("model"));
			result.setCarClass(rs.getString("class"));
			result.setStateNumber(rs.getString("state_number"));
			result.setYear(rs.getInt("year"));
			result.setRentValuePerDay(rs.getInt("rent_value_per_day"));
			result.setEquipmentInformation(rs.getString("equipment_information"));
			result.setAirConditioner(rs.getBoolean("air_conditioner"));
			result.setNavigation(rs.getBoolean("navigation"));
			return result;
		} else {
			return null;
		}
	}

}
