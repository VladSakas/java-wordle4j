package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class WordleDictionaryTest {

    private WordleDictionary dictionary;

    @BeforeEach
    public void setUp() {
        List<String> words = new ArrayList<>();
        words.add("терка");
        words.add("вахта");
        words.add("кухня");
        dictionary = new WordleDictionary(words);
    }

    @Test
    public void shouldFindExistingWord() {
        assertTrue(dictionary.containsWord("вахта"));
    }

    @Test
    public void shouldFindWordWithYo() {
        assertTrue(dictionary.containsWord("тёрка"));
    }

    @Test
    public void shouldFindWordWithSpacesAndUpperCase() {
        assertTrue(dictionary.containsWord("        КУХНЯ    "));
    }

    @Test
    public void shouldNotFindMissingWord() {
        assertFalse(dictionary.containsWord("кот"));
    }
}