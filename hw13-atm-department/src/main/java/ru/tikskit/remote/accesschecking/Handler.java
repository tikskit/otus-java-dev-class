package ru.tikskit.remote.accesschecking;

public interface Handler {
    Handler setNext(Handler handler);
    boolean checkAccess(String user, String pass);
}
