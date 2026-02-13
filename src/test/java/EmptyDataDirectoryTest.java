import org.junit.jupiter.api.*;
import java.io.File;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EmptyDataDirectoryTest {

    private final Path dataDir = Path.of("data");
    private final Path backupDir = Path.of("data_backup_for_test");
    private final List<Path> movedFiles = new ArrayList<>();

    @BeforeEach
    void backupDataFiles() throws Exception {
        Files.createDirectories(dataDir);
        Files.createDirectories(backupDir);

        // Move all files from data/ -> backup/
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dataDir)) {
            for (Path p : stream) {
                if (Files.isRegularFile(p)) {
                    Path dest = backupDir.resolve(p.getFileName());
                    Files.move(p, dest, StandardCopyOption.REPLACE_EXISTING);
                    movedFiles.add(dest);
                }
            }
        }
    }

    @AfterEach
    void restoreDataFiles() throws Exception {
        // Move everything back from backup/ -> data/
        for (Path pInBackup : movedFiles) {
            Path dest = dataDir.resolve(pInBackup.getFileName());
            // Best effort restore (won’t throw if already restored)
            if (Files.exists(pInBackup)) {
                Files.move(pInBackup, dest, StandardCopyOption.REPLACE_EXISTING);
            }
        }
        movedFiles.clear();
    }

    @Test
    void listMode_whenNoFiles_doesNotCrash() throws Exception {
        DetermineUsage req = new DetermineUsage(DetermineUsage.Mode.LIST, null, null);

        // If your RequestProcessor returns a message or empty string, either is fine.
        // We just want "no crash" behavior.
        String out = RequestProcessor.run(req);

        assertNotNull(out);
    }
}
