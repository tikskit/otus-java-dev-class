package ru.tikskit.remote.atm;

import ru.tikskit.remote.RemoteAccessException;

public interface Memento {
    void restore(String user, String pass) throws RemoteAccessException;
}
