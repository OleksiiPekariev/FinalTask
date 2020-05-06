package ua.nure.pekariev.conrtollers;

import static ua.nure.pekariev.Constants.CURRENT_ACCOUNT;
import static ua.nure.pekariev.Constants.ERROR_MESSAGE;
import static ua.nure.pekariev.Constants.REFERER;
import static ua.nure.pekariev.Constants.SUCCESS_MESSAGE;
import static ua.nure.pekariev.Constants.TARGET_URI;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.nure.pekariev.exceptions.ValidationException;
import ua.nure.pekariev.model.Account;

@WebServlet("/login")
public class LoginController extends AbstractController {

	private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

	private static final long serialVersionUID = 4269776035205330957L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Account currentAccount = (Account) req.getSession().getAttribute(CURRENT_ACCOUNT);
		if (currentAccount == null) {
			forwardToPage("login.jsp", req, resp);
		} else {
			String refererUrl = req.getHeader(REFERER);
			if (refererUrl == null) {
				String role = currentAccount.getRole().getName();
				resp.sendRedirect("/" + role + "/home");
			} else {
				resp.sendRedirect(refererUrl);
			}
		}
		req.getSession().removeAttribute(SUCCESS_MESSAGE);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String login = req.getParameter("login");
		String password = req.getParameter("password");
		String targetUri = (String) req.getSession().getAttribute(TARGET_URI);
		try {
			Account currentAccount = getCommonService().login(login, password);
			req.getSession().setAttribute(CURRENT_ACCOUNT, currentAccount);
			if (targetUri != null) {
				resp.sendRedirect(targetUri);
				req.getSession().removeAttribute(TARGET_URI);
			} else {
				String role = currentAccount.getRole().getName();
				resp.sendRedirect("/" + role + "/home");
			}
		} catch (ValidationException e) {
			req.setAttribute(ERROR_MESSAGE, e.getMessage());
			LOG.error(e.getMessage(), e);
			forwardToPage("login.jsp", req, resp);
			e.printStackTrace();
		}
	}

}
