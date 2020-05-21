package ru.tikskit.atm;

class MoneyStorage extends MoneyCollection {

    public void withdraw(LinkedNominal nominal, int count) throws OutOfBanknotesException {
        Integer curBanknotesCount = content.get(nominal);

        if (curBanknotesCount == null) {
            throw new OutOfBanknotesException(count);
        } else {
            if (curBanknotesCount == count) {
                content.remove(nominal);
            } else if (curBanknotesCount > count) {
                content.replace(nominal, curBanknotesCount - count);
            }
            else {
                throw new OutOfBanknotesException(count);
            }
        }
    }

}
