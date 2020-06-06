package ru.tikskit.department;

import ru.tikskit.atm.ATMFactory;
import ru.tikskit.atm.remote.ATMRemote;
import ru.tikskit.atm.remote.Memento;
import ru.tikskit.atm.remote.accesschecking.RemoteAccessException;

import java.util.HashSet;
import java.util.Set;

public class ATMDepartmentImpl implements ATMDepartment {
    private static final int ATM_COUNT = 20;
    private final String user;
    private final String pass;

    private final Set<ATMRemote> atm;
    private final Set<Memento> initStates;

    public ATMDepartmentImpl(String user, String pass) throws RemoteAccessException {
        this.user = user;
        this.pass = pass;
        atm = populateATMSet();
        initStates = storeAll();
    }

    @Override
    public void restore() throws RemoteAccessException {
        for (Memento m : initStates) {
            m.restore(user, pass);
        }
    }

    private static Set<ATMRemote> populateATMSet() {
        Set<ATMRemote> res = new HashSet<>();

        for (int i = 0; i < ATM_COUNT; i++) {
            res.add(ATMFactory.createATMRemote(ATMIdProducer.getInstance().requestId()));
        }

        return res;
    }

    private Set<Memento> storeAll() throws RemoteAccessException {
        Set<Memento> res = new HashSet<>();
        for (ATMRemote atm : atm) {
            res.add(atm.store(user, pass));
        }

        return res;
    }

}
