package ru.tikskit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;
import ru.otus.core.service.DbServiceUserImpl;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.hibernate.dao.UserDaoHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;
import ru.tikskit.server.UsersWebServer;
import ru.tikskit.server.UsersWebServerImpl;
import ru.tikskit.services.TemplateProcessor;
import ru.tikskit.services.TemplateProcessorImpl;
import ru.tikskit.services.UserAuthService;
import ru.tikskit.services.UserAuthServiceImpl;

public class HW23WebServerMainClass {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";

    private static Logger logger = LoggerFactory.getLogger(HW23WebServerMainClass.class);

    public static void main(String[] args) throws Exception {

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        DBServiceUser dbServiceUser = createDBServiceUser();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        UserAuthService authService = new UserAuthServiceImpl(dbServiceUser);

        UsersWebServer webServer = new UsersWebServerImpl(WEB_SERVER_PORT, authService, dbServiceUser, gson,
                templateProcessor);

        populateUsers(dbServiceUser);

        webServer.start();
        webServer.join();
    }

    private static DBServiceUser createDBServiceUser() {
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml",
                User.class);

        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new UserDaoHibernate(sessionManager);
        return new DbServiceUserImpl(userDao);
    }

    private static void populateUsers(DBServiceUser dbServiceUser) {
        User user = new User(0, "admin", "123");
        dbServiceUser.saveUser(user);
/*
        dbServiceUser.saveUser(new User(2, "programmer", "qwe123"));
        dbServiceUser.saveUser(new User(3, "driver", "GfjklsfF234!!!f_s"));
*/
    }

}
