package ua.nure.pekariev.dao;

import java.util.Date;
import java.util.List;

import ua.nure.pekariev.model.Order;

public interface OrdersDao {

	public static final int NOT_CONFIRMED_ORDERS = 1;
	public static final int CONFIRMED_ORDERS = 2;
	public static final int FINISHED_ORDERS = 4;
	public static final int DECLINED_ORDERS = 8;
	public static final int CLOSED_ORDERS = 16;

	Order createOrder(Long accountId, Long carId, Date dateOfStart, Date dateOfEnd, Long totalRentValue,
			Long amountOfDays, Boolean withDriver);

	Order getOrderById(Long id);

	Order checkOrderAvailabilityByDate(Date dateOfStart, Date dateOfEnd, Long carId);

	List<Order> getAllOrders(String[] carNames, String[] carClasses, String sortBy, int limit, int offset);

	List<Order> getOrdersWithStatus(String[] carNames, String[] carClasses, String sortBy, int status, int limit,
			int offset);

	Order updateOrderStatus(Long orderId, int status);

	void setFinishedOrders();

	void setConfirmationDate(Long orderId);

	void setDeclineReason(Long orderId, String reason);

	List<Order> getActiveOrdersForUser(Long accountId, String sortBy, int limit, int offset);

	List<Order> getHistoryOrdersForUser(Long accountId, String sortBy, int limit, int offset);

	Long countOrdersWithStatus(int orderStatus);

	Long countActiveOrders(Long accountId);

	Long countHistoryOrders(Long accountId);

	Order assigneOrderAsPaid(Long orderId);

	List<Order> getOrderForCar(Long carId);

}
