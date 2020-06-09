package ru.tikskit.remote.atm;

public interface ATMRemote {

    int getId(String user, String pass);
    Memento store(String user, String pass);

}
