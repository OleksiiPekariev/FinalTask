package ua.nure.pekariev.conrtollers.client;

import static ua.nure.pekariev.Constants.CURRENT_ACCOUNT;
import static ua.nure.pekariev.Constants.MAX_ACCOUNTS_PER_HTML_PAGE;
import static ua.nure.pekariev.Constants.MAX_ORDERS_PER_HTML_PAGE;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.pekariev.conrtollers.AbstractController;
import ua.nure.pekariev.model.Account;
import ua.nure.pekariev.model.Order;

@WebServlet("/client/history")
public class HistoryController extends AbstractController {

	private static final long serialVersionUID = -8803563166414184413L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Account account = (Account) req.getSession().getAttribute(CURRENT_ACCOUNT);
		req.setAttribute("currenURI", req.getRequestURI());
		int currentPageNumber = getPage(req);
		String sortBy = req.getParameter("sortBy");
		List<Order> historyCarOrders = getClientService().getHistoryOrdersForUser(account.getId(), sortBy,
				currentPageNumber, MAX_ORDERS_PER_HTML_PAGE);
		Long countHistoryCarOrders = getClientService().countHistoryOrders(account.getId());
		int pageCount = getPageCount(countHistoryCarOrders, MAX_ACCOUNTS_PER_HTML_PAGE);
		req.setAttribute("lastPage", pageCount);
		req.setAttribute("orders", historyCarOrders);
		forwardToPage("/client/home.jsp", req, resp);
	}
}
