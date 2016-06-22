package esgi.crypto.classes;

import esgi.crypto.intefaces.ICipher;
import esgi.crypto.model.Key;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Cipher implements ICipher{

    protected List<Letter> message_count;
    protected List<Letter> crypted_count;
    protected Key hacked_key;

    public Cipher() {
        hacked_key = new Key();
    }

    /**
     * Method for encoding a message from a file with a key to a file
     *
     * @param message
     * @param referLine
     * @param key
     * @param crypted
     * @throws IOException
     */
    public void encode(File message, String referLine, String key, File crypted) throws IOException {
		PrintWriter out;
        out = new PrintWriter(crypted);
        String newstring;

        FileInputStream fstream;

        fstream = new FileInputStream(message);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String strLine;

        while ((strLine = br.readLine()) != null){
            newstring = "";
            for (int i = 0; i < strLine.length(); i++) {
                int index = referLine.indexOf(strLine.charAt(i));
                if (index == -1) {
                    System.out.println(strLine.charAt(i));
                    newstring += ' ';
                } else {
                    newstring += key.charAt(index);
                }
            }
            out.println(newstring);
        }
        out.close();
        //Close the input stream
    }

    /**
     * Method to decode a message from a gile to a file. Use the encode method
     * @param crypted
     * @param referLine
     * @param key
     * @param message
     * @throws IOException
     */
    public void decode(File crypted, String referLine, String key, File message) throws IOException {
		encode(crypted, key, referLine, message);
    }

    /**
     * Method to generate a key. take a int as parameter and return the generated key
     * @param param
     * @return Key
     */
    public Key generateKey(int param) {
		
		Key result = new Key();
		String alpha = result.getLine1();	
		String newkey = scramble(param, alpha);
		result.setLine2(newkey);
		
		return result;
    }

    /**
     * Method to read a key from a file
     * @param f
     * @return
     * @throws IOException
     */
    public Key readKey(File f) throws IOException {
		FileInputStream fstream;
		
		Key result = new Key();
        fstream = new FileInputStream(f);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String strLine;

          strLine = br.readLine();
          result.setLine2(strLine);

          br.close();

          return result;
        //Close the input stream
    }

    /**
     * Method to write a key from a file
     * @param key
     * @param f
     */
    public void writeKey(Key key, File f) {
        PrintWriter out = null;
        try {
            out = new PrintWriter(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        out.println(key.getLine2());
        out.close();
    }

    /**
     * Method to generate a shuffled version of the string parameters
     * @param n
     * @param inputString
     * @return
     */
    protected String scramble(int n, String inputString) {
        Random random = new Random();
        char a[] = inputString.toCharArray();

        for (int i = 0; i < a.length - 1; i++) {
            int j = random.nextInt(n);
            // Swap letters
            char temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }
        return new String(a);
    }

    /**
     * Method to write the frequency of each letters from message and crypted
     * @param s
     * @param message
     * @param crypted
     * @throws IOException
     */
    public void count(String s, File message, File crypted) throws IOException {
        writeOccurence(s, message, new File("message_count.txt"));
        writeOccurence(s, crypted,new File("crypted_count.txt"));
    }


    /**
     * Method to write in a file the number of each letters from the message of a file
     * @param s
     * @param fileToRead
     * @param fileToWrite
     * @throws IOException
     */
    private void writeOccurence(String s, File fileToRead, File fileToWrite) throws IOException {
            PrintWriter out = new PrintWriter(fileToWrite);
            FileInputStream fstream;
        BufferedReader br;
        String strLine;
            for (int i = 0; i < s.length(); i++) {
                // count char occurrence
                int count = 0;
                fstream = new FileInputStream(fileToRead);
                br = new BufferedReader(new InputStreamReader(fstream));

                while ((strLine = br.readLine()) != null) {
                    for (int j = 0; j < strLine.length(); j++) {
                        if(strLine.charAt(j) == s.charAt(i)){
                            count++;
                        }
                    }
                }
                out.println(""+ s.charAt(i) + "~" + count);
            }
            out.close();

    }

    /**
     * Method to decode a crypted message without key
     * @throws IOException
     */
    public void attack() throws IOException {
		message_count = new ArrayList<>();
		crypted_count = new ArrayList<>();

        writeResultCount(new FileInputStream("message_count.txt"), message_count);
        writeResultCount(new FileInputStream("crypted_count.txt"), crypted_count);


		File message_hacked = keyHack();
		this.decode(new File("crypted.txt"), hacked_key.getLine1(), hacked_key.getLine2(), message_hacked);
    }

    /**
     * Method to hack the key
     * @return
     */
    protected File keyHack() {
        String newstring = "";
        int find;
        char c;
        for (Letter letter : message_count) {

            find = 0;
            c =' ';
            for (Letter crypted_letter : crypted_count) {
                if(letter.count == crypted_letter.count){
                    c = crypted_letter.value;
                    find++;
                }
            }
            if(find == 1){
                newstring += c;
            }else{
                newstring += ' ';
            }
        }

        hacked_key.setLine2(newstring);
        return new File("message_hacked.txt");
    }

    /**
     * Method to write the count result from attack
     * @param fstream
     * @param letters
     * @throws IOException
     */
    private void writeResultCount(FileInputStream fstream, List<Letter> letters) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String strLine;
        String[] parts;

        while ((strLine = br.readLine()) != null) {
            parts = strLine.split("~");
            letters.add(new Letter(parts[0].charAt(0), Integer.parseInt(parts[1])));
        }
        System.out.println("message_count: " + letters.toString());
    }
}
