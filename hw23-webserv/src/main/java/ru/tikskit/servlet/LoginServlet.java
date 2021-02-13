package ru.tikskit.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.tikskit.services.UserAuthService;

import java.io.IOException;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

public class LoginServlet extends HttpServlet {

    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "pass";
    private static final int MAX_INACTIVE_INTERVAL = 30;
    private static final String LOGIN_PAGE_TEMPLATE = "index.html";


    private final UserAuthService userAuthService;
    private final String commonResourceDir;

    public LoginServlet(UserAuthService userAuthService, String commonResourceDir) {
        this.userAuthService = userAuthService;
        this.commonResourceDir = commonResourceDir;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.getWriter().println(ServletUtils.getLoginPageContent(commonResourceDir, LOGIN_PAGE_TEMPLATE));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String name = request.getParameter(PARAM_LOGIN);
        String password = request.getParameter(PARAM_PASSWORD);

        if (userAuthService.authenticate(name, password)) {
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(MAX_INACTIVE_INTERVAL);
            response.sendRedirect("/users");
        } else {
            response.setStatus(SC_UNAUTHORIZED);
        }
    }


}
