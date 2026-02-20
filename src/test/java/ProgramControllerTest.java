import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import Homework2.ProgramController;
import Shared.FileOpener;

public class ProgramControllerTest {
    private static class FakeFileOpener implements FileOpener {

        private final List<String> files;
        private String content;
        private final boolean failRead;

        FakeFileOpener(List<String> files, String content, boolean failRead) {
            this.files = files;
            this.content = content;
            this.failRead = failRead;
        }

        @Override
        public String getFileContent(String fileName) throws IOException {
            if (failRead) throw new IOException();
            return content;
        }

        @Override
        public List<String> getFileLines (String fileName) {
            return null;
        }

        @Override
        public List<String> getAvailableFiles() {
            return files;
        }
    }

//    @Test
//    public void noArgsListFiles() {
//        ProgramController pc = new ProgramController(new FakeFileOpener(List.of("a.txt", "b.txt"), "X", false));
//
//        String result = pc.run(new String[]{});
//        Assertions.assertTrue(result.contains("01 a.txt"));
//        Assertions.assertTrue(result.contains("02 b.txt"));
//    }

    @Test
    public void invalidNumberReturnError() {
        ProgramController pc = new ProgramController(new FakeFileOpener(List.of("a.txt"), "X", false));

        String result = pc.run(new String[]{"abc"});
        Assertions.assertTrue(result.startsWith("Error"));
    }

    @Test
    public void outOfRangeReturnsError() {
        ProgramController pc = new ProgramController(new FakeFileOpener(List.of("a.txt"), "X", false));

        String result = pc.run(new String[] {"99"});
        Assertions.assertTrue(result.startsWith("Error"));
    }

    @Test
    public void validSelectionReturnsContent() {
        ProgramController pc = new ProgramController(new FakeFileOpener(List.of("a.txt"), "12345", false));

        String result = pc.run(new String[]{"01"});
        Assertions.assertTrue(result.contains("12345"));
    }

    @Test
    public void fileReadFailureReturnsError() {
        ProgramController pc = new ProgramController(new FakeFileOpener(List.of("a.txt"), "HELLO", true));

        String result = pc.run(new String[]{"01"});
        Assertions.assertTrue(result.startsWith("Error"));
    }
}


