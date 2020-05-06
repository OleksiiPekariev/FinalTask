package ua.nure.pekariev.services;

import java.util.List;

import ua.nure.pekariev.exceptions.ValidationException;
import ua.nure.pekariev.model.Checkable;
import ua.nure.pekariev.model.Order;

public interface ManagerService {

	List<Checkable> getAllCarNamesInOrders() throws ValidationException;

	List<Order> getOrdersByStatus(String[] carNames, String[] carClasses, String sortBy, int status, int page,
			int limit) throws ValidationException;

	Order getOrderById(Long orderId) throws ValidationException;

	Order confirmOrder(Long orderId) throws ValidationException;

	Order closeOrder(Long orderId) throws ValidationException;

	void setFinishedOrders();

	Order declineOrder(Long orderId, String reason) throws ValidationException;

	Long countOrdersWithStatus(int orderStatus);

	Order assigneOrderAsPaid(Long orderId) throws ValidationException;

}
