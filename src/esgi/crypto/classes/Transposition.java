package esgi.crypto.classes;

import esgi.crypto.model.Key;

import java.io.*;
import java.util.Random;

public class Transposition extends Cipher {

    @Override
    public void encode(File message, String referLine, String key, File crypted) throws IOException {
        PrintWriter out;
        out = new PrintWriter(crypted);
        String newstring;

        FileInputStream fstream;

        fstream = new FileInputStream(message);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String strLine;
        while ((strLine = br.readLine()) != null) {
            int number = key.length();
            newstring = "";
            while (number < strLine.length()) {
                String tmp = strLine.substring(number - key.length(), number);
                for (int i = 0; i < key.length(); i++) {
                    int index = Character.getNumericValue(key.charAt(i));
                    newstring += tmp.charAt(index);
                }
                number += key.length();
            }
            if (number != strLine.length() && strLine.length() > 0) {
                newstring += strLine.substring(number - key.length(), strLine.length());
            }

            out.println(newstring);
        }
        out.close();
        //Close the input stream
    }

    @Override
    public void decode(File crypted, String referLine, String key, File message) throws IOException {
        PrintWriter out;
        out = new PrintWriter(message);
        String newstring;

        FileInputStream fstream;

        fstream = new FileInputStream(crypted);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String strLine;
        while ((strLine = br.readLine()) != null) {
            int number = key.length();
            newstring = "";
            while (number < strLine.length()) {
                String tmp = strLine.substring(number - key.length(), number);
                for (int i = 0; i < key.length(); i++) {
                    newstring += tmp.charAt(key.indexOf(Character.forDigit(i, 10)));
                }
                number += key.length();
            }
            if (number != strLine.length() && strLine.length() > 0) {
                newstring += strLine.substring(number - key.length(), strLine.length());
            }

            out.println(newstring);
        }
        out.close();
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
