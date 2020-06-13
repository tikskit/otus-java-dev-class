package ru.tikskit.mygson.fields;

import javax.json.JsonObjectBuilder;

class StringStrategy extends BaseStrategy implements FieldTypeStrategy {

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
