package forgetmenot.utils;

import java.util.Map;

public final class MapUtils {

    private MapUtils() {
        throw new AssertionError("Cannot instantiate.");
    }

    public static <T> T getProperty(Map<String, Object> map, String key, Class<T> type) {
        Object obj = map.get(key);
        if (!type.isInstance(obj)) {
            throw new IllegalArgumentException("Missing or invalid key: " + key);
        }
        return type.cast(obj);
    }
}
