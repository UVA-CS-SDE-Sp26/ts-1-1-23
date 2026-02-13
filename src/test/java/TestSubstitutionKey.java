import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

  // Chiper Inpu
}
