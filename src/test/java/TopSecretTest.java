
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class TopSecretTest {

    @BeforeEach
    void setup() throws Exception {
        new File("data").mkdirs();
        new File("ciphers").mkdirs();

        // Don't delete files (Windows file locks). Just ensure at least one known file exists.
        Files.writeString(Path.of("data/topsecret_test_file.txt"), "Ifmmp gspn gjmf B\n");

        Files.writeString(Path.of("ciphers/key.txt"),
                "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890\n" +
                        "bcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890a\n");
    }

    @Test
    void main_noArgs_printsFileList() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(baos));
        try {
            TopSecret.main(new String[]{});
        } finally {
            System.setOut(oldOut);
        }

        String out = baos.toString();
        assertTrue(out.contains("topsecret_test_file.txt"));
    }

    @Test
    void main_invalidArgs_printsUsage() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(baos));
        try {
            TopSecret.main(new String[]{"1"}); // invalid: not two digits
        } finally {
            System.setOut(oldOut);
        }

        String out = baos.toString();
        assertTrue(out.contains("USAGE:"));
        assertTrue(out.toLowerCase().contains("error"));
    }
}

