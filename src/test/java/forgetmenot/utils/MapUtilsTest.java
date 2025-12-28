package forgetmenot.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class MapUtilsTest {

    private static final HashMap<String, Object> MAP = new HashMap<>(
        Map.of(
            "integer", 1,
            "string", "1",
            "boolean", true
        )
    );

    @Test
    void testGetProperty() {
        assertEquals(1, MapUtils.getProperty(MAP, "integer", Integer.class));
        assertEquals("1", MapUtils.getProperty(MAP, "string", String.class));
        assertEquals(true, MapUtils.getProperty(MAP, "boolean", Boolean.class));
    }

    @Test
    void testGetPropertyInvalid() {
        assertThrows(
            IllegalArgumentException.class,
            () -> {
                MapUtils.getProperty(MAP, "missing", Integer.class);
            }
        );
        assertThrows(
            IllegalArgumentException.class,
            () -> {
                MapUtils.getProperty(MAP, "string", Integer.class);
            }
        );
    }
}
