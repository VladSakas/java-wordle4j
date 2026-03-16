package ru.yandex.practicum;

import org.junit.jupiter.api.Test;
import java.io.PrintWriter;
import static org.junit.jupiter.api.Assertions.*;

class WordleTest {

    @Test
    public void loggerShouldBeCreated() {
        PrintWriter logger = new PrintWriter(System.out);
        assertNotNull(logger);
    }
}