package ru.otus;

import java.sql.SQLException;
import java.util.Optional;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.UserDaoImpl;
import ru.otus.core.service.DbServiceUserImpl;
import ru.otus.h2.DataSourceH2;
import ru.otus.core.model.User;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

/**
 * @author sergey
 * created on 03.02.19.
 */
public class DbServiceDemo {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceDemo.class);

    public static void main(String[] args) throws Exception {
        var dataSource = new DataSourceH2();
        var demo = new DbServiceDemo();

        demo.createUserTable(dataSource);
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

     private void createUserTable(DataSource dataSource) throws SQLException {
        try (var connection = dataSource.getConnection();
             var pst = connection.prepareStatement(
                     "create table user(id bigint(20) not null auto_increment, name varchar(255), age int(3))")) {
            pst.executeUpdate();
        }
        System.out.println("table created");
    }
}
