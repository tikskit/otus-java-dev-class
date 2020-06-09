package ru.tikskit.mygson;

import ru.tikskit.mygson.arrays.ArrayItemFactory;
import ru.tikskit.mygson.arrays.ArrayItemTypeStrategy;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import java.lang.reflect.Array;
import java.util.Collection;

class CollectionToJson {
    public static JsonArrayBuilder invokeCollection(Collection<?> collection) {
        if (collection == null) {
            return null;
        }

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        for (Object o : collection) {
            ArrayItemTypeStrategy strategy = ArrayItemFactory.createTypeStrategy(o.getClass());
            if (strategy == null) {
                System.out.println("Элемент коллекции пропущен!");
            } else {
                strategy.execute(o, arrayBuilder);
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
            ArrayItemTypeStrategy strategy = ArrayItemFactory.createTypeStrategy(o.getClass());
            if (strategy == null) {
                System.out.println(String.format("Элемент коллекции #%d пропущен!", i));
            } else {
                strategy.execute(o, arrayBuilder);
            }
        }

        return arrayBuilder;
    }
}
