package ru.tikskit.money;

/**
 * Номинал банкноты
 */
public enum Denomination {
    FIFTY(50),
    HUNDRED(100),
    FIVE_HUNDRED(500),
    THOUSAND(1000);

    private int value;

    Denomination(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Denomination getBiggest() {
        return THOUSAND;
    }
}
