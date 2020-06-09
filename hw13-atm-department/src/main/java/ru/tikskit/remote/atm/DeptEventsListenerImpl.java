package ru.tikskit.remote.atm;

public class DeptEventsListenerImpl implements DeptEventsListener {
    private final ATMRemoteImpl atm;

    public DeptEventsListenerImpl(ATMRemoteImpl atm) {
        this.atm = atm;
    }

    @Override
    public int getMoneyAmount() {
        return atm.calcTotalAmount();
    }

    @Override
    public void init(ATMCommand atmCommand) {
        atmCommand.execute(atm);
    }
}
