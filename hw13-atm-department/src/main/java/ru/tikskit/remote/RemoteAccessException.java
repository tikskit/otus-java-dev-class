package ru.tikskit.remote;

import ru.tikskit.atm.ATMException;

public class RemoteAccessException extends ATMException {
    public RemoteAccessException(String message) {
        super(message);
    }
}
