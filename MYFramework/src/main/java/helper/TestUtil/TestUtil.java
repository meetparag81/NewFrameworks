package helper.TestUtil;

import java.io.File;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import helper.logger.LoggerHelper;
import testBase.TestBase;

public class TestUtil extends TestBase {
	
public static long IMPLICIT_WAIT = 30;
public static long PAGE_LOAD_TIMEOUT = 60;
private static  Logger log=LoggerHelper.GetLogger(TestUtil.class);
private static String destination;
private static JavascriptExecutor jsExec;
protected static WebDriverWait wait;
public static long webdriverTimeout=30;

	
	

	public static void ClickOn(WebDriver driver,WebElement locator,int timeout)
	{
		new WebDriverWait(driver, timeout).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeSelected(locator));
		log.info("Driver is wait for the element  to be clicked.");
		locator.click();
		log.info("Driver is wait for the element  to be clicked.");
		
	}
	public static void  VisibleOn(WebDriver driver,WebElement element,int timeout)
	{
		new WebDriverWait(driver, timeout).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.visibilityOf(element));
		log.info("Driver is waited for"+element+"to be seen for" + timeout);
	}
	
	public static void  VisibleElementsOn(WebDriver driver,List<WebElement> element,int timeout)
	{
		new WebDriverWait(driver, timeout).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.visibilityOfAllElements(element));
	}
	
	public static void ScrollthePage(JavascriptExecutor js,WebDriver driver)
	{
		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		js1.executeScript("scroll(0, 250);");
		
	}
	public static boolean iselementExists (WebElement element)
	{
		boolean result=false;;
		try{
			if(element.isDisplayed())
				result=true;
		}
		catch(Exception e){
			result=false;
		}
		return result;
	}
	
	
	
	
	public static void takeScreenshotAtEndOfTest1()
	{
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		try 
		{
		FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots1/" + System.currentTimeMillis() + ".png"));
		}
		catch (IOException e) 
		{
			System.out.println("Exception are" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void takescreenshot(WebDriver driver, String screenshotname)  
	{
		TakesScreenshot ts=(TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		try 
		{
			log.info("Screenshot Name is"+ screenshotname+".png");
			FileUtils.copyFile(source, new File(currentDir + "/screenshots"+ screenshotname +".png"));
		} 
		catch (IOException e) 
		{
			System.out.println("Exception are" + e.getMessage());
			e.printStackTrace();
		}	

	}
	public static  void ActionForMovetoElement(WebElement element )
	{
		
		Actions act1 = new Actions(driver);
		act1.moveToElement(element);
		
	}
	
	public static String getScreenshot(WebDriver driver, String screenshotName,int i, int j) throws Exception 
		{
	        //below line is just to append the date format with the screenshot name to avoid duplicate names 
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy"  +","+ "hh-mm-ss");
	    formatter = new SimpleDateFormat("dd,MM,yyyy"); 
	    String dateName = new SimpleDateFormat("dd,MM,yyyy" +","+ "hh-mm-ss").format(new Date());
	      //  String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
	TakesScreenshot ts = (TakesScreenshot) driver;
	File source = ts.getScreenshotAs(OutputType.FILE);
	if(!(i==j))
	{
		 //after execution, you could see a folder "FailedTestsScreenshots" under src folder
		
		 destination = System.getProperty("user.dir") + "/PassesTestsScreenshots/"+screenshotName+dateName+".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
	}
	else
	{
	       //after execution, you could see a folder "FailedTestsScreenshots" under src folder
		log.info("Screenshot Name is"+ screenshotName+".png");
		 destination = System.getProperty("user.dir") + "/FailedTestsScreenshots/"+screenshotName+dateName+".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
	}
	 
	
	
	log.info(" Failed screenshot saved at DestinationLocation" +destination );
	        //Returns the captured file path
	return destination;
		}
	public static String getMonthForInt(int num) 
	{
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11 ) 
        {
            month = months[num];
        }
        return month;	
		
	}
	public static String Date()
	{
		Date date1 = new Date();  
	    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
	    formatter = new SimpleDateFormat("MM/dd/yyyy"); 
	    String strDate = formatter.format(date1);  
		strDate = formatter.format(date1); 
		
		
		return strDate;
		
	}
	public static String DateForCompare()
	{
		Date date1 = new Date();  
	    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
	    formatter = new SimpleDateFormat("MM/dd/yy"); 
	    String strDate = formatter.format(date1);  
		strDate = formatter.format(date1); 
		
		
		return strDate;
		
	}
	
	public static void IntDate()
	{
		LocalDateTime now = LocalDateTime.now();
		int year = now.getYear();
		int month = now.getMonthValue();
		int day = now.getDayOfMonth();
		int hour = now.getHour();
		int minute = now.getMinute();
		int second = now.getSecond();
	}
	public static String CurrentDate()
	{
		Date date = new Date();  
	    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
	    formatter = new SimpleDateFormat("dd-MMM-yyyy"); 
	    String strDate = formatter.format(date);  
		return strDate;  
	}
	public static void ActionForMovetoElementSelect(Select select, WebElement element){
		Select select1 = new Select(element);
		Actions act1 = new Actions(driver);
		act1.moveToElement((WebElement) select1);
		
	}

	
	
	public boolean ckEdit(WebDriver driver, ExtentTest extentTest, String[] identifier, String value, String controlName,String xpath) {
        try {
                       WebElement element = driver.findElement(By.xpath(xpath));
                       ((JavascriptExecutor) driver).executeScript("CKEDITOR.instances['" + identifier[0] + "'].setData('" + value + "');");
        } catch (Exception e) {
                       return false;
        }
        return true;
}

public boolean jsClick(WebDriver driver, ExtentTest extentTest, String[] identifier, String value, String controlName,String xpath) {
	try {
		WebElement element = driver.findElement(By.xpath(xpath));
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", element);
	} catch (Exception e) {
    return false;
	}
	return true;
}
public void waitTillbusycursor() throws InterruptedException{
while(TestUtil.iselementExists(driver.findElement(By.xpath("//div[contains(@id,'shim')]")))) {

Thread.sleep(1000);

}

}

public static WebElement WaitTillclickable(WebElement element) throws InterruptedException{
	WebDriverWait wait = new WebDriverWait(driver, 30);
	WebElement desireelement = wait.until(ExpectedConditions.elementToBeClickable(element));
	while((desireelement==null)){
		Thread.sleep(5);
		}
	return desireelement;
}
public static String CurrentTimestamp(){
	 
	 String timestamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
	return timestamp;
}




}
