package ru.otus.jdbc.mapper;

import ru.otus.core.model.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T>{
    private String className;
    private Constructor<T> constructor;
    private Field idField;
    private List<Field> fields;
    private List<Field> fieldsWithoutId;
    private final Class<T> entityClass;

    public EntityClassMetaDataImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public String getName() {
        if (className == null) {
            className = entityClass.getSimpleName();
        }
        return className;
    }

    @Override
    public Constructor<T> getConstructor() {
        if (constructor == null) {
            try {
                constructor = entityClass.getConstructor();
            } catch (NoSuchMethodException e) {
                throw new EntityClassMetaDataException(e);
            }
        }

        return constructor;
    }

    @Override
    public Field getIdField() {
        if (idField == null) {
            for (Field f : entityClass.getDeclaredFields()) {
                if (f.isAnnotationPresent(Id.class)) {
                    f.setAccessible(true);
                    idField = f;
                    return idField;
                }
            }
        }
        return idField;
    }

    @Override
    public List<Field> getAllFields() {
        if (fields == null) {
            fields = new ArrayList<>();
            for (Field f : entityClass.getDeclaredFields()) {
                f.setAccessible(true);
                fields.add(f);
            }
        }
        return fields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        if (fieldsWithoutId == null) {
            fieldsWithoutId = new ArrayList<>();
            Field idxField = getIdField();
            for (Field field : getAllFields()) {
                if (field != idxField) {
                    fieldsWithoutId.add(field);
                }
            }
        }
        return fieldsWithoutId;

    }
}
