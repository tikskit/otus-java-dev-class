package ru.tikskit.mygson.primitives;

import javax.json.JsonObjectBuilder;

public interface TypeStrategy {
    void execute(String name, Object value, JsonObjectBuilder target);
}
