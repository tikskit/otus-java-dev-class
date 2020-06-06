package ru.tikskit;

import ru.tikskit.department.DeptEventsNotifier;
import ru.tikskit.remote.accesschecking.RemoteAccessException;
import ru.tikskit.department.ATMDepartment;
import ru.tikskit.department.ATMDepartmentImpl;

public class ATMDepMainClass {
    private final static String USER = "root";
    private final static String PASS = "1234";

    public static void main(String[] args) {
        try {
            ATMDepartment department = new ATMDepartmentImpl(USER, PASS);
            DeptEventsNotifier deptEventsNotifier = (DeptEventsNotifier) department;

            System.out.println(String.format("Все деньги департамента: %d", deptEventsNotifier.requestTotalMoneyAmount()));
            deptEventsNotifier.initAll();
            System.out.println(String.format("Все деньги департамента: %d", deptEventsNotifier.requestTotalMoneyAmount()));

            department.restore();
            System.out.println(String.format("Все деньги департамента: %d", deptEventsNotifier.requestTotalMoneyAmount()));
        } catch (RemoteAccessException e) {
            e.printStackTrace();
        }
    }
}
