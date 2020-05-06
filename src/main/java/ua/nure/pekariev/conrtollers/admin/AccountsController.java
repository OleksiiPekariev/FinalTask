package ua.nure.pekariev.conrtollers.admin;

import static ua.nure.pekariev.Constants.MAX_ACCOUNTS_PER_HTML_PAGE;
import static ua.nure.pekariev.Constants.ERROR_MESSAGE;
import static ua.nure.pekariev.Constants.SUCCESS_MESSAGE;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.pekariev.conrtollers.AbstractController;
import ua.nure.pekariev.model.Account;

@WebServlet("/admin/accounts")
public class AccountsController extends AbstractController {

	private static final long serialVersionUID = -4538996562980667612L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int currentPageNumber = getPage(req);
		String roleParameter = req.getParameter("role");
		Long totalCount;
		List<Account> accounts;
		if (roleParameter == null) {
			accounts = getAdminService().getAll(currentPageNumber, MAX_ACCOUNTS_PER_HTML_PAGE);
			totalCount = getAdminService().countAllAccounts();
		} else {
			accounts = getAdminService().getAccountsWithRole(roleParameter, currentPageNumber,
					MAX_ACCOUNTS_PER_HTML_PAGE);
			totalCount = getAdminService().countAllAccountsWithRole(roleParameter);
		}

		req.setAttribute("accounts", accounts);
		req.setAttribute("lastPage", getPageCount(totalCount, MAX_ACCOUNTS_PER_HTML_PAGE));
		forwardToPage("admin/accounts.jsp", req, resp);
		req.getSession().removeAttribute(SUCCESS_MESSAGE);
		req.getSession().removeAttribute(ERROR_MESSAGE);
	}
}
