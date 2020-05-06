package ua.nure.pekariev.conrtollers.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import ua.nure.pekariev.conrtollers.AbstractController;

@WebServlet("/ajax/sectionscontroller")
public class AjaxController extends AbstractController {

	private static final long serialVersionUID = 7905693409230526204L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String carName = (String) req.getParameter("carName");
		List<String> carModelsOfCarName = getAdminService().getCarModelsForName(carName);
		String modelDropDown = printCarModelsHtml(carModelsOfCarName);
		JSONObject jsonEnt = new JSONObject();
		jsonEnt.put("modelsDropdown", modelDropDown);
		resp.setContentType("application/json");
		resp.getWriter().print(jsonEnt.toString());
	}
}
