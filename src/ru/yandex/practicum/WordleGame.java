package ru.yandex.practicum;

import ru.yandex.practicum.GameExceptions.EmptyDictionaryException;
import ru.yandex.practicum.GameExceptions.GameAlreadyOverException;
import ru.yandex.practicum.GameExceptions.InvalidWordLengthException;
import ru.yandex.practicum.GameExceptions.WordNotFoundException;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordleGame {

    private final String answer;
    private int stepsLeft;
    private final WordleDictionary dictionary;
    private List<String> guesses; //введенные слова
    private List<String> hints; //подсказки
    private boolean isWin;
    private boolean isGameOver;
    private final PrintWriter logger;

    public WordleGame(WordleDictionary dictionary, PrintWriter logger) throws EmptyDictionaryException {
        if (dictionary == null || dictionary.getWords().isEmpty()) {
            throw new EmptyDictionaryException("Словарь не может быть пустым");
        }
        this.dictionary = dictionary;
        this.logger = logger;
        this.answer = dictionary.getRandomWord();
        this.stepsLeft = 6;
        this.guesses = new ArrayList<>();
        this.hints = new ArrayList<>();
        this.isWin = false;
        this.isGameOver = false;
    }

    private String analyzeGuess(String input) throws InvalidWordLengthException {

        if (input.length() != 5) {
            throw new InvalidWordLengthException("Слова должны состоять из 5 букв!");
        }

        StringBuilder result = new StringBuilder("-".repeat(input.length()));

        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == answer.charAt(i)) {
                result.setCharAt(i, '+');
            }
        }

        for (int i = 0; i < input.length(); i++) {

            if (result.charAt(i) == '+') continue;

            for (int j = 0; j < input.length(); j++) {

                if (j == i || result.charAt(j) == '+') continue;

                if (input.charAt(i) == answer.charAt(j)) {
                    result.setCharAt(i, '^');
                    break;
                }
            }
        }

        return result.toString();
    }

    public String playGame(String word)
            throws GameAlreadyOverException, WordNotFoundException, InvalidWordLengthException {

        if (isGameOver || isWin) {
            logger.println("Ошибка! Игра уже закончена!");
            throw new GameAlreadyOverException("Игра уже закончена!");
        }

        String formattedWord = WordUtils.formatWord(word);

        if (formattedWord.length() != 5) {
            logger.println("Ошибка! Слово " + word + " должно состоять из 5 букв!");
            throw new InvalidWordLengthException("Слово должно состоять из 5 букв!");
        }

        if (!dictionary.containsWord(formattedWord)) {
            logger.println("Ошибка! Слово " + word + " не найдено в словаре");
            throw new WordNotFoundException("Слово " + word + " не найдено в словаре!");
        }

        guesses.add(formattedWord);

        String hint = analyzeGuess(formattedWord);
        hints.add(hint);
        stepsLeft--;

        logger.println("Ход: " + formattedWord + " -> " + hint + " (осталось " + stepsLeft + " попыток)");

        if (stepsLeft == 0) {
            isGameOver = true;
            logger.println("Попытки закончились. Загаданное слово: " + answer);
        }

        if (formattedWord.equals(answer)) {
            isWin = true;
            isGameOver = true;
            logger.println("ИГРОК ВЫИГРАЛ! Слово: " + answer);
        }

        return hint;
    }

    public String getComputerSuggestion() {
        if (guesses.isEmpty()) {
            return dictionary.getRandomWord();
        }

        Set<Character> badLetters = new HashSet<>();
        Set<Character> goodLetters = new HashSet<>();

        for (int i = 0; i < guesses.size(); i++) {
            String word = guesses.get(i);
            String hint = hints.get(i);

            for (int j = 0; j < hint.length(); j++) {
                char letter = word.charAt(j);
                char hintChar = hint.charAt(j);

                if (hintChar == '+' || hintChar == '^') {
                    goodLetters.add(letter);
                } else if (hintChar == '-') {
                    badLetters.add(letter);
                }
            }
        }

        for (String word : dictionary.getWords()) {
            if (guesses.contains(word)) continue;

            boolean hasBadLetter = false;
            for (char bad : badLetters) {
                if (word.indexOf(bad) >= 0) {
                    hasBadLetter = true;
                    break;
                }
            }
            if (hasBadLetter) continue;

            boolean hasAllGood = true;
            for (char good : goodLetters) {
                if (word.indexOf(good) < 0) {
                    hasAllGood = false;
                    break;
                }
            }
            if (!hasAllGood) continue;

            return word;
        }

        return dictionary.getRandomWord();
    }

    public String getGameHistory() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ИСТОРИЯ ИГРЫ ===\n");

        for (int i = 0; i < guesses.size(); i++) {
            sb.append(" ").append(i + 1).append(". ")
                    .append(guesses.get(i)).append(" : ")
                    .append(hints.get(i)).append("\n");
        }

        sb.append("Осталось попыток: ").append(stepsLeft);
        return sb.toString();
    }

    public int getStepsLeft() {
        return stepsLeft;
    }

    public boolean isWin() {
        return isWin;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public String getAnswer() {
        return answer;
    }
}