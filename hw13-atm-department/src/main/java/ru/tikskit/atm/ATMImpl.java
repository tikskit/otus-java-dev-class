package ru.tikskit.atm;

import ru.tikskit.money.Denomination;
import ru.tikskit.money.MoneyPack;

import java.util.Arrays;

public class ATMImpl implements ATM {
    protected final BanknotesStorage banknotesStorage = new BanknotesStorageImpl();

    @Override
    public void put(MoneyPack moneyPack) {
        for (Denomination d : moneyPack.getDenominations()) {
            banknotesStorage.put(d, moneyPack.getBanknotesCount(d));
        }
    }

    @Override
    public MoneyPack withdraw(int moneyAmount) throws NotEnoughMoneyException, CantWithdrawException {

        if (moneyAmount <= 0) {
            throw new IllegalArgumentException(String.format("Недопустимое значение суммы: %d", moneyAmount));
        }
        if (moneyAmount > calcTotalAmount()) {
            throw new NotEnoughMoneyException(moneyAmount);
        }

        return withdrawBanknotes(moneyAmount);
    }

    @Override
    public int calcTotalAmount() {
        return banknotesStorage.calcTotalAmount();
    }

    public boolean canExchange(int moneyAmount) {
        BanknotesStorage.Memento banknotesStorageMemento = banknotesStorage.store();
        try {
            try {
                withdraw(moneyAmount);
                return true;
            } catch (Exception e) {
                return false;
            }
        } finally {
            banknotesStorageMemento.restore();
        }
    }


    private MoneyPack withdrawBanknotes(int moneyAmount) throws CantWithdrawException {
        MoneyPack res = new MoneyPack();

        Denomination[] denominations = Denomination.values();
        Arrays.sort(denominations, (o1, o2) -> o2.getValue() - o1.getValue());

        BanknotesStorage.Memento banknotesStorageMemento = banknotesStorage.store();
        try {
            for (Denomination d : denominations) {
                int banknotesNeeded = moneyAmount / d.getValue();
                int banknotesAvailable = banknotesStorage.getBanknotesCount(d);
                int banknotesUsed = Math.min(banknotesNeeded, banknotesAvailable);

                if (banknotesUsed > 0) {
                    try {
                        banknotesStorage.withdraw(d, banknotesUsed);
                    } catch (OutOfBanknotesException e) {
                    /* Ошибки не должно быть, потому что выше мы проверяем количество доступных банкнот. Но этот метод
                    выкидывает OutOfBanknotesException, поэтому просто перевыкидываем RuntimeException */
                        throw new RuntimeException(
                                "Произошла ошибка при попытке уменьшить количество банкнот в хранилище!", e);
                    }
                    int moneyWithdrawn = banknotesUsed * d.getValue();
                    moneyAmount -= moneyWithdrawn;
                    res.add(d, banknotesUsed);
                }
            }
            if (moneyAmount > 0) {
                throw new CantWithdrawException(moneyAmount);
            }
        } catch (Exception e) {
            banknotesStorageMemento.restore();
            throw e;
        }

        return res;
    }


}
