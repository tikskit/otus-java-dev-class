package ru.tikskit.remote.atm;

import ru.tikskit.remote.accesschecking.RemoteAccessException;

public interface ATMRemote {

    int getId(String user, String pass) throws RemoteAccessException;
    Memento store(String user, String pass) throws RemoteAccessException;

}
