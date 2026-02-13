/**
 * Represents a validated command-line request for the TopSecret program.
 *
 * This class acts as a data container that stores:
 *  - The mode of operation (LIST or SHOW)
 *  - The selected file number (if applicable)
 *  - An optional cipher key override (if provided)
 *
 * Instances of this class are created by UserInterfaceParser after
 * validating command-line arguments. The resulting object is then
 * passed to RequestProcessor for execution.
 *
 * This class does not perform validation itself; it only stores
 * already-validated input data.
 */


public class DetermineUsage {

    public enum Mode {
        LIST,
        SHOW
    }

    private final Mode mode;
    private final String fileNumber;   // null if LIST
    private final String keyOverride;  // null if not provided

    public DetermineUsage(Mode mode, String fileNumber, String keyOverride) {
        this.mode = mode;
        this.fileNumber = fileNumber;
        this.keyOverride = keyOverride;
    }

    public Mode getMode() {
        return mode;
    }

    public String getFileNumber() {
        return fileNumber;
    }

    public String getKeyOverride() {
        return keyOverride;
    }
}
