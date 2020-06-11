package ru.tikskit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.DbServiceDemo;
import ru.otus.core.dao.UserDaoImpl;
import ru.otus.core.model.User;
import ru.otus.core.service.DbServiceUserImpl;
import ru.otus.h2.DataSourceH2;
import ru.otus.jdbc.DbExecutorImpl;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Optional;

public class HW17JDBCMainClass {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceDemo.class);

    public static void main(String[] args) throws Exception {
        var dataSource = new DataSourceH2();
        var hw = new HW17JDBCMainClass();

        hw.createTable(dataSource);

        var sessionManager = new SessionManagerJdbc(dataSource);
        var userDao = new UserDaoImpl(sessionManager);

        var dbServiceUser = new DbServiceUserImpl(userDao);
        var id = dbServiceUser.saveUser(new User(0, "dbServiceUser", 10));
        Optional<User> user = dbServiceUser.getUser(id);

        user.ifPresentOrElse(
                crUser -> logger.info("created user, name:{}", crUser.getName()),
                () -> logger.info("user was not created")
        );

    }

    private void createTable(DataSource dataSource) throws SQLException {
        try (var connection = dataSource.getConnection();
             var pst = connection.prepareStatement("create table user(id long auto_increment, name varchar(50))")) {
            pst.executeUpdate();
        }
        System.out.println("table created");
    }}
