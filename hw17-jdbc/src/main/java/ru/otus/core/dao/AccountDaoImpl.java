package ru.otus.core.dao;

import ru.otus.core.model.Account;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.jdbc.mapper.JdbcMapper;
import ru.otus.jdbc.mapper.JdbcMapperImpl;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import java.util.Optional;

public class AccountDaoImpl implements AccountDao {

    private final SessionManagerJdbc sessionManager;
    private final JdbcMapper<Account> mapper;

    public AccountDaoImpl(SessionManagerJdbc sessionManager) {
        this.sessionManager = sessionManager;
        mapper = new JdbcMapperImpl<>(Account.class, sessionManager);
    }

    @Override
    public Optional<Account> findByNo(long no) {
        return Optional.ofNullable(mapper.findById(no, Account.class));
    }

    @Override
    public long insertAccount(Account account) {
        mapper.insert(account);
        return account.getNo();
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
