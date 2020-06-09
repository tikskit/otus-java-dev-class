package ru.tikskit.mygson.primitives;

import javax.json.JsonObjectBuilder;

class LongStrategy extends BaseStrategy implements TypeStrategy {

    public static boolean supportsType(Class<?> clazz) {
        return clazz == Long.class || clazz == long.class;
    }

    @Override
    public void execute(String name, Object value, JsonObjectBuilder target) {
        if (!addNull(name, value, target)){
            target.add(name, (Long)value);
        }
    }
}
