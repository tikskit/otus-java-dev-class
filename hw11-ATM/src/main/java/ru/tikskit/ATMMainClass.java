package ru.tikskit;

import ru.tikskit.atm.ATM;
import ru.tikskit.atm.CantWithdrawException;
import ru.tikskit.atm.MoneyPack;
import ru.tikskit.atm.NominalImpl;
import ru.tikskit.atm.NotEnoughMoneyException;

public class ATMMainClass {
    public static void main(String[] args) {

        ATM atm = new ATM();

        atm.put(new MoneyPack()
                .addBanknotes(new NominalImpl(50), 1)
                .addBanknotes(new NominalImpl(100), 30)
                .addBanknotes(new NominalImpl(500), 10)
                .addBanknotes(new NominalImpl(1000), 1)
        );

        try {
            System.out.println(String.format("В банкомате: %d", atm.calcTotalAmount()));

            MoneyPack mp = atm.get(750);
            System.out.println(mp.toString());
            System.out.println(String.format("В банкомате: %d", atm.calcTotalAmount()));
        } catch (NotEnoughMoneyException | CantWithdrawException e) {
            e.printStackTrace();
        }
    }
}
