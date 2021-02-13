package ru.tikskit.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;
import ru.tikskit.services.TemplateProcessor;

import java.io.IOException;

public class NewUserServlet extends HttpServlet {
    private static final String NEW_USER_PAGE_TEMPLATE = "newuser.html";

    private final DBServiceUser dbServiceUser;
    private final TemplateProcessor templateProcessor;
    private final String commonResourceDir;

    public NewUserServlet(TemplateProcessor templateProcessor, DBServiceUser dbServiceUser, String commonResourceDir) {
        this.dbServiceUser = dbServiceUser;
        this.templateProcessor = templateProcessor;
        this.commonResourceDir = commonResourceDir;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.getWriter().println(ServletUtils.getLoginPageContent(commonResourceDir, NEW_USER_PAGE_TEMPLATE));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getUserFromRequest(req);
        if (user == null) {
            resp.sendRedirect("/newuser");
        } else {
            dbServiceUser.saveUser(user);
            resp.sendRedirect("/users");
        }
    }

    private User getUserFromRequest(HttpServletRequest req) {
        String login = req.getParameter("login");
        String pass = req.getParameter("pass");
        if (!login.equals("") && !pass.equals("")) {
            return new User(0, login, pass);
        } else {
            return null;
        }

    }
}
