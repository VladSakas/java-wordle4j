package ru.yandex.practicum;

import ru.yandex.practicum.GameExceptions.DictionaryLoadException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class WordleDictionaryLoader {

    private final PrintWriter logger;

    public WordleDictionaryLoader(PrintWriter logger) {
        this.logger = logger;
    }

    public WordleDictionary load(String fileName) throws DictionaryLoadException {
        List<String> words = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName, StandardCharsets.UTF_8))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String word = WordUtils.formatWord(line);

                if (word.length() == 5) {
                    words.add(word);
                }
            }

            if (words.isEmpty()) {
                logger.println("ОШИБКА! В файле " + fileName + " нет слов из 5 букв!");
                throw new DictionaryLoadException("В файле " + fileName + " нет слов из 5 букв!");
            }

        } catch (IOException e) {
            logger.println("Ошибка загрузки словаря: " + e.getMessage());
            throw new DictionaryLoadException("Ошибка чтения файла: " + fileName, e);
        }

        return new WordleDictionary(words);
    }
}