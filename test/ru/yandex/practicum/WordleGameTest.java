package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.PrintWriter;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class WordleGameTest {

    private WordleGame game;
    private PrintWriter logger;

    @BeforeEach
    public void setUp() {
        List<String> words = List.of("герой", "мышка", "город");
        WordleDictionary dictionary = new WordleDictionary(words);
        logger = new PrintWriter(System.out);
        game = new WordleGame(dictionary, logger);
    }

    @Test
    public void shouldAcceptValidWord() {
        String result = game.playGame("город");
        assertEquals(5, result.length());
    }

    @Test
    public void shouldRejectInvalidWord() {
        assertThrows(GameExceptions.InvalidWordLengthException.class,
                () -> game.playGame("кот"));
    }

    @Test
    public void shouldReturnSuggestion() {
        String suggestion = game.getComputerSuggestion();
        assertNotNull(suggestion);
        assertEquals(5, suggestion.length());
    }

    @Test
    public void shouldDecreaseStepsAfterGuess() {
        int stepsBefore = game.getStepsLeft();
        game.playGame("город");
        assertEquals(stepsBefore - 1, game.getStepsLeft());
    }
}