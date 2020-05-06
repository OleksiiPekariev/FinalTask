package ua.nure.pekariev.services.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import ua.nure.pekariev.dao.CarsDao;
import ua.nure.pekariev.dao.OrdersDao;
import ua.nure.pekariev.exceptions.ApplicationException;
import ua.nure.pekariev.exceptions.ValidationException;
import ua.nure.pekariev.model.Car;
import ua.nure.pekariev.model.CarName;
import ua.nure.pekariev.model.Checkable;
import ua.nure.pekariev.model.Order;
import ua.nure.pekariev.services.ManagerService;
import ua.nure.pekariev.utils.ConnectionUtils;

public class ManagerServiceImpl implements ManagerService {

	private final DataSource dataSource;
	private final CarsDao carsDao;
	private final OrdersDao ordersDao;

	public ManagerServiceImpl(DataSource dataSource, CarsDao carsDao, OrdersDao ordersDao) {
		this.dataSource = dataSource;
		this.carsDao = carsDao;
		this.ordersDao = ordersDao;
	}

	@Override
	public List<Checkable> getAllCarNamesInOrders() throws ValidationException {
		try (Connection connection = dataSource.getConnection()) {
			ConnectionUtils.setConnection(connection);
			List<Car> cars = carsDao.getCarsInOrders(null, null, null, 0, 0);
			Set<Checkable> carNames = new LinkedHashSet<Checkable>();
			for (Car car : cars) {
				CarName carName = new CarName();
				carName.setName(car.getManufacturer());
				carNames.add(carName);
			}
			return Arrays.asList(carNames.toArray(new Checkable[0]));
		} catch (SQLException e) {
			throw new ApplicationException("Exception during getting the car manufacturer names from DB", e);
		} finally {
			ConnectionUtils.clearConnection();
		}
	}

	@Override
	public List<Order> getOrdersByStatus(String[] carNames, String[] carClasses, String sortBy, int status, int page,
			int limit) throws ValidationException {
		int offset = (page - 1) * limit;
		try (Connection connection = dataSource.getConnection()) {
			ConnectionUtils.setConnection(connection);
			if (status == 0) {
				return ordersDao.getAllOrders(carNames, carClasses, sortBy, limit, offset);
			} else {
				return ordersDao.getOrdersWithStatus(carNames, carClasses, sortBy, status, limit, offset);
			}
		} catch (SQLException e) {
			throw new ApplicationException("Exception during getting the car manufacturer names from DB", e);
		} finally {
			ConnectionUtils.clearConnection();
		}
	}

	@Override
	public Order confirmOrder(Long orderId) throws ValidationException {
		try (Connection connection = dataSource.getConnection()) {
			connection.setAutoCommit(false);
			ConnectionUtils.setConnection(connection);
			if (ordersDao.getOrderById(orderId) == null) {
				throw new ValidationException("There is no order in database with ID " + orderId);
			} else {
				ordersDao.setConfirmationDate(orderId);
				Order confirmedOrder = ordersDao.updateOrderStatus(orderId, 2);
				connection.commit();
				return confirmedOrder;
			}
		} catch (SQLException e) {
			throw new ApplicationException("Exception during getting the car manufacturer names from DB", e);
		} finally {
			ConnectionUtils.clearConnection();
		}
	}

	@Override
	public void setFinishedOrders() {
		try (Connection connection = dataSource.getConnection()) {
			ConnectionUtils.setConnection(connection);
			ordersDao.setFinishedOrders();
		} catch (SQLException e) {
			throw new ApplicationException("Exception during getting the car manufacturer names from DB", e);
		} finally {
			ConnectionUtils.clearConnection();
		}
	}

	@Override
	public Order closeOrder(Long orderId) throws ValidationException {
		try (Connection connection = dataSource.getConnection()) {
			ConnectionUtils.setConnection(connection);
			Order order = ordersDao.getOrderById(orderId);
			if (order == null) {
				throw new ValidationException("There is no order in database with ID " + orderId);
			} else {
				return ordersDao.updateOrderStatus(orderId, 16);
			}
		} catch (SQLException e) {
			throw new ApplicationException("Exception during getting the car manufacturer names from DB", e);
		} finally {
			ConnectionUtils.clearConnection();
		}
	}

	@Override
	public Order declineOrder(Long orderId, String reason) throws ValidationException {
		try (Connection connection = dataSource.getConnection()) {
			connection.setAutoCommit(false);
			ConnectionUtils.setConnection(connection);
			if (ordersDao.getOrderById(orderId) == null) {
				throw new ValidationException("There is no order in database with ID " + orderId);
			} else {
				ordersDao.setDeclineReason(orderId, reason);
				ordersDao.setConfirmationDate(orderId);
				Order declinedOrder = ordersDao.updateOrderStatus(orderId, 8);
				connection.commit();
				return declinedOrder;
			}
		} catch (SQLException e) {
			throw new ApplicationException("Exception during getting the car manufacturer names from DB", e);
		} finally {
			ConnectionUtils.clearConnection();
		}
	}

	@Override
	public Long countOrdersWithStatus(int orderStatus) {
		try (Connection connection = dataSource.getConnection()) {
			ConnectionUtils.setConnection(connection);
			return ordersDao.countOrdersWithStatus(orderStatus);
		} catch (SQLException e) {
			throw new ApplicationException("Exception during getting the car manufacturer names from DB", e);
		} finally {
			ConnectionUtils.clearConnection();
		}
	}

	@Override
	public Order getOrderById(Long orderId) throws ValidationException {
		try (Connection connection = dataSource.getConnection()) {
			ConnectionUtils.setConnection(connection);
			Order order = ordersDao.getOrderById(orderId);
			if (order == null) {
				throw new ValidationException("There is no order in database with ID " + orderId);
			} else {
				return order;
			}
		} catch (SQLException e) {
			throw new ApplicationException("Exception during getting the car manufacturer names from DB", e);
		} finally {
			ConnectionUtils.clearConnection();
		}
	}

	@Override
	public Order assigneOrderAsPaid(Long orderId) throws ValidationException {
		try (Connection connection = dataSource.getConnection()) {
			ConnectionUtils.setConnection(connection);
			Order paidOrder = ordersDao.getOrderById(orderId);
			if (!paidOrder.getPaid()) {
				return ordersDao.assigneOrderAsPaid(orderId);
			} else {
				throw new ValidationException("Невозможно установить оплату заказа. Заказ уже оплачен.");
			}
		} catch (SQLException e) {
			throw new ApplicationException("Exception during getting the car manufacturer names from DB", e);
		} finally {
			ConnectionUtils.clearConnection();
		}
	}

}
