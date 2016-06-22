package esgi.crypto.classes;

/**
 * Class representing a letter with a value and a count of this value
 */
public class Letter {
    public char value;
    public int count;

    public Letter(char value, int count){
        this.value = value;
        this.count = count;
    }
}
