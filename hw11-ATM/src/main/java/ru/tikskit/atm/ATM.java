package ru.tikskit.atm;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

public class ATM {
    private final MoneyStorage moneyStorage;
    private final LinkedNominal nominalRoot;

    public ATM() {
        nominalRoot = buildNominalChain(new Integer[]{50, 100, 500, 1000});
        moneyStorage = new MoneyStorage();
    }

    public void put(MoneyPack moneyPack) {
        for (Map.Entry<Nominal, Integer> es : moneyPack.content.entrySet()) {
            if (!existsNominal(es.getKey())) {
                throw new IllegalArgumentException(
                        String.format("Банкноты с номиналом %d не поддерживаются банкоматом!"));
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

        return withdrawBanknotes(nominalRoot, moneyAmount);
    }

    public int calcTotalAmount() {
        int res = 0;
        for (Nominal n : moneyStorage.getNominals()) {
            res += n.getValue() * moneyStorage.getBanknotesCount(n);
        }

        return res;
    }

    private MoneyPack withdrawBanknotes(LinkedNominal nominal, int moneyAmount) throws
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

    private void withdrawNext(MoneyCollection moneyPack, LinkedNominal nominal, int moneyAmount) throws
            CantWithdrawException {

        LinkedNominal smallerNominal = nominal.getSmaller();
        if (smallerNominal == null) {
            throw new CantWithdrawException(moneyAmount);
        }

        moneyPack.add(withdrawBanknotes(smallerNominal, moneyAmount));
    }

    private static LinkedNominal buildNominalChain(Integer[] nominalValues) {
        if (nominalValues == null || nominalValues.length == 0) {
            throw new IllegalArgumentException("Пустой массив номиналов банкнот!");
        }

        // Упорядочиваем по возрастанию
        Arrays.sort(nominalValues, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });

        LinkedNominal ln = new LinkedNominalImpl(nominalValues[0], null);
        for (int i = 1; i < nominalValues.length; i++) {
            ln = new LinkedNominalImpl(nominalValues[i], ln);
        }

        return ln;
    }

    private boolean existsNominal(Nominal nominal) {
        LinkedNominal ln = nominalRoot;
        while (ln != null) {
            if (ln.getValue() == nominal.getValue()) {
                return true;
            }

            ln = ln.getSmaller();
        }

        return false;
    }

}
