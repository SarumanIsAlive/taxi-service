package taxi.controller.driver;

import taxi.lib.Injector;
import taxi.model.Driver;
import taxi.service.DriverService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddDriverController extends HttpServlet {
    private static final String ADD_DRIVER_JSP_PATH = "/WEB-INF/views/drivers/add.jsp";
    private static final String ADD_DRIVER_URL = "/drivers/add";
    private static final Injector injector = Injector.getInstance("taxi");
    private final DriverService driverService = (DriverService) injector
            .getInstance(DriverService.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher(ADD_DRIVER_JSP_PATH).forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String name = req.getParameter("name");
        String licenseNumber = req.getParameter("license_number");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        Driver driver = new Driver(name, licenseNumber, login, password);
        driverService.create(driver);
        resp.sendRedirect(ADD_DRIVER_URL);
    }
}
