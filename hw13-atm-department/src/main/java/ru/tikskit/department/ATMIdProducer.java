package ru.tikskit.department;

/**
 * Каждый банкомат имеет свой уникальный идентификатор, а этот класс генерирует эти уникальные идентификаторы
 */
public class ATMIdProducer {
    private int lastId = 0;
    private final static ATMIdProducer instance = new ATMIdProducer();

    private ATMIdProducer() {
    }

    public static ATMIdProducer getInstance() {
        return instance;
    }

    public int requestId() {
        return ++lastId;
    }
}
