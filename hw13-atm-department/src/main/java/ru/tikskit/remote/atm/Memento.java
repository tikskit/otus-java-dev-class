package ru.tikskit.remote.atm;

import ru.tikskit.remote.accesschecking.RemoteAccessException;

public interface Memento {
    void restore(String user, String pass) throws RemoteAccessException;
}
