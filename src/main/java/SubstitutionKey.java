import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a substitution key taken from a file.
 * Allows for deciphering of text substitution key.
 */
public class SubstitutionKey {
  // Key is the cipher char and value is the base char that substitutes cipher char
  private HashMap<Character, Character> substitutionKey;

  /**
   * Verifies that given substitution key file exists and is in the below format.
   * -base character line-
   * -chipher character line-
   *
   * @param fileOpener The opener that will be used to open key
   * @param keyPath File path to key that contains substitution cipher
   * @exception IllegalArgumentException If file opener cannot extract from path or led to
   *                                     invalid substitution key.
   */
  public SubstitutionKey(
          FileOpener fileOpener,
          final String keyPath) throws IllegalArgumentException {
    if (keyPath == null) {
      throw new IllegalArgumentException("Key file path cannot be null.");
    }
    if (fileOpener == null) {
      throw new IllegalArgumentException("File opener cannot be null.");
    }
    // Verify file has enough text
    String baseLine;
    String cipherLine;
    try {
      List<String> fileLines = fileOpener.getFileLines(keyPath);

      if (fileLines.size() < 2) {
        throw new IllegalArgumentException("Key file does not have enough lines.");
      }
      baseLine = fileLines.get(0);
      cipherLine = fileLines.get(1);
    } catch (IOException exception) {
      throw new IllegalArgumentException("Key path does not lead to a valid txt file.");
    }

    // Verify that lines extracted from file will work as a substitution key
    if (cipherLine.length() != baseLine.length()) {
      throw new IllegalArgumentException(
              "Base character line and substituted letter line don't have the same length.");
    }
    HashSet<Character> cipherSet = new HashSet<>();
    for (int lineIter = 0; lineIter < cipherLine.length() ; lineIter++) {
      char cipherChar = cipherLine.charAt(lineIter);
      if (cipherSet.contains(cipherChar)) {
        throw new IllegalArgumentException(
                "Duplicate character in cipher line causing ambiguous substitution key.");
      }
      cipherSet.add(cipherChar);
    }

    // Actually sets up hash map
    substitutionKey = new HashMap<>();
    for (int lineIter = 0; lineIter < cipherLine.length() ; lineIter++) {
      substitutionKey.put(cipherLine.charAt(lineIter), baseLine.charAt(lineIter));
    }
  }

  /**
   * Runs a string through a substitution key,
   * transposing each cipher character to a base char using key and return the result.
   * If a character is not part of the substitution key it will not be transposed
   * and will stay the same in the decrypted string.
   *
   * @param encryptedString String encrypted with given substitution key.
   * @return Decrypted string.
   */
  public String decipher(final String encryptedString) {
    if (encryptedString == null) {
      return "";
    }

    StringBuilder decryptedOutputStringBuilder = new StringBuilder(encryptedString.length());
    // Goes char by char finding a valid substitution char to put in decrypted output.
    for (int lineIter = 0 ; lineIter < encryptedString.length() ; lineIter++) {
      char encryptedChar = encryptedString.charAt(lineIter);
      char decryptedChar;
      // Char exists in substitution key, and decrypted in accordance to substitution key.
      if (substitutionKey.containsKey(encryptedChar)) {
        decryptedChar = substitutionKey.get(encryptedString.charAt(lineIter));
      }
      // Char doesn't exist in substitution key and directly inputted into decrypted output.
      else {
        decryptedChar = encryptedChar;
      }
      decryptedOutputStringBuilder.append(decryptedChar);
    }
    return decryptedOutputStringBuilder.toString();
  }
}
