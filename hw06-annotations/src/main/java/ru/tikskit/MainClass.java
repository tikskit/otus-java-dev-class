package ru.tikskit;

import ru.tikskit.testframework.Tester;

public class MainClass {
    public static void main(String[] args) {
        try {
            new Tester().doTest("ru.tikskit.hwtests.HomeworkTest", System.out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
