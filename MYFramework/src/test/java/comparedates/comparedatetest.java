package comparedates;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Locale;

public class comparedatetest {

	public static void main(String[] args) throws ParseException {
		String date1="4/9/2021 02:27 AM America/New York";
		
		
		String date2="4/9/2021 02:27";
		int endIndex=date1.indexOf("AM");
		date1=date1.substring(0, endIndex);
		SimpleDateFormat format = new SimpleDateFormat("d/M/yyyy hh:mm");
		//SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.US);
		Date date = format.parse(date1);
		//format = new SimpleDateFormat("dd/MM/yyyy hh:mm ,a");
		String dateString = format.format(date);
		System.out.println(dateString);
		System.out.println(dateString.compareTo(date2));
		
		

		
	}  
}
       
