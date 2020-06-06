package ru.tikskit.remote.atm;

import ru.tikskit.atm.ATM;

public interface InitCommand {
    void execute(ATM atm);
}
