package esgi.crypto.classes;

import java.io.*;
import java.util.Random;

public class Polyalphabetique extends Cipher {

    @Override
    public void encode(File message, String referLine, String key, File crypted) throws IOException {
        PrintWriter out;
        out = new PrintWriter(crypted);
        String newstring;

        FileInputStream fstream;

        fstream = new FileInputStream(message);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String strLine;
        int k = 0;
        while ((strLine = br.readLine()) != null) {
            newstring = "";
            for (int i = 0; i < strLine.length(); i++) {
                if (k >= key.length()) k =0;
                char c = key.charAt(k);
                int dec = referLine.indexOf(c);
                System.out.println("dec "+ dec);
                int index = referLine.indexOf(strLine.charAt(i));
                System.out.println("index "+ index);
                newstring +=  (dec + index >= referLine.length())? referLine.charAt(index + dec - referLine.length()) : referLine.charAt(index + dec);
                k++;
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
        int k = 0;
        while ((strLine = br.readLine()) != null) {
            newstring = "";
            for (int i = 0; i < strLine.length(); i++) {
                if (k >= key.length()) k =0;
                char c = key.charAt(k);
                int dec = referLine.indexOf(c);
                int index = referLine.indexOf(strLine.charAt(i));
                newstring +=  (index - dec < 0)? referLine.charAt(index - dec + referLine.length()) : referLine.charAt(index - dec);
                k++;
            }
            out.println(newstring);
        }

        out.close();
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
}
