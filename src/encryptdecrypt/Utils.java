package encryptdecrypt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Utils {
    public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    static void outputCyphertext(StringBuilder cyphertext, String out) {
        if (out.isEmpty()) {
            System.out.println(cyphertext);
        } else {
            File outFile = new File(out);
            try (FileWriter writer = new FileWriter(outFile)) {
                writer.write(String.valueOf(cyphertext));
            } catch (IOException e) {
                System.out.println("Error - Output file not found!");
            }
        }
    }
}
