package ua.nure.pekariev.listeners;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class ApplicationSessionListener implements HttpSessionListener {

	private static final Logger LOG = LoggerFactory.getLogger(ApplicationSessionListener.class);

	public void sessionCreated(HttpSessionEvent se) {
		LOG.debug("Created " + se.getSession().getId());
	}

	public void sessionDestroyed(HttpSessionEvent se) {
		LOG.debug("Destroyed " + se.getSession().getId());
	}
}
