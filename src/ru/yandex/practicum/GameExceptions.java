package ru.yandex.practicum;

public class GameExceptions {

    public static class GameAlreadyOverException extends IllegalStateException {
        public GameAlreadyOverException(String message) {
            super(message);
        }
    }

    public static class WordNotFoundException extends IllegalArgumentException {
        public WordNotFoundException(String message) {
            super(message);
        }
    }

    public static class InvalidWordLengthException extends IllegalArgumentException {
        public InvalidWordLengthException(String message) {
            super(message);
        }
    }

    public static class EmptyDictionaryException extends IllegalStateException {
        public EmptyDictionaryException(String message) {
            super(message);
        }
    }

    public static class DictionaryLoadException extends RuntimeException {
        public DictionaryLoadException(String message) {
            super(message);
        }

        public DictionaryLoadException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}