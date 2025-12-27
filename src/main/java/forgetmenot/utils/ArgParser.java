package forgetmenot.utils;

import java.lang.AssertionError;

public final class ArgParser {

    private ArgParser() {
        throw new AssertionError("Cannot instantiate ArgParser.");
    }

    public static void validateArgCount(int count, int min, int max) {
        if (count < min || count > max) {
            throw new IllegalArgumentException("Invalid number of command arguments.");
        }
    }

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
