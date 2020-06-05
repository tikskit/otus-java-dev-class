package ru.tikskit.atm;

public class ATMException extends Exception{
    public ATMException(String message) {
        super(message);
    }

    public ATMException(Throwable cause) {
        super(cause);
    }
}
