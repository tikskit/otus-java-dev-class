package ru.tikskit.mygson.arrays;

import javax.json.JsonArrayBuilder;

class DoubleStrategy extends BaseStrategy implements ArrayItemTypeStrategy{
    public static boolean supportsType(Class<?> clazz) {
        return clazz == double.class || clazz == Double.class;
    }

    @Override
    public void execute(Object value, JsonArrayBuilder target) {
        if (!addNull(value, target)) {
            target.add((double) value);
        }
    }
}
