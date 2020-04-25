package ru.tikskit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class TesterTest {

    private Tester tester;
    private ByteArrayOutputStream out;

    @BeforeEach
    void setUp() {
        tester = new Tester();
        out = new ByteArrayOutputStream();
    }

    @DisplayName("Должно печатать: \"Tests passed: 1, tests failed: 0, total: 1\" и не выкидывать исключений")
    @Test
    void oneTestPassed() {
        assertDoesNotThrow(() -> tester.doTest("ru.tikskit.OneTestPassedHWTest", new PrintStream(out)));
        assertEquals(String.format(Tester.RESULT_MSG, 1, 0, 1), out.toString().stripTrailing());
    }

    @DisplayName("Должно печатать: \"Tests passed: 0, tests failed: 1, total: 1\" и не выкидывать исключений")
    @Test
    void oneTestFailed() {
        assertDoesNotThrow(() -> tester.doTest("ru.tikskit.OneTestFailedHWTest", new PrintStream(out)));

        String expected = String.format(Tester.RESULT_MSG, 0, 1, 1);
        String resultMSG = out.toString().stripTrailing();
        assertTrue(resultMSG.length() >= expected.length());
        resultMSG = resultMSG.substring(resultMSG.length() - expected.length(), resultMSG.length() - expected.length() + expected.length());
        assertEquals(expected, resultMSG);

    }

    @DisplayName("Должно печатать: \"Tests passed: 1, tests failed: 1, total: 2\" и не выкидывать исключений")
    @Test
    void oneTestPassedOneFailed() {
        assertDoesNotThrow(() -> tester.doTest("ru.tikskit.OneTestPassedOneFailedHWTest", new PrintStream(out)));

        String expected = String.format(Tester.RESULT_MSG, 1, 1, 2);
        String resultMSG = out.toString().stripTrailing();
        assertTrue(resultMSG.length() >= expected.length());
        resultMSG = resultMSG.substring(resultMSG.length() - expected.length(), resultMSG.length() - expected.length() + expected.length());
        assertEquals(expected, resultMSG);

    }
}