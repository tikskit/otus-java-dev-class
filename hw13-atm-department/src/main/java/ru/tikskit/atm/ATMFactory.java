package ru.tikskit.atm;

public class ATMFactory {
    public static ATM createATM() {
        return new ATMProxy(new ATMImpl());
    }
}
