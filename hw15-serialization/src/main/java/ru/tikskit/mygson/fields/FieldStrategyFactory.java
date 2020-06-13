package ru.tikskit.mygson.fields;

public class FieldStrategyFactory {

    public static FieldTypeStrategy createStrategy(Class<?> clazz){
        if (IntegerStrategy.supportsType(clazz)) {
            return new IntegerStrategy();
        } else if (StringStrategy.supportsType(clazz)) {
            return new StringStrategy();
        } else if (CharStrategy.supportsType(clazz)) {
            return new CharStrategy();
        } else if (BooleanStrategy.supportsType(clazz)) {
            return new BooleanStrategy();
        } else if (DoubleStrategy.supportsType(clazz)) {
            return new DoubleStrategy();
        } else if (LongStrategy.supportsType(clazz)) {
            return new LongStrategy();
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
