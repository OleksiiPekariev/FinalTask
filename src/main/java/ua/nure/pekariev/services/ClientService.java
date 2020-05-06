package ua.nure.pekariev.services;

import java.text.ParseException;
import java.util.List;

import ua.nure.pekariev.exceptions.OrderException;
import ua.nure.pekariev.model.Order;

public interface ClientService {

	Order createOrder(Order order) throws OrderException, ParseException;

	List<Order> getActiveOrdersForUser(Long userId, String sortBy, int page, int limit);

	List<Order> getHistoryOrdersForUser(Long userId, String sortBy, int page, int limit);

	Long countActiveOrders(Long userId);

	Long countHistoryOrders(Long userId);

}
