package ua.nure.pekariev.filters;

import static ua.nure.pekariev.Constants.CURRENT_ACCOUNT;
import static ua.nure.pekariev.Constants.ERROR_MESSAGE;
import static ua.nure.pekariev.Constants.SESSION_LOCALE;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.pekariev.context.ApplicationContext;
import ua.nure.pekariev.model.Account;

@WebFilter(filterName = "OrderFilter")
public class CarOrderFilter extends AbstractFilter {

	@Override
	public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		ApplicationContext appContext = ApplicationContext.getApplicationContext(req.getServletContext());
		Locale sessionLocale = (Locale) req.getSession().getAttribute(SESSION_LOCALE);

		Account currentAcc = (Account) req.getSession().getAttribute(CURRENT_ACCOUNT);
		if (currentAcc != null) {
			String role = currentAcc.getRole().getName();
			if (!"client".equals(role)) {

				String message = appContext.getI18nService().getMessage("message.login_as_client_for_order",
						sessionLocale);

				req.getSession().setAttribute(ERROR_MESSAGE, message);
				resp.sendRedirect("/cars");
			}
		}
		chain.doFilter(req, resp);
	}
}
