package ru.tikskit;

import ru.tikskit.business.Car;
import ru.tikskit.business.Toyota;
import ru.tikskit.instrumentation.ClassFactory;

public class MainClass {
    public static void main(String args[]) {
        Car toyota = ClassFactory.createToyota();
        toyota.meth1(10, 'a');
        toyota.meth1(20, 'b');
        toyota.meth1(10, false);
        toyota.meth2();

    }
}
