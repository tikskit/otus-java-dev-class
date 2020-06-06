package ru.tikskit.atm;

import ru.tikskit.remote.atm.ATMRemote;
import ru.tikskit.remote.atm.ATMRemoteImpl;
import ru.tikskit.remote.atm.DeptEventsListenerImpl;
import ru.tikskit.department.DeptEventsNotifier;

public class ATMFactory {
    public static ATM createATM(int id) {
        return new ATMProxy(new ATMImpl());
    }

    public static ATMRemote createATMRemote(int id, DeptEventsNotifier deptEventsNotifier) {
        ATMRemoteImpl res = new ATMRemoteImpl(id);
        deptEventsNotifier.addListener(new DeptEventsListenerImpl(res));

        return res;
    }
}
