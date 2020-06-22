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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class HW21CacheMainClass {

    private static Logger logger = LoggerFactory.getLogger(HW21CacheMainClass.class);

    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml",
                User.class, AddressDataSet.class, PhoneDataSet.class);

        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new UserDaoHibernate(sessionManager);
        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);

        List<Long> userIds = populateUsers(2048, dbServiceUser);

        try {
            // Эта пауза нужна, чтобы успеть запустить jconsole
            Thread.sleep(TimeUnit.SECONDS.toMillis(5));
            logger.info("we're about to start getting users");
            List<User> userList1 = getUsers(userIds, dbServiceUser);
            // На следующей строке удобно ставить брейкпойнит, чтобы посмотреть в логах как работал кеш
            logger.info("first round is done");
            List<User> userList2 = getUsers(userIds, dbServiceUser);
            logger.info("second round is done");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    private static List<Long> populateUsers(int count, DBServiceUser dbServiceUser) {

        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            User vasya = new User(0, "вася№" + i, 28);
            vasya.setAddress(new AddressDataSet(0, "Lenina"));
            vasya.getPhones().add(new PhoneDataSet(0, "123456"));
            vasya.getPhones().add(new PhoneDataSet(0, "ASDFG"));


            long id = dbServiceUser.saveUser(vasya);
            ids.add(id);
        }

        return ids;
    }

    private static List<User> getUsers(List<Long> ids, DBServiceUser dbServiceUser) throws InterruptedException {
        List<User> res = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            Optional<User> dbUser = dbServiceUser.getUser(ids.get(i));
            if (dbUser.isPresent()) {
                res.add(dbUser.get());
            }
            if (i % 10 == 0) {
                Thread.sleep(100);
            }
        }

        return res;
    }
}
