package ru.tikskit.atm;

import ru.tikskit.money.Denomination;

interface BanknotesStorage {
    void put(Denomination denomination, int count);
    void withdraw(Denomination denomination, int count) throws OutOfBanknotesException;

    int calcTotalAmount();
    int getBanknotesCount(Denomination denomination);
}
