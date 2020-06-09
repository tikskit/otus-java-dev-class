package ru.tikskit.mygson.arrays;

import javax.json.JsonArrayBuilder;

class LongStrategy extends BaseStrategy implements ArrayItemTypeStrategy{
    public static boolean supportsType(Class<?> clazz) {
        return clazz == long.class || clazz == Long.class;
    }

    @Override
    public void execute(Object value, JsonArrayBuilder target) {
        if (!addNull(value, target)) {
            target.add((long) value);
        }
    }
}
