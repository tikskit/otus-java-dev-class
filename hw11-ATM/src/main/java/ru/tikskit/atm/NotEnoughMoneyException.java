package ru.tikskit.atm;

public class NotEnoughMoneyException extends Exception {

    public NotEnoughMoneyException(int requiredAmount) {
        super(String.format("Недостаточно денег в банкомате: %d!", requiredAmount));
    }
}
