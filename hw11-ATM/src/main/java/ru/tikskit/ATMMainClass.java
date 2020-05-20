package ru.tikskit;

import ru.tikskit.atm.ATM;
import ru.tikskit.atm.CantWithdrawException;
import ru.tikskit.atm.Denomination;
import ru.tikskit.atm.MoneyPack;
import ru.tikskit.atm.NotEnoughMoneyException;
import ru.tikskit.atm.OutOfBanknotesException;

public class ATMMainClass {
    public static void main(String[] args) {
        System.out.println("hi!");
        ATM atm = new ATM();


        atm.put(
                new MoneyPack(Denomination.FIFTY, 5).
                        add(Denomination.HUNDRED, 1)
        );

        try {
            System.out.println(String.format("В банкомате: %d", atm.calcTotalAmount()));

            MoneyPack mp = atm.get(950);
            System.out.println(mp.toString());
            System.out.println(String.format("В банкомате: %d", atm.calcTotalAmount()));
        } catch (NotEnoughMoneyException | CantWithdrawException | OutOfBanknotesException e) {
            e.printStackTrace();
        }
    }
}
