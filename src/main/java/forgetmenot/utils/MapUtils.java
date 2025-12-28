package forgetmenot.utils;

import java.util.Map;

/**
 * Utility class for Map operations.
 */
public final class MapUtils {

    private MapUtils() {
        throw new AssertionError("Cannot instantiate.");
    }

    /**
     * Retrieves a property from a map with type safety.
     * @param map Map containing the properties
     * @param key Key of the property to retrieve
     * @param type Expected type of the property
     * @param <T> Type parameter
     * @return Property value cast to the expected type
     * @throws IllegalArgumentException if the key is missing or the value is of incorrect type
     */
    public static <T> T getProperty(Map<String, Object> map, String key, Class<T> type) {
        Object obj = map.get(key);
        if (!type.isInstance(obj)) {
            throw new IllegalArgumentException("Missing or invalid key: " + key);
        }
        return type.cast(obj);
    }
}
