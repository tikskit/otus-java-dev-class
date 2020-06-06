package ru.tikskit.atm;

import ru.tikskit.money.Denomination;

import java.util.HashMap;
import java.util.Map;

class BanknotesStorageImpl implements BanknotesStorage {
    private Map<Denomination, BanknotesCell> cells;

    public BanknotesStorageImpl() {
        cells = new HashMap<>();

        for (Denomination d : Denomination.values()) {
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

    @Override
    public Memento store() {
        return new MementoImpl();
    }

    private class MementoImpl implements BanknotesStorage.Memento {
        private final Map<Denomination, BanknotesCell> cells;

        public MementoImpl() {
            cells = new HashMap<>();

            copyCells(BanknotesStorageImpl.this.cells, cells);
        }

        @Override
        public void restore() {
            BanknotesStorageImpl.this.cells = new HashMap<>();
            copyCells(cells, BanknotesStorageImpl.this.cells);
        }

        private void copyCells(Map<Denomination, BanknotesCell> srcCells, Map<Denomination, BanknotesCell> tarCells) {
            for (Map.Entry<Denomination, BanknotesCell> m: srcCells.entrySet()) {
                tarCells.put(m.getKey(), m.getValue().copy());
            }
        }
    }
}
