package Namecreation;

import java.util.Random;

public class Createrandomnames {

	public static void main(String[] args) {
		for(int i=0;i<4;i++){
		String output = getSaltString();
		System.out.println(output);
		}

	}
	
	protected static String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        
        while (salt.length() < 14) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

}
