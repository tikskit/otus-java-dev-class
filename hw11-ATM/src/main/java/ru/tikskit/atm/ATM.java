package ru.tikskit.atm;

public class ATM {
    private final MoneyPack content = new MoneyPack();

    public void put(MoneyPack moneyPack) {
        content.add(moneyPack);
    }

    public MoneyPack get(int amount) throws NotEnoughMoneyException, CantWithdrawException, OutOfBanknotesException {

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
        for (Denomination d : content.getDenominations()) {
            res += d.getValue() * content.getBanknotesCount(d);
        }

        return res;
    }

    private MoneyPack withdrawBanknotes(Denomination curDenomination, int moneyAmount) throws OutOfBanknotesException,
            CantWithdrawException {

        MoneyPack res = new MoneyPack();

        int banknotesNeeded = moneyAmount / curDenomination.getValue();
        int banknotesAvailable = content.getBanknotesCount(curDenomination);
        int banknotesUsed = Math.min(banknotesNeeded, banknotesAvailable);


        if (banknotesUsed > 0) {
            content.withdraw(curDenomination, banknotesUsed);
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

    private void withdrawNext(MoneyPack moneyPack, Denomination curDenomination, int moneyAmount) throws
            OutOfBanknotesException, CantWithdrawException {

        Denomination nextDenomination = reduceDenomination(curDenomination);
        if (nextDenomination == null) {
            throw new CantWithdrawException(moneyAmount);
        }

        moneyPack.add(withdrawBanknotes(nextDenomination, moneyAmount));
    }



    private Denomination reduceDenomination(Denomination cur) {
        switch(cur) {
            case FIFTY:
                return null;
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
