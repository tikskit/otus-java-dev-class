package ru.tikskit.atm;

public class CantWithdrawException extends Throwable {
    public CantWithdrawException(int amount) {
        super(String.format("Невозможно выдать сумму: %d", amount));
    }
}
