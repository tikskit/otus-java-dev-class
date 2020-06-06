package ru.tikskit.remote.atm;

public interface DeptEventsListener {
    int getMoneyAmount();
    void init(InitCommand initCommand);
}
