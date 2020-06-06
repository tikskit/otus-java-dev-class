package ru.tikskit.atm.remote;

import ru.tikskit.atm.remote.accesschecking.RemoteAccessException;

public interface Memento {
    void restore(String user, String pass) throws RemoteAccessException;
}
