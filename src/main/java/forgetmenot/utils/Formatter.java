package forgetmenot.utils;

public final class Formatter {

    public static final int ID_LEN = 4;

    public static final int DESC_LEN = 60;

    public static final int STATUS_LEN = 11;

    public static final int DATETIME_LEN = 20;

    public static String padRight(String s, int length) {
        return padRight(s, length, ' ');
    }

    public static String padRight(String s, int length, char padchar) {
        if (length == 0) {
            return "";
        }
        if (s == null) {
            s = "";
        }
        if (s.length() >= length) {
            return s.substring(0, length);
        }
        return s + String.valueOf(padchar).repeat(length - s.length());
    }
}
