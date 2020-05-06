package ua.nure.pekariev.context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.commons.dbcp2.BasicDataSource;

import ua.nure.pekariev.dao.AccountDao;
import ua.nure.pekariev.dao.CarsDao;
import ua.nure.pekariev.dao.OrdersDao;
import ua.nure.pekariev.dao.impl.AccountDaoImpl;
import ua.nure.pekariev.dao.impl.CarsDaoImpl;
import ua.nure.pekariev.dao.impl.OrderDaoImpl;
import ua.nure.pekariev.exceptions.ApplicationException;
import ua.nure.pekariev.services.AdminService;
import ua.nure.pekariev.services.ClientService;
import ua.nure.pekariev.services.CommonService;
import ua.nure.pekariev.services.I18nService;
import ua.nure.pekariev.services.ManagerService;
import ua.nure.pekariev.services.NotificationService;
import ua.nure.pekariev.services.impl.AdminSeviceImpl;
import ua.nure.pekariev.services.impl.AsyncEmailNotificationService;
import ua.nure.pekariev.services.impl.ClientServiceImpl;
import ua.nure.pekariev.services.impl.CommonServiceImpl;
import ua.nure.pekariev.services.impl.I18nServiceImpl;
import ua.nure.pekariev.services.impl.ManagerServiceImpl;

public final class ApplicationContext {
	public static final String APP_CONTEXT = "APP_CONTEXT";
	private static final Properties applicationProperties = new Properties();

	private final BasicDataSource dataSource;

	private final CommonService commonService;
	private final AdminService adminService;
	private final ManagerService managerService;
	private final ClientService clientService;
	private final NotificationService notificationService;
	private final I18nService i18nService;

	private final AccountDao accountDao;
	private final CarsDao carsDao;
	private final OrdersDao ordersDao;

	private ApplicationContext() {
		loadApplicationProperties();
		dataSource = setupDataSource();
		accountDao = new AccountDaoImpl();
		carsDao = new CarsDaoImpl();
		ordersDao = new OrderDaoImpl();
		commonService = new CommonServiceImpl(dataSource, accountDao, carsDao);
		adminService = new AdminSeviceImpl(dataSource, accountDao, carsDao, ordersDao);
		managerService = new ManagerServiceImpl(dataSource, carsDao, ordersDao);
		clientService = new ClientServiceImpl(dataSource, ordersDao);
		notificationService = new AsyncEmailNotificationService();
		i18nService = new I18nServiceImpl();
	}

	public static ApplicationContext getApplicationContext(ServletContext sc) {
		ApplicationContext context = (ApplicationContext) sc.getAttribute(APP_CONTEXT);
		if (context == null) {
			context = new ApplicationContext();
			sc.setAttribute(APP_CONTEXT, context);
		}
		return context;
	}

	public BasicDataSource getDataSource() {
		return dataSource;
	}

	public CommonService getCommonService() {
		return commonService;
	}

	public AdminService getAdminService() {
		return adminService;
	}

	public ManagerService getManagerService() {
		return managerService;
	}

	public ClientService getClientService() {
		return clientService;
	}

	public OrdersDao getOrderDao() {
		return ordersDao;
	}

	public AccountDao getAccauntDao() {
		return accountDao;
	}

	public CarsDao getCarsDao() {
		return carsDao;
	}

	public I18nService getI18nService() {
		return i18nService;
	}

	public NotificationService getNotificationService() {
		return notificationService;
	}

	public static String getApplicationProperty(String key) {
		String value = applicationProperties.getProperty(key);
		if (isSystemProperty(value)) {
			return resolveSystemProperty(value);
		} else {
			return value;
		}
	}

	public void shutDown() {
		try {
			dataSource.close();
		} catch (Exception e) {

		}
	}

	private BasicDataSource setupDataSource() {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(getApplicationProperty("db.driver"));
		ds.setUrl(getApplicationProperty("db.url"));
		ds.setUsername(getApplicationProperty("db.username"));
		ds.setPassword(getApplicationProperty("db.password"));
		ds.setInitialSize(Integer.parseInt(getApplicationProperty("db.pool.initSize")));
		ds.setMaxTotal(Integer.parseInt(getApplicationProperty("db.pool.maxSize")));
		return ds;
	}

	private void loadApplicationProperties() {
		try (InputStream in = ApplicationContext.class.getClassLoader().getResourceAsStream("application.properties")) {
			applicationProperties.load(in);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static boolean isSystemProperty(String propertyValue) {
		return propertyValue.startsWith("${sysProp.");
	}

	private static String resolveSystemProperty(String propertyValue) {
		String systemProperty = propertyValue.replace("${sysProp.", "").replace("}", "");
		String value = System.getProperty(systemProperty);
		if (value != null) {
			return value;
		} else {
			throw new ApplicationException("System property " + systemProperty + " not found");
		}
	}
}
