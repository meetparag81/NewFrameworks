package helper.TestUtil;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import helper.logger.LoggerHelper;
import testBase.TestBase;

public class CustomMethodhelper extends TestBase {
	static WebDriverWait wait;
	private JavascriptExecutor jsExec;
	private static  Logger log=LoggerHelper.GetLogger(CustomMethodhelper.class);
	
	public static void enterText(WebElement input, String text){
		if (text == null || text.isEmpty())
			return;
		waitForClickable(input);
		input.clear();
		if (text.equals("NULL"))
			return;
		input.sendKeys(text);
	}

	public static void click(WebElement elem){
		waitForClickable(elem);
		elem.click();
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

	public static boolean waitForClickable(WebElement element){
		boolean result;
		try {
			wait = new WebDriverWait(driver, TestUtil.webdriverTimeout);
			WebElement elm=wait.until(ExpectedConditions.elementToBeClickable(element));
			if(elm.equals(null)) {
				result = false;
			}
			else {
				result=true;
			}
		}
		catch(Exception elementnotfound) {
			result=false;
		}
		return result;
	}
	
	

	public void waitForJQueryLoad() {
		try {
			ExpectedCondition<Boolean> jQueryLoad = driver -> ((Long) ((JavascriptExecutor) this.driver).executeScript("return jQuery.active") == 0);
			boolean jqueryReady = (Boolean) jsExec.executeScript("return jQuery.active==0");
			if (!jqueryReady) {
				wait.until(jQueryLoad);
			} 
		}
		catch (Exception ignored) {
		}
	}

	public boolean isEditable(WebElement element){
		boolean result=true;
		try {
			String attribute=element.getAttribute("readonly");
			if(attribute.equals("true")) {
				result=false;
			}
		}
		catch(Exception propertynotfound) {
			return result;
		}
		return result;
	}

	public void sendKeyPress(WebElement elm, Keys k){
		if (elm != null)
			elm.sendKeys(k);
	}
	
	public static WebElement WaitTillElementisnotclickable(WebElement element) throws InterruptedException{
		WebDriverWait wait = new WebDriverWait(driver, 60);
		WebElement desireelement = wait.until(ExpectedConditions.elementToBeClickable(element));
		while((desireelement==null)){
			Thread.sleep(5);
			log.info("waited for 5seconds");
		}
		return desireelement;
	}




}
