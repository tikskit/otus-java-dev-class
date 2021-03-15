package ru.tikskit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class MainClass {
    private static final Logger logger = LoggerFactory.getLogger(MainClass.class);


    private static class Counter {
        private static final int MIN_VALUE = 1;
        private static final int MAX_VALUE = 10;

        private Thread lastUpdater = null;
        private int value = 0;
        private int increment = 1;

        public void update() {
            value += increment;

            if (lastUpdater != null && ((value == MIN_VALUE) || (value == MAX_VALUE))) {
                increment *= -1;
            }
            lastUpdater = Thread.currentThread();
        }

        public Thread getLastUpdater() {
            return lastUpdater;
        }
    }

    private static class Processor {
        private final Counter counter = new Counter();

        public synchronized void doJob(CountDownLatch latch) {
            latch.countDown(); // Первый поток гарантировано зашел в синхронизированную секцию до второго
            while (!Thread.currentThread().isInterrupted()) {
                counter.update();
                logger.info("Thread {} set value to {}", Thread.currentThread().getName(), counter.value);
                sleep();
                notifyAll();
                do {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                } while (counter.getLastUpdater() == Thread.currentThread());
            }

        }

        private void sleep() {
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(1));
            } catch (InterruptedException e) {
                logger.info(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }



    public static void main(String... args) {
        Processor processor = new Processor();
        CountDownLatch latch = new CountDownLatch(1);

        Thread t1 = new Thread(() -> {
            logger.info("{} starts", Thread.currentThread().getName());
            processor.doJob(latch);
        }, "Thread 1");

        Thread t2 = new Thread(() -> {
            logger.info("{} starts", Thread.currentThread().getName());
            try {
                latch.await(); // ждет, когда кто-нибудь другой снимет блокировку
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            processor.doJob(latch);
        }, "Thread 2");

        t1.start();
        t2.start();
    }


}
