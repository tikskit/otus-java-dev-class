package ru.tikskit.mygson;

import javax.json.Json;
import javax.json.JsonValue;
import java.math.BigDecimal;
import java.math.BigInteger;

public class JsonValueFactory {
    public static JsonValue createJsonValue(Object value) {
        if (value == null) {
            return null;
        }

        if (isInt(value.getClass())) {
            return createIntValue(value);
        } else if (isByte(value.getClass())) {
            return createByteValue(value);
        } else if (isBoolean(value.getClass())) {
            return createBooleanValue(value);
        } else if (isShort(value.getClass())) {
            return createShortValue(value);
        } else if (isFloat(value.getClass())) {
            return createFloatValue(value);
        } else if (isLong(value.getClass())) {
            return createLongValue(value);
        } else if (isDouble(value.getClass())) {
            return createDoubleValue(value);
        } else if (isString(value.getClass())) {
            return createStringValue(value);
        } else if (isChar(value.getClass())) {
            return createCharValue(value);
        } else if (isBigDecimal(value.getClass())) {
            return createBigDecimalValue(value);
        } else if (isBigInteger(value.getClass())) {
            return createBigInteger(value);
        } else {
            return null;
        }
    }

    private static JsonValue createBooleanValue(Object value) {
        return (Boolean)value ? JsonValue.TRUE : JsonValue.FALSE;
    }

    private static boolean isBoolean(Class<?> clazz) {
        return clazz == boolean.class || clazz == Boolean.class;
    }

    private static JsonValue createCharValue(Object value) {
        String strValue = Character.toString((char)value);
        return Json.createValue(strValue);
    }

    private static boolean isChar(Class<?> clazz) {
        return clazz == char.class || clazz == Character.class;
    }

    private static JsonValue createFloatValue(Object value) {
        return Json.createValue((Float)value);
    }

    private static boolean isFloat(Class<?> clazz) {
        return clazz == float.class || clazz == Float.class;
    }

    private static JsonValue createShortValue(Object value) {
        return Json.createValue((Short)value);
    }

    private static boolean isShort(Class<?> clazz) {
        return clazz == short.class || clazz == Short.class;
    }

    private static JsonValue createByteValue(Object value) {
        return Json.createValue((Byte)value);
    }

    private static boolean isByte(Class<?> clazz) {
        return clazz == byte.class || clazz == Byte.class;
    }

    private static JsonValue createBigInteger(Object value) {
        return Json.createValue((BigInteger) value);
    }

    private static boolean isBigInteger(Class<?> clazz) {
        return clazz == BigInteger.class;
    }

    private static JsonValue createBigDecimalValue(Object value) {
        return Json.createValue((BigDecimal) value);
    }

    private static boolean isBigDecimal(Class<?> clazz) {
        return clazz == BigDecimal.class;
    }

    private static JsonValue createStringValue(Object value) {
        return Json.createValue((String)value);
    }

    private static boolean isString(Class<?> clazz) {
        return clazz == String.class;
    }

    private static JsonValue createDoubleValue(Object value) {
        return Json.createValue((Double)value);
    }

    private static boolean isDouble(Class<?> clazz) {
        return clazz == double.class || clazz == Double.class;
    }

    private static JsonValue createLongValue(Object value) {
        return Json.createValue((Long)value);
    }

    private static boolean isLong(Class<?> clazz) {
        return clazz == long.class || clazz == Long.class;
    }

    private static boolean isInt(Class<?> clazz){
        return clazz == int.class || clazz == Integer.class;
    }

    private static JsonValue createIntValue(Object value) {
        return Json.createValue((Integer)value);
    }
}
