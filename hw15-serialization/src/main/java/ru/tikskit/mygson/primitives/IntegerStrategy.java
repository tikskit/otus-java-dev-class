package ru.tikskit.mygson.primitives;

import javax.json.JsonObjectBuilder;

class IntegerStrategy extends BaseStrategy implements TypeStrategy {

    public static boolean supportsType(Class<?> clazz) {
        return clazz == Integer.class || clazz == int.class;
    }

    @Override
    public void execute(String name, Object value, JsonObjectBuilder target) {
        if (!addNull(name, value, target)){
            target.add(name, (Integer)value);
        }
    }
}
