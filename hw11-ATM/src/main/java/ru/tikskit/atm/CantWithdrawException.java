package ru.tikskit.atm;

/**
 * Нельзя выдать требуемую сумму, потому что она не кратна наименьшему номиналу существующих банкнот
 */
public class CantWithdrawException extends ATMException {
    public CantWithdrawException(int amount) {
        super(String.format("Невозможно выдать сумму: %d", amount));
    }

    public CantWithdrawException(Throwable cause) {
        super(cause);
    }
}
