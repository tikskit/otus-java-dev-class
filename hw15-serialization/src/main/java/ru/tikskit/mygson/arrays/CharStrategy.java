package ru.tikskit.mygson.arrays;

import javax.json.JsonArrayBuilder;

class CharStrategy extends BaseStrategy implements ArrayItemTypeStrategy {
    public static boolean supportsType(Class<?> clazz) {
        return clazz == char.class || clazz == Character.class;
    }

    @Override
    public void execute(Object value, JsonArrayBuilder target) {
        if (!addNull(value, target)) {
            String strValue = Character.toString((char)value);
            target.add(strValue);
        }
    }

}
