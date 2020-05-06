package ua.nure.pekariev.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import ua.nure.pekariev.context.ApplicationContext;

public class ApplicationContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ApplicationContext.getApplicationContext(sce.getServletContext());
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ApplicationContext.getApplicationContext(sce.getServletContext()).shutDown();

	}
	

}
