package ru.tikskit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.tikskit.department.ATMDepartment;
import ru.tikskit.department.ATMDepartmentImpl;
import ru.tikskit.department.DeptEventsNotifier;
import ru.tikskit.money.Denomination;
import ru.tikskit.money.MoneyPack;


import static org.junit.jupiter.api.Assertions.*;

public class ATMDepartmentTest {
    private final static int ATM_AMOUNT = 11;

    private ATMDepartment dept;
    private DeptEventsNotifier deptEventsNotifier;

    @BeforeEach
    public void setUp() {
        dept = new ATMDepartmentImpl(ATM_AMOUNT);
        deptEventsNotifier = (DeptEventsNotifier) dept;
    }

    @DisplayName("Проверяем, что после загрузки всех банкоматов, сумма правильная")
    @Test
    public void checkCharging() {

        MoneyPack moneyPack = new MoneyPack();
        moneyPack.addBanknotes(Denomination.THOUSAND, 1).addBanknotes(Denomination.FIFTY, 1);

        deptEventsNotifier.initAll(moneyPack);

        assertEquals(moneyPack.calcTotalSum() * ATM_AMOUNT, deptEventsNotifier.requestTotalMoneyAmount());
    }

    @DisplayName("Проверяем, что после вызова restore сумма во всех банкоматах равна 0")
    @Test
    public void checkRestoring() {
        final int ATM_AMOUNT = 11;
        MoneyPack moneyPack = new MoneyPack();
        moneyPack.addBanknotes(Denomination.THOUSAND, ATM_AMOUNT).addBanknotes(Denomination.FIFTY, 1);

        deptEventsNotifier.initAll(moneyPack);

        dept.restore();
        assertEquals(deptEventsNotifier.requestTotalMoneyAmount(), 0);
    }

}
