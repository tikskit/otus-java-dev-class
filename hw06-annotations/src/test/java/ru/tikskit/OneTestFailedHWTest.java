package ru.tikskit;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OneTestFailedHWTest {
    @Test
    public void doFailingTest() {
        assertTrue(false);
    }

}
