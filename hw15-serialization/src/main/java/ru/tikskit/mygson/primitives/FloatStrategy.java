package ru.tikskit.mygson.primitives;

import javax.json.JsonObjectBuilder;

public class FloatStrategy extends BaseStrategy implements TypeStrategy {
    public static boolean supportsType(Class<?> clazz) {
        return clazz == Float.class || clazz == float.class;
    }

    @Override
    public void execute(String name, Object value, JsonObjectBuilder target) {
        if (!addNull(name, value, target)){
            target.add(name, (Float)value);
        }
    }

}
