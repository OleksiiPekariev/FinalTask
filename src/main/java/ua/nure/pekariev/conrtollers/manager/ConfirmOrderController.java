package ua.nure.pekariev.conrtollers.manager;

import static ua.nure.pekariev.Constants.CONFIRMATION_EMAIL_SUBJECT;
import static ua.nure.pekariev.Constants.ERROR_MESSAGE;
import static ua.nure.pekariev.Constants.SUCCESS_MESSAGE;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.nure.pekariev.conrtollers.AbstractController;
import ua.nure.pekariev.exceptions.ValidationException;
import ua.nure.pekariev.model.Order;

@WebServlet("/manager/confirmOrder")
public class ConfirmOrderController extends AbstractController {

	private static final Logger LOG = LoggerFactory.getLogger(ConfirmOrderController.class);

	private static final long serialVersionUID = 6114738772041603560L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Long orderId = Long.parseLong(req.getParameter("orderId"));
			Order confirmedOrder = getManagerService().confirmOrder(orderId);
			getNotificationService().sendNotificationMessage("catch.the.fish2011@gmail.com", CONFIRMATION_EMAIL_SUBJECT,
					"Your order was confirmed. Wait for our manager's call for details.");
			req.getSession().setAttribute(SUCCESS_MESSAGE, "Order with ID: " + confirmedOrder.getId()
					+ " . Phone to client with number: " + confirmedOrder.getAccount().getPhone());
			resp.sendRedirect("/manager/home");
		} catch (ValidationException ve) {
			req.getSession().setAttribute(ERROR_MESSAGE, ve.getMessage());
			LOG.error(ve.getMessage(), ve);
			resp.sendRedirect("/manager/home");
			ve.printStackTrace();
		} catch (NumberFormatException nfe) {
			LOG.error(nfe.getMessage(), nfe);
		}

	}
}
