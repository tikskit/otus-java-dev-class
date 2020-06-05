package ru.tikskit.atm;

/**
 * Сумма денег в банкомате меньше, чем требуется выдать
 */
public class NotEnoughMoneyException extends ATMException {

    public NotEnoughMoneyException(int requiredAmount) {
        super(String.format("Недостаточно денег в банкомате: %d!", requiredAmount));
    }
}
