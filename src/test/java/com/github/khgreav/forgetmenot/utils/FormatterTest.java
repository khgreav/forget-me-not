package com.github.khgreav.forgetmenot.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FormatterTest {

    @Test
    void testPadRightBasicPadding() {
        assertEquals("abc     ", Formatter.padRight("abc", 8));
    }

    @Test
    void testPadRightExactLength() {
        assertEquals("abcdef", Formatter.padRight("abcdef", 6));
    }

    @Test
    void testPadRightClampsLongString() {
        assertEquals("abcdef", Formatter.padRight("abcdefXYZ", 6));
    }

    @Test
    void testPadRightEmptyString() {
        assertEquals("     ", Formatter.padRight("", 5));
    }

    @Test
    void testPadRightZeroWidth() {
        assertEquals("", Formatter.padRight("abc", 0));
    }

    @Test
    void testPadRightNullString() {
        assertEquals("     ", Formatter.padRight(null, 5));
    }

    @Test
    void testPadRightNullStringZeroWidth() {
        assertEquals("", Formatter.padRight(null, 0));
    }

    @Test
    void testPadRightCustomCharPadding() {
        assertEquals("abc---", Formatter.padRight("abc", 6, '-'));
    }

    @Test
    void testPadRightCustomCharExactLength() {
        assertEquals("hello", Formatter.padRight("hello", 5, '.'));
    }

    @Test
    void testPadRightCustomCharClampsLongString() {
        assertEquals("hello", Formatter.padRight("hello world", 5, '.'));
    }

    @Test
    void testPadRightCustomCharEmptyString() {
        assertEquals("*****", Formatter.padRight("", 5, '*'));
    }

    @Test
    void testPadRightCustomCharNullString() {
        assertEquals("00000", Formatter.padRight(null, 5, '0'));
    }

    @Test
    void testPadRightCustomCharZeroWidth() {
        assertEquals("", Formatter.padRight("abc", 0, '.'));
    }
}
