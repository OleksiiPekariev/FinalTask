package ua.nure.pekariev.filters;

import static ua.nure.pekariev.Constants.SESSION_LOCALE;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

@WebFilter(filterName = "LangFilter")
public class LangFilter extends AbstractFilter {

	public static final String LANG_PARAM = "lang";

	@Override
	public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		String lang = req.getParameter(LANG_PARAM);
		Locale sessionLocale = (Locale) req.getSession().getAttribute(SESSION_LOCALE);
		if (StringUtils.isNotBlank(lang)) {
			sessionLocale = new Locale(lang);
			req.getSession().setAttribute(SESSION_LOCALE, sessionLocale);
		}
		if (sessionLocale == null) {
			Locale browserLocale = req.getLocale();
			req.getSession().setAttribute(SESSION_LOCALE, browserLocale);
		}
		chain.doFilter(req, resp);
	}

}
