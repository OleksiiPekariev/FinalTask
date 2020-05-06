package ua.nure.pekariev.conrtollers.manager;

import static ua.nure.pekariev.Constants.ERROR_MESSAGE;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.pekariev.conrtollers.AbstractController;
import ua.nure.pekariev.exceptions.ValidationException;
import ua.nure.pekariev.model.Order;

@WebServlet("/manager/declineOrder")
public class DeclineOrderController extends AbstractController {
	private static final String EMAIL_SUBJECT = "Car Rent. Your order was declined";

	private static final long serialVersionUID = 6114738772041603560L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		forwardToPage("/manager/decline.jsp", req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Long orderId = Long.parseLong(req.getParameter("orderId"));
			String declineComment = req.getParameter("comment");
			Order declinedOrder = getManagerService().declineOrder(orderId, declineComment);
			getNotificationService().sendNotificationMessage(declinedOrder.getAccount().getEmail(), EMAIL_SUBJECT,
					"Your order was declined. Please contact us for the details");
			resp.sendRedirect("/manager/home");
		} catch (ValidationException ve) {
			req.getSession().setAttribute(ERROR_MESSAGE, ve.getMessage());
			resp.sendRedirect("/manager/home");
			ve.printStackTrace();
		} catch (NumberFormatException nfe) {
			resp.sendRedirect("/manager/home");
		}

	}
}
