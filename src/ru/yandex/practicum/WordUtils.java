package ru.yandex.practicum;

public class WordUtils {

    private WordUtils() {
    }

    public static String formatWord(String word) {
        if (word == null || word.isEmpty()) return "";
        return word.trim()
                .toLowerCase()
                .replace('ё', 'е');
    }
}
