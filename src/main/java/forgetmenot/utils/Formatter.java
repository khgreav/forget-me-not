package forgetmenot.utils;

public final class Formatter {

    public static String padRight(String s, int length) {
        return padRight(s, length, ' ');
    }

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
