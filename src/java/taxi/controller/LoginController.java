package taxi.controller;

import taxi.exception.AuthenticationException;
import taxi.lib.Injector;
import taxi.model.Driver;
import taxi.service.AuthenticationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginController extends HttpServlet {
    private static final String LOGIN_JSP_PATH = "/WEB-INF/views/login.jsp";
    private static final String INDEX_URL = "/index";
    private static final String ATTRIBUTE_SESSION_ID = "driver_id";
    private static final Injector injector = Injector.getInstance("taxi");
    private AuthenticationService authenticationService =
            (AuthenticationService) injector.getInstance(AuthenticationService.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher(LOGIN_JSP_PATH).forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("login");
        String password = req.getParameter("password");
        try {
            Driver driver = authenticationService.login(username, password);
            HttpSession session = req.getSession();
            session.setAttribute(ATTRIBUTE_SESSION_ID, driver.getId());
            resp.sendRedirect(INDEX_URL);
        } catch (AuthenticationException e) {
            req.setAttribute("errorMsg", e.getMessage());
            req.getRequestDispatcher(LOGIN_JSP_PATH).forward(req, resp);
        }
    }
}
