package helper.alerthelper;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import testBase.TestBase;

public class Alerthelper extends TestBase {
	static WebDriverWait wait;
	
	public static  boolean isalertexists()
	{
		boolean result=false;
		try {
			driver.switchTo().alert();
			result=true;
		}
		catch(Exception noalertfound) {
			return result;
		}
		return result;
	}

	public  static void acceptAlert()
	{
		
		if (wait.until(ExpectedConditions.alertIsPresent()) != null){
			Alert alert = driver.switchTo().alert();
			alert.accept();
		}
	}
	
	public  static void CancelAlert()
	{
		
		if (wait.until(ExpectedConditions.alertIsPresent()) != null){
			Alert alert = driver.switchTo().alert();
			alert.accept();
		}
	}
	
	public  static void getAlertMessage()
	{
		
		if (wait.until(ExpectedConditions.alertIsPresent()) != null){
			String alert = driver.switchTo().alert().getText();
			alert.toString();
		}
	}
	
	

}
