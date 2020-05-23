package ru.tikskit.atm;

import ru.tikskit.money.Denomination;

import java.util.HashMap;
import java.util.Map;

class BanknotesStorageImpl implements BanknotesStorage {
    private final Map<Denomination, BanknotesCell> cells;

    public BanknotesStorageImpl() {
        Denomination[] denominations = Denomination.values();
        cells = new HashMap<>(denominations.length);

        for (Denomination d : denominations) {
            cells.put(d, new BanknotesCellImpl());
        }
    }

    @Override
    public void put(Denomination denomination, int count) {
        if (count > 0) {
            BanknotesCell cell = cells.get(denomination);
            cell.put(count);
        }
    }

    @Override
    public int calcTotalAmount() {
        int res = 0;
        for (Denomination d : cells.keySet()) {
            BanknotesCell cell = cells.get(d);
            if (cell != null) {
                res += d.getValue() * cell.getCount();
            }
        }

        return res;
    }

    @Override
    public int getBanknotesCount(Denomination denomination) {
        return cells.get(denomination).getCount();
    }

    @Override
    public void withdraw(Denomination denomination, int count) throws OutOfBanknotesException {
        cells.get(denomination).get(count);
    }


}
