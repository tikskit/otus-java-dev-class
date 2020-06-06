package ru.tikskit.remote.atm;

import ru.tikskit.atm.ATMImpl;
import ru.tikskit.atm.BanknotesStorage;
import ru.tikskit.remote.accesschecking.Handler;
import ru.tikskit.remote.accesschecking.RemoteAccessException;
import ru.tikskit.remote.accesschecking.RoleHandler;
import ru.tikskit.remote.accesschecking.UserExistsHandler;

public class ATMRemoteImpl extends ATMImpl implements ATMRemote{
    private final int id;
    private final Handler remoteAccessHandler;


    public ATMRemoteImpl(int id) {
        this.id = id;
        remoteAccessHandler = new UserExistsHandler();
        remoteAccessHandler.setNext(new RoleHandler());
    }

    @Override
    public int getId(String user, String pass) throws RemoteAccessException {
        if (remoteAccessHandler.checkAccess(user, pass)) {
            return id;
        } else {
            throw new RemoteAccessException("Нет прав доступа!");
        }
    }

    @Override
    public Memento store(String user, String pass) throws RemoteAccessException {
        if (remoteAccessHandler.checkAccess(user, pass)) {
            return new MementoImpl();
        } else {
            throw new RemoteAccessException("Нет прав доступа!");
        }
    }

    private class MementoImpl implements Memento {
        private final BanknotesStorage.Memento banknotesStorageMemento;

        public MementoImpl() {
            this.banknotesStorageMemento = banknotesStorage.store();
        }

        @Override
        public void restore(String user, String pass) throws RemoteAccessException {
            if (remoteAccessHandler.checkAccess(user, pass)) {
                banknotesStorageMemento.restore();
            } else {
                throw new RemoteAccessException("Нет прав доступа!");
            }
        }
    }

}
