package ru.tikskit;

import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

public class Tester {

    public static final String RESULT_MSG = "Tests passed: %d, tests failed: %d";

    private TestClassContext getTestClassContext(Class<?> clazz) throws NoSuchMethodException {

        Constructor<?> constructor = clazz.getConstructor();
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

    private List<TestResult> execTests(String testClassName) throws Exception {
        Class<?> clazz = Class.forName(testClassName);
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
                    case EXCEPTION:
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
                }
            }
        }

        out.println(String.format(RESULT_MSG, passedCount, failedCount));
    }

    public void doTest(String testClassName, PrintStream out) throws Exception {
        List<TestResult> execResults = execTests(testClassName);
        printResults(execResults, out);

    }
}
