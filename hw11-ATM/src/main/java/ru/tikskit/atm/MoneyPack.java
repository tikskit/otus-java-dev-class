package ru.tikskit.atm;

import java.util.Map;

public class MoneyPack extends MoneyCollection {

    public MoneyPack addBanknotes(Nominal nominal, int count) {
        add(nominal, count);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Nominal, Integer> es : content.entrySet()) {
            sb.append("Номинал: ").
                    append(es.getKey().getValue()).
                    append(" количество: ").
                    append(es.getValue()).
                    append("\r\n");
        }

        return sb.toString();
    }
}
