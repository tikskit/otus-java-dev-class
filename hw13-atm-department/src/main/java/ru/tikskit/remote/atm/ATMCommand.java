package ru.tikskit.remote.atm;

import ru.tikskit.atm.ATM;

public interface ATMCommand {
    void execute(ATM atm);
}
