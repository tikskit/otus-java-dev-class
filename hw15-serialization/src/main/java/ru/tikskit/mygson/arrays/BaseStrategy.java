package ru.tikskit.mygson.arrays;

import javax.json.JsonArrayBuilder;

abstract class BaseStrategy {
    protected static boolean addNull(Object value, JsonArrayBuilder target) {
        if (value == null) {
            target.addNull();
            return true;
        } else {
            return false;
        }
    }

}
