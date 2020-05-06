package ua.nure.pekariev.conrtollers.manager;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.nure.pekariev.conrtollers.AbstractController;
import ua.nure.pekariev.exceptions.ValidationException;

@WebServlet("/payment")
public class PaymentController extends AbstractController {

	private static final Logger LOG = LoggerFactory.getLogger(PaymentController.class);

	private static final long serialVersionUID = -2202841858440304718L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Long orderId = Long.parseLong(req.getParameter("orderId"));
			getManagerService().assigneOrderAsPaid(orderId);
		} catch (ValidationException e) {
			LOG.error(e.getMessage(), e);
			e.printStackTrace();
		} catch (NumberFormatException nfe) {
			LOG.error(nfe.getMessage(), nfe);
			resp.sendRedirect("/client/home");
		}
		resp.sendRedirect("/client/home");
	}
}
