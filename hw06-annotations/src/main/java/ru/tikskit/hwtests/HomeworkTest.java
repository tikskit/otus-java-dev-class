package ru.tikskit.hwtests;

import ru.tikskit.testframework.annotations.After;
import ru.tikskit.testframework.annotations.Before;
import ru.tikskit.testframework.annotations.Test;

public class HomeworkTest {

    @Before
    public void setUp0() {
        System.out.println("set up 0");
    }

    @Before
    public void setUp1() {
        System.out.println("set up 1");
    }

    @After
    public void tearDown() {
        System.out.println("tear down");
    }

    @Test
    public void test1() {
        throw new AssertionError("Условие не выполняется");
    }

    @Test
    public void test2() {
        throw new UnsupportedOperationException("Метод не поддерживается");
    }

    @Test
    public void test3() {
        throw new AssertionError();
    }

    @Test
    public void test4() {

    }

}
