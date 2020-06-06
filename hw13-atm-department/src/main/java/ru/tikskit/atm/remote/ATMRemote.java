package ru.tikskit.atm.remote;

import ru.tikskit.atm.remote.accesschecking.RemoteAccessException;

public interface ATMRemote {

    int getId(String user, String pass) throws RemoteAccessException;
    Memento store(String user, String pass) throws RemoteAccessException;

}
