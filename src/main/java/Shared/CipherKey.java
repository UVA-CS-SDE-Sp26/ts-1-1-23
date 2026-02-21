package Shared;

// A key of a certain kind of cipher.
// Allows for strings encrypted with that cipher to be decrypted into plain text.
public interface CipherKey {
  /**
   * Runs a string through a key and returns the decrypted string.
   *
   * @param encryptedString String encrypted with given key.
   * @exception IllegalArgumentException Thrown if encrypted string is null.
   * @return Decrypted string.
   */
  public String decipher(final String encryptedString) throws IllegalArgumentException;
}
