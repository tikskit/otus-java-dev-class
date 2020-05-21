package ru.tikskit.atm;

public class NominalImpl implements Nominal{

    private final int value;

    public NominalImpl(int value) {
        if (value <= 0) {
            throw new IllegalArgumentException(String.format("Недопустимое значение номинала банкноты: %d!", value));
        }

        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return getValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        return value == ((Nominal) obj).getValue();
    }
}
