package esgi.crypto.main;

import esgi.crypto.classes.Ceasar;
import esgi.crypto.model.Key;

import java.io.File;
import java.io.IOException;

public class Exercice2 {
	public static void main(String[] args) {
		Ceasar m = new Ceasar();
		File temp = new File("key.txt");
		File message = new File("message.txt");
		File result = new File("result.txt");
		File crypted = new File("crypted.txt");

		Key k = m.generateKey(10);
		m.writeKey(k, temp);

		try {

			Key readedkey = m.readKey(temp);
			System.out.println("readedkey: "+readedkey.toString());

			System.out.println(readedkey.toString());

			m.encode(message, readedkey.getLine1(), readedkey.getLine2(), crypted);
			m.decode(crypted, readedkey.getLine1(), readedkey.getLine2(), result);
			m.count(readedkey.getLine1(), message, crypted);
			m.attack();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
