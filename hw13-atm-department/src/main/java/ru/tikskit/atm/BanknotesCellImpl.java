package ru.tikskit.atm;

class BanknotesCellImpl implements BanknotesCell {
    // Количество банкнот в ячейке
    private int count = 0;

    @Override
    public void put(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException(String.format("Некорректное количество банкнот: %d!", count));
        }
        this.count += count;
    }

    @Override
    public void get(int count) throws OutOfBanknotesException {
        if (count > this.count) {
            throw new OutOfBanknotesException(count);
        }

        this.count -= count;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public BanknotesCell copy() {
        BanknotesCellImpl res = new BanknotesCellImpl();
        res.count = count;
        return res;
    }
}
