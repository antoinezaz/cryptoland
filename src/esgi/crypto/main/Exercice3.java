package esgi.crypto.main;

import esgi.crypto.classes.Homophonique;
import esgi.crypto.model.Key;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Exercice3 {
    public static void main(String[] args) {
        Homophonique m = new Homophonique();
        File message = new File("message.txt");
        File crypted = new File("crypted.txt");
        File result = new File("result.txt");
        File key = new File("key.txt");
        try{
            Key k = m.generateKey(10, message);
            System.out.println(k.toString());
            m.writeKey(k, key);
            Key readedKey = m.readKey(key);
            HashMap<Character, String> map = getMapFromString(readedKey.getLine2());
            System.out.println(map.toString());
            m.encode(message, readedKey, crypted);
            m.decode(crypted, readedKey, result);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static HashMap<Character, String> getMapFromString(String key){
        HashMap<Character, String> myMap = new HashMap<>();
        String[] pairs = key.split(",");
        for (int i=0;i<pairs.length;i++) {
            String pair = pairs[i];
            System.out.println(pair);
            String[] keyValue = pair.split("=");
            myMap.put(keyValue[0].charAt(1), keyValue[1]);
        }
        return myMap;
    }
}
