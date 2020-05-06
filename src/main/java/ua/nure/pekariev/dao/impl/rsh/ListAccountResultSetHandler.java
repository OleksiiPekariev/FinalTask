package ua.nure.pekariev.dao.impl.rsh;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ua.nure.pekariev.model.Account;
import ua.nure.pekariev.model.Role;

public class ListAccountResultSetHandler {

	public List<Account> handle(ResultSet rs) throws SQLException {
		List<Account> result = new ArrayList<>();
		while (rs.next()) {
			Account account = new Account();
			Role role = new Role();
			account.setId(rs.getLong("id"));
			account.setFirstName(rs.getString("first_name"));
			account.setLastName(rs.getString("last_name"));
			account.setEmail(rs.getString("email"));
			account.setPassportData(rs.getString("passport_data"));
			account.setPassword(rs.getString("password"));
			account.setCreated(new Date(rs.getTimestamp("created").getTime()));
			account.setActive(rs.getBoolean("active"));
			account.setPhone(rs.getLong("phone"));
			account.setAddress(rs.getString("address"));

			role.setId(rs.getInt("role_id"));
			role.setName(rs.getString("role"));
			account.setRole(role);
			result.add(account);
		}
		return result;
	}

}
