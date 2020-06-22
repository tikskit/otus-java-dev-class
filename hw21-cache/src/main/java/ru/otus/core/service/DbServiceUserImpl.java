package ru.otus.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.HwCache;
import ru.otus.cachehw.HwListener;
import ru.otus.cachehw.MyCache;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.User;

import java.util.Optional;

public class DbServiceUserImpl implements DBServiceUser {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceUserImpl.class);
    private static final HwCache<String, User> cache = new MyCache<>();

    /*
        Слушатель тут добавлен как поле класса, потому что если его добавить как анонимный класс, то он будет удален GC
        при первой же сборке
    */
    private final HwListener<String, User> cacheListener = new HwListener<String, User>() {
        @Override
        public void notify(String key, User value, String action) {
            if (action.compareToIgnoreCase("put") == 0) {
                logger.info("put user to the cache: {}", value);
            } else if (action.compareToIgnoreCase("get") == 0) {
                logger.info("got user from the cache: {}", value);
            } else if (action.compareToIgnoreCase("remove") == 0) {
                logger.info("removed user from the cache: {}", value);
            }
        }
    };

    private final UserDao userDao;

    public DbServiceUserImpl(UserDao userDao) {
        this.userDao = userDao;
        cache.addListener(cacheListener);
    }

    @Override
    public long saveUser(User user) {
        try (var sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                var userId = userDao.insertUser(user);
                sessionManager.commitSession();

                logger.info("created user: {}", userId);
                return userId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public Optional<User> getUser(long id) {
        User cachedUser = getUserFromCache(id);
        if (cachedUser != null) {
            return Optional.of(cachedUser);
        } else {
            Optional<User> user = getUserFromDB(id);
            if (user.isPresent()) {
                cache.put("id" + id, user.get());
            }

            return user;
        }
    }

    private User getUserFromCache(long id) {
        return cache.get("id" + id);
    }

    private Optional<User> getUserFromDB(long id) {
        try (var sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<User> userOptional = userDao.findById(id);

                logger.info("user from db: {}", userOptional.orElse(null));
                return userOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }
}
