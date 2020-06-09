package ru.tikskit.department;

import ru.tikskit.remote.RemoteAccessException;

public interface ATMDepartment {
    void restore() throws RemoteAccessException;
}
