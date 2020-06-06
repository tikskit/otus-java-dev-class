package ru.tikskit.atm;

import ru.tikskit.atm.remote.ATMRemote;
import ru.tikskit.atm.remote.ATMRemoteImpl;

public class ATMFactory {
    public static ATM createATM(int id) {
        return new ATMProxy(new ATMImpl());
    }
    public static ATMRemote createATMRemote(int id) {
        return new ATMRemoteImpl(id);
    }
}
