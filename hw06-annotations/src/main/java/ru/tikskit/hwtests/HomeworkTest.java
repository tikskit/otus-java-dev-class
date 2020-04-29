package ru.tikskit.hwtests;

import ru.tikskit.testframework.annotations.After;
import ru.tikskit.testframework.annotations.Before;
import ru.tikskit.testframework.annotations.Test;

public class HomeworkTest {
    private static int setUpCounter = 0;

    @Before
    public void setUp0() throws Exception {
        every3dThrowsException();
        System.out.println("set up 0");
    }

    @Before
    public void setUp1() throws Exception {
        every3dThrowsException();
        System.out.println("set up 1");
    }

    private void every3dThrowsException() {
        if (setUpCounter++ % 4 == 1) {
            throw new UnsupportedOperationException("thrown exception");
        }
    }

    @After
    public void tearDown() {
        System.out.println("tear down");
    }

    @Test
    public void test1() {
        /*throw new AssertionError("Условие не выполняется");*/
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
