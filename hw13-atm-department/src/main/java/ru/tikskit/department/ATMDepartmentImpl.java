package ru.tikskit.department;

import ru.tikskit.atm.ATMFactory;
import ru.tikskit.money.MoneyPack;
import ru.tikskit.remote.atm.ATMRemote;
import ru.tikskit.remote.atm.DeptEventsListener;
import ru.tikskit.remote.atm.Memento;
import ru.tikskit.remote.RemoteAccessException;

import java.util.HashSet;
import java.util.Set;

public class ATMDepartmentImpl implements ATMDepartment, DeptEventsNotifier {
    private final int atmCount;
    private final String user;
    private final String pass;
    private final Set<DeptEventsListener> eventsListeners = new HashSet<>();

    private final Set<ATMRemote> atmSet;
    private final Set<Memento> initStates;

    public ATMDepartmentImpl(int atmCount, String user, String pass) throws RemoteAccessException {
        this.atmCount = atmCount;
        this.user = user;
        this.pass = pass;
        atmSet = populateATMSet();
        initStates = storeAll();
    }

    @Override
    public void restore() throws RemoteAccessException {
        for (Memento m : initStates) {
            m.restore(user, pass);
        }
    }

    @Override
    public void addListener(DeptEventsListener listener) {
        eventsListeners.add(listener);
    }

    @Override
    public int requestTotalMoneyAmount() {
        int res = 0;
        for (DeptEventsListener listener : eventsListeners) {
            res += listener.getMoneyAmount();
        }

        return res;
    }

    @Override
    public void initAll(MoneyPack moneyPack) {
        for (DeptEventsListener listener : eventsListeners) {
            listener.init(new ChargeCommandImpl(moneyPack));
        }
    }

    @Override
    public void delListener(DeptEventsListener listener) {
        eventsListeners.remove(listener);
    }

    private Set<ATMRemote> populateATMSet() {
        Set<ATMRemote> res = new HashSet<>();

        for (int i = 0; i < atmCount; i++) {
            res.add(ATMFactory.createATMRemote(ATMIdProducer.getInstance().requestId(), this));
        }

        return res;
    }

    private Set<Memento> storeAll() throws RemoteAccessException {
        Set<Memento> res = new HashSet<>();
        for (ATMRemote atm : atmSet) {
            res.add(atm.store(user, pass));
        }

        return res;
    }

}
