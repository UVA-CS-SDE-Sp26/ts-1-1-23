import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestSubsitutionKey {
  // Validation tests
  @Test
  public void wrongPathTest() {
    Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> new SubsitutionKey(
                    "wow/this is invalido3"));
    Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> new SubsitutionKey(
                    "chiphers/bobbobccc..!!"));
    Assertions.assertDoesNotThrow(
            () -> new SubsitutionKey(
                    "src/test/testKeys/validationTestKeys/key.txt"));
  }

  @Test
  public void rightLines() {
    Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> new SubsitutionKey(
                    "src/test/testKeys/validationTestKeys/emptyKey.txt"));
    Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> new SubsitutionKey(
                    "src/test/testKeys/validationTestKeys/oneLineKey.txt"));
    Assertions.assertDoesNotThrow(
            () -> new SubsitutionKey(
                    "src/test/testKeys/validationTestKeys/moreLineKey.txt"));
  }

  @Test
  public void duplicateBaseCharacterTest() {
    Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> new SubsitutionKey(
                    "src/test/testKeys/validationTestKeys/duplicateBaseLine.txt"));
    Assertions.assertDoesNotThrow(
            () -> new SubsitutionKey(
                    "src/test/testKeys/validationTestKeys/duplicateSubsitutionLine.txt"));
  }

  @Test
  public void mismatchLineLength() {
    Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> new SubsitutionKey(
                    "src/test/testKeys/validationTestKeys/keyMismatch.txt"));
  }

  // Dechipher tests
  @Test
  public void exampleTest() {
    SubsitutionKey key = new SubsitutionKey(
            "src/test/testKeys/dechipherTestKeys/key.txt");
    Assertions.assertEquals(key.dechiper(
            "abcdefghijklmnopqurstuvwxyzABCDEFGHIJKLMNOPQURSTUVWXYZ1234567890"),
            "bcdefghijklmnopqurstuvwxyzABCDEFGHIJKLMNOPQURSTUVWXYZ1234567890a");
  }

  @Test
  public void keepSameCharTest() {

  }

  @Test
  public void mixedTest() {

  }
}
