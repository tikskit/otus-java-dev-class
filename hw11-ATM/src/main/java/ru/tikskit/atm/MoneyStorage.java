package ru.tikskit.atm;

import java.util.Set;

class MoneyStorage extends MoneyCollection {

    public void withdraw(Nominal nominal, int count) throws OutOfBanknotesException {
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

    public Set<Nominal> getNominals() {
        return content.keySet();
    }

    public Integer getBanknotesCount(Nominal nominal) {
        return content.get(nominal) == null ? 0 : content.get(nominal);
    }
}
