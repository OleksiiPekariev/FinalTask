package ua.nure.pekariev.conrtollers.manager;

import static ua.nure.pekariev.Constants.ERROR_MESSAGE;
import static ua.nure.pekariev.Constants.MAX_ORDERS_PER_HTML_PAGE;
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
import ua.nure.pekariev.model.Checkable;
import ua.nure.pekariev.model.Order;

@WebServlet("/manager/home")
public class HomeController extends AbstractController {
	private static final long serialVersionUID = -5297778884336388618L;

	private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		getManagerService().setFinishedOrders();
		List<Checkable> carNamesList;
		List<Checkable> carClassesList;
		int currentPageNumber = getPage(req);
		String[] checkedCarNames = req.getParameterValues("carNames");
		String[] checkedCarClasses = req.getParameterValues("carClasses");
		String sortBy = req.getParameter("sortBy");
		String status = req.getParameter("status");
		int orderStatus;
		try {
			orderStatus = Integer.parseInt(status);
		} catch (NumberFormatException e) {
			orderStatus = 0;
		}
		try {
			carNamesList = getCommonService().getAllRegisteredCarNames();
			req.setAttribute("allCarNames", carNamesList);

			carClassesList = getCommonService().getAllCarClasses();
			req.setAttribute("allCarClasses", carClassesList);

			setCheckedinput(checkedCarNames, carNamesList);
			setCheckedinput(checkedCarClasses, carClassesList);

			List<Order> orders = getManagerService().getOrdersByStatus(checkedCarNames, checkedCarClasses, sortBy,
					orderStatus, currentPageNumber, MAX_ORDERS_PER_HTML_PAGE);
			Long countOrdersWithStatus = getManagerService().countOrdersWithStatus(orderStatus);
			int pageCount = getPageCount(countOrdersWithStatus, MAX_ORDERS_PER_HTML_PAGE);

			req.setAttribute("lastPage", pageCount);
			req.setAttribute("orders", orders);

			forwardToPage("/manager/home.jsp", req, resp);
			req.getSession().removeAttribute(SUCCESS_MESSAGE);
			req.getSession().removeAttribute(ERROR_MESSAGE);
		} catch (ValidationException e) {
			req.getSession().setAttribute(ERROR_MESSAGE, e.getMessage());
			LOG.error(e.getMessage(), e);
			resp.sendRedirect("/manager/home");
			e.printStackTrace();
		}
	}
}
