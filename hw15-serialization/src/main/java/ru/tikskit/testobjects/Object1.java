package ru.tikskit.testobjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class Object1 {
    private char charValue;
    private Character characterValue;
    private int intValue;
    private Integer IntegerValue;
    private String stringValueNull;
    private String stringValue;
    private boolean boolValue;
    private Boolean booleanValue;
    private Double doubleValue;
    private double dValue;
    private Long longValue;
    private long lValue;
    private int[] intArray;
    private Collection<Integer> collection;
    private byte byteValue;
    private Byte byteObjValue;
    private Integer[] integerArray;
    private Float floatObjValue;
    private float floatValue;
    private Short shortObjValue;
    private short shortValue;



    public void setValues() {
        charValue = 'W';
        characterValue = 'H';
        intValue = 110;
        IntegerValue = 550;
        stringValueNull = null;
        stringValue = "Привет, мир!";
        boolValue = true;
        booleanValue = true;
        doubleValue = 10.5;
        dValue = 100.789;
        longValue = 77L;
        lValue = 98L;
        intArray = new int[]{1, 2, 3, 4};

        collection = new ArrayList();
        collection.add(10);
        collection.add(20);
        collection.add(30);

        byteValue = 4;
        byteObjValue = 100;
        integerArray = new Integer[]{5, 6, 7};
        floatObjValue = 3.3F;
        floatValue = 95.456F;
        shortObjValue = 20000;
        shortValue = 10000;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Object1 object1 = (Object1) o;
        return charValue == object1.charValue &&
                intValue == object1.intValue &&
                boolValue == object1.boolValue &&
                Double.compare(object1.dValue, dValue) == 0 &&
                lValue == object1.lValue &&
                byteValue == object1.byteValue &&
                Float.compare(object1.floatValue, floatValue) == 0 &&
                shortValue == object1.shortValue &&
                Objects.equals(characterValue, object1.characterValue) &&
                Objects.equals(IntegerValue, object1.IntegerValue) &&
                Objects.equals(stringValueNull, object1.stringValueNull) &&
                Objects.equals(stringValue, object1.stringValue) &&
                Objects.equals(booleanValue, object1.booleanValue) &&
                Objects.equals(doubleValue, object1.doubleValue) &&
                Objects.equals(longValue, object1.longValue) &&
                Arrays.equals(intArray, object1.intArray) &&
                Objects.equals(collection, object1.collection) &&
                Objects.equals(byteObjValue, object1.byteObjValue) &&
                Arrays.equals(integerArray, object1.integerArray) &&
                Objects.equals(floatObjValue, object1.floatObjValue) &&
                Objects.equals(shortObjValue, object1.shortObjValue);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(charValue, characterValue, intValue, IntegerValue, stringValueNull, stringValue, boolValue, booleanValue, doubleValue, dValue, longValue, lValue, collection, byteValue, byteObjValue, floatObjValue, floatValue, shortObjValue, shortValue);
        result = 31 * result + Arrays.hashCode(intArray);
        result = 31 * result + Arrays.hashCode(integerArray);
        return result;
    }

    @Override
    public String toString() {
        return "Object1{" +
                "charValue=" + charValue +
                ", characterValue=" + characterValue +
                ", intValue=" + intValue +
                ", IntegerValue=" + IntegerValue +
                ", stringValueNull='" + stringValueNull + '\'' +
                ", stringValue='" + stringValue + '\'' +
                ", boolValue=" + boolValue +
                ", booleanValue=" + booleanValue +
                ", doubleValue=" + doubleValue +
                ", dValue=" + dValue +
                ", longValue=" + longValue +
                ", lValue=" + lValue +
                ", intArray=" + Arrays.toString(intArray) +
                ", collection=" + collection +
                ", byteValue=" + byteValue +
                ", byteObjValue=" + byteObjValue +
                ", integerArray=" + Arrays.toString(integerArray) +
                ", floatObjValue=" + floatObjValue +
                ", floatValue=" + floatValue +
                ", shortObjValue=" + shortObjValue +
                ", shortValue=" + shortValue +
                '}';
    }
}
