package ru.otus.core.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.model.User;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.jdbc.mapper.JdbcMapper;
import ru.otus.jdbc.mapper.JdbcMapperImpl;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;


import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    private final SessionManagerJdbc sessionManager;
    private final JdbcMapper<User> mapper;

    public UserDaoImpl(SessionManagerJdbc sessionManager) {
        this.sessionManager = sessionManager;
        mapper = new JdbcMapperImpl<>(User.class, sessionManager);
    }

    @Override
    public Optional<User> findById(long id) {
        return Optional.ofNullable(mapper.findById(id, User.class));
    }

    @Override
    public long insertUser(User user) {
        mapper.insert(user);
        return user.getId();
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
