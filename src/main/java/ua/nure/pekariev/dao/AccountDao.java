package ua.nure.pekariev.dao;

import java.util.List;

import ua.nure.pekariev.model.Account;

public interface AccountDao {

	Account getAccountById(Long id);

	Account getAccountByLoginOrEmail(String login);

	Long createAccount(String email, String password, String firstName, String lastName);

	public void insertRoleForAccount(Long accountId);

	boolean remove(Long accountID);

	Account updateAccountData(Account account);

	void assigneRoleForAccount(Account account, String role);

	List<Account> getAll(int limit, int offset);

	Long countAllAccounts();

	List<Account> getAccountsWithRole(String role, int limit, int offset);

	Long countAccountsWithRole(String role);

}
