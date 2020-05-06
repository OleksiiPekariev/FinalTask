package ua.nure.pekariev.dao.impl.rsh;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import ua.nure.pekariev.model.Account;
import ua.nure.pekariev.model.Role;

public class AccountResultSetHandler {

	public Account handle(ResultSet rs) throws SQLException {
		Account result = new Account();
		Role role = new Role();
		rs.beforeFirst();
		if (rs.next()) {
			result.setId(rs.getLong("id"));
			result.setFirstName(rs.getString("first_name"));
			result.setLastName(rs.getString("last_name"));
			result.setEmail(rs.getString("email"));
			result.setPassportData(rs.getString("passport_data"));
			result.setPassword(rs.getString("password"));
			result.setCreated(new Date(rs.getTimestamp("created").getTime()));
			result.setActive(rs.getBoolean("active"));
			if (rs.getLong("phone") != 0) {
				result.setPhone(rs.getLong("phone"));
			}
			result.setAddress(rs.getString("address"));

			role.setId(rs.getInt("role_id"));
			role.setName(rs.getString("role"));
			result.setRole(role);
			return result;
		} else {
			return null;
		}
	}

}
