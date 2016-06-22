package esgi.crypto.intefaces;

import java.io.File;
import java.io.IOException;

import esgi.crypto.model.Key;

public interface ICipher {
	void encode(File message, String referLine, String key, File crypted) throws IOException;
	void decode(File crypted, String referLine, String key, File message) throws IOException;
	Key generateKey(int param);
	Key readKey(File f) throws IOException;
	void writeKey(Key key, File f);
}
