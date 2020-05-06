package ua.nure.pekariev.dao.impl;

import static ua.nure.pekariev.queries.AccountDaoQueries.CREATE_ACCOUNT_WITH_LOGIN_PASS_QUERY;
import static ua.nure.pekariev.queries.AccountDaoQueries.GET_ACOUNT_BY_ID_QUERY;
import static ua.nure.pekariev.queries.AccountDaoQueries.GET_ALL_ACCOUNTS_QUERY;
import static ua.nure.pekariev.queries.AccountDaoQueries.GET_ALL_ACCOUNTS_WITH_ROLE_QUERY;
import static ua.nure.pekariev.queries.AccountDaoQueries.GET_ALL_ACCOUNTS_BY_LOGIN_QUERY;
import static ua.nure.pekariev.queries.AccountDaoQueries.INSERT_ROLE_FOR_CREATED_ACCOUNT_QUERY;
import static ua.nure.pekariev.queries.AccountDaoQueries.UPDATE_ACCOUNT_DATA_QUERY;
import static ua.nure.pekariev.queries.AccountDaoQueries.UPDATE_ROLE_FOR_ACCOUNT_QUERY;
import static ua.nure.pekariev.queries.AccountDaoQueries.COUNT_ACCOUNTS_QUERY;
import static ua.nure.pekariev.queries.AccountDaoQueries.COUNT_ACCOUNTS_WITH_ROLE_QUERY;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.nure.pekariev.dao.AccountDao;
import ua.nure.pekariev.dao.impl.rsh.AccountResultSetHandler;
import ua.nure.pekariev.dao.impl.rsh.ListAccountResultSetHandler;
import ua.nure.pekariev.exceptions.ApplicationException;
import ua.nure.pekariev.model.Account;
import ua.nure.pekariev.utils.ConnectionUtils;

public class AccountDaoImpl implements AccountDao {

	private static final Logger LOG = LoggerFactory.getLogger(AccountDaoImpl.class);

	public Account getAccountByLoginOrEmail(String login) {
		LOG.debug("Trying to retrive account by login : '{}'", login);
		Connection connection = ConnectionUtils.getConnection();
		try (PreparedStatement st = connection.prepareStatement(GET_ALL_ACCOUNTS_BY_LOGIN_QUERY,
				ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
			st.setString(1, login);
			ResultSet rs = st.executeQuery();
			AccountResultSetHandler rsh = new AccountResultSetHandler();
			return rsh.handle(rs);
		} catch (SQLException e) {
			throw new ApplicationException(e);
		}
	}

	@Override
	public boolean remove(Long accountID) {
		return false;
	}

	@Override
	public Long createAccount(String email, String password, String firstName, String lastName) {
		Connection connection = ConnectionUtils.getConnection();
		try (PreparedStatement st = connection.prepareStatement(CREATE_ACCOUNT_WITH_LOGIN_PASS_QUERY,
				Statement.RETURN_GENERATED_KEYS)) {
			st.setString(1, email);
			st.setString(2, password);
			st.setString(3, firstName);
			st.setString(4, lastName);
			st.executeUpdate();
			ResultSet rs = st.getGeneratedKeys();
			rs.next();
			return rs.getLong("id");
		} catch (SQLException e) {
			throw new ApplicationException(e);
		}
	}

	public Account getAccountById(Long id) {
		Connection connection = ConnectionUtils.getConnection();
		try (PreparedStatement st = connection.prepareStatement(GET_ACOUNT_BY_ID_QUERY,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
			st.setLong(1, id);
			ResultSet rs = st.executeQuery();
			return new AccountResultSetHandler().handle(rs);
		} catch (SQLException e) {
			throw new ApplicationException(e);
		}
	}

	public Account getAccountById(Connection connection, Long id) {
		try (PreparedStatement st = connection.prepareStatement(GET_ACOUNT_BY_ID_QUERY,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
			st.setLong(1, id);
			ResultSet rs = st.executeQuery();
			return new AccountResultSetHandler().handle(rs);
		} catch (SQLException e) {
			throw new ApplicationException(e);
		}
	}

	public void insertRoleForAccount(Long accountId) {
		Connection connection = ConnectionUtils.getConnection();
		try (PreparedStatement st = connection.prepareStatement(INSERT_ROLE_FOR_CREATED_ACCOUNT_QUERY)) {
			st.setLong(1, accountId);
			st.executeUpdate();
		} catch (SQLException e) {
			throw new ApplicationException(e);
		}
	}

	@Override
	public Account updateAccountData(Account account) {
		Connection connection = ConnectionUtils.getConnection();
		try (PreparedStatement st = connection.prepareStatement(UPDATE_ACCOUNT_DATA_QUERY,
				Statement.RETURN_GENERATED_KEYS)) {
			st.setString(1, account.getFirstName());
			st.setString(2, account.getLastName());
			st.setString(3, account.getEmail());
			st.setString(4, account.getPassportData());
			st.setString(5, account.getAddress());
			st.setLong(6, account.getPhone());
			st.setLong(7, account.getId());
			st.executeUpdate();
			ResultSet rs = st.getGeneratedKeys();
			rs.next();
			Long insertedAccountId = rs.getLong("id");
			return getAccountById(connection, insertedAccountId);
		} catch (SQLException e) {
			throw new ApplicationException(e);
		}
	}

	@Override
	public List<Account> getAll(int limit, int offset) {
		Connection connection = ConnectionUtils.getConnection();
		try (PreparedStatement st = connection.prepareStatement(GET_ALL_ACCOUNTS_QUERY, ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY)) {
			st.setInt(1, limit);
			st.setInt(2, offset);
			ResultSet rs = st.executeQuery();
			return new ListAccountResultSetHandler().handle(rs);
		} catch (SQLException e) {
			throw new ApplicationException(e);
		}
	}

	@Override
	public Long countAllAccounts() {
		Connection connection = ConnectionUtils.getConnection();
		try (Statement st = connection.createStatement()) {
			ResultSet rs = st.executeQuery(COUNT_ACCOUNTS_QUERY);
			if (rs.next()) {
				return rs.getLong("count");
			} else {
				return 0L;
			}
		} catch (SQLException e) {
			throw new ApplicationException(e);
		}
	}

	@Override
	public Long countAccountsWithRole(String role) {
		Connection connection = ConnectionUtils.getConnection();
		try (PreparedStatement st = connection.prepareStatement(COUNT_ACCOUNTS_WITH_ROLE_QUERY)) {
			st.setString(1, role);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				return rs.getLong("count");
			} else {
				return 0L;
			}
		} catch (SQLException e) {
			throw new ApplicationException(e);
		}
	}

	@Override
	public void assigneRoleForAccount(Account account, String role) {
		Connection connection = ConnectionUtils.getConnection();
		try (PreparedStatement st = connection.prepareStatement(UPDATE_ROLE_FOR_ACCOUNT_QUERY,
				Statement.RETURN_GENERATED_KEYS)) {
			st.setString(1, role);
			st.setLong(2, account.getId());
			st.executeUpdate();
		} catch (SQLException e) {
			throw new ApplicationException(e);
		}
	}

	@Override
	public List<Account> getAccountsWithRole(String role, int limit, int offset) {
		Connection connection = ConnectionUtils.getConnection();
		try (PreparedStatement st = connection.prepareStatement(GET_ALL_ACCOUNTS_WITH_ROLE_QUERY,
				ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
			st.setString(1, role);
			st.setInt(2, limit);
			st.setInt(3, offset);
			ResultSet rs = st.executeQuery();
			return new ListAccountResultSetHandler().handle(rs);
		} catch (SQLException e) {
			throw new ApplicationException(e);
		}
	}

}
