import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

import static java.nio.file.Files.createFile;
import static org.junit.jupiter.api.Assertions.*;
/* JUNIT: Running this program will make a new file in docs, then test the FileOpenerImplementation.java methods
and assert that they equal the contents of the new file.
 */

public class TestFileOpenerImp {
    private void createFile(File file, String content) throws IOException {
        // Make sure the parent folder exists (e.g., "docs/")
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }
        // Write the content
        FileWriter writer = new FileWriter(file);
        writer.write(content);
        writer.close();
    }
    @Test
    public void Test() throws IOException { //tests all three methods for FileOpenerImplementation  getFileContent(), getFileLines(), getAvailableFiles()
        int i = 0;
        File dir = new File("docs");
        File[] files = dir.listFiles();
        for (File singFile : files) {
            i += 1;
        }


        File file = new File("src/test/testFileOpener/testfile" + String.valueOf(i));
        createFile(file, "Junit Test " + String.valueOf(i));

        FileOpenerImplementation fileOpener = new FileOpenerImplementation();
        String data = fileOpener.getFileContent("src/test/testfileOpener/testfile" + String.valueOf((i))); //getFileContent() test
        ArrayList<String> dataList = (ArrayList<String>)fileOpener.getFileLines("src/test/testfileOpener/testfile" + String.valueOf(i)); //getFileLines() test
        assertEquals("Junit Test " + String.valueOf(i), data);
        assertEquals("Junit Test " + String.valueOf(i), dataList.getFirst());
        ArrayList<String> testListAllFiles =(ArrayList<String>) fileOpener.getAvailableFiles();
        File file1 = new File("data");
        ArrayList<String> list = (ArrayList<String>) fileOpener.getDir(file1);
        assertEquals(list, fileOpener.getAvailableFiles());


        File newFile = new File("hey");
        assertNull(fileOpener.getDir(newFile));//at the time of making this test, there are no files in "data" directory
    }


}
