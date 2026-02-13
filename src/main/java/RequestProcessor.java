import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class RequestProcessor {

    private static final String DEFAULT_KEY_PATH = "ciphers/key.txt";

    public static String run(DetermineUsage req) throws IOException {
        FileOpener opener = new FileOpenerImplementation();

        // LIST MODE
        if (req.getMode() == DetermineUsage.Mode.LIST) {
            List<String> files = opener.getAvailableFiles();
            return formatFileMenu(files);
        }

        // SHOW MODE
        List<String> files = opener.getAvailableFiles();
        if (files == null || files.isEmpty()) {
            throw new IllegalArgumentException("No files available.");
        }

        Collections.sort(files);

        int index = parseTwoDigitIndex(req.getFileNumber(), files.size());
        String fileName = files.get(index);

        // FileOpenerImplementation expects full relative path
        String rawText = opener.getFileContent("data/" + fileName);

        // Determine key usage
        String keyPath = req.getKeyOverride();

        if (keyPath == null) {
            // Use default only if it exists
            if (new File(DEFAULT_KEY_PATH).exists()) {
                keyPath = DEFAULT_KEY_PATH;
            } else {
                return rawText; // no cipher present (3-person team)
            }
        }

        if (!new File(keyPath).exists()) {
            throw new IllegalArgumentException("Cipher key file not found: " + keyPath);
        }

        SubstitutionKey key = new SubstitutionKey(opener,keyPath);
        return key.decipher(rawText);
    }

    private static String formatFileMenu(List<String> files) {
        if (files == null || files.isEmpty()) {
            return "No files available.\n";
        }

        Collections.sort(files);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < files.size(); i++) {
            sb.append(String.format("%02d %s%n", i + 1, files.get(i)));
        }
        return sb.toString();
    }

    private static int parseTwoDigitIndex(String nn, int size) {
        int num = Integer.parseInt(nn);
        int index = num - 1;

        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Invalid file number: " + nn);
        }

        return index;
    }
}
