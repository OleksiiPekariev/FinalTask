package ua.nure.pekariev.dao.impl.rsh;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ua.nure.pekariev.model.Account;
import ua.nure.pekariev.model.Car;
import ua.nure.pekariev.model.Order;
import ua.nure.pekariev.model.Role;

public class ListOrdersResultSetHandler {
	public List<Order> handle(ResultSet rs) throws SQLException {

		List<Order> orders = new ArrayList<Order>();

		while (rs.next()) {
			Order result = new Order();
			Car car = new Car();
			Account account = new Account();
			Role role = new Role();
			result.setId(rs.getLong("id"));
			result.setCreated(new Date(rs.getTimestamp("created").getTime()));
			try {
				result.setConfirmed(new Date(rs.getTimestamp("confirmed").getTime()));
			} catch (NullPointerException e) {
				result.setConfirmed(null);
			}
			result.setStarts(new Date(rs.getTimestamp("starts").getTime()));
			result.setExpires(new Date(rs.getTimestamp("expires").getTime()));
			result.setDaysAmount(rs.getLong("days_amount"));
			result.setTotalRentValue(rs.getLong("total_rent_value"));
			result.setStatus(rs.getShort("status"));
			result.setStatusComment(rs.getString("status_comment"));
			result.setWithDriver(rs.getBoolean("driver"));
			result.setPaid(rs.getBoolean("paid"));

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
			result.setCar(car);

			account.setId(rs.getLong("id"));
			account.setFirstName(rs.getString("first_name"));
			account.setLastName(rs.getString("last_name"));
			account.setEmail(rs.getString("email"));
			account.setPassportData(rs.getString("passport_data"));
			account.setCreated(new Date(rs.getTimestamp("created").getTime()));
			account.setActive(rs.getBoolean("active"));
			account.setPhone(rs.getLong("phone"));
			account.setAddress(rs.getString("phone"));

			role.setId(rs.getInt("role_id"));
			role.setName(rs.getString("role"));
			account.setRole(role);

			result.setAccount(account);

			orders.add(result);
		}
		if (orders.size() > 0) {
			return orders;
		} else {
			return null;
		}

	}
}
