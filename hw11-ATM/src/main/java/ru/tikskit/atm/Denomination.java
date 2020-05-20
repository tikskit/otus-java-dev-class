package ru.tikskit.atm;

public enum Denomination {

    FIFTY(50),
    HUNDRED(100),
    FIVE_HUNDRED(500),
    THOUSAND(1000);

    public static final Denomination MAX_DENOMINATION = Denomination.THOUSAND;

    private int value;

    Denomination(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
