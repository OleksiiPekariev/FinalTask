package ua.nure.pekariev.conrtollers.manager;

import static ua.nure.pekariev.Constants.ERROR_MESSAGE;
import static ua.nure.pekariev.Constants.SUCCESS_MESSAGE;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.pekariev.conrtollers.AbstractController;
import ua.nure.pekariev.exceptions.ValidationException;
import ua.nure.pekariev.model.Order;

@WebServlet("/manager/closeOrder")
public class CloseOrderController extends AbstractController {
	private static final String EMAIL_SUBJECT = "Car Rent. Your order was closed";

	private static final long serialVersionUID = 6114738772041603560L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Long orderId = Long.parseLong(req.getParameter("orderId"));
		try {
			Order closedOrder = getManagerService().closeOrder(orderId);
			getNotificationService().sendNotificationMessage("catch.the.fish2011@gmail.com", EMAIL_SUBJECT,
					"Your order was closed. Thank You for using our service");
			req.getSession().setAttribute(SUCCESS_MESSAGE, "Order with ID: " + closedOrder.getId() + " was closed.");
		} catch (ValidationException e) {
			req.getSession().setAttribute(ERROR_MESSAGE, e.getMessage());
			resp.sendRedirect("/manager/home");
			e.printStackTrace();
		}
		resp.sendRedirect("/manager/home");
	}
}
