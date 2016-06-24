package esgi.crypto.classes;

import esgi.crypto.model.Key;

import java.io.*;
import java.util.Map;
import java.util.Random;

public class Transposition extends Cipher {

    private Map<String, Integer> Occurrences;

    @Override
    public void encode(File message, String referLine, String key, File crypted) throws IOException {
        String newstring ="";

        String strLine = readFile(message);
        /*for (int i = 0; i < strLine.length(); i++) {
            if ((number + i) % key.length() == 0) {
                number += key.length();
                if (number > strLine.length()) break;
                tmp = strLine.substring(number - key.length(), number);
            }
            int index = i%key.length();
            newstring += tmp.charAt(key.indexOf(Character.forDigit(index, 10)));
        }*/
        int number = key.length();
        while (number <= strLine.length()) {
            String tmp = strLine.substring(number - key.length(), number);
            newstring += encodeString(tmp, key);
            number += key.length();
        }

        if (number != strLine.length() && strLine.length() > 0) {
            String tmp = strLine.substring(number - key.length(), strLine.length());
            for (int i = tmp.length(); i < key.length(); i++) {
                tmp += " ";
            }
            newstring += encodeString(tmp, key);
        }

        writeFile(newstring, crypted);
    }

    private String encodeString(String crypted, String key) {
        String newStr = "";
        for (int i = 0; i < key.length(); i++) {
            newStr += crypted.charAt(key.indexOf(Character.forDigit(i, 10)));
        }
        return  newStr;
    }

    private String decodeString(String message, String key) {
        String newStr = "";
        for (int i = 0; i < key.length(); i++) {
            int index = Character.getNumericValue(key.charAt(i));
            newStr += message.charAt(index);
        }
        return  newStr;
    }

    @Override
    public void decode(File crypted, String referLine, String key, File message) throws IOException {
        String newstring = "";
        String strLine = readFile(crypted);

        int number = key.length();
        while (number <= strLine.length()) {
            String tmp = strLine.substring(number - key.length(), number);
            newstring += decodeString(tmp, key);
            number += key.length();
        }

        writeFile(newstring.trim(), message);
    }

    @Override
    protected String scramble(int n, String inputString) {
        char a [] = new char[n];
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            a[i] = Character.forDigit(i, 10);
        }
        for (int i = 0 ; i < n; i++) {
            int rand = random.nextInt(n);
            char tmp = a[i];
            a[i] = a[rand];
            a[rand] = tmp;
        }
        return String.valueOf(a);
    }

}
