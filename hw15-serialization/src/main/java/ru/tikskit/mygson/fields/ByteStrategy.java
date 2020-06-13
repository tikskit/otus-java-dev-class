package ru.tikskit.mygson.fields;

import javax.json.JsonObjectBuilder;

class ByteStrategy extends BaseStrategy implements FieldTypeStrategy {

    public static boolean supportsType(Class<?> clazz) {
        return clazz == Byte.class || clazz == byte.class;
    }

    @Override
    public void execute(String name, Object value, JsonObjectBuilder target) {
        if (!addNull(name, value, target)){
            target.add(name, (Byte)value);
        }
    }

}
