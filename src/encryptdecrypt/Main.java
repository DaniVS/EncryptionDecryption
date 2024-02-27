package encryptdecrypt;

public class Main {

    public static void main(String[] args) {
        String algorithm = "";
        String mode = "enc";
        int key = 0;
        String data = "";
        String in = "";
        String out = "";

        for (int i = 0; i < args.length; i+=2){
            switch (args[i]){
                case "-alg":
                    algorithm = args[i+1];
                    break;
                case "-mode":
                    mode = args[i+1];
                    break;
                case "-key":
                    key = Integer.parseInt(args[i+1]);
                    break;
                case "-data":
                    data = args[i+1];
                    break;
                case "-in":
                    in = args[i+1];
                    break;
                case "-out":
                    out = args[i+1];
                    break;
            }
        }

        switch (mode){
            case "enc" :
                new Encrypter(algorithm, data, key, in, out).run();
                break;
            case "dec":
                new Decrypter(algorithm, data, key, in, out).run();
                break;
            default:
                System.out.println(data);
        }
    }
}
