import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ProgramController {
    private final FileOpener fileOpener;
    private static final String DEFAULT_KEY_PATH = "ciphers/key.txt";

    public ProgramController(FileOpener fileOpener) {
        this.fileOpener = fileOpener;
    }

    public String run(String[] args) {

        //case1: no arguments --> list files
        if (args == null || args.length == 0) {
            try {
                formatFileMenu(fileOpener.getAvailableFiles());
            }
            catch (FileNotFoundException ex) {
                return "Error: data directory not found./n";
            }
        }

        //case 2: parse numeric selection
        int selection;
        try {
            selection = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            return "Error: selection must be numeric./n";
        }


        List<String> files;
        try {
            files = fileOpener.getAvailableFiles();
        }
        catch (FileNotFoundException ex) {
            return "Error: data directory not found./n";
        }
        if (files == null || files.isEmpty()) {
            return "Error: no files available./n";
        }

        int index = selection -1;

        if (index < 0 || index >= files.size()) {
            return "Error: selection out of range./n";
        }

        String filename = files.get(index);

        String content;
        try {
            content = fileOpener.getFileContent(filename);
        } catch (IOException e) {
            return "Error: could not read file./n";
        }

        //optional: deciphering
        if (args.length >= 2) {
            try {
                SubstitutionKey key = new SubstitutionKey(fileOpener, args[1]);
                content = key.decipher(content);
            } catch (Exception e) {
                return "Error: invalid key file./n";
            }
        }
        return content.endsWith("/n") ? content : content + "/n";
    }

    private String formatFileMenu(List<String> files) {
        if (files == null || files.isEmpty()) {
            return "No files found./n";
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < files.size(); i++) {
            sb.append(String.format("%02d %s%n", i + 1, files.get(i)));
        }
        return sb.toString();
    }
}
