import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserInterfaceParserTest {

    // -------- LIST MODE TESTS --------

    @Test
    void nullArgs_treatedAsNoArgs_listMode() {
        DetermineUsage req = UserInterfaceParser.parse(null);
        assertEquals(DetermineUsage.Mode.LIST, req.getMode());
        assertNull(req.getFileNumber());
        assertNull(req.getKeyOverride());
    }

    @Test
    void noArgs_listMode() {
        DetermineUsage req = UserInterfaceParser.parse(new String[]{});
        assertEquals(DetermineUsage.Mode.LIST, req.getMode());
        assertNull(req.getFileNumber());
        assertNull(req.getKeyOverride());
    }

    // -------- SHOW MODE (1 ARG) --------

    @Test
    void oneArg_twoDigits_showMode_defaultKey() {
        DetermineUsage req = UserInterfaceParser.parse(new String[]{"01"});
        assertEquals(DetermineUsage.Mode.SHOW, req.getMode());
        assertEquals("01", req.getFileNumber());
        assertNull(req.getKeyOverride());
    }

    // -------- SHOW MODE (2 ARGS) --------

    @Test
    void twoArgs_showMode_withKey_executesValidateKey() {
        // This ensures validateKey() executes successfully
        DetermineUsage req =
                UserInterfaceParser.parse(new String[]{"01", "ciphers/key.txt"});

        assertEquals(DetermineUsage.Mode.SHOW, req.getMode());
        assertEquals("01", req.getFileNumber());
        assertEquals("ciphers/key.txt", req.getKeyOverride());
    }

    // -------- INVALID FILE NUMBER TESTS --------

    @Test
    void invalidFileNumber_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> UserInterfaceParser.parse(new String[]{"1"}));   // not 2 digits

        assertThrows(IllegalArgumentException.class,
                () -> UserInterfaceParser.parse(new String[]{"abc"})); // not digits

        assertThrows(IllegalArgumentException.class,
                () -> UserInterfaceParser.parse(new String[]{"0a"}));  // mixed
    }

    // -------- INVALID KEY TESTS --------

    @Test
    void blankKey_throws_executesValidateKey() {
        // Ensures validateKey() is entered and throws
        assertThrows(IllegalArgumentException.class,
                () -> UserInterfaceParser.parse(new String[]{"01", ""}));

        assertThrows(IllegalArgumentException.class,
                () -> UserInterfaceParser.parse(new String[]{"01", "   "}));
    }

    // -------- TOO MANY ARGS --------

    @Test
    void tooManyArgs_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> UserInterfaceParser.parse(new String[]{"01", "k", "x"}));
    }
}
