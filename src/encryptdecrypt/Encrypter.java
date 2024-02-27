package encryptdecrypt;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Encrypter implements Runnable {
    private final String alg;
    private final String data;
    private final int key;
    private final String in;
    private final String out;

    Encrypter(String alg, String data, int key, String in, String out){
        this.alg = alg;
        this.data = data;
        this.key = key;
        this.in = in;
        this.out = out;
    }

    @Override
    public void run() {
        StringBuilder cyphertext = new StringBuilder();

        if (in.isEmpty()){
            cyphertext.append(getCiphertext(data));
        } else {
            File inFile = new File(in);
            try (Scanner scanner = new Scanner(inFile)){
                while (scanner.hasNext()){
                    cyphertext.append(getCiphertext(scanner.nextLine()));
                }
            } catch (FileNotFoundException e) {
                System.out.println("Error - Input file not found!");
            }
        }

        Utils.outputCyphertext(cyphertext, out);
    }

    private String getCiphertext(String data) {
        return switch (alg) {
            case "unicode" -> getUnicodeEncrypted(data);
            default -> getShiftEncrypted(data);
        };
    }

    private String getShiftEncrypted(String data) {
        StringBuilder shifted = new StringBuilder();
        for (int x = 0; x < data.length(); x++){
            String actualChar = String.valueOf(data.charAt(x));
            if (!actualChar.matches("[a-zA-Z]")){
                shifted.append(actualChar);
            }

            if (actualChar.matches("[a-z]")){
                shifted.append(getShiftedChar(data.charAt(x)));
            }

            if (actualChar.matches("[A-Z]")){
                shifted.append(getShiftedChar(data.toLowerCase().charAt(x)).toUpperCase());
            }
        }

        return shifted.toString();
    }

    private String getShiftedChar(char c) {
        // pnew = (pold + shift) mod n
        int pold = Utils.ALPHABET.indexOf(c);
        int pnew = (pold + key) % Utils.ALPHABET.length();

        return String.valueOf(Utils.ALPHABET.charAt(pnew));
    }

    private String getUnicodeEncrypted(String data) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < data.length(); i++){
            char c = data.charAt(i);

            result.append((char) (c + key));
        }

        return result.toString();
    }
}

