package ru.tikskit;

import ru.tikskit.atm.ATM;
import ru.tikskit.atm.ATMImpl;
import ru.tikskit.atm.CantWithdrawException;
import ru.tikskit.atm.Denomination;
import ru.tikskit.atm.MoneyPack;
import ru.tikskit.atm.NotEnoughMoneyException;

public class ATMMainClass {
    public static void main(String[] args) {

        ATM atm = new ATMImpl();

        atm.put(new MoneyPack()
                .addBanknotes(Denomination.FIFTY, 1)
                .addBanknotes(Denomination.HUNDRED, 30)
                .addBanknotes(Denomination.FIVE_HUNDRED, 10)
                .addBanknotes(Denomination.THOUSAND, 1)
        );

        try {
            System.out.println(String.format("В банкомате: %d", atm.calcTotalAmount()));

            MoneyPack mp = atm.withdraw(750);
            System.out.println(mp.toString());
            System.out.println(String.format("В банкомате: %d", atm.calcTotalAmount()));
        } catch (NotEnoughMoneyException | CantWithdrawException e) {
            e.printStackTrace();
        }
    }
}
