package ru.tikskit.testframework;

import ru.tikskit.testframework.annotations.After;
import ru.tikskit.testframework.annotations.Before;
import ru.tikskit.testframework.annotations.Test;

import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class Tester {

    public static final String RESULT_MSG = "Tests passed: %d, tests failed: %d, total: %d";


    public void doTest(String testClassName, PrintStream out) throws Exception{
        List<TestResult> execResults;
        try {
            execResults = execTests(testClassName);
        } catch (TestInstantiateException e) {
            out.println("Test class instantiate failed with exception:");
            e.printStackTrace(out);
            return;
        }
        printResults(execResults, out);

    }

    private TestClassContext getTestClassContext(Class<?> clazz) throws TestInstantiateException {

        Constructor<?> constructor;
        try {
            constructor = clazz.getConstructor();
        } catch (NoSuchMethodException | SecurityException e) {
            throw new TestInstantiateException(e);
        }
        TestClassContext tc = new TestClassContext(constructor);

        Method[] methods = clazz.getMethods();
        for (Method method: methods) {
            if (method.isAnnotationPresent(Test.class)) {
                tc.addTest(method);
            } else if (method.isAnnotationPresent(Before.class)) {
                tc.addBefore(method);
            } else if (method.isAnnotationPresent(After.class)) {
                tc.addAfter(method);
            }
        }

        return tc.isEmpty() ? null : tc;
    }

    private List<TestResult> execTests(String testClassName) throws TestInstantiateException, InvocationTargetException,
            IllegalAccessException {
        Class<?> clazz;
        try {
            clazz = Class.forName(testClassName);
        } catch (ClassNotFoundException e) {
            throw new TestInstantiateException(e);
        }
        TestClassContext tcc = getTestClassContext(clazz);
        if (tcc != null) {
            return tcc.invoke();
        } else {
            return null;
        }
    }

    private void printResults(List<TestResult> execResults, PrintStream out) {
        int passedCount = 0;
        int failedCount = 0;

        if (execResults != null) {
            for (TestResult et: execResults) {
                switch (et.getResult()) {
                    case TEST_EXCEPTION:
                        failedCount++;
                        out.println(String.format("Method '%s' failed with exception: %s", et.getTestMethod().getName(),
                                et.getException()));
                        break;
                    case PASSED:

                        passedCount++;
                        break;
                    case FAILED:
                        out.println(String.format("Method '%s' didn't passed with failed assertion: %s",
                                et.getTestMethod().getName(), et.getException().getMessage()));
                        failedCount++;
                        break;
                    case SETUP_EXCEPTION:
                        out.println("Test setup exception:");
                        et.getException().printStackTrace(out);
                        break;
                }
            }
        }

        out.println(String.format(RESULT_MSG, passedCount, failedCount, passedCount + failedCount));
    }

}
