package ua.nure.pekariev.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebFilter(filterName = "ErrorHandler")
public class ErrorHandler extends AbstractFilter {

	private static final Logger LOG = LoggerFactory.getLogger(ErrorHandler.class);

	@Override
	public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		try {
			chain.doFilter(req, resp);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			resp.sendRedirect("/error");
		}
	}
}
