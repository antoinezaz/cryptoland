package esgi.crypto.classes;

import esgi.crypto.intefaces.ICipherHomo;
import esgi.crypto.model.Key;

import java.io.*;
import java.util.*;

public class Homophonique implements ICipherHomo{

    @Override
    public void encode(File message, Key key, File crypted) {
        // TODO Auto-generated method stub
        PrintWriter out;
        try {
            out = new PrintWriter(crypted);
            String newstring = "";

            FileInputStream fstream;

            try {
                fstream = new FileInputStream(message);
                BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

                String strLine;
                try {
                    while ((strLine = br.readLine()) != null){
                        newstring = "";
                        HashMap<Character, String> map_line2 = getMapFromString(key.getLine2());
                        Random random = new Random();
                        for (int i = 0; i < strLine.length(); i++) {
                            if(strLine.charAt(i) != ','){
                                String s = map_line2.get(strLine.charAt(i));
                                if(s != null){
                                    int r = random.nextInt(s.length());
                                    newstring += s.charAt(r);
                                }else{
                                    newstring += ' ';
                                }
                            }
                        }
                        out.println(newstring);
                    }
                    out.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                //Close the input stream

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    @Override
    public void decode(File crypted, Key key, File message) {
        // TODO Auto-generated method stub
        PrintWriter out;
        try {
            out = new PrintWriter(message);

            FileInputStream fstream;
            String newstring = "";

            try {
                fstream = new FileInputStream(crypted);
                BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

                String strLine;

                try {

                    while ((strLine = br.readLine()) != null){
                        newstring = "";
                        HashMap<Character, String> map_line2 = getMapFromString(key.getLine2());
                        for (int i = 0; i < strLine.length(); i++) {
                            for (Map.Entry<Character, String> entry : map_line2.entrySet()) {
                                Character k = entry.getKey();
                                String value = entry.getValue();
                                if(value.contains(String.valueOf(strLine.charAt(i)))){
                                    newstring += k;
                                    break;
                                }
                            }
                        }
                        out.println(newstring);
                    }
                    out.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                //Close the input stream

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    @Override
    public Key generateKey(int param, File message) throws IOException {
        // TODO Auto-generated method stub
        try {
            generateAsciiTable();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Key result = new Key();

        //generate occurence count for each letter
        count(result.getLine1(), message);

        List<Letter> message_count = new ArrayList<Letter>();

        FileInputStream fstream_count;

        try {
            fstream_count = new FileInputStream("message_count.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream_count));

            String strLine;
            try {

                while ((strLine = br.readLine()) != null){
                    String[] parts = strLine.split("~");
                    message_count.add(new Letter(parts[0].charAt(0), Integer.parseInt(parts[1])));
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        message_count.sort(new Comparator<Letter>() {
            @Override
            public int compare(Letter o1, Letter o2) {
                // TODO Auto-generated method stub
                return o2.count.compareTo(o1.count);
            }
        });

        System.out.println(message_count.toString());

        FileInputStream fstream;
        int count = 0;

        File ascii = new File("ascii.txt");
        fstream = new FileInputStream(ascii);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String ascii_alpha = br.readLine();
        System.out.println("ascii:  "+ascii_alpha);
        String newkey = scramble(message_count, ascii_alpha);

        result.setLine2(newkey);

        return result;
    }

    @Override
    public Key readKey(File f) {
        // TODO Auto-generated method stub
        FileInputStream fstream;

        Key result = new Key();
        try {
            fstream = new FileInputStream(f);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

            String strLine;

            try {
                strLine = br.readLine();
                result.setLine2(strLine);

                br.close();

                return result;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }
            //Close the input stream

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void writeKey(Key key, File f) {
        // TODO Auto-generated method stub
        try {
            PrintWriter out = new PrintWriter(f);
            out.println(key.getLine2());
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void count(String s, File message) throws IOException {
        // TODO Auto-generated method stub
        File f = new File("message_count.txt");
        try {
            PrintWriter out = new PrintWriter(f);
            FileInputStream fstream;
            for (int i = 0; i < s.length(); i++) {
                // count char occurrence
                int count = 0;
                fstream = new FileInputStream(message);
                BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

                String strLine;
                while ((strLine = br.readLine()) != null){
                    for (int j = 0; j < strLine.length(); j++) {
                        if(strLine.charAt(j) == s.charAt(i)){
                            count++;
                        }
                    }
                }
                out.println(""+ s.charAt(i) + "~" + count);
            }
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    protected String scramble(List<Letter> list, String inputString) {
        int n = 10;
        HashMap<Character, String> map = new HashMap<Character, String>();
        char a[] = inputString.toCharArray();

        System.out.println(String.valueOf(a));

        List<Character> listC = new ArrayList<Character>();
        for (char c : a) {
            listC.add((Character) c);
        }

        System.out.println(listC.toString());

        for (Letter letter : list) {
            String temp = "";
            Random random = new Random();
            if(list.indexOf(letter) < 5){
                for (int i = 0; i <= 5; i++) {
                    int r = random.nextInt(listC.size());
                    temp += listC.get(r);
                    listC.remove(r);
                }
            }
            else if(list.indexOf(letter) > 5 && list.indexOf(letter) < 10){
                for (int i = 0; i <= 4; i++) {
                    int r = random.nextInt(listC.size());
                    temp += listC.get(r);
                    listC.remove(r);
                }
            }
            else if(list.indexOf(letter) > 10 && list.indexOf(letter) < 15){
                for (int i = 0; i <= 3; i++) {
                    int r = random.nextInt(listC.size());
                    temp += listC.get(r);
                    listC.remove(r);
                }
            }
            else if(list.indexOf(letter) > 15 && list.indexOf(letter) < 20){
                for (int i = 0; i <= 2; i++) {
                    int r = random.nextInt(listC.size());
                    temp += listC.get(r);
                    listC.remove(r);
                }
            }else{
                int r = random.nextInt(listC.size());
                temp += listC.get(r);
                listC.remove(r);
            }
            map.put(letter.value, temp);
            System.out.println(list.indexOf(letter));
        }

        return map.toString();
    }

    public void generateAsciiTable() throws FileNotFoundException{
        char[] ascii = new char[189];
        int skipped = 0;
        for (int i = 32; i < 256; i++) {
            if((char) i == ',' || (char) i == '=' || (i >= 128 && i <= 160)){
                skipped++;
            }else{
                ascii[i-32-skipped] = (char) i;
            }
        }
        File temp = new File("ascii.txt");
        PrintWriter out = new PrintWriter(temp);
        out.println(String.valueOf(ascii));
        out.close();
        System.out.println(String.valueOf(ascii));
    }

    public class Letter{
        public char value;
        public Integer count;

        public Letter(char value, Integer count){
            this.value = value;
            this.count = count;
        }

        @Override
        public String toString() {
            return "Letter [value=" + value + ", count=" + count + "]";
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
