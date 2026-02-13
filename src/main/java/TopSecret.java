/**
 * Entry point for the TopSecret command-line utility.
 *
 * This class:
 *  - Receives command-line arguments from the user
 *  - Delegates argument parsing to UserInterfaceParser
 *  - Delegates execution logic to RequestProcessor
 *  - Handles user-facing error messages
 *
 * The program supports:
 *    java topsecret
 *    java topsecret <nn>
 *    java topsecret <nn> <key>
 *
 * Errors caused by invalid arguments result in usage instructions being printed.
 * Unexpected runtime errors are caught and displayed as error messages.
 */

/**
 * Commmand Line Utility
 */
public class TopSecret {

    public static void main(String[] args) {
      try {
        DetermineUsage req = UserInterfaceParser.parse(args);
          String output = RequestProcessor.run(req);
          System.out.print(output);
      } catch (IllegalArgumentException ex) {
          System.out.println("Error: " + ex.getMessage());
          printUsage();
      } catch (Exception ex) {
        System.out.println("Error: " + ex.getMessage());
      }
    }


    private static void printUsage() {
      System.out.println("USAGE:");
      System.out.println("  java topsecret");
      System.out.println("  java topsecret <nn>");
      System.out.println("  java topsecret <nn> <key>");
    }
  }
