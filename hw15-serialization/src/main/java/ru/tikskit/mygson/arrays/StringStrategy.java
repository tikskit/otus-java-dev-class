package ru.tikskit.mygson.arrays;

import javax.json.JsonArrayBuilder;

class StringStrategy extends BaseStrategy implements ArrayItemTypeStrategy{
    public static boolean supportsType(Class<?> clazz) {
        return clazz == String.class;
    }

    @Override
    public void execute(Object value, JsonArrayBuilder target) {
        if (!addNull(value, target)) {
            target.add((String) value);
        }
    }

}
