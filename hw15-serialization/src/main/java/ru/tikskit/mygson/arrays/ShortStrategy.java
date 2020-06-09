package ru.tikskit.mygson.arrays;

import javax.json.JsonArrayBuilder;

class ShortStrategy extends BaseStrategy implements ArrayItemTypeStrategy{
    public static boolean supportsType(Class<?> clazz) {
        return clazz == short.class || clazz == Short.class;
    }

    @Override
    public void execute(Object value, JsonArrayBuilder target) {
        if (!addNull(value, target)) {
            target.add((short) value);
        }
    }
}
