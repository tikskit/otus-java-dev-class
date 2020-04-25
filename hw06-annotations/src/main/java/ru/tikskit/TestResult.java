package ru.tikskit;

import java.lang.reflect.Method;

class TestResult {
    private final Method testMethod;
    private final Result result;
    private final Throwable exception;

    public TestResult(Method testMethod, Result result, Throwable exception) {
        this.testMethod = testMethod;
        this.exception = exception;
        this.result = result;
    }

    public TestResult(Method testMethod, Result result) {
        this.testMethod = testMethod;
        this.result = result;
        this.exception = null;
    }

    public Method getTestMethod() {
        return testMethod;
    }

    public Result getResult() {
        return result;
    }

    public Throwable getException() {
        return exception;
    }

    public enum Result {
        PASSED,
        FAILED,
        EXCEPTION
    }
}
