package ru.tikskit.mygson.primitives;

import javax.json.JsonObjectBuilder;

class DoubleStrategy extends BaseStrategy implements TypeStrategy {

    public static boolean supportsType(Class<?> clazz) {
        return clazz == Double.class || clazz == double.class;
    }

    @Override
    public void execute(String name, Object value, JsonObjectBuilder target) {
        if (!addNull(name, value, target)){
            target.add(name, (Double)value);
        }
    }

}
