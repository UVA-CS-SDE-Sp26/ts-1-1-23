import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import Homework2.TopSecret;

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



//    @Test
//    void main_triggersGenericCatch_byBreakingDataDirectory() throws Exception {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        PrintStream oldOut = System.out;
//        System.setOut(new PrintStream(baos));
//
//        Path dataPath = Path.of("data");
//        Path backupDir = Path.of("Homework2");
//
//        try {
//            // If data/ exists and has files, move it out of the way (no deletion).
//            if (Files.exists(dataPath) && Files.isDirectory(dataPath)) {
//                Files.createDirectories(backupDir);
//                // Move the directory itself if possible
//                // (If it fails on Windows, we fall back to skipping)
//                try {
//                    Files.move(dataPath, backupDir.resolve("data"), StandardCopyOption.REPLACE_EXISTING);
//                } catch (Exception moveFail) {
//                    // If we can't safely move it, skip so we don't break your project
//                    return;
//                }
//            } else if (Files.exists(dataPath)) {
//                // If it's already a file, delete it so we can recreate cleanly
//                Files.deleteIfExists(dataPath);
//            }
//
//            // Create a FILE named "data" (not a directory)
//            Files.writeString(dataPath, "not a directory");
//
//            // Run main with no args -> LIST mode -> NPE inside Core.FileOpenerImplementation -> generic catch
//            TopSecret.main(new String[]{});
//
//        } finally {
//            System.setOut(oldOut);
//
//            // Cleanup: remove our "data" file
//            try { Files.deleteIfExists(dataPath); } catch (Exception ignored) {}
//
//            // Restore original data directory if we moved it
//            Path movedDataDir = backupDir.resolve("data");
//            if (Files.exists(movedDataDir) && Files.isDirectory(movedDataDir)) {
//                try {
//                    Files.move(movedDataDir, dataPath, StandardCopyOption.REPLACE_EXISTING);
//                } catch (Exception ignored) {}
//            }
//        }
//
//        String out = baos.toString();
//        assertTrue(out.contains("Error:"));
//    }

}

