package ua.nure.pekariev.filters;

import static ua.nure.pekariev.Constants.CURRENT_ACCOUNT;
import static ua.nure.pekariev.Constants.TARGET_URI;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import ua.nure.pekariev.model.Account;

@WebFilter(filterName = "AuthFilter")
public class AuthFilter extends AbstractFilter {

	@Override
	public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		String reqUri = req.getRequestURI();
		Account currentAcc = (Account) req.getSession().getAttribute(CURRENT_ACCOUNT);
		if (isUrlForLoggedInAccount(reqUri)) {
			if (currentAcc == null) {
				req.getSession().setAttribute(TARGET_URI, reqUri + "?" + req.getQueryString());
				resp.sendRedirect("/login");
			} else {
				if (!StringUtils.contains(reqUri, currentAcc.getRole().getName())) {
					resp.sendRedirect("/" + currentAcc.getRole().getName() + "/home");
				} else {
					chain.doFilter(req, resp);
				}
			}
		} else {
			chain.doFilter(req, resp);
		}
	}

	private boolean isUrlForLoggedInAccount(String reqUri) {
		return StringUtils.startsWithIgnoreCase(reqUri, "/admin") || StringUtils.startsWithIgnoreCase(reqUri, "/client")
				|| StringUtils.startsWithIgnoreCase(reqUri, "/manager");
	}
}
