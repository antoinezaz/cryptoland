package esgi.crypto.classes;

import java.io.*;
import java.util.Random;

public class Polyalphabetique extends Cipher {

    @Override
    public void encode(File message, String referLine, String key, File crypted) throws IOException {
        String newstring = "";
        String strLine = readFile(message);
        int k = 0;

        for (int i = 0; i < strLine.length(); i++) {
            if (k >= key.length()) k =0;
            char c = key.charAt(k);
            int dec = referLine.indexOf(c);
            int index = referLine.indexOf(strLine.charAt(i));
            newstring +=  (dec + index >= referLine.length())? referLine.charAt(index + dec - referLine.length()) : referLine.charAt(index + dec);
            k++;
        }

        writeFile(newstring, crypted);
    }

    @Override
    public void decode(File crypted, String referLine, String key, File message) throws IOException {
        String newstring = "";
        String strLine = readFile(crypted);

        int k = 0;
        for (int i = 0; i < strLine.length(); i++) {
            if (k >= key.length()) k =0;
            char c = key.charAt(k);
            int dec = referLine.indexOf(c);
            int index = referLine.indexOf(strLine.charAt(i));
            newstring +=  (index - dec < 0)? referLine.charAt(index - dec + referLine.length()) : referLine.charAt(index - dec);
            k++;
        }

        writeFile(newstring, message);
    }

    @Override
    protected String scramble(int n, String inputString) {
        char a [] = new char[n];
        Random random = new Random();
        for (int i = 0 ; i < n; i++) {
            int rand = random.nextInt(inputString.length());
            a[i] = inputString.charAt(rand);
        }
        return String.valueOf(a);
    }

    private int nbOccurrences(String src, String search) {
        int lastIndex = 0, count = 0;
        while ((lastIndex = src.indexOf(search, lastIndex)) != -1) {
            count++;
            lastIndex += search.length();
        }
        return count;
    }
}
