package ru.tikskit.mygson.arrays;

import javax.json.JsonArrayBuilder;

class IntegerStrategy extends BaseStrategy implements ArrayItemTypeStrategy {

    public static boolean supportsType(Class<?> clazz) {
        return clazz == int.class || clazz == Integer.class;
    }

    @Override
    public void execute(Object value, JsonArrayBuilder target) {
        if (!addNull(value, target)) {
            target.add((int) value);
        }
    }
}
