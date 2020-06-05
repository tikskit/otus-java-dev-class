package ru.tikskit.atm;

import ru.tikskit.money.MoneyPack;

public class ATMProxy implements ATM {
    private final ATMImpl atm;

    public ATMProxy(ATMImpl atm) {
        this.atm = atm;
    }

    @Override
    public void put(MoneyPack moneyPack) {
        atm.put(moneyPack);
        System.out.println(String.format("Добавлено в банкомат: [%s]", moneyPack));
    }

    @Override
    public MoneyPack withdraw(int moneyAmount) throws NotEnoughMoneyException, CantWithdrawException {
        MoneyPack moneyPack = atm.withdraw(moneyAmount);
        System.out.println(String.format("Снята сумма %d [%s] ", moneyAmount, moneyPack));
        return moneyPack;

    }

    @Override
    public int calcTotalAmount() {
        return atm.calcTotalAmount();
    }
}
