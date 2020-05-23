package ru.tikskit.atm;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MoneyPack {
    protected final Map<Denomination, Integer> content = new HashMap<>();

    public MoneyPack addBanknotes(Denomination denomination, int count) {
        add(denomination, count);
        return this;
    }

    public void add(Denomination denomination, int count) {
        if (count <= 0) {
            throw new IllegalArgumentException(String.format("Недопустимое количество банкнот: %d!", count));
        }
        Integer curBanknotesCount = content.get(denomination);

        if (curBanknotesCount == null) {
            content.put(denomination, count);
        } else {
            content.replace(denomination, curBanknotesCount + count);
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

    public int getBanknotesCount(Denomination denomination) {
        Integer count = content.get(denomination);
        return content.get(denomination) == null ? 0 : count;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Denomination, Integer> es : content.entrySet()) {
            sb.append("Номинал: ").
                    append(es.getKey().getValue()).
                    append(" количество: ").
                    append(es.getValue()).
                    append("\r\n");
        }

        return sb.toString();
    }

}
