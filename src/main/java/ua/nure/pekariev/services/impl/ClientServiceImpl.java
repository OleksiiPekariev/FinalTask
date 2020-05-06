package ua.nure.pekariev.services.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import ua.nure.pekariev.dao.OrdersDao;
import ua.nure.pekariev.exceptions.ApplicationException;
import ua.nure.pekariev.exceptions.OrderException;
import ua.nure.pekariev.model.Order;
import ua.nure.pekariev.services.ClientService;
import ua.nure.pekariev.utils.ConnectionUtils;
import ua.nure.pekariev.utils.DateTimeUtils;
import ua.nure.pekariev.utils.OrderUtils;

public class ClientServiceImpl implements ClientService {
	private final DataSource dataSource;
	private final OrdersDao orderDao;

	public ClientServiceImpl(DataSource dataSource, OrdersDao orderDao) {
		this.dataSource = dataSource;
		this.orderDao = orderDao;
	}

	@Override
	public Order createOrder(Order order) throws OrderException, ParseException {
		Date dateOfStart = order.getStarts();
		Date dateOfEnd = order.getExpires();
		Long amountOfDays = DateTimeUtils.getDateDiff(dateOfStart, dateOfEnd, TimeUnit.DAYS);
		Integer rentValuePerDay = order.getCar().getRentValuePerDay();
		Boolean withDriver = order.getWithDriver();
		Long totalRentValue = OrderUtils.countTotalRentValue(rentValuePerDay, amountOfDays, withDriver);

		try (Connection connection = dataSource.getConnection()) {
			ConnectionUtils.setConnection(connection);
			Order foundOrder = orderDao.checkOrderAvailabilityByDate(dateOfStart, dateOfEnd, order.getCar().getId());
			if (foundOrder != null) {
				throw new OrderException(
						"This car is not vacant fo these dates. This car is not available within dates "
								+ foundOrder.getStarts() + " - " + foundOrder.getExpires());
			} else {
				return orderDao.createOrder(order.getAccount().getId(), order.getCar().getId(), dateOfStart, dateOfEnd,
						totalRentValue, amountOfDays, withDriver);
			}
		} catch (SQLException e) {
			throw new ApplicationException("Exception during creating order", e);
		} finally {
			ConnectionUtils.clearConnection();
		}
	}

	@Override
	public List<Order> getActiveOrdersForUser(Long userId, String sortBy, int page, int limit) {
		int offset = (page - 1) * limit;
		try (Connection connection = dataSource.getConnection()) {
			ConnectionUtils.setConnection(connection);
			return orderDao.getActiveOrdersForUser(userId, sortBy, limit, offset);
		} catch (SQLException e) {
			throw new ApplicationException("Exception during creating order", e);
		} finally {
			ConnectionUtils.clearConnection();
		}
	}

	@Override
	public List<Order> getHistoryOrdersForUser(Long userId, String sortBy, int page, int limit) {
		int offset = (page - 1) * limit;
		try (Connection connection = dataSource.getConnection()) {
			ConnectionUtils.setConnection(connection);
			return orderDao.getHistoryOrdersForUser(userId, sortBy, limit, offset);
		} catch (SQLException e) {
			throw new ApplicationException("Exception during creating order", e);
		} finally {
			ConnectionUtils.clearConnection();
		}
	}

	@Override
	public Long countActiveOrders(Long accountId) {
		try (Connection connection = dataSource.getConnection()) {
			ConnectionUtils.setConnection(connection);
			return orderDao.countActiveOrders(accountId);
		} catch (SQLException e) {
			throw new ApplicationException("Exception during getting the car manufacturer names from DB", e);
		} finally {
			ConnectionUtils.clearConnection();
		}
	}

	@Override
	public Long countHistoryOrders(Long accountId) {
		try (Connection connection = dataSource.getConnection()) {
			ConnectionUtils.setConnection(connection);
			return orderDao.countHistoryOrders(accountId);
		} catch (SQLException e) {
			throw new ApplicationException("Exception during getting the car manufacturer names from DB", e);
		} finally {
			ConnectionUtils.clearConnection();
		}
	}
}
