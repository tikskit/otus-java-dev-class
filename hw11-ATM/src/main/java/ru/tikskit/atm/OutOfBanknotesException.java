package ru.tikskit.atm;

public class OutOfBanknotesException extends Exception {

    public OutOfBanknotesException(int banknotesRequired) {
        super(String.format("Недостаточно банкнот: %d!", banknotesRequired));
    }
}
