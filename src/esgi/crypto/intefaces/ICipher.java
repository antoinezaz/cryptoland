package esgi.crypto.intefaces;

import esgi.crypto.model.Key;

import java.io.File;
import java.io.IOException;

/**
 * Interface defining the method for Ciper class
 */
public interface ICipher {
	void encode(File message, String referLine, String key, File crypted) throws IOException;
	void decode(File crypted, String referLine, String key, File message) throws IOException;
	Key generateKey(int param);
	Key readKey(File f) throws IOException;
	void writeKey(Key key, File f);
}
