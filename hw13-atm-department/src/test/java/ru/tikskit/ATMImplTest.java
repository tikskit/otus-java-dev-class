package ru.tikskit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.tikskit.atm.ATM;
import ru.tikskit.atm.ATMImpl;
import ru.tikskit.atm.CantWithdrawException;
import ru.tikskit.money.Denomination;
import ru.tikskit.money.MoneyPack;
import ru.tikskit.atm.NotEnoughMoneyException;

import static org.junit.jupiter.api.Assertions.*;

public class ATMImplTest {
    private ATM atm;

    @BeforeEach
    public void setUp() {
        atm = new ATMImpl();
    }

    @DisplayName("Проверяем, что до наполнения банкомат пустой")
    @Test
    public void checkATMEmpty() {
        assertEquals(atm.calcTotalAmount(), 0);
    }

    @DisplayName("Проверяем, что банкомат наполняется правильно")
    @Test
    public void checkATMStuffedRight() {
        atm.put(new MoneyPack()
                .addBanknotes(Denomination.FIFTY, 1)
                .addBanknotes(Denomination.HUNDRED, 30)
                .addBanknotes(Denomination.FIVE_HUNDRED, 10)
                .addBanknotes(Denomination.THOUSAND, 1)
        );

        assertEquals(atm.calcTotalAmount(), 9050);
    }

    @DisplayName("Проверяем, что добавление в банкомат неверного количества купюр вызывает IllegalArgumentException")
    @Test
    public void checkWrongBanknotesCountThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            atm.put(new MoneyPack().addBanknotes(Denomination.FIFTY, 0));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            atm.put(new MoneyPack().addBanknotes(Denomination.HUNDRED, -1));
        });

    }

    @DisplayName("Проверяем, что если в банкомате по одной купюре, то при снятии будет достаточно денег")
    @Test
    public void checkWithdraw1650() {
        atm.put(new MoneyPack()
                .addBanknotes(Denomination.FIFTY, 1)
                .addBanknotes(Denomination.HUNDRED, 1)
                .addBanknotes(Denomination.FIVE_HUNDRED, 1)
                .addBanknotes(Denomination.THOUSAND, 1)
        );

        assertDoesNotThrow(() -> {
            atm.withdraw(1650);
        });
    }

    @Test
    public void checkWithdraw3150() {
        atm.put(new MoneyPack()
                .addBanknotes(Denomination.FIFTY, 30)
                .addBanknotes(Denomination.HUNDRED, 1)
                .addBanknotes(Denomination.FIVE_HUNDRED, 1)
                .addBanknotes(Denomination.THOUSAND, 1)
        );

        assertDoesNotThrow(() -> {
            atm.withdraw(3100);
        });
    }

    @Test
    public void checkWithdraw3150_1() {
        atm.put(new MoneyPack()
                .addBanknotes(Denomination.FIFTY, 3)
                .addBanknotes(Denomination.THOUSAND, 3)
        );

        assertDoesNotThrow(() -> {
            atm.withdraw(3100);
        });
    }
    @Test
    public void checkWithdraw3150_2() {
        atm.put(new MoneyPack()
                .addBanknotes(Denomination.FIFTY, 60)
                .addBanknotes(Denomination.THOUSAND, 3)
        );

        assertDoesNotThrow(() -> {
            atm.withdraw(3100);
        });
    }

    @DisplayName("Проверяем, что если в банкомате недостаточно денег для снятия, то выбрасывается NotEnoughMoneyException")
    @Test
    public void checkNotEnoughMoneyExceptionThrowsWhenNotEnough() {
        atm.put(new MoneyPack()
                .addBanknotes(Denomination.FIFTY, 1)
        );

        assertThrows(NotEnoughMoneyException.class, () -> {
            atm.withdraw(100);
        });
    }

    @DisplayName("Проверяем, что если банкомат пустой, то выбрасывается NotEnoughMoneyException при попытке снятия")
    @Test
    public void checkNotEnoughMoneyExceptionThrowsEmpty() {
        assertThrows(NotEnoughMoneyException.class, () -> {
            atm.withdraw(100);
        });
    }

    @DisplayName("Проверяем, что выбрасывается IllegalArgumentException при попытке снять некорректную сумму")
    @Test
    public void checkZeroWithdrawAmountThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            atm.withdraw(0);
        });
    }

    @DisplayName("Проверяем, что выбрасывается IllegalArgumentException при попытке снять некорректную сумму")
    @Test
    public void checkWrongWithdrawAmountThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            atm.withdraw(-10);
        });
    }

    @DisplayName("Проверяем, что если пользователь ввел сумму для снятия не кратную ни одному из номиналов, то выбрасываетс CantWithdrawException")
    @Test
    public void checkWrongWithdrawAmountThrowsCantWithdrawException1() {
        atm.put(new MoneyPack()
                .addBanknotes(Denomination.FIFTY, 1)
        );
        assertThrows(CantWithdrawException.class, () -> {
            atm.withdraw(15);
        });
    }
    @DisplayName("Проверяем, что если произошла ошибка CantWithdrawException, то количество денег в банкомате не изменилось")
    @Test
    public void checkCantWithdrawExceptionDoesntChangeMoney() {
        atm.put(new MoneyPack()
                .addBanknotes(Denomination.FIFTY, 1)
        );
        assertThrows(CantWithdrawException.class, () -> {
            atm.withdraw(15);
        });

        assertEquals(atm.calcTotalAmount(), 50);
    }
}
