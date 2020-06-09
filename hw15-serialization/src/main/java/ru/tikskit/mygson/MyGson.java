package ru.tikskit.mygson;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class MyGson {
    public String toJson(Object object) {
        JsonObjectBuilder builder = ObjectToJson.invoke(object);

        if (builder == null) {
            return null;
        } else {
            JsonObject jsonObject = builder.build();
            return jsonObject.toString();
        }
    }

}
