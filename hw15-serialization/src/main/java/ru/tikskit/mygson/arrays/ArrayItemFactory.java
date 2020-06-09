package ru.tikskit.mygson.arrays;

public class ArrayItemFactory {

    public static ArrayItemTypeStrategy createTypeStrategy(Class<?> clazz) {
        if (BooleanStrategy.supportsType(clazz)) {
            return new BooleanStrategy();
        } else if (IntegerStrategy.supportsType(clazz)) {
            return new IntegerStrategy();
        } else if (CharStrategy.supportsType(clazz)) {
            return new CharStrategy();
        } else if (DoubleStrategy.supportsType(clazz)) {
            return new DoubleStrategy();
        } else if (LongStrategy.supportsType(clazz)) {
            return new LongStrategy();
        } else if (StringStrategy.supportsType(clazz)) {
            return new StringStrategy();
        } else if (ByteStrategy.supportsType(clazz)) {
            return new ByteStrategy();
        } else if (ShortStrategy.supportsType(clazz)) {
            return new ShortStrategy();
        } else if (FloatStrategy.supportsType(clazz)) {
            return new FloatStrategy();
        }
        else {
            return null;
        }
    }
}
