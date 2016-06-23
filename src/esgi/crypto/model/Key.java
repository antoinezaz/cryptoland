package esgi.crypto.model;

import java.util.Arrays;

public class Key {
	private String line1 = " !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}";
	private String line2 = "";
	
	public Key() {
		super();
	}
	
	public Key(String line2) {
		super();
		this.line2 = line2;
	}

	public String getLine1() {
		return line1;
	}

	public void setLine1(String line1) {
		this.line1 = line1;
	}

	public String getLine2() {
		return line2;
	}

	public void setLine2(String line2) {
		this.line2 = line2;
	}

	@Override
	public String toString() {
		return "Key [line1=" + line1 + ", line2=" + line2 + "]";
	}
}
