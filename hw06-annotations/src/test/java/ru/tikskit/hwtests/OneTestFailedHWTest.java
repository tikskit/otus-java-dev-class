package ru.tikskit.hwtests;

import ru.tikskit.testframework.annotations.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OneTestFailedHWTest {
    @Test
    public void doFailingTest() {
        assertTrue(false);
    }

}
