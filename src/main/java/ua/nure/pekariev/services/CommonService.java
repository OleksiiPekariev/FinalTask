package ua.nure.pekariev.services;

import java.util.List;

import ua.nure.pekariev.exceptions.ValidationException;
import ua.nure.pekariev.model.Account;
import ua.nure.pekariev.model.Car;
import ua.nure.pekariev.model.Checkable;

public interface CommonService {

	Account login(String login, String pass) throws ValidationException;

	List<Checkable> getAllRegisteredCarNames() throws ValidationException;

	List<Checkable> getAllCarClasses() throws ValidationException;

	List<Car> getAvailableCars(String[] names, String[] classes, String sortBy, int page, int offset)
			throws ValidationException;

	Long countAvailableCars(String[] names, String[] classes);

	Car getCarById(Long carId) throws ValidationException;

	Account register(String email, String pass, String firstName, String lastName) throws ValidationException;

	Account updateAccount(Account account);

	String remindPassword(String email);

}
