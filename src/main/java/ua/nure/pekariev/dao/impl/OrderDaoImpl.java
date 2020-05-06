package ua.nure.pekariev.dao.impl;

import static ua.nure.pekariev.queries.OrderDaoQueries.CONFIRMATION_DATE_ORDER_QUERY;
import static ua.nure.pekariev.queries.OrderDaoQueries.GET_ALL_ORDERS;
import static ua.nure.pekariev.queries.OrderDaoQueries.GET_ORDERS_WITH_STATUS_QUERY;
import static ua.nure.pekariev.queries.OrderDaoQueries.GET_ORDER_BY_ID_QUERY;
import static ua.nure.pekariev.queries.OrderDaoQueries.GET_ORDER_WITHIN_DATES_QUERY;
import static ua.nure.pekariev.queries.OrderDaoQueries.INSERT_NEW_ORDER_QUERY;
import static ua.nure.pekariev.queries.OrderDaoQueries.SELECT_ACTIVE_ORDERS_FOR_USER_QUERY;
import static ua.nure.pekariev.queries.OrderDaoQueries.SELECT_HISTORY_ORDERS_FOR_USER_QUERY;
import static ua.nure.pekariev.queries.OrderDaoQueries.UPDATE_FINISHED_ORDERS_QUERY;
import static ua.nure.pekariev.queries.OrderDaoQueries.UPDATE_ORDER_DECLINE_REASON_QUERY;
import static ua.nure.pekariev.queries.OrderDaoQueries.UPDATE_ORDER_STATUS_QUERY;
import static ua.nure.pekariev.queries.OrderDaoQueries.COUNT_ORDERS_WITH_STATUS_QUERY;
import static ua.nure.pekariev.queries.OrderDaoQueries.COUNT_ACTIVE_ORDERS_QUERY;
import static ua.nure.pekariev.queries.OrderDaoQueries.COUNT_HISTORY_ORDERS_QUERY;
import static ua.nure.pekariev.queries.OrderDaoQueries.ASSIGNE_ORDER_AS_PAID_QUEY;
import static ua.nure.pekariev.queries.OrderDaoQueries.GET_ORDERS_FOR_CAR_QUERY;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ua.nure.pekariev.dao.OrdersDao;
import ua.nure.pekariev.dao.impl.rsh.AccountResultSetHandler;
import ua.nure.pekariev.dao.impl.rsh.CarResultSetHandler;
import ua.nure.pekariev.dao.impl.rsh.ListOrdersResultSetHandler;
import ua.nure.pekariev.dao.impl.rsh.OrderResultSetHandler;
import ua.nure.pekariev.exceptions.ApplicationException;
import ua.nure.pekariev.model.Account;
import ua.nure.pekariev.model.Car;
import ua.nure.pekariev.model.Order;
import ua.nure.pekariev.utils.ConnectionUtils;
import ua.nure.pekariev.utils.QueryUtils;

public class OrderDaoImpl implements OrdersDao {

	@Override
	public Order createOrder(Long accountId, Long carId, Date dateOfStart, Date dateOfEnd, Long totalRentValue,
			Long amountOfDays, Boolean withDriver) {
		Connection connection = ConnectionUtils.getConnection();
		try (PreparedStatement st = connection.prepareStatement(INSERT_NEW_ORDER_QUERY,
				Statement.RETURN_GENERATED_KEYS)) {
			st.setLong(1, accountId);
			st.setLong(2, carId);
			st.setTimestamp(3, new Timestamp(dateOfStart.getTime()), Calendar.getInstance());
			st.setTimestamp(4, new Timestamp(dateOfEnd.getTime()), Calendar.getInstance());
			st.setLong(5, totalRentValue);
			st.setLong(6, amountOfDays);
			st.setBoolean(7, withDriver);
			st.executeUpdate();
			ResultSet rs = st.getGeneratedKeys();
			rs.next();
			Long insertedOrderId = rs.getLong("id");
			return getOrderById(connection, insertedOrderId);
		} catch (SQLException e) {
			throw new ApplicationException(e);
		}
	}

	@Override
	public Order getOrderById(Long id) {
		Connection connection = ConnectionUtils.getConnection();
		try (PreparedStatement st = connection.prepareStatement(GET_ORDER_BY_ID_QUERY, ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY)) {
			st.setLong(1, id);
			ResultSet rs = st.executeQuery();
			Account account = new AccountResultSetHandler().handle(rs);
			Car car = new CarResultSetHandler().handle(rs);
			Order order = new OrderResultSetHandler().handle(rs);
			order.setAccount(account);
			order.setCar(car);
			return order;
		} catch (SQLException e) {
			throw new ApplicationException(e);
		}
	}

	public Order getOrderById(Connection connection, Long id) {
		try (PreparedStatement st = connection.prepareStatement(GET_ORDER_BY_ID_QUERY, ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY)) {
			st.setLong(1, id);
			ResultSet rs = st.executeQuery();
			return new OrderResultSetHandler().handle(rs);
		} catch (SQLException e) {
			throw new ApplicationException(e);
		}
	}

	@Override
	public Order checkOrderAvailabilityByDate(Date dateOfStart, Date dateOfEnd, Long carId) {
		Connection connection = ConnectionUtils.getConnection();
		try (PreparedStatement st = connection.prepareStatement(GET_ORDER_WITHIN_DATES_QUERY,
				ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
			Timestamp dateOfStartTimeStamp = new Timestamp(dateOfStart.getTime());
			Timestamp dateOfEndTimeStamp = new Timestamp(dateOfEnd.getTime());
			st.setLong(1, carId);
			st.setTimestamp(2, dateOfStartTimeStamp, Calendar.getInstance());
			st.setTimestamp(3, dateOfEndTimeStamp, Calendar.getInstance());
			st.setTimestamp(4, dateOfStartTimeStamp, Calendar.getInstance());
			st.setTimestamp(5, dateOfEndTimeStamp, Calendar.getInstance());
			ResultSet rs = st.executeQuery();
			return new OrderResultSetHandler().handle(rs);
		} catch (SQLException e) {
			throw new ApplicationException(e);
		}
	}

	@Override
	public List<Order> getOrdersWithStatus(String[] carNames, String[] carClasses, String sortBy, int status, int limit,
			int offset) {
		String carNameSelection = QueryUtils.getSelectionQueryPart("name", carNames);
		String carClassesSelection = QueryUtils.getSelectionQueryPart("car_class.class", carClasses);
		String resultQuery = QueryUtils.generateOrderQueryWithParameters(GET_ORDERS_WITH_STATUS_QUERY, carNameSelection,
				carClassesSelection, sortBy);
		Connection connection = ConnectionUtils.getConnection();
		try (PreparedStatement st = connection.prepareStatement(resultQuery, ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY)) {
			st.setInt(1, status);
			st.setInt(2, limit);
			st.setInt(3, offset);
			ResultSet rs = st.executeQuery();
			return new ListOrdersResultSetHandler().handle(rs);
		} catch (SQLException e) {
			throw new ApplicationException(e);
		}
	}

	@Override
	public List<Order> getAllOrders(String[] carNames, String[] carClasses, String sortBy, int limit, int offset) {
		String carNameSelection = QueryUtils.getSelectionQueryPart("name", carNames);
		String carClassesSelection = QueryUtils.getSelectionQueryPart("car_class.class", carClasses);
		String resultQuery = QueryUtils.generateOrderQueryWithParameters(GET_ALL_ORDERS, carNameSelection,
				carClassesSelection, sortBy);

		Connection connection = ConnectionUtils.getConnection();
		try (PreparedStatement st = connection.prepareStatement(resultQuery, ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY)) {
			st.setInt(1, limit);
			st.setInt(2, offset);
			ResultSet rs = st.executeQuery();
			return new ListOrdersResultSetHandler().handle(rs);
		} catch (SQLException e) {
			throw new ApplicationException(e);
		}
	}

	@Override
	public Order updateOrderStatus(Long orderId, int status) {
		Connection connection = ConnectionUtils.getConnection();
		try (PreparedStatement st = connection.prepareStatement(UPDATE_ORDER_STATUS_QUERY,
				Statement.RETURN_GENERATED_KEYS)) {
			st.setInt(1, status);
			st.setLong(2, orderId);
			st.executeUpdate();
			ResultSet rs = st.getGeneratedKeys();
			rs.next();
			Long updatedOrderId = rs.getLong(1);
			return getOrderById(connection, updatedOrderId);
		} catch (SQLException e) {
			throw new ApplicationException(e);
		}
	}

	@Override
	public void setFinishedOrders() {
		Connection connection = ConnectionUtils.getConnection();
		try (Statement st = connection.createStatement()) {
			st.executeUpdate(UPDATE_FINISHED_ORDERS_QUERY);
		} catch (SQLException e) {
			throw new ApplicationException(e);
		}
	}

	@Override
	public void setConfirmationDate(Long orderId) {
		Connection connection = ConnectionUtils.getConnection();
		try (PreparedStatement st = connection.prepareStatement(CONFIRMATION_DATE_ORDER_QUERY,
				Statement.RETURN_GENERATED_KEYS)) {
			st.setLong(1, orderId);
			st.executeUpdate();
		} catch (SQLException e) {
			throw new ApplicationException(e);
		}
	}

	@Override
	public void setDeclineReason(Long orderId, String reason) {
		Connection connection = ConnectionUtils.getConnection();
		try (PreparedStatement st = connection.prepareStatement(UPDATE_ORDER_DECLINE_REASON_QUERY,
				Statement.RETURN_GENERATED_KEYS)) {
			st.setString(1, reason);
			st.setLong(2, orderId);
			st.executeUpdate();
		} catch (SQLException e) {
			throw new ApplicationException(e);
		}
	}

	@Override
	public List<Order> getActiveOrdersForUser(Long userId, String sortBy, int limit, int offset) {
		Connection connection = ConnectionUtils.getConnection();
		String query = QueryUtils.orderByForClient(SELECT_ACTIVE_ORDERS_FOR_USER_QUERY, sortBy);
		try (PreparedStatement st = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY)) {
			st.setLong(1, userId);
			st.setInt(2, limit);
			st.setInt(3, offset);
			ResultSet rs = st.executeQuery();
			return new ListOrdersResultSetHandler().handle(rs);
		} catch (SQLException e) {
			throw new ApplicationException(e);
		}
	}

	@Override
	public List<Order> getHistoryOrdersForUser(Long userId, String sortBy, int limit, int offset) {
		Connection connection = ConnectionUtils.getConnection();
		String query = QueryUtils.orderByForClient(SELECT_HISTORY_ORDERS_FOR_USER_QUERY, sortBy);
		try (PreparedStatement st = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY)) {
			st.setLong(1, userId);
			st.setInt(2, limit);
			st.setInt(3, offset);
			ResultSet rs = st.executeQuery();
			return new ListOrdersResultSetHandler().handle(rs);
		} catch (SQLException e) {
			throw new ApplicationException(e);
		}
	}

	@Override
	public Long countOrdersWithStatus(int orderStatus) {
		Connection connection = ConnectionUtils.getConnection();
		try (PreparedStatement st = connection.prepareStatement(COUNT_ORDERS_WITH_STATUS_QUERY)) {
			st.setInt(1, orderStatus);
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
	public Long countActiveOrders(Long accountId) {
		Connection connection = ConnectionUtils.getConnection();
		try (PreparedStatement st = connection.prepareStatement(COUNT_ACTIVE_ORDERS_QUERY)) {
			st.setLong(1, accountId);
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
	public Long countHistoryOrders(Long accountId) {
		Connection connection = ConnectionUtils.getConnection();
		try (PreparedStatement st = connection.prepareStatement(COUNT_HISTORY_ORDERS_QUERY)) {
			st.setLong(1, accountId);
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
	public Order assigneOrderAsPaid(Long orderId) {
		Connection connection = ConnectionUtils.getConnection();
		try (PreparedStatement st = connection.prepareStatement(ASSIGNE_ORDER_AS_PAID_QUEY,
				Statement.RETURN_GENERATED_KEYS)) {
			st.setLong(1, orderId);
			st.executeUpdate();
			ResultSet rs = st.getGeneratedKeys();
			rs.next();
			Long paidOrderId = rs.getLong(1);
			return getOrderById(connection, paidOrderId);
		} catch (SQLException e) {
			throw new ApplicationException(e);
		}
	}

	@Override
	public List<Order> getOrderForCar(Long carId) {
		Connection connection = ConnectionUtils.getConnection();
		try (PreparedStatement st = connection.prepareStatement(GET_ORDERS_FOR_CAR_QUERY,
				ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
			st.setLong(1, carId);
			ResultSet rs = st.executeQuery();
			return new ListOrdersResultSetHandler().handle(rs);
		} catch (SQLException e) {
			throw new ApplicationException(e);
		}
	}
}
