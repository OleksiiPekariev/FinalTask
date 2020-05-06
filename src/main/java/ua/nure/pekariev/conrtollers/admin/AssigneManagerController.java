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
import ua.nure.pekariev.model.Account;

@WebServlet("/admin/assigneManager")
public class AssigneManagerController extends AbstractController {

	private static final Logger LOG = LoggerFactory.getLogger(AssigneManagerController.class);

	private static final long serialVersionUID = -4538996562980667612L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long userId = Long.parseLong(req.getParameter("id"));
		try {
			Account account = getAdminService().assigneUserAsManager(userId);
			req.getSession().setAttribute(SUCCESS_MESSAGE,
					"The account with ID = " + account.getId() + " was succsesfully assigned as manager");
			resp.sendRedirect("/admin/accounts");
		} catch (ValidationException e) {
			req.getSession().setAttribute(ERROR_MESSAGE, e.getMessage());
			LOG.error(e.getMessage(), e);
			resp.sendRedirect("/admin/accounts");
			e.printStackTrace();
		}
	}
}
