package ru.tikskit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.tikskit.atm.ATM;
import ru.tikskit.atm.CantWithdrawException;
import ru.tikskit.atm.MoneyPack;
import ru.tikskit.atm.NominalImpl;
import ru.tikskit.atm.NotEnoughMoneyException;

import static org.junit.jupiter.api.Assertions.*;

public class ATMTest {
    private ATM atm;

    @BeforeEach
    public void setUp() {
        atm = new ATM();
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
                .addBanknotes(new NominalImpl(50), 1)
                .addBanknotes(new NominalImpl(100), 30)
                .addBanknotes(new NominalImpl(500), 10)
                .addBanknotes(new NominalImpl(1000), 1)
        );

        assertEquals(atm.calcTotalAmount(), 9050);
    }

    @DisplayName("Проверяем, что добавление в банкомат купюры неверного номинала вызывает IllegalArgumentException")
    @Test
    public void checkWrongNominalThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            atm.put(new MoneyPack().addBanknotes(new NominalImpl(0), 1));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            atm.put(new MoneyPack().addBanknotes(new NominalImpl(-110), 1));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            atm.put(new MoneyPack().addBanknotes(new NominalImpl(60), 1));
        });

    }

    @DisplayName("Проверяем, что добавление в банкомат неверного количества купюр вызывает IllegalArgumentException")
    @Test
    public void checkWrongBanknotesCountThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            atm.put(new MoneyPack().addBanknotes(new NominalImpl(50), 0));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            atm.put(new MoneyPack().addBanknotes(new NominalImpl(100), -1));
        });

    }

    @DisplayName("Проверяем, что если в банкомате недостаточно денег для снятия, то выбрасывается NotEnoughMoneyException")
    @Test
    public void checkNotEnoughMoneyExceptionThrowsWhenNotEnough() {
        atm.put(new MoneyPack()
                .addBanknotes(new NominalImpl(50), 1)
        );

        assertThrows(NotEnoughMoneyException.class, () -> {
            atm.get(100);
        });
    }

    @DisplayName("Проверяем, что если банкомат пустой, то выбрасывается NotEnoughMoneyException при попытке снятия")
    @Test
    public void checkNotEnoughMoneyExceptionThrowsEmpty() {
        assertThrows(NotEnoughMoneyException.class, () -> {
            atm.get(100);
        });
    }

    @DisplayName("Проверяем, что выбрасывается IllegalArgumentException при попытке снять некорректную сумму")
    @Test
    public void checkZeroWithdrawAmountThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            atm.get(0);
        });
    }

    @DisplayName("Проверяем, что выбрасывается IllegalArgumentException при попытке снять некорректную сумму")
    @Test
    public void checkWrongWithdrawAmountThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            atm.get(-10);
        });
    }

    @DisplayName("Проверяем, что если пользователь ввел сумму для снятия не кратную ни одному из номиналов, то выбрасываетс CantWithdrawException")
    @Test
    public void checkWrongWithdrawAmountThrowsCantWithdrawException1() {
        atm.put(new MoneyPack()
                .addBanknotes(new NominalImpl(50), 1)
        );
        assertThrows(CantWithdrawException.class, () -> {
            atm.get(15);
        });
    }
}
