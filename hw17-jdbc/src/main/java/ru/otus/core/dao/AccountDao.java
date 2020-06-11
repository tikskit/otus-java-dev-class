package ru.otus.core.dao;

import ru.otus.core.model.Account;
import ru.otus.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface AccountDao {
    Optional<Account> findByNo(long no);

    long insertAccount(Account account);

    //void updateUser(User user);
    //void insertOrUpdate(User user);

    SessionManager getSessionManager();

}
