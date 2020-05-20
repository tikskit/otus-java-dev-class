package ru.tikskit.atm;

public class ATM {
    private final MoneyStorage moneyStorage = new MoneyStorage();

    public void put(Denomination denomination, int count) {
        moneyStorage.add(denomination, count);
    }

    public MoneyCollection get(int moneyAmount) throws NotEnoughMoneyException, CantWithdrawException {

        if (moneyAmount <= 0) {
            throw new IllegalArgumentException(String.format("Недопустимое значение суммы: %d", moneyAmount));
        }
        if (moneyAmount > calcTotalAmount()) {
            throw new NotEnoughMoneyException(moneyAmount);
        }

        return withdrawBanknotes(Denomination.MAX_DENOMINATION, moneyAmount);
    }

    public int calcTotalAmount() {
        int res = 0;
        for (Denomination d : moneyStorage.getDenominations()) {
            res += d.getValue() * moneyStorage.getBanknotesCount(d);
        }

        return res;
    }

    private MoneyCollection withdrawBanknotes(Denomination curDenomination, int moneyAmount) throws
            CantWithdrawException {

        MoneyCollection res = new MoneyCollection();

        int banknotesNeeded = moneyAmount / curDenomination.getValue();
        int banknotesAvailable = moneyStorage.getBanknotesCount(curDenomination);
        int banknotesUsed = Math.min(banknotesNeeded, banknotesAvailable);

        if (banknotesUsed > 0) {
            try {
                moneyStorage.withdraw(curDenomination, banknotesUsed);
            } catch (OutOfBanknotesException e) {
                /* Ошибки не должно быть, потому что выше мы проверяем количество доступных банкнот. Но этот метод
                выкидывает OutOfBanknotesException, поэтому просто перевыкидываем RuntimeException */
                throw new RuntimeException("Произошла ошибка при попытке уменьшить количество банкнот в хранилище!", e);
            }
            int moneyWithdrawn = banknotesUsed * curDenomination.getValue();
            int remaindersToWithdraw = moneyAmount - moneyWithdrawn;
            res.add(curDenomination, banknotesUsed);
            if (remaindersToWithdraw > 0) {
                withdrawNext(res, curDenomination, remaindersToWithdraw);
            }

        } else {
            withdrawNext(res, curDenomination, moneyAmount);
        }

        return res;
    }

    private void withdrawNext(MoneyCollection moneyPack, Denomination curDenomination, int moneyAmount) throws
            CantWithdrawException {

        Denomination nextDenomination = getSmallerDenomination(curDenomination);
        if (nextDenomination == null) {
            throw new CantWithdrawException(moneyAmount);
        }

        moneyPack.add(withdrawBanknotes(nextDenomination, moneyAmount));
    }



    private Denomination getSmallerDenomination(Denomination cur) {
        switch(cur) {
            case HUNDRED:
                return Denomination.FIFTY;
            case FIVE_HUNDRED:
                return Denomination.HUNDRED;
            case THOUSAND:
                return Denomination.FIVE_HUNDRED;
            default:
                return null;
        }
    }
}
