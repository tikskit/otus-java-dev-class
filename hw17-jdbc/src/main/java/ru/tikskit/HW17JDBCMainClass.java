package ru.tikskit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.AccountDaoImpl;
import ru.otus.core.dao.UserDaoImpl;
import ru.otus.core.model.Account;
import ru.otus.core.model.User;
import ru.otus.core.service.DbServiceAccountImpl;
import ru.otus.core.service.DbServiceUserImpl;
import ru.otus.h2.DataSourceH2;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Optional;

public class HW17JDBCMainClass {
    private static final Logger logger = LoggerFactory.getLogger(HW17JDBCMainClass.class);

    public static void main(String[] args) throws Exception {
        var dataSource = new DataSourceH2();
        var demo = new HW17JDBCMainClass();

        demo.createUserTable(dataSource);
        demo.createAccountTable(dataSource);
        var sessionManager = new SessionManagerJdbc(dataSource);
        var userDao = new UserDaoImpl(sessionManager);
        var accountDao = new AccountDaoImpl(sessionManager);

        var dbServiceUser = new DbServiceUserImpl(userDao);
        var id = dbServiceUser.saveUser(new User(0, "dbServiceUser", 10));
        Optional<User> user = dbServiceUser.getUser(id);

        user.ifPresentOrElse(
                crUser -> logger.info("created user, name:{}", crUser.getName()),
                () -> logger.info("user was not created")
        );

        var dbServiceAccount = new DbServiceAccountImpl(accountDao);
        var no = dbServiceAccount.saveAccount(new Account(0, "dbServiceAccountType", new BigDecimal(1.5)));
        Optional<Account> account = dbServiceAccount.getAccount(no);

        account.ifPresentOrElse(
                crAccount -> logger.info("created account, type:{}", crAccount.getType()),
                () -> logger.info("account was not created")
        );

    }

    private void createUserTable(DataSource dataSource) throws SQLException {
        try (var connection = dataSource.getConnection();
             var pst = connection.prepareStatement(
                     "create table User(id bigint(20) not null auto_increment, name varchar(255), age int(3))")) {
            pst.executeUpdate();
        }
        System.out.println("table User created");
    }

    private void createAccountTable(DataSource dataSource) throws SQLException {
        try (var connection = dataSource.getConnection();
             var pst = connection.prepareStatement(
                     "create table Account(no bigint(20) not null auto_increment, type varchar(255), rest number)")
        ) {
            pst.executeUpdate();
        }
        System.out.println("table Account created");
    }
}
