package ru.tikskit;

import ru.tikskit.atm.ATM;
import ru.tikskit.atm.CantWithdrawException;
import ru.tikskit.atm.Denomination;
import ru.tikskit.atm.MoneyCollection;
import ru.tikskit.atm.NotEnoughMoneyException;

public class ATMMainClass {
    public static void main(String[] args) {

        ATM atm = new ATM();

        atm.put(Denomination.FIFTY, 53);
        atm.put(Denomination.HUNDRED, 3);
        atm.put(Denomination.FIVE_HUNDRED, 4);
        atm.put(Denomination.THOUSAND, 6);

        try {
            System.out.println(String.format("В банкомате: %d", atm.calcTotalAmount()));

            MoneyCollection mp = atm.get(10650);
            System.out.println(mp.toString());
            System.out.println(String.format("В банкомате: %d", atm.calcTotalAmount()));
        } catch (NotEnoughMoneyException | CantWithdrawException e) {
            e.printStackTrace();
        }
    }
}
