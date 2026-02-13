import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class FileOpenerImplementation implements FileOpener {

        /*
         * reads the entire content of a specific file and returns it as a string from the data directory.
         * * param fileName is the name of the file to read (like, "house_data.csv").
         * Do not include "data/" in this string; the handler adds it automatically.
         * returns a string containing the full text content of the file and
         * throws **IOException If the file cannot be found or read.
         */
    @Override
    public String getFileContent(String fileName) throws IOException {
    File file = new File(fileName);
    Scanner reader = new Scanner(file);
    String data ="";
    while(reader.hasNextLine()){
        data += reader.nextLine();
    }
    return data;
    }

    /*
     * reads a specific file (filename) from the data directory and returns it line-by-line.
     * this is often easier for processing data files like CSVs.
     * * param fileName is the name of the file to read.
     * returns A List of Strings, where each string is one line from the file.
     * throws IOException If the file cannot be found or read.
     */
    @Override
    public List<String> getFileLines(String fileName) throws IOException {
        File file = new File(fileName);
        ArrayList<String> DataLineList = new ArrayList<String>();
        Scanner scnr = new Scanner(file);
        while(scnr.hasNextLine()){
            DataLineList.add(scnr.nextLine());
        }
        return DataLineList;
    }

    /*
     * Scans the "data" folder and returns a list of all available filenames.
     * will display a menu of files to the user.
     * * returns A List of filenames found in the data folder (e.g., ["graph1.txt", "prices.csv"]).
     */
    @Override
    public List<String> getAvailableFiles() throws FileNotFoundException {
        File folder = new File("data");
        return (ArrayList<String>)getDir(folder);
        }

    public List<String> getDir(File file){
        ArrayList<String> dirList = new ArrayList<String>();
        File[] files = file.listFiles();
        if(files == null){
            return null;
        }
        for (File singfile : files){
            dirList.add(singfile.getName());
        }
        return dirList;
    }


}



