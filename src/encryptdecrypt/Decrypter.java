package encryptdecrypt;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Decrypter implements Runnable{
    private final String alg;
    private final String data;
    private final int key;
    private final String in;
    private final String out;

    public Decrypter(String alg, String data, int key, String in, String out) {
        this.alg = alg;
        this.data = data;
        this.key = key;
        this.in = in;
        this.out = out;
    }

    @Override
    public void run() {
        StringBuilder cyphetext = new StringBuilder();

        if (in.isEmpty()){
            cyphetext.append(getDecrypted(data));
        } else {
            File inFile = new File(in);
            try (Scanner scanner = new Scanner(inFile)){
                while (scanner.hasNext()){
                    cyphetext.append(getDecrypted(scanner.nextLine()));
                }
            } catch (FileNotFoundException e) {
                System.out.println("Error - input file not found!");
            }
        }

        Utils.outputCyphertext(cyphetext, out);
    }

    private String getDecrypted(String data) {
        return switch (alg) {
            case "unicode" -> getUnicodeDecrypted(data);
            default -> getShiftDecrypted(data);
        };
    }

    private String getShiftDecrypted(String data) {
        StringBuilder decrypted = new StringBuilder();

        for (int x = 0; x < data.length(); x++){
            String actualChar = String.valueOf(data.charAt(x));

            if (!actualChar.matches("[a-zA-Z]")){
                decrypted.append(actualChar);
            }

            if (actualChar.matches("[a-z]")){
                decrypted.append(getShiftedChar(data.charAt(x)));
            }

            if (actualChar.matches("[A-Z]")){
                decrypted.append(getShiftedChar(data.toLowerCase().charAt(x)).toUpperCase());
            }
        }

        return decrypted.toString();
    }

    private String getShiftedChar(char c) {
        // pold = (n + pnew - shift) mod n
        int pnew = Utils.ALPHABET.indexOf(c);
        int pold = (Utils.ALPHABET.length() + pnew - key) % Utils.ALPHABET.length();

        return String.valueOf(Utils.ALPHABET.charAt(pold));
    }

    private String getUnicodeDecrypted(String data) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < data.length(); i++){
            char c = data.charAt(i);

            result.append((char) (c - key));
        }

        return result.toString();
    }
}
