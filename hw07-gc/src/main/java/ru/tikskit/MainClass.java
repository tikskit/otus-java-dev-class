package ru.tikskit;

import java.util.ArrayList;
import java.util.List;

public class MainClass {

    public static void main(String[] args) {
        stuffMemory();
    }


/*
* Эксперимент 1:
-Xms512m
-Xmx512m
-XX:+UseParallelGC

Произошло 3 сборки PS Scavenge (young gen), что заняло 0.144 секунд
Произошло 8 сборок PS MarkSweep (old gen), что заняло 1.646 секунд

Эксперимент 2:
-Xms512m
-Xmx512m
-XX:+UseG1GC

Произошло 52 сборок G1 Young Generation, что заняло 0.887 секунд
Проищошло 21 сборок G1 Old Generation, что заняло 4.385 секунд

Эксперимент 3:
-Xms256m
-Xmx256m
-XX:+UseParallelGC

Произошло 3 сборки PS Scavenge (young gen), что заняло 0.128 секунд
Произошло 5 сборок PS MarkSweep (old gen), что заняло 0.717 секунд

Эксперимент 4:
-Xms256m
-Xmx256m
-XX:+UseG1GC
* */

    private static void stuffMemory() {
        final long sleep_512_G1 = 10;
        final long sleep_512_Parallel = 40;
        final long sleep_256_Parallel = 230;
        final long sleep_256_G1 = 200;
        final long sleep_33_423_Parallel = 1;
//        final long sleep_33_423_G1 = 200;

        final int OBJECTS_COUNT = 1000_000_000;
        List<DataObject> data = new ArrayList<>();
        int i = 0;
        try {
            while (i < OBJECTS_COUNT) {

                data.add(new DataObject());

                if (i % 10000 == 0) {
                    /*Удаляем из списка каждый N объект, чтобы удалялись
                    * как старые, так и новые объекты */
                    for (int r = 0, step = 300; r < data.size(); r += step) {
                        data.remove(r);
                    }
                }
                if (i % 1000 == 0) {
                    Thread.sleep(sleep_33_423_Parallel);
                }
                i++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
