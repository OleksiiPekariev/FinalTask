package ua.nure.pekariev.dao.impl.rsh;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.nure.pekariev.model.Car;

public class ListCarResultSetHandler {

	public List<Car> handle(ResultSet rs) throws SQLException {
		List<Car> result = new ArrayList<>();
		while (rs.next()) {
			Car car = new Car();
			car.setId(rs.getLong("id"));
			car.setManufacturer(rs.getString("name"));
			car.setModel(rs.getString("model"));
			car.setCarClass(rs.getString("class"));
			car.setStateNumber(rs.getString("state_number"));
			car.setYear(rs.getInt("year"));
			car.setRentValuePerDay(rs.getInt("rent_value_per_day"));
			car.setEquipmentInformation(rs.getString("equipment_information"));
			car.setAirConditioner(rs.getBoolean("air_conditioner"));
			car.setNavigation(rs.getBoolean("navigation"));
			result.add(car);
		}
		return result;
	}

}
