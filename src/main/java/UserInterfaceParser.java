/**
 * Parses and validates command-line arguments for the TopSecret program.
 *
 * This class is responsible for:
 *  - Interpreting the raw String[] args passed into main()
 *  - Validating argument count and format
 *  - Ensuring file numbers follow the required two-digit format
 *  - Ensuring optional cipher key paths are not blank
 *  - Constructing a DetermineUsage object that represents the user's request
 *
 * The parser throws IllegalArgumentException when arguments are invalid.
 * It does not execute program logic — it only prepares structured input
 * for further processing.
 */


public class UserInterfaceParser {

    public static DetermineUsage parse(String[] args) {
        if (args == null) args = new String[0];

        if (args.length == 0) {
            return new DetermineUsage(DetermineUsage.Mode.LIST, null, null);
        }

        if (args.length == 1) {
            validateTwoDigits(args[0]);
            return new DetermineUsage(DetermineUsage.Mode.SHOW, args[0], null);
        }

        if (args.length == 2) {
            validateTwoDigits(args[0]);
            validateKey(args[1]);
            return new DetermineUsage(DetermineUsage.Mode.SHOW, args[0], args[1]);
        }

        throw new IllegalArgumentException("Too many arguments.");
    }

    private static void validateTwoDigits(String s) {
        if (s == null || !s.matches("\\d\\d")) {
            throw new IllegalArgumentException("File number must be two digits (e.g., 01).");
        }
    }

    private static void validateKey(String key) {
        if (key == null || key.isBlank()) {
            throw new IllegalArgumentException("Key must not be blank.");
        }
    }
}
