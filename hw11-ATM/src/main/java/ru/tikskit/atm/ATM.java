package ru.tikskit.atm;

import ru.tikskit.money.MoneyPack;

public interface ATM {
    void put(MoneyPack moneyPack);
    MoneyPack withdraw(int moneyAmount) throws NotEnoughMoneyException, CantWithdrawException;
    int calcTotalAmount();
}
