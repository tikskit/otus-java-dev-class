package ru.tikskit.atm;

public interface ATM {
    void put(MoneyPack moneyPack);
    MoneyPack withdraw(int moneyAmount) throws NotEnoughMoneyException, CantWithdrawException;
    int calcTotalAmount();
}
