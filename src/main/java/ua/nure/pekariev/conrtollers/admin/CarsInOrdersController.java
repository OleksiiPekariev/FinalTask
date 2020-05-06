package ua.nure.pekariev.conrtollers.admin;

import static ua.nure.pekariev.Constants.MAX_CARS_PER_HTML_PAGE;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.nure.pekariev.Constants;
import ua.nure.pekariev.conrtollers.AbstractController;
import ua.nure.pekariev.exceptions.ValidationException;
import ua.nure.pekariev.model.Car;
import ua.nure.pekariev.model.Checkable;

@WebServlet("/admin/cars/inOrders")
public class CarsInOrdersController extends AbstractController {

	private static final Logger LOG = LoggerFactory.getLogger(CarsInOrdersController.class);

	private static final long serialVersionUID = -5297778884336388618L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Checkable> carNamesList;
		List<Checkable> carClassesList;

		int currentPageNumber = getPage(req);

		String[] checkedCarNames = req.getParameterValues("carNames");
		String[] checkedCarClasses = req.getParameterValues("carClasses");
		String sortBy = req.getParameter("sortBy");

		try {
			carNamesList = getCommonService().getAllRegisteredCarNames();
			req.setAttribute("allCarNames", carNamesList);

			carClassesList = getCommonService().getAllCarClasses();
			req.setAttribute("allCarClasses", carClassesList);

			setCheckedinput(checkedCarNames, carNamesList);
			setCheckedinput(checkedCarClasses, carClassesList);

			Long availableCarCount = getAdminService().countCarsInOrder(checkedCarNames, checkedCarClasses);
			List<Car> cars = getAdminService().getCarsInOrders(checkedCarNames, checkedCarClasses, sortBy,
					currentPageNumber, MAX_CARS_PER_HTML_PAGE);
			int pageCount = getPageCount(availableCarCount, MAX_CARS_PER_HTML_PAGE);

			req.setAttribute("lastPage", pageCount);
			req.setAttribute("cars", cars);

			forwardToPage("/admin/cars-in-orders.jsp", req, resp);
			req.getSession().removeAttribute(Constants.SUCCESS_MESSAGE);
		} catch (ValidationException e) {
			req.setAttribute(Constants.ERROR_MESSAGE, e.getMessage());
			LOG.error(e.getMessage(), e);
			forwardToPage("admin/home.jsp", req, resp);
			e.printStackTrace();
		}
	}
}
