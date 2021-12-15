package taxi.controller.car;

import taxi.lib.Injector;
import taxi.model.Car;
import taxi.model.Manufacturer;
import taxi.service.CarService;
import taxi.service.ManufacturerService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddCarController extends HttpServlet {
    private static final String CARS_ADD_JSP_PATH = "/WEB-INF/views/cars/add.jsp";
    private static final String CARS_ADD_URL = "/cars/add";
    private static final Injector injector = Injector.getInstance("taxi");
    private final CarService carService = (CarService) injector
            .getInstance(CarService.class);
    private final ManufacturerService manufacturerService = (ManufacturerService) injector
            .getInstance(ManufacturerService.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher(CARS_ADD_JSP_PATH).forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String model = req.getParameter("model");
        long manufacturerId = Long.parseLong(req.getParameter("manufacturer_id"));
        Manufacturer manufacturer = manufacturerService.get(manufacturerId);
        Car car = new Car(model, manufacturer);
        carService.create(car);
        resp.sendRedirect(CARS_ADD_URL);
    }
}
