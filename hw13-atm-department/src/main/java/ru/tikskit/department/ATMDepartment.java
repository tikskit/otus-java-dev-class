package ru.tikskit.department;

import ru.tikskit.remote.accesschecking.RemoteAccessException;

public interface ATMDepartment {
    void restore() throws RemoteAccessException;
}
