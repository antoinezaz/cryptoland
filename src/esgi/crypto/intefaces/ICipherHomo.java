package esgi.crypto.intefaces;

import esgi.crypto.model.Key;

import java.io.File;
import java.io.IOException;

public interface ICipherHomo {
    void encode(File message, Key key, File crypted);
    void decode(File crypted, Key key, File message);
    Key readKey(File f);
    void writeKey(Key key, File f);
    Key generateKey(int param, File message) throws IOException;
}
