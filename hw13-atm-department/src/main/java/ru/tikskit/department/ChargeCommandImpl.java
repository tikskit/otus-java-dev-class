package ru.tikskit.department;

import ru.tikskit.atm.ATM;
import ru.tikskit.money.MoneyPack;
import ru.tikskit.remote.atm.ATMCommand;

public class ChargeCommandImpl implements ATMCommand {
    private final MoneyPack moneyPack;

    public ChargeCommandImpl(MoneyPack moneyPack) {
        this.moneyPack = moneyPack;
    }

    @Override
    public void execute(ATM atm) {
        atm.put(moneyPack);
    }
}
