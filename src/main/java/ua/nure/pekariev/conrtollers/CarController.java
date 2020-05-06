package ua.nure.pekariev.conrtollers;

import static ua.nure.pekariev.Constants.ERROR_MESSAGE;
import static ua.nure.pekariev.Constants.MAX_ACCOUNTS_PER_HTML_PAGE;
import static ua.nure.pekariev.Constants.MAX_CARS_PER_HTML_PAGE;
import static ua.nure.pekariev.Constants.SUCCESS_MESSAGE;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.nure.pekariev.exceptions.ValidationException;
import ua.nure.pekariev.model.Car;
import ua.nure.pekariev.model.Checkable;

@WebServlet("/cars")
public class CarController extends AbstractController {

	private static final Logger LOG = LoggerFactory.getLogger(CarController.class);

	private static final long serialVersionUID = 6114738772041603560L;

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

			Long availableCarCount = getCommonService().countAvailableCars(checkedCarNames, checkedCarClasses);
			List<Car> cars = getCommonService().getAvailableCars(checkedCarNames, checkedCarClasses, sortBy,
					currentPageNumber, MAX_CARS_PER_HTML_PAGE);
			int pageCount = getPageCount(availableCarCount, MAX_ACCOUNTS_PER_HTML_PAGE);

			req.setAttribute("lastPage", pageCount);
			req.setAttribute("cars", cars);

			forwardToPage("cars.jsp", req, resp);
			req.getSession().removeAttribute(SUCCESS_MESSAGE);
			req.getSession().removeAttribute(ERROR_MESSAGE);
		} catch (ValidationException e) {
			LOG.error(e.getMessage(), e);
			req.setAttribute(ERROR_MESSAGE, e.getMessage());
			forwardToPage("cars.jsp", req, resp);
			e.printStackTrace();
		}
	}
}
