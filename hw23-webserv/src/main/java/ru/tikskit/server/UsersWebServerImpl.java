package ru.tikskit.server;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.core.service.DBServiceUser;
import ru.otus.helpers.FileSystemHelper;

import org.eclipse.jetty.server.Server;
import ru.tikskit.services.TemplateProcessor;
import ru.tikskit.services.UserAuthService;
import ru.tikskit.servlet.AuthorizationFilter;
import ru.tikskit.servlet.LoginServlet;
import ru.tikskit.servlet.NewUserServlet;
import ru.tikskit.servlet.UsersServlet;

import java.util.Arrays;

public class UsersWebServerImpl implements UsersWebServer {
    private static final String START_PAGE_NAME = "index.html";
    private static final String COMMON_RESOURCES_DIR = "static";


    private final DBServiceUser dbServiceUser;
    private final TemplateProcessor templateProcessor;
    private final Server server;
    private final UserAuthService authService;


    public UsersWebServerImpl(int port,
                              UserAuthService authService,
                              DBServiceUser dbServiceUser,
                              TemplateProcessor templateProcessor) {

        this.dbServiceUser = dbServiceUser;
        this.templateProcessor = templateProcessor;
        this.authService = authService;
        server = new Server(port);
    }

    @Override
    public void start() throws Exception {
        if (server.getHandlers().length == 0) {
            initContext();
        }
        server.start();
    }

    @Override
    public void join() throws Exception {
        server.join();
    }

    @Override
    public void stop() throws Exception {
        server.stop();
    }

    private Server initContext() {
        ResourceHandler resourceHandler = createResourceHandler();
        ServletContextHandler servletContextHandler = createServletContextHandler();

        HandlerList handlers = new HandlerList();
        handlers.addHandler(resourceHandler);
        handlers.addHandler(applySecurity(servletContextHandler, "/users", "/newuser"));


        server.setHandler(handlers);
        return server;
    }

    private ResourceHandler createResourceHandler() {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(false);
        resourceHandler.setWelcomeFiles(new String[]{START_PAGE_NAME});
        resourceHandler.setResourceBase(FileSystemHelper.localFileNameOrResourceNameToFullPath(COMMON_RESOURCES_DIR));
        return resourceHandler;
    }

    private ServletContextHandler createServletContextHandler() {
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.addServlet(new ServletHolder(new UsersServlet(templateProcessor, dbServiceUser)), "/users");
        servletContextHandler.addServlet(new ServletHolder(new NewUserServlet(dbServiceUser, COMMON_RESOURCES_DIR)), "/newuser");
        return servletContextHandler;
    }

    private Handler applySecurity(ServletContextHandler servletContextHandler, String... paths) {
        servletContextHandler.addServlet(new ServletHolder(new LoginServlet(authService, COMMON_RESOURCES_DIR)), "/login");
        AuthorizationFilter authorizationFilter = new AuthorizationFilter();
        Arrays.stream(paths).forEachOrdered(path -> servletContextHandler.addFilter(new FilterHolder(authorizationFilter), path, null));
        return servletContextHandler;
    }

}
