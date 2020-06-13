package ru.tikskit.mygson.fields;

import javax.json.JsonObjectBuilder;

public interface FieldTypeStrategy {
    void execute(String name, Object value, JsonObjectBuilder target);
}
