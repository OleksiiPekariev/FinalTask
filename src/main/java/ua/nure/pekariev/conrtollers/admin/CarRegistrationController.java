package ua.nure.pekariev.conrtollers.admin;

import static ua.nure.pekariev.Constants.ERROR_MESSAGE;
import static ua.nure.pekariev.Constants.SUCCESS_MESSAGE;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.nure.pekariev.conrtollers.AbstractController;
import ua.nure.pekariev.exceptions.ValidationException;
import ua.nure.pekariev.model.Car;
import ua.nure.pekariev.model.Checkable;

@WebServlet("/admin/carRegistration")
public class CarRegistrationController extends AbstractController {

	private static final Logger LOG = LoggerFactory.getLogger(CarRegistrationController.class);

	private static final long serialVersionUID = 7905693409230526204L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("years", getYearsLIst());
		try {
			List<Checkable> carNamesList = getAdminService().getAllCarNames();
			req.setAttribute("allCarNames", carNamesList);
			forwardToPage("/admin/car-registration.jsp", req, resp);
		} catch (ValidationException e) {
			req.getSession().setAttribute(ERROR_MESSAGE, e.getMessage());
			LOG.error(e.getMessage(), e);
			forwardToPage("/admin/home.jsp", req, resp);
			e.printStackTrace();
		}
		req.getSession().removeAttribute(ERROR_MESSAGE);
		req.getSession().removeAttribute(SUCCESS_MESSAGE);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Car carToRegister;
		try {
			carToRegister = fillCarFromRequest(new Car(), req);
			Car registeredCar = getAdminService().registerNewCar(carToRegister);
			req.getSession().setAttribute(SUCCESS_MESSAGE,
					"New car " + registeredCar.getManufacturer() + " " + registeredCar.getModel() + " "
							+ registeredCar.getStateNumber() + " was successfully aded to database with ID: "
							+ registeredCar.getId());
			resp.sendRedirect("/admin/home");
		} catch (ValidationException e) {
			req.getSession().setAttribute(ERROR_MESSAGE, e.getMessage());
			LOG.error(e.getMessage(), e);
			resp.sendRedirect("/admin/carRegistration");
			e.printStackTrace();
		}
	}

}
