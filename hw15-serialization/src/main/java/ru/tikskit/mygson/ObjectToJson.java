package ru.tikskit.mygson;

import ru.tikskit.mygson.primitives.StrategyFactory;
import ru.tikskit.mygson.primitives.TypeStrategy;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.lang.reflect.Field;
import java.util.Collection;

class ObjectToJson {
    public static JsonObjectBuilder invoke(Object object) {
        if (object == null) {
            return null;
        } else {
            JsonObjectBuilder builder = Json.createObjectBuilder();
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.getType().isArray()) {
                    addArrayField(object, field, builder);
                } else if (Collection.class.isAssignableFrom(field.getType())) {
                    addCollectionField(object, field, builder);
                } else {
                    addSimpleField(object, field, builder);
                }
            }
            return builder;
        }

    }

    private static void addSimpleField(Object object, Field field, JsonObjectBuilder builder) {
        TypeStrategy typeStrategy = StrategyFactory.createStrategy(field.getType());
        if (typeStrategy == null) {
            System.out.println(String.format("Не найден обработчик для поля \"%s\"", field.getName()));
        } else {
            try {
                typeStrategy.execute(field.getName(), field.get(object), builder);
            } catch (IllegalAccessException e) {
                System.out.println(String.format("Поле \"%s\" пропущено", field.getName()));
            }
        }
    }

    private static void addCollectionField(Object object, Field field, JsonObjectBuilder builder) {
        try {
            JsonArrayBuilder arrayBuilder = CollectionToJson.invokeCollection((Collection<?>)field.get(object));
            if (arrayBuilder == null) {
                builder.addNull(field.getName());
            } else {
                builder.add(field.getName(), arrayBuilder);
            }
        } catch (IllegalAccessException e) {
            System.out.println(String.format("Поле \"%s\" пропущено", field.getName()));
        }
    }

    private static void addArrayField(Object object, Field field, JsonObjectBuilder builder) {
        try {
            JsonArrayBuilder arrayBuilder = CollectionToJson.invokeArray(field.get(object));
            if (arrayBuilder == null) {
                builder.addNull(field.getName());
            } else {
                builder.add(field.getName(), arrayBuilder);
            }
        } catch (IllegalAccessException e) {
            System.out.println(String.format("Поле \"%s\" пропущено", field.getName()));
        }
    }
}
