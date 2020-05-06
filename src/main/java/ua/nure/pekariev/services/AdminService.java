package ua.nure.pekariev.services;

import java.util.List;

import ua.nure.pekariev.exceptions.ValidationException;
import ua.nure.pekariev.model.Account;
import ua.nure.pekariev.model.Car;
import ua.nure.pekariev.model.Checkable;

public interface AdminService {

	boolean remove(Account account);

	List<Account> getAll(int page, int limit);

	Long countAllAccounts();

	List<Checkable> getAllCarNames() throws ValidationException;

	Long countCarsInOrder(String[] carNames, String[] carClasses);

	List<Car> getCarsInOrders(String[] carNames, String[] carClasses, String sortBy, int page, int limit)
			throws ValidationException;

	List<String> getCarModelsForName(String carName);

	Car registerNewCar(Car carToRegister) throws ValidationException;

	Car updateCar(Car carToUpdate) throws ValidationException;

	Account assigneUserAsManager(Long userId) throws ValidationException;

	List<Account> getAccountsWithRole(String roleName, int page, int limit);

	Long countAllAccountsWithRole(String role);

	boolean removeCarById(Long carId) throws ValidationException;

}
