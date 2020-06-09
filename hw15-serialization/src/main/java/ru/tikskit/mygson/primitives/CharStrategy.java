package ru.tikskit.mygson.primitives;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

class CharStrategy extends BaseStrategy implements TypeStrategy {
    public static boolean supportsType(Class<?> clazz) {
        return clazz == Character.class || clazz == char.class;
    }

    @Override
    public void execute(String name, Object value, JsonObjectBuilder target) {
        if (!addNull(name, value, target)){
            String strValue = Character.toString((char)value);
            target.add(name, strValue);
        }
    }
}
