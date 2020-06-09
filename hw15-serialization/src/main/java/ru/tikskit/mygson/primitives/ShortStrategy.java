package ru.tikskit.mygson.primitives;

import javax.json.JsonObjectBuilder;

class ShortStrategy extends BaseStrategy implements TypeStrategy{
    public static boolean supportsType(Class<?> clazz) {
        return clazz == Short.class || clazz == short.class;
    }

    @Override
    public void execute(String name, Object value, JsonObjectBuilder target) {
        if (!addNull(name, value, target)){
            target.add(name, (Short)value);
        }
    }

}
