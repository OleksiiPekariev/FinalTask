package ua.nure.pekariev.conrtollers;

import static ua.nure.pekariev.Constants.CURRENT_ACCOUNT;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.pekariev.model.Account;

@WebServlet(urlPatterns = "/logout")
public class LogoutController extends AbstractController {
	private static final long serialVersionUID = 1385428631136401791L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Account currentAcc = (Account) req.getSession().getAttribute(CURRENT_ACCOUNT);
		if (currentAcc != null) {
			req.getSession().invalidate();
		}
		resp.sendRedirect("/login");
	}
}
