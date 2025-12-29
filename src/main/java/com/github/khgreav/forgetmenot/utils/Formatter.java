package com.github.khgreav.forgetmenot.utils;

/**
 * Utility class for string formatting.
 */
public final class Formatter {

    private Formatter() {
        throw new AssertionError("Cannot instantiate.");
    }

    /**
     * Pads the input string on the right with spaces or truncates it to fit the specified length.
     * @param s Input string
     * @param length Desired length
     * @return Padded or truncated string
     */
    public static String padRight(String s, int length) {
        return padRight(s, length, ' ');
    }

    /**
     * Pads the input string on the right with the specified character or truncates it to fit the specified length.
     * @param s Input string
     * @param length Desired length
     * @param padchar Character to use for padding
     * @return Padded or truncated string
     */
    public static String padRight(String s, int length, char padchar) {
        if (s == null) {
            s = "";
        }
        if (s.length() >= length) {
            return s.substring(0, length);
        }
        return s + String.valueOf(padchar).repeat(length - s.length());
    }
}
