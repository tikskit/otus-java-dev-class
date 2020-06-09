package ru.tikskit.mygson.primitives;

import javax.json.JsonObjectBuilder;

class BooleanStrategy extends BaseStrategy implements TypeStrategy {

    public static boolean supportsType(Class<?> clazz) {
        return clazz == Boolean.class || clazz == boolean.class;
    }

    @Override
    public void execute(String name, Object value, JsonObjectBuilder target) {
        if (!addNull(name, value, target)){
            target.add(name, (Boolean)value);
        }
    }
}
