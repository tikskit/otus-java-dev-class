package ru.tikskit;

public class MainClass {
    public static void main(String[] args) {
        try {
            new Tester().doTest("ru.tikskit.HomeworkTest", System.out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
