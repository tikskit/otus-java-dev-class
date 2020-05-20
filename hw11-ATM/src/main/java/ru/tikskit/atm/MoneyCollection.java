package ru.tikskit.atm;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MoneyCollection {
    protected final Map<Denomination, Integer> content = new HashMap<>();

    public void add(Denomination denomination, int count) {
        Integer curBanknotesCount = content.get(denomination);

        if (curBanknotesCount == null) {
            content.put(denomination, count);
        } else {
            content.replace(denomination, curBanknotesCount + count);
        }
    }

    public void add(MoneyCollection moneyCollection) {
        if (moneyCollection != null) {
            for (Map.Entry<Denomination, Integer> es : moneyCollection.content.entrySet()) {
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
