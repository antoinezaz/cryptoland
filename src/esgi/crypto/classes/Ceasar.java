package esgi.crypto.classes;

import java.io.File;

public class Ceasar extends Cipher {
	
	@Override
	protected String scramble(int n, String inputString) {
		// TODO Auto-generated method stub
		
		char a[] = inputString.toCharArray();
		char temp[] = new char[a.length];
		System.out.println(a.length);
        for (int i = 0; i < a.length - 1; i++) {
            // Swap letters
            temp[i] = (i + n < a.length)? a[i + n] : a[i + n - a.length];
            System.out.println(String.valueOf(temp));
        }
        
		return String.valueOf(temp);
	}

	@Override
	protected File keyHack() {
		char thechar = ' ';
		char thechar_crypted = ' ';
		int find = 0;
		char c =' ';
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
				thechar = letter.value;
				thechar_crypted = c;
				break;
			}
		}

		int diff = hacked_key.getLine1().indexOf(thechar_crypted) - hacked_key.getLine1().indexOf(thechar);

		String newstring = scramble(diff, hacked_key.getLine1());

		hacked_key.setLine2(newstring);

		System.out.println(hacked_key.toString());
		return new File("message_hacked.txt");
	}
}
