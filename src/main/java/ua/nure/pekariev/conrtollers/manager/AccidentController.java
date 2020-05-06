package ua.nure.pekariev.conrtollers.manager;

import static ua.nure.pekariev.Constants.SUCCESS_MESSAGE;
import static ua.nure.pekariev.Constants.ERROR_MESSAGE;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.pekariev.conrtollers.AbstractController;
import ua.nure.pekariev.exceptions.ValidationException;
import ua.nure.pekariev.model.Order;
import ua.nure.pekariev.utils.OrderUtils;

@WebServlet("/manager/accident")
public class AccidentController extends AbstractController {

	private static final long serialVersionUID = -2202841858440304718L;

	private static final String EMAIL_SUBJECT = "Car Rent. Accident payment";

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		forwardToPage("/manager/accident.jsp", req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Long orderId = Long.parseLong(req.getParameter("orderId"));
			Long penaltyValue = Long.parseLong(req.getParameter("penaltyValue"));
			Order order = getManagerService().getOrderById(orderId);
			String penaltyMessage = OrderUtils.createPenaltyMessage(order, penaltyValue);
			getNotificationService().sendNotificationMessage("catch.the.fish2011@gmail.com", EMAIL_SUBJECT,
					penaltyMessage);
			req.getSession().setAttribute(SUCCESS_MESSAGE, "Accident bill was sent to client "
					+ order.getAccount().getFirstName() + " " + order.getAccount().getFirstName());
			resp.sendRedirect("/manager/home");
		} catch (ValidationException e) {
			req.getSession().setAttribute(ERROR_MESSAGE, e.getMessage());
			resp.sendRedirect("/manager/home");
			e.printStackTrace();
		} catch (NumberFormatException nfe) {
			resp.sendRedirect("/manager/home");
		}
	}
}
