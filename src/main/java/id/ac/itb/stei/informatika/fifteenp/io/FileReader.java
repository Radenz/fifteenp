package id.ac.itb.stei.informatika.fifteenp.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * {@code FileReader} is a file reader that reads a file
 * to a string and handle any errors in the process.
 */
public class FileReader {

    private String result = "";

    /**
     * Creates a new {@code FileReader}.
     */
    public FileReader() {
    }

    /**
     * Reads file at a given file path.
     * @param path file path to read.
     * @return true if reading succeeds, false if an error
     *         is encountered (mainly file not found error).
     */
    public boolean readFile(String path) {
        this.result = "";
        try {
            File file = new File(path);
            Scanner reader = new Scanner(file);
            if (reader.hasNextLine()) {
                this.result += reader.nextLine();
            }
            while (reader.hasNextLine()) {
                this.result += "\n" + reader.nextLine();
            }
        } catch (FileNotFoundException e) {
            return false;
        }
        return true;
    }

    /**
     * Returns the result of reading a file.
     * @return result of reading a file.
     */
    public String result() {
        return this.result;
    }
}