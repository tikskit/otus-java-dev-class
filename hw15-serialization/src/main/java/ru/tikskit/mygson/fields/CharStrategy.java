package ru.tikskit.mygson.fields;

import javax.json.JsonObjectBuilder;

class CharStrategy extends BaseStrategy implements FieldTypeStrategy {
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
