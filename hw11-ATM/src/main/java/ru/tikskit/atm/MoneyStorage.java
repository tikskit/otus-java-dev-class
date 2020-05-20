package ru.tikskit.atm;

class MoneyStorage extends MoneyCollection {

    public void withdraw(Denomination denomination, int count) throws OutOfBanknotesException {
        Integer curBanknotesCount = content.get(denomination);

        if (curBanknotesCount == null) {
            throw new OutOfBanknotesException(count);
        } else {
            if (curBanknotesCount == count) {
                content.remove(denomination);
            } else if (curBanknotesCount > count) {
                content.replace(denomination, curBanknotesCount - count);
            }
            else {
                throw new OutOfBanknotesException(count);
            }
        }
    }

}
