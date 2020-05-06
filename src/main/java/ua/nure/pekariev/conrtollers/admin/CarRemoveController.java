package ua.nure.pekariev.conrtollers.admin;

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

@WebServlet("/admin/carDelete")
public class CarRemoveController extends AbstractController {

	private static final Logger LOG = LoggerFactory.getLogger(CarRemoveController.class);

	private static final long serialVersionUID = 7905693409230526204L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("years", getYearsLIst());
		try {
			Long carId = Long.parseLong(req.getParameter("carId"));
			boolean success = getAdminService().removeCarById(carId);
			if (success) {
				req.getSession().setAttribute(SUCCESS_MESSAGE, "The car with ID " + carId + " was deleted");
			}
		} catch (ValidationException ve) {
			req.getSession().setAttribute(ERROR_MESSAGE, ve.getMessage());
			LOG.error(ve.getMessage(), ve);
			ve.printStackTrace();
		} catch (NumberFormatException nfe) {
			LOG.error(nfe.getMessage(), nfe);
		}
		resp.sendRedirect("/admin/home");
	}
}
