package ru.tikskit.atm;

interface Cells {
    void put(Denomination denomination, int count);
    void withdraw(Denomination denomination, int count) throws OutOfBanknotesException;

    int calcTotalAmount();
    int getBanknotesCount(Denomination denomination);
}
