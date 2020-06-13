package ru.tikskit.mygson.fields;

import javax.json.JsonObjectBuilder;

abstract class BaseStrategy {
    protected static boolean addNull(String name, Object value, JsonObjectBuilder target) {
        if (value == null) {
            target.addNull(name);
            return true;
        } else {
            return false;
        }
    }
}
