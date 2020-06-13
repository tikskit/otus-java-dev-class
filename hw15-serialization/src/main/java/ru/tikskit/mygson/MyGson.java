package ru.tikskit.mygson;

import ru.tikskit.mygson.arrays.ArrayItemFactory;
import ru.tikskit.mygson.arrays.ArrayItemTypeStrategy;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import java.lang.reflect.Array;
import java.util.Collection;

public class MyGson {
    public String toJson(Object object) {
        if (object == null) {
            return "null";
        }
        if (object.getClass().isArray()) {
            return arrayToJson(object);
        } else if (Collection.class.isAssignableFrom(object.getClass())) {
            return collectionToJson((Collection<?>) object);
        } else if (isSupportedValue(object)) {
            return valueToJson(object);
        }
        else {
            return objectToJson(object);
        }

    }

    private String objectToJson(Object object) {
        JsonObjectBuilder builder = ObjectToJson.invoke(object);

        if (builder == null) {
            return "null";
        } else {
            JsonObject jsonObject = builder.build();
            return jsonObject.toString();
        }
    }

    private String valueToJson(Object object) {
        JsonValue jsonValue = JsonValueFactory.createJsonValue(object);
        if (jsonValue == null) {
            return "null";
        } else {
            return jsonValue.toString();
        }
    }

    private String collectionToJson(Collection<?> object) {
        JsonArrayBuilder builder = CollectionToJson.invokeCollection(object);
        if (builder == null) {
            return "null";
        } else {
            return builder.build().toString();
        }
    }

    private String arrayToJson(Object object) {
        JsonArrayBuilder builder = Json.createArrayBuilder();

        for (int i = 0; i < Array.getLength(object); i++) {
            Object itemObject = Array.get(object, i);
            ArrayItemTypeStrategy strategy = ArrayItemFactory.createTypeStrategy(itemObject.getClass());
            if (strategy == null) {
                System.out.println("Значение пропущено!");
            } else {
                strategy.execute(itemObject, builder);
            }
        }

        return builder.build().toString();
    }

    private boolean isSupportedValue(Object object) {
        return JsonValueFactory.createJsonValue(object) != null;
    }

}
