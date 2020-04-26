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

    public List<TestResult> invoke() throws  IllegalAccessException, InvocationTargetException, TestInstantiateException {
        if (!isEmpty()) {

            List<TestResult> res = new ArrayList<>();

            for (Method test : tests) {
                Object inst;
                try {
                    inst = constructor.newInstance();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    throw new TestInstantiateException(e);
                }

                try {
                    invokeBefores(inst);
                } catch (InvocationTargetException | IllegalAccessException e) {
                    res.add(new TestResult(test, TestResult.Result.SETUP_EXCEPTION, e));
                    return res;
                }
                try {
                    res.add(invokeTest(inst, test));
                } finally {
                    invokeAfters(inst);
                }
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

    private TestResult invokeTest(Object instance, Method test) throws IllegalAccessException {
        try {
            test.invoke(instance);
            return new TestResult(test, TestResult.Result.PASSED);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof AssertionError) {
                return new TestResult(test, TestResult.Result.FAILED, e.getCause());
            } else {
                return new TestResult(test, TestResult.Result.TEST_EXCEPTION, e.getCause());
            }
        }
    }


}
