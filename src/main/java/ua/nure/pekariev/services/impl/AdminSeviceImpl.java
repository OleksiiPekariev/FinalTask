package ua.nure.pekariev.services.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import ua.nure.pekariev.dao.AccountDao;
import ua.nure.pekariev.dao.CarsDao;
import ua.nure.pekariev.dao.OrdersDao;
import ua.nure.pekariev.exceptions.ApplicationException;
import ua.nure.pekariev.exceptions.ValidationException;
import ua.nure.pekariev.model.Account;
import ua.nure.pekariev.model.Car;
import ua.nure.pekariev.model.Checkable;
import ua.nure.pekariev.model.Order;
import ua.nure.pekariev.services.AdminService;
import ua.nure.pekariev.utils.ConnectionUtils;

public class AdminSeviceImpl implements AdminService {

	private final DataSource dataSource;
	private final AccountDao accountDao;
	private final CarsDao carsDao;
	private final OrdersDao ordersDao;

	public AdminSeviceImpl(DataSource dataSource, AccountDao accountDao, CarsDao carsDao, OrdersDao ordersDao) {
		this.dataSource = dataSource;
		this.accountDao = accountDao;
		this.carsDao = carsDao;
		this.ordersDao = ordersDao;
	}

	@Override
	public boolean remove(Account account) {
		try (Connection connection = dataSource.getConnection()) {
			connection.setAutoCommit(false);
			ConnectionUtils.setConnection(connection);
			accountDao.remove(account.getId());
			connection.commit();
			return true;
		} catch (SQLException e) {
			throw new ApplicationException(e);
		}
	}

	@Override
	public List<Checkable> getAllCarNames() throws ValidationException {
		try (Connection connection = dataSource.getConnection()) {
			ConnectionUtils.setConnection(connection);
			List<Checkable> carNames = carsDao.getAllCarNames();
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
	public Long countCarsInOrder(String[] carNames, String[] carClasses) {
		try (Connection connection = dataSource.getConnection()) {
			ConnectionUtils.setConnection(connection);
			return carsDao.countCheckedCarsInOrders(carNames, carClasses);
		} catch (SQLException e) {
			throw new ApplicationException("Exception cars in order retrievment", e);
		} finally {
			ConnectionUtils.clearConnection();
		}
	}

	@Override
	public List<Car> getCarsInOrders(String[] carNames, String[] carClasses, String sortBy, int page, int limit)
			throws ValidationException {
		int offset = (page - 1) * limit;
		try (Connection connection = dataSource.getConnection()) {
			ConnectionUtils.setConnection(connection);
			List<Car> availableCars = carsDao.getCarsInOrders(carNames, carClasses, sortBy, limit, offset);
			if (availableCars == null) {
				throw new ValidationException("Автомобили с такими параметрами в базе не найдены");
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
	public List<String> getCarModelsForName(String carName) {
		try (Connection connection = dataSource.getConnection()) {
			ConnectionUtils.setConnection(connection);
			return carsDao.getAllCarModelsForName(carName);
		} catch (SQLException e) {
			throw new ApplicationException("Exception cars in order retrievment", e);
		} finally {
			ConnectionUtils.clearConnection();
		}
	}

	@Override
	public Car registerNewCar(Car carToRegister) throws ValidationException {
		try (Connection connection = dataSource.getConnection()) {
			ConnectionUtils.setConnection(connection);
			Car car = carsDao.getCarByStateNumber(carToRegister.getStateNumber());
			if (car != null) {
				throw new ValidationException("There is the car with the state number " + car.getStateNumber()
						+ " already exists in the database");
			} else {
				return carsDao.createCar(carToRegister);
			}
		} catch (SQLException e) {
			throw new ApplicationException("Exception during registering a car ", e);
		} finally {
			ConnectionUtils.clearConnection();
		}
	}

	@Override
	public Car updateCar(Car carToUpdate) throws ValidationException {
		try (Connection connection = dataSource.getConnection()) {
			ConnectionUtils.setConnection(connection);
			Car car = carsDao.getCar(carToUpdate.getId());
			if (car == null) {
				throw new ValidationException("There is no car with ID=" + carToUpdate.getId() + " in the database");
			} else {
				return carsDao.editCar(carToUpdate);
			}
		} catch (SQLException e) {
			throw new ApplicationException("Exception cars in order retrievment", e);
		} finally {
			ConnectionUtils.clearConnection();
		}
	}

	@Override
	public List<Account> getAll(int page, int limit) {
		int offset = (page - 1) * limit;
		try (Connection connection = dataSource.getConnection()) {
			ConnectionUtils.setConnection(connection);
			return accountDao.getAll(limit, offset);
		} catch (SQLException e) {
			throw new ApplicationException(e);
		} finally {
			ConnectionUtils.clearConnection();
		}
	}

	@Override
	public Long countAllAccounts() {
		try (Connection connection = dataSource.getConnection()) {
			ConnectionUtils.setConnection(connection);
			return accountDao.countAllAccounts();
		} catch (SQLException e) {
			throw new ApplicationException(e);
		} finally {
			ConnectionUtils.clearConnection();
		}
	}

	@Override
	public Long countAllAccountsWithRole(String role) {
		try (Connection connection = dataSource.getConnection()) {
			ConnectionUtils.setConnection(connection);
			return accountDao.countAccountsWithRole(role);
		} catch (SQLException e) {
			throw new ApplicationException(e);
		} finally {
			ConnectionUtils.clearConnection();
		}
	}

	@Override
	public Account assigneUserAsManager(Long userId) throws ValidationException {
		try (Connection connection = dataSource.getConnection()) {
			ConnectionUtils.setConnection(connection);
			Account account = accountDao.getAccountById(userId);
			if (account == null) {
				throw new ValidationException("There is now accounts with such ID in database");
			} else {
				if ("manager".equals(account.getRole().getName())) {
					throw new ValidationException("This user already has role \"manager\"");
				} else {
					accountDao.assigneRoleForAccount(account, "manager");
				}
			}
			Account updatedAccount = accountDao.getAccountById(userId);
			if (!updatedAccount.getRole().getName().equals("manager")) {
				throw new ValidationException(
						"An error occurs during assigning the role for account with ID = " + account.getId());
			} else {
				return updatedAccount;
			}
		} catch (SQLException e) {
			throw new ApplicationException(e);
		} finally {
			ConnectionUtils.clearConnection();
		}
	}

	@Override
	public List<Account> getAccountsWithRole(String roleName, int page, int limit) {
		int offset = (page - 1) * limit;
		try (Connection connection = dataSource.getConnection()) {
			ConnectionUtils.setConnection(connection);
			return accountDao.getAccountsWithRole(roleName, limit, offset);
		} catch (SQLException e) {
			throw new ApplicationException(e);
		} finally {
			ConnectionUtils.clearConnection();
		}
	}

	@Override
	public boolean removeCarById(Long carId) throws ValidationException {
		try (Connection connection = dataSource.getConnection()) {
			ConnectionUtils.setConnection(connection);
			List<Order> ordersWithCar = ordersDao.getOrderForCar(carId);
			if (ordersWithCar == null) {
				Car car = carsDao.getCar(carId);
				if (car != null) {
					return carsDao.removeCar(car);
				} else {
					throw new ValidationException("There is no car with such ID in database.");
				}
			} else {
				throw new ValidationException(
						"This car is in active order! Close all orders for this car before deleting");
			}
		} catch (SQLException e) {
			throw new ApplicationException(e);
		} finally {
			ConnectionUtils.clearConnection();
		}
	}
}
