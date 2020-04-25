package ru.tikskit;

public class HomeworkTest {
    public HomeworkTest() {
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
