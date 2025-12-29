package com.github.khgreav.forgetmenot.utils;

import java.lang.AssertionError;

/**
 * Utility class for command-line argument parsing and validation.
 */
public final class CliArgUtils {

    private CliArgUtils() {
        throw new AssertionError("Cannot instantiate ArgParser.");
    }

    /**
     * Validates the number of command arguments.
     * @param count Actual argument count
     * @param min Minimum expected count
     * @param max Maximum expected count
     * @throws IllegalArgumentException if the argument count is outside the expected range
     */
    public static void validateArgCount(int count, int min, int max) {
        if (count < min || count > max) {
            throw new IllegalArgumentException("Invalid number of command arguments.");
        }
    }

    /**
     * Parses and validates a task ID from string.
     * <p>
     * The ID must be a non-zero positive integer.
     * </p>
     * @param val String value representing the task ID
     * @return Parsed task ID as an integer
     * @throws IllegalArgumentException if the value is not a valid positive integer
     */
    public static int parseId(String val) throws IllegalArgumentException {
        try {
            int id = Integer.parseInt(val);
            if (id < 1) {
                throw new IllegalArgumentException("ID argument value should be non-zero positive integer.");
            }
            return id;
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid ID argument value.", e);
        }
    }
}
