package ru.tikskit.testframework;

public class TestInstantiateException extends Exception{
    public TestInstantiateException() {
    }

    public TestInstantiateException(String message) {
        super(message);
    }

    public TestInstantiateException(String message, Throwable cause) {
        super(message, cause);
    }

    public TestInstantiateException(Throwable cause) {
        super(cause);
    }

    public TestInstantiateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
