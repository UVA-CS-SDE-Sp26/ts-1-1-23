
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
