package ru.tikskit.atm;

public class IncorrectMoneyAmountException extends Exception {

    public IncorrectMoneyAmountException(int amount) {
        super(String.format("Некорректное значение суммы денег: %d!", amount));
    }
}
