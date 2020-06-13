package ru.tikskit.mygson;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonValue;
import java.lang.reflect.Array;
import java.util.Collection;

class CollectionToJson {
    public static JsonArrayBuilder invokeCollection(Collection<?> collection) {
        if (collection == null) {
            return null;
        }

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        for (Object o : collection) {
            JsonValue value = JsonValueFactory.createJsonValue(o);
            if (value == null) {
                System.out.println("Элемент коллекции пропущен!");
            } else {
                arrayBuilder.add(value);
            }
        }

        return arrayBuilder;
    }

    public static JsonArrayBuilder invokeArray(Object array) {
        if (array == null) {
            return null;
        }

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (int i = 0; i < Array.getLength(array); i++) {
            Object o = Array.get(array, i);
            JsonValue value = JsonValueFactory.createJsonValue(o);
            if (value == null) {
                System.out.println(String.format("Элемент коллекции #%d пропущен!", i));
            } else {
                arrayBuilder.add(value);
            }
        }

        return arrayBuilder;
    }
}
