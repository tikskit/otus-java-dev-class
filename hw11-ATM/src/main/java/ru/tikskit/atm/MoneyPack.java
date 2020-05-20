package ru.tikskit.atm;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MoneyPack {
    private final Map<Denomination, Integer> content = new HashMap<>();

    public MoneyPack() {}

    public MoneyPack(Denomination denomination, int count) {
        content.put(denomination, count);
    }

    public MoneyPack add(Denomination denomination, int count) {
        Integer curBanknotesCount = content.get(denomination);

        if (curBanknotesCount == null) {
            content.put(denomination, count);
        } else {
            content.replace(denomination, curBanknotesCount + count);
        }

        return this;
    }

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

    public void add(MoneyPack moneyPack) {
        if (moneyPack != null) {
            for (Map.Entry<Denomination, Integer> es : moneyPack.content.entrySet()) {
                add(es.getKey(), es.getValue());
            }
        }
    }

    public Set<Denomination> getDenominations() {
        return content.keySet();
    }

    public Integer getBanknotesCount(Denomination denomination) {
        return content.get(denomination) == null ? 0 : content.get(denomination);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Denomination, Integer> es : content.entrySet()) {
            //add(es.getKey(), es.getValue());
            sb.append("Номинал: ").
                    append(es.getKey().getValue()).
                    append(" количество: ").
                    append(es.getValue()).
                    append("\r\n");
        }

        return sb.toString();
    }
}
