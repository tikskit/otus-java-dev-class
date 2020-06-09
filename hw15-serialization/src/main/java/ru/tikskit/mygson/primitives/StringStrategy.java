package ru.tikskit.mygson.primitives;

import javax.json.JsonObjectBuilder;

class StringStrategy extends BaseStrategy implements TypeStrategy {

    public static boolean supportsType(Class<?> clazz) {
        return clazz == String.class;
    }

    @Override
    public void execute(String name, Object value, JsonObjectBuilder target) {
        if (!addNull(name, value, target)){
            target.add(name, (String)value);
        }
    }
}
