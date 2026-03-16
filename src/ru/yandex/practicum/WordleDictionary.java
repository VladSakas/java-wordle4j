package ru.yandex.practicum;

import ru.yandex.practicum.GameExceptions.EmptyDictionaryException;

import java.util.List;
import java.util.Random;

public class WordleDictionary {

    private final List<String> words;
    private final Random random = new Random();

    public WordleDictionary(List<String> words) {
        this.words = List.copyOf(words);
    }

    public List<String> getWords() {
        return List.copyOf(words);
    }

    public boolean containsWord(String word) {
        String formattedWord = WordUtils.formatWord(word);
        return words.contains(formattedWord);
    }

    public String getRandomWord() {

        if (words.isEmpty()) {
            throw new EmptyDictionaryException("Невозможно выбрать слово. Словарь пуст.");
        }

        int randomIndex = random.nextInt(words.size());
        return words.get(randomIndex);
    }
}
