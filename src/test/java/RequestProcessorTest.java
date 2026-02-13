import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class RequestProcessorTest {

    @BeforeEach
    void setup() throws Exception {
        // Ensure folders exist
        new File("data").mkdirs();
        new File("ciphers").mkdirs();

        // Clean data/
        File[] dataFiles = new File("data").listFiles();
        if (dataFiles != null) for (File f : dataFiles) f.delete();

        // Write default key
        Files.writeString(Path.of("ciphers/key.txt"),
                "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890\n" +
                        "bcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890a\n");

        // Put ciphered content in filea.txt so default decipher produces readable output
        Files.writeString(Path.of("data/filea.txt"), "Ifmmp gspn gjmf B");
    }

    @Test
    void listMode_outputsNumberedFiles() throws Exception {
        DetermineUsage req = new DetermineUsage(DetermineUsage.Mode.LIST, null, null);
        String out = RequestProcessor.run(req);
        assertTrue(out.contains("01"));
        assertTrue(out.contains("filea.txt"));
    }

    @Test
    void showMode_defaultKey_deciphers() throws Exception {
        DetermineUsage req = new DetermineUsage(DetermineUsage.Mode.SHOW, "01", null);
        String out = RequestProcessor.run(req);
        assertTrue(out.contains("Hello from file A"));
    }

    @Test
    void showMode_invalidFileNumber_throws() {
        DetermineUsage req = new DetermineUsage(DetermineUsage.Mode.SHOW, "99", null);
        assertThrows(IllegalArgumentException.class, () -> RequestProcessor.run(req));
    }

    @Test
    void showMode_keyOverride_missing_throws() {
        DetermineUsage req = new DetermineUsage(DetermineUsage.Mode.SHOW, "01", "ciphers/nope.txt");
        assertThrows(IllegalArgumentException.class, () -> RequestProcessor.run(req));
    }}

