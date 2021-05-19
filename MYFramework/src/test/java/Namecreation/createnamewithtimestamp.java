package Namecreation;

import java.text.SimpleDateFormat;
import java.util.Date;

public class createnamewithtimestamp {

	public static void main(String[] args) {
		String outputTimeStamp=new SimpleDateFormat("yyMMddhhmmss").format(new Date());
		for(int i=0;i<6;i++){
			int max=100;
			int min=1;
			int b = (int)(Math.random()*(max-min+1)+min);  
			String Stringoutput = "Test_"+b+"_"+outputTimeStamp;
			System.out.println(Stringoutput);
		}

	}

}
