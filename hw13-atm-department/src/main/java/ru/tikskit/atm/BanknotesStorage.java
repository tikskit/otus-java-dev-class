package ru.tikskit.atm;

import ru.tikskit.money.Denomination;

public interface BanknotesStorage {
    interface Memento {
        void restore();
    }

    void put(Denomination denomination, int count);
    void withdraw(Denomination denomination, int count) throws OutOfBanknotesException;

    int calcTotalAmount();
    int getBanknotesCount(Denomination denomination);
    Memento store();
}
