package ru.tikskit.atm;

import ru.tikskit.money.Denomination;
import ru.tikskit.money.MoneyPack;

public class ATMImpl implements ATM {
    private final BanknotesStorage banknotesStorage = new BanknotesStorageImpl();

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

        return withdrawBanknotes(Denomination.getBiggest(), moneyAmount);
    }

    @Override
    public int calcTotalAmount() {
        return banknotesStorage.calcTotalAmount();
    }

    private MoneyPack withdrawBanknotes(Denomination denomination, int moneyAmount) throws
            CantWithdrawException {

        MoneyPack res = new MoneyPack();

        int banknotesNeeded = moneyAmount / denomination.getValue();
        int banknotesAvailable = banknotesStorage.getBanknotesCount(denomination);
        int banknotesUsed = Math.min(banknotesNeeded, banknotesAvailable);

        if (banknotesUsed > 0) {
            try {
                banknotesStorage.withdraw(denomination, banknotesUsed);
            } catch (OutOfBanknotesException e) {
                /* Ошибки не должно быть, потому что выше мы проверяем количество доступных банкнот. Но этот метод
                выкидывает OutOfBanknotesException, поэтому просто перевыкидываем RuntimeException */
                throw new RuntimeException("Произошла ошибка при попытке уменьшить количество банкнот в хранилище!", e);
            }
            int moneyWithdrawn = banknotesUsed * denomination.getValue();
            int remaindersToWithdraw = moneyAmount - moneyWithdrawn;
            res.add(denomination, banknotesUsed);
            if (remaindersToWithdraw > 0) {
                withdrawNext(res, denomination, remaindersToWithdraw);
            }

        } else {
            withdrawNext(res, denomination, moneyAmount);
        }

        return res;
    }

    private void withdrawNext(MoneyPack moneyPack, Denomination denomination, int moneyAmount) throws
            CantWithdrawException {

        Denomination smallerDenomination = getSmallerDenomination(denomination);
        if (smallerDenomination == null) {
            throw new CantWithdrawException(moneyAmount);
        }

        moneyPack.add(withdrawBanknotes(smallerDenomination, moneyAmount));
    }

    private Denomination getSmallerDenomination(Denomination denomination) {
        switch (denomination) {
            case FIFTY:
                return null;
            case HUNDRED:
                return Denomination.FIFTY;
            case FIVE_HUNDRED:
                return Denomination.HUNDRED;
            case THOUSAND:
                return Denomination.FIVE_HUNDRED;
            default:
                throw new IllegalArgumentException(String.format("Некорректный номинал банкноты: %d!",
                        denomination.getValue()));
        }

    }

}
