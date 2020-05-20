package ru.tikskit.atm;

public class ATM {
    private final MoneyStorage moneyStorage = new MoneyStorage();

    public void put(Denomination denomination, int count) {
        moneyStorage.add(denomination, count);
    }

    public MoneyCollection get(int amount) throws NotEnoughMoneyException, CantWithdrawException,
            OutOfBanknotesException {

        if (amount <= 0) {
            throw new IllegalArgumentException(String.format("Недопустимое значение суммы: %d", amount));
        }
        if (amount > calcTotalAmount()) {
            throw new NotEnoughMoneyException(amount);
        }

        return withdrawBanknotes(Denomination.MAX_DENOMINATION, amount);
    }

    public int calcTotalAmount() {
        int res = 0;
        for (Denomination d : moneyStorage.getDenominations()) {
            res += d.getValue() * moneyStorage.getBanknotesCount(d);
        }

        return res;
    }

    private MoneyCollection withdrawBanknotes(Denomination curDenomination, int moneyAmount) throws
            OutOfBanknotesException, CantWithdrawException {

        MoneyCollection res = new MoneyCollection();

        int banknotesNeeded = moneyAmount / curDenomination.getValue();
        int banknotesAvailable = moneyStorage.getBanknotesCount(curDenomination);
        int banknotesUsed = Math.min(banknotesNeeded, banknotesAvailable);


        if (banknotesUsed > 0) {
            moneyStorage.withdraw(curDenomination, banknotesUsed);
            int moneyWithdrawn = banknotesUsed * curDenomination.getValue();
            int remains = moneyAmount - moneyWithdrawn;
            res.add(curDenomination, banknotesUsed);
            if (remains > 0) {
                withdrawNext(res, curDenomination, remains);
            }

        } else {
            withdrawNext(res, curDenomination, moneyAmount);
        }

        return res;
    }

    private void withdrawNext(MoneyCollection moneyPack, Denomination curDenomination, int moneyAmount) throws
            OutOfBanknotesException, CantWithdrawException {

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
