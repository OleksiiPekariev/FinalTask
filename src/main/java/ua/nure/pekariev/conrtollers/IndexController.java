package ua.nure.pekariev.conrtollers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/index.html", "/CarRent" })
public class IndexController extends AbstractController {
	private static final long serialVersionUID = 2723308912123285317L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("/cars");
	}
}
