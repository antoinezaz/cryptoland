package esgi.crypto.main;

import esgi.crypto.classes.Transposition;
import esgi.crypto.classes.Vernam;
import esgi.crypto.model.Key;

import java.io.File;
import java.io.IOException;

public class Exercice6 {

    public static void main(String[] args) {
        try {
            Vernam m = new Vernam();
            File temp = new File("key.txt");
            File message = new File("message.txt");
            File result = new File("result.txt");
            File crypted = new File("crypted.txt");

            Key k = m.generateKey(10, message);

            m.writeKey(k, temp);
            Key readedkey = m.readKey(temp);
            System.out.println("readedkey: "+readedkey.toString());

            m.encode(message, readedkey.getLine1(), readedkey.getLine2(), crypted);
            m.decode(crypted, readedkey.getLine1(), readedkey.getLine2(), result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
