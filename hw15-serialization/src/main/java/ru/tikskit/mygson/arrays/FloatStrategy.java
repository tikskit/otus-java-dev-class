package ru.tikskit.mygson.arrays;

import javax.json.JsonArrayBuilder;

public class FloatStrategy extends BaseStrategy implements ArrayItemTypeStrategy{

    public static boolean supportsType(Class<?> clazz) {
        return clazz == float.class || clazz == Float.class;
    }

    @Override
    public void execute(Object value, JsonArrayBuilder target) {
        if (!addNull(value, target)) {
            target.add((float) value);
        }
    }

}
