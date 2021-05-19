package stringManipulation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Splitstringandkeepcharacters {
	private static boolean flag;
	

	public static void main(String[] args) {
		/*String str="[This record was not saved because there were errors in other records for 10000269.]";
		 String[] splitstring = str.split(".");
		 char lastcharacter= str.charAt(str.length() -3);
		  String[] input = str.substring(str.length() - 2).split("]");
		 try{
		  int value = Character.getNumericValue(lastcharacter);	  
		  flag=true;
		 }
		 catch (Exception e) {
			// TODO: handle exception
		}
		 if(flag){
		 }*/
		
		String str="[This record was not saved because there were errors in other records for 10000269.]";
        String str1=str;
        int index = str1.lastIndexOf("for ");
        int index1= str1.lastIndexOf("]");
        str1=str1.substring(index,index1).trim();
        System.out.println(str1);
        str=str.replace(str1, "");
        System.out.println(str);
			 
		 
		 
			 
		/*boolean numberflag = str.matches(".*[0-9].*");
		if(numberflag) {
		String cellnamme = str.split("(?<=\\D)(?=\\d)")[0];
		System.out.println(cellnamme);
		
		String foo = "bar123";
		Pattern p = Pattern.compile(".*[0-9]+$");
		Matcher m = p.matcher(foo);
		if(m.matches())
		    System.out.println("yeah!");
		*/
		}
	}


