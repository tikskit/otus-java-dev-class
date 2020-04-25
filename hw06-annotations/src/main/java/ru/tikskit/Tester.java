package ru.tikskit;

import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tester {

    public static final String RESULT_MSG = "Tests passed: %d, tests failed: %d";

    private enum Result {
        PASSED,
        FAILED,
        EXCEPTION
    }

    private static class ExecutedTest {
        private final Method method;
        private final Result result;
        private final Throwable exception;

        public ExecutedTest(Method method, Result result, Throwable exception) {
            this.method = method;
            this.exception = exception;
            this.result = result;
        }

        public ExecutedTest(Method method, Result result) {
            this.method = method;
            this.result = result;
            this.exception = null;
        }

    }

    private Map<Method, Object> prepareTestMethods(Class<?> clazz) throws Exception {
        Map<Method, Object> res = new HashMap<>();

        Constructor<?> constructor = clazz.getConstructor();
        Method[] methods = clazz.getMethods();
        for (Method method: methods) {
            if (method.isAnnotationPresent(Test.class)) {
                Object inst = constructor.newInstance();
                res.put(method, inst);
            }
        }

        return res;
    }

    private List<ExecutedTest> execTests(String testClassName) throws Exception {
        Class<?> clazz = Class.forName(testClassName);
        Map<Method, Object> tests = prepareTestMethods(clazz);
        List<ExecutedTest> results = new ArrayList<>();
        for (Method method: tests.keySet()) {
            Object inst = tests.get(method);

            try {
                method.invoke(inst);
                results.add(new ExecutedTest(method, Result.PASSED));
            } catch (InvocationTargetException e) {
                if (e.getCause() instanceof AssertionError) {
                    results.add(new ExecutedTest(method, Result.FAILED, e.getCause()));
                } else {
                    results.add(new ExecutedTest(method, Result.EXCEPTION, e.getCause()));
                }
            }
        }
        return results;
    }

    private void printResults(List<ExecutedTest> execResults, PrintStream out) {
        int passedCount = 0;
        int failedCount = 0;
        for (ExecutedTest et: execResults) {
            switch (et.result) {
                case EXCEPTION:
                    failedCount++;
                    out.println(String.format("Method '%s' failed with exception: %s", et.method.getName(), et.exception));
                    break;
                case PASSED:
                    passedCount++;
                    break;
                case FAILED:
                    out.println(String.format("Method '%s' didn't passed with failed assertion: %s", et.method.getName(), et.exception.getMessage()));
                    failedCount++;
                    break;
            }
        }

        out.println(String.format(RESULT_MSG, passedCount, failedCount));
    }

    public void doTest(String testClassName, PrintStream out) throws Exception {
        List<ExecutedTest> execResults = execTests(testClassName);
        printResults(execResults, out);

    }
}
