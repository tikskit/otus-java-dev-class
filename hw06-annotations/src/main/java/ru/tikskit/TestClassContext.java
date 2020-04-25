package ru.tikskit;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class TestClassContext {
    // Пользователь мог поставить аннотацию @Before на несколько методов
    private final Set<Method> befores = new HashSet<>();
    // Пользователь мог поставить аннотацию @After на несколько методов
    private final Set<Method> afters = new HashSet<>();
    private final Set<Method> tests = new HashSet<>();
    private final Constructor<?> constructor;

    public TestClassContext(Constructor<?> constructor) {
        this.constructor = constructor;
    }

    public void addBefore(Method before) {
        befores.add(before);
    }

    public void addAfter(Method after) {
        afters.add(after);
    }

    public void addTest(Method test) {
        tests.add(test);
    }

    public boolean isEmpty() {
        return tests.isEmpty();
    }

    public List<TestResult> invoke() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        if (!isEmpty()) {
            Object inst = constructor.newInstance();
            List<TestResult> res;

            invokeBefores(inst);
            try {
                res = invokeTests(inst);
            } finally {
                invokeAfters(inst);
            }

            return res;
        } else {
            return null;
        }

    }

    private void invokeBefores(Object instance) throws InvocationTargetException, IllegalAccessException {
        for (Method before : befores) {
            before.invoke(instance);
        }
    }

    private void invokeAfters(Object instance) throws InvocationTargetException, IllegalAccessException {
        for (Method afters : afters) {
            afters.invoke(instance);
        }
    }

    private List<TestResult> invokeTests(Object instance) throws IllegalAccessException {
        List<TestResult> res = new ArrayList<>();
        for (Method test : tests) {
            try {
                test.invoke(instance);
                res.add(new TestResult(test, TestResult.Result.PASSED));
            } catch (InvocationTargetException e) {
                if (e.getCause() instanceof AssertionError) {
                    res.add(new TestResult(test, TestResult.Result.FAILED, e.getCause()));
                } else {
                    res.add(new TestResult(test, TestResult.Result.EXCEPTION, e.getCause()));
                }
            }

        }

        return res;
    }


}
