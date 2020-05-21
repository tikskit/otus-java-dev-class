package ru.tikskit.atm;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class MoneyCollection {
    protected final Map<Nominal, Integer> content = new HashMap<>();

    public void add(Nominal nominal, int count) {
        if (count <= 0) {
            throw new IllegalArgumentException(String.format("Недопустимое количество банкнот: %d!", count));
        }
        Integer curBanknotesCount = content.get(nominal);

        if (curBanknotesCount == null) {
            content.put(nominal, count);
        } else {
            content.replace(nominal, curBanknotesCount + count);
        }
    }

    public void add(MoneyCollection moneyCollection) {
        if (moneyCollection != null) {
            for (Map.Entry<Nominal, Integer> es : moneyCollection.content.entrySet()) {
                add(es.getKey(), es.getValue());
            }
        }
    }
}
