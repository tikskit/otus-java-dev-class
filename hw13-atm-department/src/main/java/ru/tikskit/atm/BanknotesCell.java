package ru.tikskit.atm;

interface BanknotesCell {
    void put(int count);
    void get(int count) throws OutOfBanknotesException;
    int getCount();
}
