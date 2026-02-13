/* You are responsible for the part of the problem that reads data files.
The program should expect files in a folder named data (in the same directory
of the program, or in the root of your project folder.) When a request for a
file is received, this part of the program will return the file contents.
This part of the program should handle all the direct access to files.
Document the plan your team makes for the design of your software in a
text file named filehandler.txt.
 */
/* this interface should automatically
  look inside the "data/" directory relative to the project root.*/
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
public interface FileOpener {
    /*
     * reads the entire content of a specific file from the data directory.
     * * param fileName is the name of the file to read (like, "house_data.csv").
     * returns a string containing the full text content of the file and
     * throws **IOException If the file cannot be found or read.
     */
    public String getFileContent(String fileName) throws IOException;

    /*
     * reads a specific file (filename) from the data directory and returns it line-by-line.
     * this is often easier for processing data files like CSVs.
     * * param fileName is the name of the file to read.
     * returns A List of Strings, where each string is one line from the file.
     * throws IOException If the file cannot be found or read.
     */
    public List<String> getFileLines(String fileName) throws IOException;
    /*
     * Scans the "data" folder and returns a list of all available filenames.
     * will display a menu of files to the user.
     * * returns A List of filenames found in the data folder (e.g., ["graph1.txt", "prices.csv"]).
     */
    public List<String> getAvailableFiles() throws FileNotFoundException;
}


