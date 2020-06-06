package ru.tikskit.atm.remote.accesschecking;

abstract class BaseHandlerImpl implements Handler {

    private Handler next;

    @Override
    public Handler setNext(Handler handler) {
        if (handler == this) {
            throw new IllegalArgumentException("Нельзя в качестве следующего обработчика установить самого себя!");
        }
        next = handler;
        return next;
    }

    protected boolean checkNext(String user, String pass) {
        if (next == null) {
            return true;
        } else {
            return next.checkAccess(user, pass);
        }
    }
}
