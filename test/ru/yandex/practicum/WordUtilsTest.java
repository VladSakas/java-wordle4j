package ru.yandex.practicum;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordUtilsTest {

    @Test
    public void testFormatWords_shouldReplaceYo() {
        assertEquals("елка", WordUtils.formatWord("ЁлкА"));
        assertEquals("еееее", WordUtils.formatWord("ЁёеЁЕ"));
    }

    @Test
    public void testFormatWords_shouldLowerCase() {
        assertEquals("совок", WordUtils.formatWord("СОВОк"));
    }

    @Test
    public void testFormatWords_shouldTrim() {
        assertEquals("поток", WordUtils.formatWord("        поток "));
    }

    @Test
    public void testFormatWords_shouldTrimEmptyString() {
        assertEquals("", WordUtils.formatWord(""));
    }

    @Test
    public void testFormatWords_shouldTrimOnlySpaces() {
        assertEquals("", WordUtils.formatWord("             "));
    }

    @Test
    public void testFormatWords_shouldHandleNull() {
        assertEquals("", WordUtils.formatWord(null));
    }

}