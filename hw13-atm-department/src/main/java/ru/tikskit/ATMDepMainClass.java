package ru.tikskit;

import ru.tikskit.department.DeptEventsNotifier;
import ru.tikskit.money.Denomination;
import ru.tikskit.money.MoneyPack;
import ru.tikskit.department.ATMDepartment;
import ru.tikskit.department.ATMDepartmentImpl;

public class ATMDepMainClass {

    public static void main(String[] args) {
        ATMDepartment department = new ATMDepartmentImpl(20);
        DeptEventsNotifier deptEventsNotifier = (DeptEventsNotifier) department;

        System.out.println(String.format("Все деньги департамента: %d", deptEventsNotifier.requestTotalMoneyAmount()));

        MoneyPack moneyPack = new MoneyPack();
        moneyPack.addBanknotes(Denomination.THOUSAND, 1).addBanknotes(Denomination.FIFTY, 1);
        deptEventsNotifier.initAll(moneyPack);
        System.out.println(String.format("Все деньги департамента: %d", deptEventsNotifier.requestTotalMoneyAmount()));

        department.restore();
        System.out.println(String.format("Все деньги департамента: %d", deptEventsNotifier.requestTotalMoneyAmount()));
    }
}
