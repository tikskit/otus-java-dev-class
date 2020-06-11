package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData{

    private final EntityClassMetaData<?> meta;

    public EntitySQLMetaDataImpl(Class<?> entityClass) {
        meta = new EntityClassMetaDataImpl<>(entityClass);
    }

    @Override
    public String getSelectAllSql() {
        return String.format("select %s from %s", getAllFieldsStr(), meta.getName());
    }

    @Override
    public String getSelectByIdSql() {
        return String.format("select %s from %s where %s=?", getAllFieldsStr(), meta.getName(),
                meta.getIdField().getName());
    }

    @Override
    public String getInsertSql() {
        return String.format("insert into %s(%s) values(%s)", meta.getName(), getFieldsWithoutIdStr(),
                getInsertValuesStr());
    }

    @Override
    public String getUpdateSql() {
        return String.format("update %s set %s where %s=?", meta.getName(), getUpdateFieldsStr(),
                meta.getIdField().getName());
    }

    private String getUpdateFieldsStr() {
        StringBuilder sb = new StringBuilder();
        for (Field field : meta.getFieldsWithoutId()) {
            sb.append(field.getName()).
                    append("=?, ");
        }
        return removeComma(sb);
    }

    private String getFieldsWithoutIdStr() {
        StringBuilder sb = new StringBuilder();
        for (Field field : meta.getFieldsWithoutId()) {
            sb.append(field.getName()).
                    append(", ");
        }
        return removeComma(sb);
    }

    private String getAllFieldsStr() {
        StringBuilder sb = new StringBuilder();
        for (Field field : meta.getAllFields()) {
            sb.append(field.getName()).
                    append(", ");
        }
        return removeComma(sb);
    }

    private String getInsertValuesStr() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < meta.getFieldsWithoutId().size(); i++) {
            sb.append("?, ");
        }
        return removeComma(sb);
    }

    private static String removeComma(StringBuilder sb) {
        if (sb.length() > 2) {
            return sb.toString().substring(0, sb.length() - 2);

        } else {
            return sb.toString();
        }
    }

}
