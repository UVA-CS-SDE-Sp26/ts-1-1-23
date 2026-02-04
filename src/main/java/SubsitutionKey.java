import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Takes in file and dechipers text based on
 * subsitution key.
 */
public class SubsitutionKey {
  // Key is the base char and value is the char that subsitutes that char in
  // decrypted message.
  private HashMap<Character, Character> subsitutionKey;

  /**
   * Verifies that given subsitution key file exists and is in the below format.
   * -base character line-
   * -subsituted character line-
   *
   * @param keyPath File path to key that contains subsitution chiper
   * @exception IllegalArgumentException If file path led to an invalid file or led to
   *                                     invalid subsitution key.
   */
  public SubsitutionKey(final String keyPath) throws IllegalArgumentException {
    // Verify file exists at all and begins scanning
    File keyFile;
    String baseLine;
    String subsituteLine;
    try {
      keyFile = new File(keyPath);
      Scanner keyScanner = new Scanner(keyFile);
      if (!keyScanner.hasNextLine()) {
        throw new IllegalArgumentException("Key file doesn't have any lines.");
      }
      baseLine = keyScanner.nextLine();
      if (!keyScanner.hasNextLine()) {
        throw new IllegalArgumentException("Key file has only one line.");
      }
      subsituteLine = keyScanner.nextLine();
      keyScanner.close();
    } catch (FileNotFoundException exception) {
      throw new IllegalArgumentException("Key path does not lead to a valid txt file.");
    }

    // Verify that lines extracted from file will work as a subsitution key
    if (subsituteLine.length() != baseLine.length()) {
      throw new IllegalArgumentException(
              "Base character line and subsituted letter line don't have the same length.");
    }
    HashSet<Character> baseSet = new HashSet<>();
    for (int lineIter = 0 ; lineIter < baseLine.length() ; lineIter++) {
      char baseChar = baseLine.charAt(lineIter);
      if (baseSet.contains(baseChar)) {
        System.out.println(baseChar);
        throw new IllegalArgumentException(
                "Duplicate chareter in base line causing ambiguous subsitution key.");
      }
      baseSet.add(baseChar);
    }

    // Actually sets up hash map
    subsitutionKey = new HashMap<>();
    for (int lineIter = 0 ; lineIter < baseLine.length() ; lineIter++) {
      subsitutionKey.put(baseLine.charAt(lineIter), subsituteLine.charAt(lineIter));
    }
  }

  /**
   * Runs a string through a subsitution key and return the result.
   * If a character is not part of the subsitution key,
   * it will simply be copied to the decrypted string.
   *
   * @param encryptedString String encrypted with given subsitution key.
   * @return Decrypted string.
   */
  public String dechiper(final String encryptedString) {
    StringBuilder decryptedOutputStringBuilder = new StringBuilder(encryptedString.length());
    // Goes char by char finding a valid subsitution char to put in decrypted output.
    for (int lineIter = 0 ; lineIter < encryptedString.length() ; lineIter++) {
      char encryptedChar = encryptedString.charAt(lineIter);
      char decryptedChar;
      // Char exists in subsitution key, and decrypted in accordance to subsitution key.
      if (subsitutionKey.containsKey(encryptedChar)) {
        decryptedChar = subsitutionKey.get(encryptedString.charAt(lineIter));
      }
      // Char doesn't exist in subsisitution key and directly inputted into decrypted output.
      else {
        decryptedChar = encryptedChar;
      }
      decryptedOutputStringBuilder.setCharAt(lineIter, decryptedChar);
    }
    return decryptedOutputStringBuilder.toString();
  }
}
