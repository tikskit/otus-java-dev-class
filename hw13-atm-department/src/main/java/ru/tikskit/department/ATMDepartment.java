package ru.tikskit.department;

import ru.tikskit.atm.remote.accesschecking.RemoteAccessException;

public interface ATMDepartment {
    void restore() throws RemoteAccessException;
}
