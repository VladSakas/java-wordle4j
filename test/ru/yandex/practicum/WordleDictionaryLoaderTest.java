package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WordleDictionaryLoaderTest {

    private WordleDictionaryLoader loader;
    private WordleDictionary dictionary;
    private PrintWriter logger;

    @BeforeEach
    public void setUp() {
        logger = new PrintWriter(System.out);
        loader = new WordleDictionaryLoader(logger);
        dictionary = loader.load("words_ru.txt");
    }

    @Test
    public void loaderShouldBeCreated() {
        assertNotNull(loader);
    }

    @Test
    public void loadShouldReturnNotNull() {
        assertNotNull(dictionary);
    }

    @Test
    public void loadShouldReturnNonEmptyDictionary() {
        assertFalse(dictionary.getWords().isEmpty());
    }
}