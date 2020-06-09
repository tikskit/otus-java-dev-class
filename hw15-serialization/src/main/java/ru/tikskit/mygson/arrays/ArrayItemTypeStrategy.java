package ru.tikskit.mygson.arrays;

import javax.json.JsonArrayBuilder;

public interface ArrayItemTypeStrategy {
    void execute(Object value, JsonArrayBuilder target);
}
