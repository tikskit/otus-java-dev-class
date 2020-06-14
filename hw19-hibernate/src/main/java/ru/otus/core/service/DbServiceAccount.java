package ru.otus.core.service;

import ru.otus.core.model.Account;
import ru.otus.core.model.User;

import java.util.Optional;

public interface DbServiceAccount {
    long saveAccount(Account account);

    Optional<Account> getAccount(long no);

}
