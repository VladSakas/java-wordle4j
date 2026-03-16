package ru.yandex.practicum;

import ru.yandex.practicum.GameExceptions.DictionaryLoadException;
import ru.yandex.practicum.GameExceptions.GameAlreadyOverException;
import ru.yandex.practicum.GameExceptions.InvalidWordLengthException;
import ru.yandex.practicum.GameExceptions.WordNotFoundException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Wordle {

    public static void main(String[] args) {
        try (PrintWriter logger = new PrintWriter(new FileWriter("game.log", true))) {
            logger.println("=== Новая игра началась ===");

            WordleDictionaryLoader loader = new WordleDictionaryLoader(logger);
            WordleDictionary dictionary;

            try {
                dictionary = loader.load("words_ru.txt");
            } catch (DictionaryLoadException e) {
                System.out.println("Ошибка загрузки словаря: " + e.getMessage());
                logger.println("Ошибка загрузки словаря: " + e.getMessage());
                return;
            }

            logger.println("Загружено слов: " + dictionary.getWords().size());

            WordleGame game = new WordleGame(dictionary, logger);
            logger.println("Загаданное слово: " + game.getAnswer());

            Scanner scanner = new Scanner(System.in);
            System.out.println("Игра Wordle началась!");
            System.out.printf("Угадайте слово из %s букв. У вас %s попыток!\n",
                    game.getAnswer().length(), game.getStepsLeft());
            System.out.println("[ENTER] - подсказка");

            while (!game.isGameOver()) {
                System.out.printf("У вас %s попыток!\n", game.getStepsLeft());
                logger.printf("Осталось %s попыток.\n", game.getStepsLeft());
                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    String suggestion = game.getComputerSuggestion();
                    System.out.println("Подсказка: " + suggestion);
                    logger.println("Подсказка: " + suggestion);
                    continue;
                }

                try {
                    String result = game.playGame(input);
                    System.out.println("Результат: " + result);
                    logger.println("Ход: " + input + ", результат: " + result);
                } catch (GameAlreadyOverException e) {
                    System.out.println("Игра уже закончена!");
                    logger.println("Ошибка! Игра уже закончена. " + e.getMessage());
                    break;
                } catch (WordNotFoundException | InvalidWordLengthException e) {
                    System.out.println("Ошибка ввода: " + e.getMessage());
                    logger.println("Ошибка ввода: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Неизвестная ошибка: " + e.getMessage());
                    logger.println("Неизвестная ошибка: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            if (game.isWin()) {
                System.out.println("Поздравляем! Вы угадали слово " + game.getAnswer() + "!");
                logger.println("ИГРОК ВЫИГРАЛ! Слово: " + game.getAnswer());
            } else {
                System.out.println("Игра окончена. Загаданное слово: " + game.getAnswer());
                logger.println("ИГРОК ПРОИГРАЛ! Слово: " + game.getAnswer());
            }

            System.out.println(game.getGameHistory());
            logger.println(game.getGameHistory());

        } catch (IOException e) {
            System.out.println("Ошибка при создании лог-файла: " + e.getMessage());
            e.printStackTrace();
        }

    }

}