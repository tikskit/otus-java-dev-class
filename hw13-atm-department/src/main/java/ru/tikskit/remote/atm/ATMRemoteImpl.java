package ru.tikskit.remote.atm;

import ru.tikskit.atm.ATMImpl;
import ru.tikskit.atm.BanknotesStorage;
import ru.tikskit.remote.RemoteAccessException;

public class ATMRemoteImpl extends ATMImpl implements ATMRemote{
    private final int id;


    public ATMRemoteImpl(int id) {
        this.id = id;
    }

    @Override
    public int getId(String user, String pass) {
        return id;
    }

    @Override
    public Memento store(String user, String pass) {
        return new MementoImpl();
    }

    private class MementoImpl implements Memento {
        private final BanknotesStorage.Memento banknotesStorageMemento;

        public MementoImpl() {
            this.banknotesStorageMemento = banknotesStorage.store();
        }

        @Override
        public void restore(String user, String pass) {
            banknotesStorageMemento.restore();
        }
    }

}
