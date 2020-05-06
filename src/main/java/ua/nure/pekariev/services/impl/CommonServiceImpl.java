package ua.nure.pekariev.services.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import ua.nure.pekariev.dao.AccountDao;
import ua.nure.pekariev.dao.CarsDao;
import ua.nure.pekariev.exceptions.ApplicationException;
import ua.nure.pekariev.exceptions.ValidationException;
import ua.nure.pekariev.model.Account;
import ua.nure.pekariev.model.Car;
import ua.nure.pekariev.model.Checkable;
import ua.nure.pekariev.services.CommonService;
import ua.nure.pekariev.utils.ConnectionUtils;

public class CommonServiceImpl implements CommonService {

	private final DataSource dataSource;
	private final AccountDao accountDao;
	private final CarsDao carsDao;

	public CommonServiceImpl(DataSource dataSource, AccountDao accountDao, CarsDao carsDao) {
		this.dataSource = dataSource;
		this.accountDao = accountDao;
		this.carsDao = carsDao;
	}

	@Override
	public Account login(String login, String pass) throws ValidationException {
		try (Connection connection = dataSource.getConnection()) {
			ConnectionUtils.setConnection(connection);
			Account account = accountDao.getAccountByLoginOrEmail(login);
			if (account == null) {
				throw new ValidationException("По такой ел.почте/логину не найдено записи");
			} else {
				if (!account.getPassword().equals(pass)) {
					throw new ValidationException("Пароль не соответствует логину");
				}
				return account;
			}
		} catch (SQLException e) {
			throw new ApplicationException("Exception during user login" + login, e);
		} finally {
			ConnectionUtils.clearConnection();
		}
	}

	@Override
	public List<Checkable> getAllRegisteredCarNames() throws ValidationException {

		try (Connection connection = dataSource.getConnection()) {
			ConnectionUtils.setConnection(connection);
			List<Checkable> carNames = carsDao.getAllRegisteredCarNames();
			if (carNames == null) {
				throw new ValidationException("В базе нет ни одной записи");
			} else {
				return carNames;
			}
		} catch (SQLException e) {
			throw new ApplicationException("Exception during getting the car manufacturer names from DB", e);
		} finally {
			ConnectionUtils.clearConnection();
		}
	}

	@Override
	public List<Checkable> getAllCarClasses() throws ValidationException {
		try (Connection connection = dataSource.getConnection()) {
			ConnectionUtils.setConnection(connection);
			List<Checkable> carClasses = carsDao.getAllCarClasses();
			if (carClasses == null) {
				throw new ValidationException("По такой ед.почте/логину не найдено записи");
			} else {
				return carClasses;
			}
		} catch (SQLException e) {
			throw new ApplicationException("Exception during getting the car manufacturer names from DB", e);
		} finally {
			ConnectionUtils.clearConnection();
		}
	}

	@Override
	public List<Car> getAvailableCars(String[] carNames, String[] carClasses, String sortBy, int page, int limit)
			throws ValidationException {
		int offset = (page - 1) * limit;
		try (Connection connection = dataSource.getConnection()) {
			ConnectionUtils.setConnection(connection);
			List<Car> availableCars = carsDao.getAvailableCars(carNames, carClasses, sortBy, limit, offset);
			if (availableCars == null) {
				throw new ValidationException("Нет автомобилей соответствующим таким критериям");
			} else {
				return availableCars;
			}
		} catch (SQLException e) {
			throw new ApplicationException("Exception during getting the car manufacturer names from DB", e);
		} finally {
			ConnectionUtils.clearConnection();
		}
	}

	@Override
	public Car getCarById(Long carId) throws ValidationException {
		try (Connection connection = dataSource.getConnection()) {
			ConnectionUtils.setConnection(connection);
			Car car = carsDao.getCar(carId);
			if (car == null) {
				throw new ValidationException("Не найден автомобиль в базе по ID " + carId);
			} else {
				return car;
			}
		} catch (SQLException e) {
			throw new ApplicationException("Exception during getting the car from DB", e);
		} finally {
			ConnectionUtils.clearConnection();
		}
	}

	@Override
	public Account register(String email, String pass, String firstName, String lastName) throws ValidationException {
		try (Connection connection = dataSource.getConnection()) {
			connection.setAutoCommit(false);
			ConnectionUtils.setConnection(connection);
			Account account = accountDao.getAccountByLoginOrEmail(email);
			if (account != null) {
				throw new ValidationException("Пользователь с Email " + account.getEmail() + " уже зарегистрирован");
			} else {
				Long insertedAccountId = accountDao.createAccount(email, pass, firstName, lastName);
				accountDao.insertRoleForAccount(insertedAccountId);
				connection.commit();
				return accountDao.getAccountById(insertedAccountId);
			}
		} catch (SQLException e) {
			throw new ApplicationException("Exception during user registration", e);
		} finally {
			ConnectionUtils.clearConnection();
		}
	}

	@Override
	public Long countAvailableCars(String[] names, String[] classes) {
		try (Connection connection = dataSource.getConnection()) {
			ConnectionUtils.setConnection(connection);
			return carsDao.countAllAvailableCars(names, classes);
		} catch (SQLException e) {
			throw new ApplicationException("Exception during user registration", e);
		} finally {
			ConnectionUtils.clearConnection();
		}
	}

	@Override
	public Account updateAccount(Account account) {
		try (Connection connection = dataSource.getConnection()) {
			ConnectionUtils.setConnection(connection);
			return accountDao.updateAccountData(account);
		} catch (SQLException e) {
			throw new ApplicationException("Exception during user registration", e);
		} finally {
			ConnectionUtils.clearConnection();
		}
	}

	@Override
	public String remindPassword(String email) {
		try (Connection connection = dataSource.getConnection()) {
			ConnectionUtils.setConnection(connection);
			Account account = accountDao.getAccountByLoginOrEmail(email);
			if (account != null) {
				return account.getPassword();
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new ApplicationException("Exception during user login" + email, e);
		} finally {
			ConnectionUtils.clearConnection();
		}
	}
}
