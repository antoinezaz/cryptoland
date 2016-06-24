package esgi.crypto.classes;

import esgi.crypto.model.Key;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Vernam extends Cipher {

    @Override
    public void encode(File message, String referLine, String key, File crypted) throws IOException {
        String strFile = readFile(message);
        String newStr = "";
        for (int i = 0; i < strFile.length(); i++) {
            newStr += generateBinary(convertCharToBinary(strFile.charAt(i)), convertCharToBinary(key.charAt(i)));
        }

        writeFile(newStr, crypted);
    }

    @Override
    public void decode(File crypted, String referLine, String key, File message) throws IOException {
        String strFile = readFile(crypted);
        String newStr = "";
        int k = 0;
        String bin1 = "", bin2 ="";
        for (int i = 0; i < strFile.length(); i++) {
            bin1 += strFile.charAt(i);
            if (k == 7) {
                k = 0;
                bin2 = convertCharToBinary(key.charAt((i)/8));
                newStr += convertBinaryToChar(generateBinary(bin1, bin2));
                bin1 = "";

            } else k++;
        }
        writeFile(newStr, message);
    }

    private String convertCharToBinary(char c) {
        byte b = (byte) c;
        String binary = "";
        for (int i = 0; i < 8; i++)
        {
            binary += ((b & 128) == 0) ? 0 : 1;
            b <<= 1;
        }
        return binary;
    }

    private String convertBinaryToChar(String binary) {
        String test = Character.toString((char) Integer.parseInt(binary, 2));
        return test;
    }

    private String generateBinary(String  binary1, String binary2) {
        String newBinary = "";
        for (int i = 0; i < binary1.length(); i++) {
            int i1 = Integer.parseInt(String.valueOf(binary1.charAt(i)));
            int i2 = Character.getNumericValue(binary2.charAt(i));
            if (i1 + i2 == 1) {
                newBinary += '1';
            } else {
                newBinary += '0';
            }
        }
        return newBinary;
    }

    public Key generateKey(int n, File f) throws IOException {
        String message = readFile(f);
        Key result = new Key();
        result.setLine2(scramble(message.length(), result.getLine1()));
        return result;
    }

    @Override
    protected String scramble(int n, String inputString) {
        Random random = new Random();
        String key = "";
        for (int i = 0; i < n; i++) {
            int index = random.nextInt(inputString.length());
            key += inputString.charAt(index);
        }
        return key;
    }
}
