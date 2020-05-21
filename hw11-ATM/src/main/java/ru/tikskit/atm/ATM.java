package ru.tikskit.atm;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

public class ATM {
    private final MoneyStorage moneyStorage;
    private final static Integer[] AVAILABLE_NOMINALS;

    static {
        AVAILABLE_NOMINALS = new Integer[]{50, 100, 500, 1000};
        /* Чтобы избежать ошибки, если в предыдущей строке номиналы забиты не в порядке возрастания. Кроме того, по
        этому массиву применяется Arrays.binarySearch, который требует восходящего упорядочивания */
        Arrays.sort(AVAILABLE_NOMINALS, Comparator.comparingInt(o -> o));
    }

    public ATM() {
        moneyStorage = new MoneyStorage();
    }

    public void put(MoneyPack moneyPack) {
        for (Map.Entry<Nominal, Integer> es : moneyPack.content.entrySet()) {
            if (!nominalSupported(es.getKey())) {
                throw new IllegalArgumentException(
                        String.format("Банкноты с номиналом %d не поддерживаются банкоматом!", es.getKey().getValue()));
            }
        }
        moneyStorage.add(moneyPack);
    }

    public MoneyPack get(int moneyAmount) throws NotEnoughMoneyException, CantWithdrawException {

        if (moneyAmount <= 0) {
            throw new IllegalArgumentException(String.format("Недопустимое значение суммы: %d", moneyAmount));
        }
        if (moneyAmount > calcTotalAmount()) {
            throw new NotEnoughMoneyException(moneyAmount);
        }

        return withdrawBanknotes(getBiggestNominal(), moneyAmount);
    }

    public int calcTotalAmount() {
        int res = 0;
        for (Nominal n : moneyStorage.getNominals()) {
            res += n.getValue() * moneyStorage.getBanknotesCount(n);
        }

        return res;
    }

    private static Nominal getBiggestNominal(){
        return new NominalImpl(AVAILABLE_NOMINALS[AVAILABLE_NOMINALS.length - 1]);
    }


    private MoneyPack withdrawBanknotes(Nominal nominal, int moneyAmount) throws
            CantWithdrawException {

        MoneyPack res = new MoneyPack();

        int banknotesNeeded = moneyAmount / nominal.getValue();
        int banknotesAvailable = moneyStorage.getBanknotesCount(nominal);
        int banknotesUsed = Math.min(banknotesNeeded, banknotesAvailable);

        if (banknotesUsed > 0) {
            try {
                moneyStorage.withdraw(nominal, banknotesUsed);
            } catch (OutOfBanknotesException e) {
                /* Ошибки не должно быть, потому что выше мы проверяем количество доступных банкнот. Но этот метод
                выкидывает OutOfBanknotesException, поэтому просто перевыкидываем RuntimeException */
                throw new RuntimeException("Произошла ошибка при попытке уменьшить количество банкнот в хранилище!", e);
            }
            int moneyWithdrawn = banknotesUsed * nominal.getValue();
            int remaindersToWithdraw = moneyAmount - moneyWithdrawn;
            res.add(nominal, banknotesUsed);
            if (remaindersToWithdraw > 0) {
                withdrawNext(res, nominal, remaindersToWithdraw);
            }

        } else {
            withdrawNext(res, nominal, moneyAmount);
        }

        return res;
    }

    private void withdrawNext(MoneyCollection moneyPack, Nominal nominal, int moneyAmount) throws
            CantWithdrawException {

        Nominal smallerNominal = getSmallerNominal(nominal);
        if (smallerNominal == null) {
            throw new CantWithdrawException(moneyAmount);
        }

        moneyPack.add(withdrawBanknotes(smallerNominal, moneyAmount));
    }

    private Nominal getSmallerNominal(Nominal nominal) {
        int index = Arrays.binarySearch(AVAILABLE_NOMINALS, nominal.getValue());
        if (index == 0) {
            return null;
        } else if (index > 0) {
            return new NominalImpl(AVAILABLE_NOMINALS[index - 1]);
        } else {
            throw new IllegalArgumentException(String.format("Банкноты с номиналом %d не поддерживаются банкоматом!",
                    nominal.getValue()));
        }
    }

    private boolean nominalSupported(Nominal nominal) {
        for (Integer i : AVAILABLE_NOMINALS) {
            if (i == nominal.getValue()) {
                return true;
            }
        }

        return false;
    }

}
