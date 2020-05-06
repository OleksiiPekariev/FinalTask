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

@WebServlet("/admin/carEdit")
public class CarEditController extends AbstractController {

	private static final Logger LOG = LoggerFactory.getLogger(CarEditController.class);

	private static final long serialVersionUID = 7905693409230526204L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setAttribute("years", getYearsLIst());
		try {
			Long carId = Long.parseLong(req.getParameter("carId"));
			List<Checkable> carNamesList = getAdminService().getAllCarNames();
			req.setAttribute("allCarNames", carNamesList);

			Car car = getCommonService().getCarById(carId);
			req.getSession().setAttribute("car", car);
			forwardToPage("/admin/car-edit.jsp", req, resp);
		} catch (ValidationException ve) {
			req.getSession().setAttribute(ERROR_MESSAGE, ve.getMessage());
			LOG.error(ve.getMessage(), ve);
			forwardToPage("/admin/home.jsp", req, resp);
			ve.printStackTrace();
		} catch (NumberFormatException nfe) {
			LOG.error(nfe.getMessage(), nfe);
			resp.sendRedirect("/admin/home");
		}
		req.getSession().removeAttribute(SUCCESS_MESSAGE);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Car currentCar = (Car) req.getSession().getAttribute("car");
			Car carToUpdate = fillCarFromRequest(currentCar, req);
			Car updatedCar = getAdminService().updateCar(carToUpdate);
			req.getSession().setAttribute(SUCCESS_MESSAGE,
					"Ther car information" + updatedCar.getManufacturer() + " " + updatedCar.getModel()
							+ " with state number " + updatedCar.getStateNumber() + " was successfully updated");
			resp.sendRedirect("/admin/home");
		} catch (ValidationException e) {
			req.getSession().setAttribute(ERROR_MESSAGE, e.getMessage());
			LOG.error(e.getMessage(), e);
			resp.sendRedirect("/admin/carEdit");
			e.printStackTrace();
		}
	}
}
