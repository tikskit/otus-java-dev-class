package ru.tikskit.mygson.arrays;

import javax.json.JsonArrayBuilder;

class ByteStrategy extends BaseStrategy implements ArrayItemTypeStrategy{
    public static boolean supportsType(Class<?> clazz) {
        return clazz == byte.class || clazz == Byte.class;
    }

    @Override
    public void execute(Object value, JsonArrayBuilder target) {
        if (!addNull(value, target)) {
            target.add((byte) value);
        }
    }
}
