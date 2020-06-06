package ru.tikskit.department;

import ru.tikskit.remote.atm.DeptEventsListener;

public interface DeptEventsNotifier {
    void addListener(DeptEventsListener listener);
    void delListener(DeptEventsListener listener);

    int requestTotalMoneyAmount();
    void initAll();
}
