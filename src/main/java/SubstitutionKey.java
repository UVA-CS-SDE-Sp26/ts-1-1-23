import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Represents a substitution key taken from a file.
 * Allows for deciphering of text substitution key.
 */
public class SubstitutionKey {
  // Key is the chipher char and value is the base char that substitutes chipher char
  private HashMap<Character, Character> substitutionKey;

  /**
   * Verifies that given substitution key file exists and is in the below format.
   * -base character line-
   * -chipher character line-
   *
   * @param keyPath File path to key that contains subsitution chiper
   * @exception IllegalArgumentException If file path led to an invalid file or led to
   *                                     invalid subsitution key.
   */
  public SubstitutionKey(final String keyPath) throws IllegalArgumentException {
    if (keyPath == null) {
      throw new IllegalArgumentException("Key file path cannot be null.");
    }
    // Verify file exists at all and begins scanning
    File keyFile;
    String baseLine;
    String cipherLine;
    try {
      keyFile = new File(keyPath);
      Scanner keyScanner = new Scanner(keyFile);
      if (!keyScanner.hasNextLine()) {
        throw new IllegalArgumentException("Key file doesn't have any lines.");
      }

      cipherLine = keyScanner.nextLine();
      if (!keyScanner.hasNextLine()) {
        throw new IllegalArgumentException("Key file has only one line.");
      }
      baseLine = keyScanner.nextLine();
      keyScanner.close();
    } catch (FileNotFoundException exception) {
      throw new IllegalArgumentException("Key path does not lead to a valid txt file.");
    }

    // Verify that lines extracted from file will work as a substitution key
    if (cipherLine.length() != baseLine.length()) {
      throw new IllegalArgumentException(
              "Base character line and substituted letter line don't have the same length.");
    }
    HashSet<Character> baseSet = new HashSet<>();
    for (int lineIter = 0; lineIter < baseLine.length() ; lineIter++) {
      char baseChar = baseLine.charAt(lineIter);
      if (baseSet.contains(baseChar)) {
        throw new IllegalArgumentException(
                "Duplicate character in base line causing ambiguous substitution key.");
      }
      baseSet.add(baseChar);
    }

    // Actually sets up hash map
    substitutionKey = new HashMap<>();
    for (int lineIter = 0; lineIter < baseLine.length() ; lineIter++) {
      substitutionKey.put(baseLine.charAt(lineIter), cipherLine.charAt(lineIter));
    }
  }

  /**
   * Runs a string through a substitution key,
   * transposing each character to a new char using key and return the result.
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
