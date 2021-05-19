package Namecreation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class randomname {
	
	private static Map alphabets;
	private static String lphaStringPattern2;
	private static String lastName="John";
	private static String firstName="Peter";
	private static String DOB1="04-Dec-1989";
	private static String DOB;
	private static Map oddNumbervalue;

	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder(16);
		String AlphaStringPattern1 = "ABCDEFGHIJKLMNOP";
		String NumericString = "0123456789"; 
		String FirstName="";
		String LastName="";

		//To get consonants from Firstname
		String LastNameWOvowel = lastName.replaceAll("[AaEeIiOoUu]", "");

		//To get consonants from Lastname
		String FirstNameWOvowel = firstName.replaceAll("[AaEeIiOoUu]", "");

		// last name three consonants
		if(LastNameWOvowel.length()>=3) {
			LastName = LastNameWOvowel.substring(0, 3);
		}
		else {
			LastName=lastName.substring(0,3);
		}
		sb.append(LastName.toUpperCase());

		// first name three consonants
		if(FirstNameWOvowel.length()>=3) {
			FirstName = FirstNameWOvowel.substring(0, 3);
		}
		else {
			FirstName=firstName.substring(0, 3);
		}
		sb.append(FirstName.toUpperCase());

		//To get the year
		String Year = DOB.split("/")[0];
		sb.append(Year);

		//To get Month
		String Month=GetMonthAsAlphabet(DOB.split("/")[1]);
		sb.append(Month);

		//To get Day
		String Day=DOB.split("/")[2];
		sb.append(Day);

		//To get the character
		for(int i=0;i<1;i++)
		{
			int index = (int) (AlphaStringPattern1.length() * Math.random()); 
			sb.append(AlphaStringPattern1.charAt(index)); 
		}

		//To get the Number
		for (int i = 0; i < 3; i++) {
			int index = (int) (NumericString.length() * Math.random());
			sb.append(NumericString.charAt(index));
		}

		//To get check Value
		String words = new String("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
		ArrayList<Character> CharList_odd = new ArrayList<Character>();
		for (int i = 0; i < words.length(); i++) {
			CharList_odd.add(words.charAt(i));
		}
		int[] arr_Alphavalue_odd = new int[] { 1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2, 4, 18, 20, 11, 3, 6, 8, 12, 14, 16, 10, 22, 25,24, 23 };
		HashMap Oddalphabets = new HashMap<String , Integer>();
		for (int i = 0; i < CharList_odd.size(); i++) {
			Oddalphabets.put((CharList_odd.get(i)).toString(),arr_Alphavalue_odd[i]);
		}
		String numbers = new String("0123456789");
		ArrayList<Character> numberlist = new ArrayList<Character>();
		for (int i = 0; i < numbers.length(); i++) {
			numberlist.add(numbers.charAt(i));
		}
		int[] arr_number_odd = new int[] { 1, 0, 5, 7, 9, 13, 15, 17, 19, 21 };
		HashMap oddNumbervalue = new HashMap<String,Integer>();
		for (int i = 0; i < 10; i++) {
			oddNumbervalue.put((numberlist).get(i).toString(), arr_number_odd[i]);
		}
		ArrayList<String> odd = new ArrayList<>();
		ArrayList<String> even = new ArrayList<>(); 
		odd.clear();
		even.clear();


		//To get odd characters
		for(int i=0;i<sb.length();i++) {
			odd.add(String.valueOf(sb.charAt(i)));
			i=i+1;

		}

		//To Sum odd characters
		int oddsum=0;
		for(int i=0;i<odd.size();i++) {

			int eachdigit;
			String getEachAlphabet = odd.get(i);
			char value=getEachAlphabet.charAt(0);
			if(Character.isDigit(value)) {
				eachdigit=(int) oddNumbervalue.get(getEachAlphabet);
			}
			else
			{
				eachdigit=(int) Oddalphabets.get(getEachAlphabet);               
			}
			oddsum=oddsum+eachdigit;
		}

		//Get EvenCharacters from string
		words = new String("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
		ArrayList<Character> CharList_even = new ArrayList<Character>();
		for (int i = 0; i < words.length(); i++) {
			CharList_even.add(words.charAt(i));
		}
		int[] arr_Alphavalue_even = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23,24, 25 };
		HashMap evenalphabets = new HashMap<String , Integer>();
		for (int i = 0; i < CharList_even.size(); i++) {
			evenalphabets.put((CharList_even.get(i)).toString(),arr_Alphavalue_even[i]);
		}
		numbers = new String("0123456789");
		numberlist = new ArrayList<Character>();
		for (int i = 0; i < numbers.length(); i++) {
			numberlist.add(numbers.charAt(i));
		}
		int[] arr_number_even = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		HashMap evenNumbervalue = new HashMap<String,Integer>();
		for (int i = 0; i < 10; i++) {
			evenNumbervalue.put((numberlist).get(i).toString(), arr_number_even[i]);
		}

		//To get evenCharcters
		for(int i=0;i<sb.length();i++) {
			try {

				even.add(String.valueOf(sb.charAt(i+1)));
				i=i+1;
			}
			catch(Exception e) {
				continue;
			}
		}

		//To sum even charcters
		int evensum=0;
		for(int i=0;i<even.size();i++) {
			int eachdigit;
			String getEachAlphabet = even.get(i);
			char value=getEachAlphabet.charAt(0);
			if(Character.isDigit(value)) {
				eachdigit=(int) evenNumbervalue.get(getEachAlphabet);
			}
			else {

				eachdigit=(int) evenalphabets.get(getEachAlphabet);
			}
			evensum=evensum+eachdigit;

		}

		//Calculate Remainder
		int modvalue= (evensum+oddsum)%26;

		//To get final number for checkValue
		//To get final number for checkValue
		int[] arr_Alphavalue_Final = new int[] { 0, 1, 2, 3, 4,5, 6, 7, 8, 9,10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23,24,25};

		Map FinalAlphabets = new HashMap<>();
		for (int j = 0; j < CharList_odd.size(); j++) {
			FinalAlphabets.put( arr_Alphavalue_Final[j],(CharList_odd.get(j)));
		}

		//To get the Check Value
		String CheckValue = String.valueOf(FinalAlphabets.get(modvalue));
		sb.append(CheckValue);
	}

	public static String generateDOB() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, getRandomInteger(-50, -20));
		Date date = calendar.getTime();
		SimpleDateFormat formDate = new SimpleDateFormat("MMM dd, yyyy");
		String strDate = formDate.format(date);
		SimpleDateFormat formDate1 = new SimpleDateFormat("MM,dd,yyyy");//startParag
		DOB = formDate1.format(date);//End

		return strDate;

	}
	
	public static int getRandomInteger(int maximum, int minimum) {
		return ((int) (Math.random() * (maximum - minimum))) + minimum;
	}
	
	//To Get the Month as Alphabet for National ID
		private static String GetMonthAsAlphabet(String Month) {
			String result="";
			switch (Month) {
			case "01": result="A";
			break;
			case "02": result="B";
			break;
			case "03": result="C";
			break;
			case "04": result="D";
			break;
			case "05": result="E";
			break;
			case "06": result="H";
			break;
			case "07": result="L";
			break;
			case "08": result="M";
			break;
			case "09": result="P";
			break;
			case "10": result="R";
			break;
			case "11": result="S";
			break;
			case "12": result="T";
			break;
			default: result="";
			break;
			}
			return result;
		}

}
