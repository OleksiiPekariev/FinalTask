package ua.nure.pekariev.conrtollers.client;

import static ua.nure.pekariev.Constants.CURRENT_ACCOUNT;
import static ua.nure.pekariev.Constants.CAR;
import static ua.nure.pekariev.Constants.ERROR_MESSAGE;
import static ua.nure.pekariev.Constants.SUCCESS_MESSAGE;
import static ua.nure.pekariev.Constants.BILL_EMAIL_SUBJECT;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.pekariev.conrtollers.AbstractController;
import ua.nure.pekariev.exceptions.OrderException;
import ua.nure.pekariev.exceptions.ValidationException;
import ua.nure.pekariev.model.Account;
import ua.nure.pekariev.model.Car;
import ua.nure.pekariev.model.Order;
import ua.nure.pekariev.utils.OrderUtils;

@WebServlet("/client/carOrder")
public class CarOrderController extends AbstractController {
	private static final long serialVersionUID = 6114738772041603560L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("currentDate", new Date());
		try {
			Long carId = Long.parseLong(req.getParameter("carID"));
			Car car = getCommonService().getCarById(carId);
			req.getSession().setAttribute("car", car);
			forwardToPage("/client/newOrder.jsp", req, resp);
		} catch (ValidationException ve) {
			req.setAttribute(ERROR_MESSAGE, ve.getMessage());
			forwardToPage("/client/home.jsp", req, resp);
			ve.printStackTrace();
		} catch (NumberFormatException e) {
			resp.sendRedirect("/cars");
		}
		req.getSession().removeAttribute(SUCCESS_MESSAGE);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Car car = (Car) req.getSession().getAttribute(CAR);
		Account currentAcc = (Account) req.getSession().getAttribute(CURRENT_ACCOUNT);
		Account accountToUpdate = fillAccountFromRequest(currentAcc, req);
		try {
			Account updatedAccount = getCommonService().updateAccount(accountToUpdate);
			Order order = fillOrderFromRequset(new Order(), updatedAccount, car, req);
			Order createdorder = getClientService().createOrder(order);
			req.getSession().setAttribute(SUCCESS_MESSAGE,
					"You order was created! You will receive the bill on your email " + currentAcc.getEmail());
			String content = OrderUtils.createOrderMessage(createdorder);
			getNotificationService().sendNotificationMessage("catch.the.fish2011@gmail.com", BILL_EMAIL_SUBJECT,
					content);
			req.getSession().setAttribute(CURRENT_ACCOUNT, updatedAccount);
			// updatedAccount.getEmail()
			resp.sendRedirect("/client/home");
			req.getSession().removeAttribute("car");
		} catch (OrderException | ParseException | ValidationException e) {
			req.setAttribute(ERROR_MESSAGE, e.getMessage());
			forwardToPage("/client/newOrder.jsp", req, resp);
			e.printStackTrace();
		}
	}
}
