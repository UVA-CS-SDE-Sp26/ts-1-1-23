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
