package ru.tikskit.mygson.arrays;

import javax.json.JsonArrayBuilder;

class BooleanStrategy extends BaseStrategy implements ArrayItemTypeStrategy {

    public static boolean supportsType(Class<?> clazz) {
        return clazz == boolean.class || clazz == Boolean.class;
    }

    @Override
    public void execute(Object value, JsonArrayBuilder target) {
        if (!addNull(value, target)) {
            target.add((boolean) value);
        }
    }
}
