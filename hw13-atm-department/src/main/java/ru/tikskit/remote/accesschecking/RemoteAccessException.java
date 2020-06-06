package ru.tikskit.remote.accesschecking;

import ru.tikskit.atm.ATMException;

public class RemoteAccessException extends ATMException {
    public RemoteAccessException(String message) {
        super(message);
    }
}
