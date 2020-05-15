package ru.tikskit.business;

import ru.tikskit.instrumentation.annotations.Log;

public class Toyota implements Car {

    @Override
    @Log
    public void meth1(int p1, char p2) {
        System.out.println("meth1(int p1, char p2) is called!");
    }

    @Override
    @Log
    public void meth1(int p1, boolean p2) {
        System.out.println("meth1(int p1, boolean p2) is called!");
    }

    @Override
    public void meth2() {
        System.out.println("meth2() is called!");
    }

}
