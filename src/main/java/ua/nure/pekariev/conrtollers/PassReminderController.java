package ua.nure.pekariev.conrtollers;

import static ua.nure.pekariev.Constants.CURRENT_ACCOUNT;
import static ua.nure.pekariev.Constants.PASWORD_REMINDER_EMAIL_SUBJECT;
import static ua.nure.pekariev.Constants.SUCCESS_MESSAGE;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.nure.pekariev.model.Account;

@WebServlet("/forgot-password")
public class PassReminderController extends AbstractController {

	private static final Logger LOG = LoggerFactory.getLogger(PassReminderController.class);

	private static final long serialVersionUID = 4269776035205330957L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Account currentAccount = (Account) req.getSession().getAttribute(CURRENT_ACCOUNT);
		if (currentAccount != null) {
			resp.sendRedirect("/" + currentAccount.getRole().getName() + "/home");
		} else {
			forwardToPage("password-reminder.jsp", req, resp);
		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		String pass = getCommonService().remindPassword(email);
		if (StringUtils.isNotBlank(pass)) {
			getNotificationService().sendNotificationMessage(email, PASWORD_REMINDER_EMAIL_SUBJECT,
					"Your password: " + pass);
			LOG.info("Password reminder was sent to email: " + email);
		}
		resp.sendRedirect("/login");
		req.getSession().setAttribute(SUCCESS_MESSAGE, "Your password was sent to your email");
	}
}
