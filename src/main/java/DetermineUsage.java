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
