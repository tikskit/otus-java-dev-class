package ru.tikskit;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.AddressDataSet;
import ru.otus.core.model.PhoneDataSet;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;
import ru.otus.core.service.DbServiceUserImpl;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.hibernate.dao.UserDaoHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.Optional;

public class HW21CacheMainClass {

    private static Logger logger = LoggerFactory.getLogger(HW21CacheMainClass.class);

    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml",
                User.class, AddressDataSet.class, PhoneDataSet.class);

        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new UserDaoHibernate(sessionManager);
        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);


        User vasya = new User(0, "вася", 28);
        vasya.setAddress(new AddressDataSet(0, "Lenina"));
        vasya.getPhones().add(new PhoneDataSet(0, "123456"));
        vasya.getPhones().add(new PhoneDataSet(0, "ASDFG"));


        long id = dbServiceUser.saveUser(vasya);
        logger.info("new user id: {}", id);

        Optional<User> dbUser = dbServiceUser.getUser(id);
        //logger.info("user: {}", dbUser);
        Optional<User> dbUser1 = dbServiceUser.getUser(id);
        //logger.info("user: {}", dbUser1);

    }
}
