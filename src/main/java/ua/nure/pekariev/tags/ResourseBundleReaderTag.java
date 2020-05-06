package ua.nure.pekariev.tags;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.nure.pekariev.Constants;
import ua.nure.pekariev.context.ApplicationContext;

public class ResourseBundleReaderTag extends TagSupport {
	private static final long serialVersionUID = -9022104269869158404L;
	private static final Logger LOG = LoggerFactory.getLogger(ResourseBundleReaderTag.class);
	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			ApplicationContext appContext = ApplicationContext.getApplicationContext(request.getServletContext());
			Locale sessionLocale = (Locale) request.getSession().getAttribute(Constants.SESSION_LOCALE);
			String value = appContext.getI18nService().getMessage(this.getKey(), sessionLocale);
			out.print(value);
		} catch (IOException e) {
			LOG.error("Exception into tag " + this.getClass(), e);
		}
		return SKIP_BODY;
	}
}
