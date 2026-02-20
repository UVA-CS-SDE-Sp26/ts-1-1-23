import org.junit.jupiter.api.Test;

import Homework2.DetermineUsage;

import static org.junit.jupiter.api.Assertions.*;

public class DetermineUsageTest {

    @Test
    void getters_returnConstructorValues() {
        DetermineUsage d = new DetermineUsage(DetermineUsage.Mode.SHOW, "01", "k");
        assertEquals(DetermineUsage.Mode.SHOW, d.getMode());
        assertEquals("01", d.getFileNumber());
        assertEquals("k", d.getKeyOverride());
    }

    @Test
    void listMode_allowsNulls() {
        DetermineUsage d = new DetermineUsage(DetermineUsage.Mode.LIST, null, null);
        assertEquals(DetermineUsage.Mode.LIST, d.getMode());
        assertNull(d.getFileNumber());
        assertNull(d.getKeyOverride());
    }
}

