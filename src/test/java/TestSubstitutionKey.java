import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

import static org.mockito.Mockito.*;

/**
 * Tests the class SubstitutionKey.
 */
public class TestSubstitutionKey {
  // Validate basic file input
  @Test
  public void nullKeyTest() {
    Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> new SubstitutionKey(null, "chipers/key"));
  }

  @Test
  public void nullPathTest() {
    FileOpener emptyKey = mock(FileOpener.class);
    Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> new SubstitutionKey(emptyKey, null));
  }

  @Test
  public void nullBothTest() {
    Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> new SubstitutionKey(null, null));
  }

  @Test
  public void FileOpenerThrow() throws IOException {
    FileOpener throwKey = mock(FileOpener.class);
    when(throwKey.getFileLines(anyString())).thenThrow(IOException.class);
    Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> new SubstitutionKey(
                    throwKey,
                    "data/key.txt"));
  }

  // Validate file contents
  @Test
  public void rightLines() throws IOException {
    FileOpener customLineKey = mock(FileOpener.class);
    when(customLineKey.getFileLines(anyString())).thenReturn(
            new ArrayList<>());
    Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> new SubstitutionKey(
                    customLineKey,
                    "empty"));
    when(customLineKey.getFileLines(anyString())).thenReturn(
            Arrays.asList(""));
    Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> new SubstitutionKey(
                    customLineKey,
                    "cool"));
    when(customLineKey.getFileLines(anyString())).thenReturn(
            Arrays.asList("",""));
    Assertions.assertDoesNotThrow(
            () -> new SubstitutionKey(
                    customLineKey,
                    "filler"));
    when(customLineKey.getFileLines(anyString())).thenReturn(
            Arrays.asList("","",""));
    Assertions.assertDoesNotThrow(
            () -> new SubstitutionKey(
                    customLineKey,
                    "filler"));
  }

  @Test
  public void duplicateCipherCharacterTest() throws IOException {
    // Tests duplicates in both cipher and base lines
    FileOpener customLineKey = mock(FileOpener.class);
    when(customLineKey.getFileLines(anyString())).thenReturn(
            Arrays.asList(
                    "deffffd",
                    "abcdefc"));
    Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> new SubstitutionKey(
                    customLineKey,
                    "test"));

    // Tests duplicate in cipher key
    when(customLineKey.getFileLines(anyString())).thenReturn(
            Arrays.asList(
            "abcdefg",
            "abcdegg"));
    Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> new SubstitutionKey(
                    customLineKey,
                    "test"));

    // Tests duplicate in base key
    when(customLineKey.getFileLines(anyString())).thenReturn(
            Arrays.asList(
                    "abcdegg",
                    "abcdefg"));
    Assertions.assertDoesNotThrow(
            () -> new SubstitutionKey(
                    customLineKey,
                    "test"));
  }

  @Test
  public void mismatchLineLength() throws IOException {
    FileOpener customLineKey = mock(FileOpener.class);
    when(customLineKey.getFileLines(anyString())).thenReturn(
            Arrays.asList(
                    "ab",
                    "abcd"));
    Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> new SubstitutionKey(
                    customLineKey,
                    "filler"));
    when(customLineKey.getFileLines(anyString())).thenReturn(
            Arrays.asList(
                    "abcd",
                    "ab"));
    Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> new SubstitutionKey(
                    customLineKey,
                    "filler"));
  }

  // Decipher tests
  @Test
  public void nullExampleTest() throws IOException {
    FileOpener customLineKey = mock(FileOpener.class);
    when(customLineKey.getFileLines(anyString())).thenReturn(
            Arrays.asList(
                    "bcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890a",
                    "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"));
    SubstitutionKey validKey = new SubstitutionKey(
            customLineKey,
            "wow");
    when(customLineKey.getFileLines(anyString())).thenReturn(
            Arrays.asList(
                    "",
                    "",
                    "",
                    ""));
    SubstitutionKey emptyKey = new SubstitutionKey(
            customLineKey,
            "cool");
    when(customLineKey.getFileLines(anyString())).thenReturn(
            Arrays.asList(
                    "hijklmn",
                    "abcdefg",
                    "",
                    ""));
    SubstitutionKey mixedKey = new SubstitutionKey(
            customLineKey,
            "mixed");
    Assertions.assertEquals("", validKey.decipher(null));
    Assertions.assertEquals("", emptyKey.decipher(null));
    Assertions.assertEquals("", mixedKey.decipher(null));
  }

  @Test
  public void validExampleTest() throws IOException {
    FileOpener customLineKey = mock(FileOpener.class);
    when(customLineKey.getFileLines(anyString())).thenReturn(
            Arrays.asList(
                    "bcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890a",
                    "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"));
    SubstitutionKey key = new SubstitutionKey(
            customLineKey,
            "wow");
    Assertions.assertEquals(key.decipher(
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"),
            "bcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890a");
    Assertions.assertEquals(key.decipher(
                    "KowaBunga"),
            "LpxbCvohb");
  }

  @Test
  public void keepSameCharTest() throws IOException {
    FileOpener customLineKey = mock(FileOpener.class);
    when(customLineKey.getFileLines(anyString())).thenReturn(
            Arrays.asList(
                    "",
                    "",
                    "",
                    ""));
    SubstitutionKey key = new SubstitutionKey(
            customLineKey,
            "empty");
    Assertions.assertEquals(key.decipher(
                    "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"),
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890");
    Assertions.assertEquals(key.decipher(
                    "KowaBunga"),
            "KowaBunga");
  }

  @Test
  public void mixedTest() throws IOException {
    FileOpener customLineKey = mock(FileOpener.class);
    when(customLineKey.getFileLines(anyString())).thenReturn(
            Arrays.asList(
                    "hijklmn",
                    "abcdefg",
                    "",
                    ""));
    SubstitutionKey key = new SubstitutionKey(
            customLineKey,
            "empty");
    Assertions.assertEquals(key.decipher(
                    "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"),
            "hijklmnhijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890");
    Assertions.assertEquals(key.decipher(
                    "KowaBunga"),
            "KowhBunnh");
  }

  // File opener input
  @Test
  public void fileOpenerReceive() throws IOException {
    AtomicReference<String> store = new AtomicReference<>("");
    FileOpener storeKey = mock(FileOpener.class);
    when(storeKey.getFileLines(anyString()))
            .thenAnswer(invocation -> {
              String arg = invocation.getArgument(0);
              store.updateAndGet(v -> v + arg);
              return Arrays.asList("", "");
            });

    SubstitutionKey key1 = new SubstitutionKey(
            storeKey,
            "wow awesome creative");
    Assertions.assertEquals("wow awesome creative", store.get());
  }

  @Test
  public void fileOpenerAffect() throws IOException {
    FileOpener customLineKey = mock(FileOpener.class);
    when(customLineKey.getFileLines("a")).thenReturn(
            Arrays.asList(
                    "bcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890a",
                    "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"));
    when(customLineKey.getFileLines("b")).thenReturn(
            Arrays.asList(
                    "hijklmn",
                    "abcdefg",
                    "",
                    ""));
    SubstitutionKey key1 = new SubstitutionKey(
            customLineKey,
            "a");
    Assertions.assertEquals(key1.decipher(
                    "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"),
            "bcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890a");
    SubstitutionKey key2 = new SubstitutionKey(
            customLineKey,
            "b");
    Assertions.assertEquals(key2.decipher(
                    "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"),
            "hijklmnhijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890");
  }
}
