package ru.tikskit;

import ru.tikskit.atm.remote.accesschecking.RemoteAccessException;
import ru.tikskit.department.ATMDepartment;
import ru.tikskit.department.ATMDepartmentImpl;

public class ATMDepMainClass {
    private final static String USER = "root";
    private final static String PASS = "1234";

    public static void main(String[] args) {
        try {
            ATMDepartment department = new ATMDepartmentImpl(USER, PASS);
            department.restore();
        } catch (RemoteAccessException e) {
            e.printStackTrace();
        }
    }
}
