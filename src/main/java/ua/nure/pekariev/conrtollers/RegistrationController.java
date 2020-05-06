package ua.nure.pekariev.conrtollers;

import static ua.nure.pekariev.Constants.SUCCESS_MESSAGE;
import static ua.nure.pekariev.Constants.ERROR_MESSAGE;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.nure.pekariev.exceptions.ValidationException;
import ua.nure.pekariev.model.Account;

@WebServlet("/registration")
public class RegistrationController extends AbstractController {
	private static final Logger LOG = LoggerFactory.getLogger(RegistrationController.class);

	private static final long serialVersionUID = -5297778884336388618L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		forwardToPage("registration.jsp", req, resp);
		req.getSession().removeAttribute(ERROR_MESSAGE);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String login = req.getParameter("email");
		String password = req.getParameter("password");
		String confirmedPassword = req.getParameter("confirmedPassword");
		try {
			if (confirmedPassword.equals(password)) {
				Account currentAcc = getCommonService().register(login, password, firstName, lastName);
				req.getSession().setAttribute(SUCCESS_MESSAGE,
						"You account was successfully created with email: " + currentAcc.getEmail() + ". Please login");
				resp.sendRedirect("/login");
			} else {
				throw new ValidationException("Passwords should be equal!");
			}
		} catch (ValidationException e) {
			req.getSession().setAttribute(ERROR_MESSAGE, e.getMessage());
			LOG.error(e.getMessage(), e);
			forwardToPage("registration.jsp", req, resp);
		}
	}
}
