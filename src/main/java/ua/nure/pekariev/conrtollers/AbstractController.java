package ua.nure.pekariev.conrtollers;

import static ua.nure.pekariev.Constants.CURRENT_ACCOUNT;
import static ua.nure.pekariev.Constants.CAR;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.pekariev.context.ApplicationContext;
import ua.nure.pekariev.exceptions.ValidationException;
import ua.nure.pekariev.model.Account;
import ua.nure.pekariev.model.Car;
import ua.nure.pekariev.model.Checkable;
import ua.nure.pekariev.model.Order;
import ua.nure.pekariev.services.AdminService;
import ua.nure.pekariev.services.ClientService;
import ua.nure.pekariev.services.CommonService;
import ua.nure.pekariev.services.ManagerService;
import ua.nure.pekariev.services.NotificationService;
import ua.nure.pekariev.utils.DateTimeUtils;

public abstract class AbstractController extends HttpServlet {
	private static final long serialVersionUID = -1745923969777520720L;

	private ApplicationContext applicationContext;

	@Override
	public final void init() throws ServletException {
		applicationContext = ApplicationContext.getApplicationContext(getServletContext());
	}

	public final int getPageCount(Long totalCount, int itemsPerPage) {
		int res = (int) (totalCount / itemsPerPage);
		if (res * itemsPerPage != totalCount) {
			res++;
		}
		return res;
	}

	public final int getPage(HttpServletRequest request) {
		try {
			return Integer.parseInt(request.getParameter("page"));
		} catch (NumberFormatException e) {
			return 1;
		}
	}

	protected void forwardToPage(String pageName, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.setAttribute("currentPage", "/WEB-INF/views/" + pageName);
		req.getRequestDispatcher("/WEB-INF/views/page-template.jsp").forward(req, resp);
	}

	protected void setCheckedinput(String[] checkedParameters, List<Checkable> elementsList) {
		if (checkedParameters != null) {
			List<String> checkedElementsList = Arrays.asList(checkedParameters);
			for (Checkable element : elementsList) {
				if (checkedElementsList.contains(element.getName())) {
					element.setChecked(true);
				}
			}
		}
	}

	protected String printCarModelsHtml(List<String> carModels) {
		StringBuilder builder = new StringBuilder();
		builder.append("<option selected>Choose...</option>");
		carModels.forEach(model -> builder.append("<option value=\"").append(model).append("\">").append(model)
				.append("</option>"));
		return builder.toString();
	}

	protected List<Integer> getYearsLIst() {
		List<Integer> years = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		for (int i = 1970; i <= calendar.get(Calendar.YEAR); i++) {
			years.add(i);
		}
		return years;
	}

	protected Account fillAccountFromRequest(Account account, HttpServletRequest req) {

		if (account.getFirstName() == null) {
			account.setFirstName(req.getParameter("firstName"));
		}

		if (account.getLastName() == null) {
			account.setLastName(req.getParameter("lastName"));
		}

		if (account.getEmail() == null) {
			account.setEmail(req.getParameter("email"));
		}

		if (account.getPassportData() == null) {
			account.setPassportData(req.getParameter("passportData"));
		}

		if (account.getPassword() == null) {
			account.setPassword(req.getParameter("password"));
		}

		if (account.getAddress() == null) {
			account.setAddress(req.getParameter("address"));
		}

		if (account.getPhone() == null) {
			account.setPhone(Long.parseLong(req.getParameter("phone")));
		}
		return account;
	}

	protected Car fillCarFromRequest(Car car, HttpServletRequest req) throws ValidationException {

		car.setManufacturer(req.getParameter("carName"));
		car.setModel(req.getParameter("carModel"));
		try {
			Integer year = Integer.parseInt(req.getParameter("year"));
			car.setYear(year);
		} catch (NullPointerException e) {
			car.setYear(0);
			throw new ValidationException("Year is invalid");
		}
		try {
			Integer rentValue = Integer.parseInt(req.getParameter("rentValue"));
			car.setRentValuePerDay(rentValue);
		} catch (NullPointerException e) {
			car.setRentValuePerDay(0);
			throw new ValidationException("Rent value is invalid");
		}
		car.setStateNumber(req.getParameter("stateNumber"));
		car.setRentValuePerDay(Integer.parseInt(req.getParameter("rentValue")));
		car.setEquipmentInformation(req.getParameter("equipmentInformation"));
		car.setAirConditioner(Boolean.parseBoolean(req.getParameter("airConditioner")));
		car.setNavigation(Boolean.parseBoolean(req.getParameter("navigation")));
		return car;
	}

	protected Order fillOrderFromRequset(Order order, HttpServletRequest req) throws ParseException {
		order.setCar((Car) req.getSession().getAttribute(CAR));
		order.setAccount((Account) req.getSession().getAttribute(CURRENT_ACCOUNT));
		order.setStarts(DateTimeUtils.stringToDate(req.getParameter("startDate")));
		order.setExpires(DateTimeUtils.stringToDate(req.getParameter("endDate")));
		order.setWithDriver(Boolean.parseBoolean(req.getParameter("withDriver")));
		return order;
	}

	protected Order fillOrderFromRequset(Order order, Account account, Car car, HttpServletRequest req)
			throws ParseException {
		order.setCar(car);
		order.setAccount(account);
		order.setStarts(DateTimeUtils.stringToDate(req.getParameter("startDate")));
		order.setExpires(DateTimeUtils.stringToDate(req.getParameter("endDate")));
		order.setWithDriver(Boolean.parseBoolean(req.getParameter("withDriver")));
		return order;
	}

	public CommonService getCommonService() {
		return applicationContext.getCommonService();
	}

	public ClientService getClientService() {
		return applicationContext.getClientService();
	}

	public ManagerService getManagerService() {
		return applicationContext.getManagerService();
	}

	public AdminService getAdminService() {
		return applicationContext.getAdminService();
	}

	public NotificationService getNotificationService() {
		return applicationContext.getNotificationService();
	}

}
