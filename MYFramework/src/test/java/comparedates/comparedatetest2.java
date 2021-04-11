package comparedates;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Locale;

public class comparedatetest2 {

	public static void main(String[] args) throws ParseException {
		String date1="12/1/2020 12:00";
		date1=date1+":00:00";
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.US);
		Date date = format.parse("12/1/2020 12:00:00 AM");
		format = new SimpleDateFormat("YYYY-mm-dd-HH:mm");
		String dateString = format.format(date);
		
		 Timestamp ts1 = Timestamp.valueOf(dateString);  
	        Timestamp ts2 = Timestamp.valueOf("2020-01-12 09:01:15");  
	        //compares ts1 with ts2  
	        int b3 = ts1.compareTo(ts2);  
	        if(b3==0){  
	            System.out.println("Both values are equal");  
	        }  
	        else if(b3>0){  
	            System.out.println("TimeSpan1 value is greater");  
	        }  
	        else{  
	            System.out.println("TimeSpan2 value is greater");  
	        }  
	    }  


		
	}  
       
