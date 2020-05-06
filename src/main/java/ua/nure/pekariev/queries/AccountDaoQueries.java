package ua.nure.pekariev.queries;

public final class AccountDaoQueries {
	public static final String GET_ALL_ACCOUNTS_BY_LOGIN_QUERY = "SELECT account.id, account.first_name, account.last_name, account.email, role.id AS role_id, role.role, account.passport_data, account.password, account.created, account.active, account.address, account.phone FROM account, account_role, role WHERE account_role.id_account = account.id AND account_role.id_role = role.id AND email = ?";
	public static final String GET_ACOUNT_BY_ID_QUERY = "SELECT account.id, account.first_name, account.last_name, account.email, role.id AS role_id, role.role, account.passport_data, account.password, account.created, account.active, account.address, account.phone FROM account, account_role, role WHERE account_role.id_account = account.id AND account_role.id_role = role.id AND account.id = ?";
	public static final String CREATE_ACCOUNT_WITH_LOGIN_PASS_QUERY = "INSERT INTO account(id, email, password, first_name, last_name) values(nextval('account_seq'), ?, ?, ?, ?)";
	public static final String INSERT_ROLE_FOR_CREATED_ACCOUNT_QUERY = "INSERT INTO account_role(id, id_account) values(nextval('account_role_seq'), ?)";
	public static final String UPDATE_ROLE_FOR_ACCOUNT_QUERY = "UPDATE account_role SET id_role = (SELECT role.id FROM role WHERE role.role = ?) WHERE id_account=?";
	public static final String UPDATE_ACCOUNT_DATA_QUERY = "UPDATE account SET first_name =?, last_name = ?, email = ?, passport_data = ?, address = ?, phone = ?  WHERE id = ?";
	public static final String GET_ALL_ACCOUNTS_QUERY = "SELECT account.id, account.first_name, account.last_name, account.email, role.id AS role_id, role.role, account.passport_data, account.password, account.created, account.active, account.address, account.phone FROM account, role, account_role WHERE account_role.id_account = account.id AND account_role.id_role = role.id ORDER BY account.last_name LIMIT ? OFFSET ?";
	public static final String GET_ALL_ACCOUNTS_WITH_ROLE_QUERY = "SELECT account.id, account.first_name, account.last_name, account.email, role.id AS role_id, role.role, account.passport_data, account.password, account.created, account.active, account.address, account.phone FROM account, role, account_role WHERE account_role.id_account = account.id AND account_role.id_role = role.id AND role.role = ? ORDER BY account.last_name  LIMIT ? OFFSET ?";
	public static final String COUNT_ACCOUNTS_QUERY = "SELECT count(*) FROM account";
	public static final String COUNT_ACCOUNTS_WITH_ROLE_QUERY = "SELECT count(*) FROM account_role, role WHERE account_role.id_role = role.id AND role.role = ?";
}
