package helper.Pick;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import helper.logger.LoggerHelper;
import testBase.TestBase;

public class Pickhelper extends TestBase {
	WebElement element;
	static WebDriver driver;
	private static  Logger log=LoggerHelper.GetLogger(Pickhelper.class)	;
	private static String value1;
	private static  boolean found;
	
	public static boolean picktheElement( String value,WebElement element) {
		try {
			
			// this.element = element;
			
			Thread.sleep(2000);

			if(!element.getAttribute("value").equalsIgnoreCase(""))
			{   JavascriptExecutor js = (JavascriptExecutor)driver;
                js.executeScript("arguments[0].value = '';", element);
			}
			
			if (value.split("\\(")[0].trim().length() >= 27) {
				 value1=value.split("\\(")[0].trim().substring(0, 27).trim();
				log.info("value entered in the box is"+ value1);
				element.sendKeys((value.split("\\(")[0].trim()).substring(0, 27).trim());
				
			} else {
				element.sendKeys(value.split("\\(")[0].trim());
				log.info("value entered in the box is"+ value.split("\\(")[0].trim());
			}
			
			Thread.sleep(5000);
			
			String listXpath = "//div[contains(@class,\"sapMComboBoxBasePicker-CTX\") and contains(@style, \"visibility: visible\")]//ul/li[not(contains(@class, \"sapMSelectListItemBaseInvisible\"))]";
			List<WebElement> listElement = driver.findElements(By.xpath(listXpath));
			if (listElement == null || listElement.size() == 0) {
				listXpath = "//div[contains(@class,\"sapMPopoverScroll\")]//ul[contains(@class, \"sapMSelectList\")]/li";
				listElement= driver.findElements(By.xpath(listXpath));
			}
			if (listElement == null || listElement.size() == 0) {
				listXpath = "//div[contains(@class,\"sf-combo-listselect\")]//ul/li[contains(@role,\"option\")]";
				listElement = driver.findElements(By.xpath(listXpath));
			}
			if (listElement == null || listElement.size() == 0) {
				listXpath = "//div[@class=\"sapMPopoverCont\" and contains(@style, \"overflow: auto;\")]/following::td[@class=\"sapMListTblCell\"]";
				listElement = driver.findElements(By.xpath(listXpath));
			}
			
			boolean flag=false;
			if (listElement == null || listElement.size() == 0) {
				listXpath = "//div[contains(@id,':container')]//div[@class='personACWrapper']/a";
				listElement = driver.findElements(By.xpath(listXpath));
				if(listElement.size()>0){
				   flag=true; 
				}
				
			}
			if (listElement == null || listElement.size() == 0) {
				listXpath = "(//tbody[contains(@class,'sapMTableTBody')])[1]//tr";
				listElement = driver.findElements(By.xpath(listXpath));
			}
			
			
			 found = false;
			String innerText = "";
			for (WebElement el: listElement) {
			//element.sendKeys(Keys.DOWN);
				innerText = el.getText().trim();
				if (innerText.contains("\n")) {

                              innerText = innerText.substring(0, innerText.indexOf("\n"));

               }
               if(flag){
                	innerText = el.getAttribute("title").trim(); 
                	if (innerText.contains(",")) {
                	    
                	innerText = innerText.substring(0, innerText.indexOf(","));
                	}
               }
               log.info("Inner text is " + innerText + " and value is " +value );
				if (innerText.equalsIgnoreCase(value)) {    
				el.click();
				log.info("Element" + innerText + "is" + "clicked");
				//	element.sendKeys(Keys.ENTER);
					found = true;
					break;
				}
			
		
			Thread.sleep(2000);
				
			
			}
			Thread.sleep(2000);
			
			
			

		} catch (Exception e) {
			//failureReason = e.getMessage();
			System.out.println(e.getMessage());
           return false;
			//throw e;
		}

		return found;
	}
	
	public String Getvalue(List elements)
	{
		
		return null;
		
	}
	
	public  static boolean Pick(String value,WebElement element) {
		boolean found = false;
		try {
			
			Thread.sleep(2000);

			if(!element.getAttribute("value").equalsIgnoreCase(""))
			{   JavascriptExecutor js = (JavascriptExecutor)driver;
                js.executeScript("arguments[0].value = '';", element);
			}
			value = value.replaceAll("\\u200e", "").replaceAll("\\u200f", "").replaceAll("\\h"," ");
			if (value.split("\\(")[0].trim().length() >= 27) {
			    String oValue = value.split("\\(")[0].trim();
				element.sendKeys(oValue.substring(0, 27).trim());
			} else {
			    String oValue = value.split("\\(")[0].trim();
				element.sendKeys(oValue);
				Thread.sleep(2000);
			}
			Thread.sleep(5000);
			

			String listXpath = "//div[contains(@class,\"sapMComboBoxBasePicker-CTX\") and contains(@style, \"visibility: visible\")]//ul/li[not(contains(@class, \"sapMSelectListItemBaseInvisible\"))]";
			List<WebElement> listElement = driver.findElements(By.xpath(listXpath));
			if (listElement == null || listElement.size() == 0) {
				listXpath = "//div[contains(@class,\"sapMPopoverScroll\")]//ul[contains(@class, \"sapMSelectList\")]/li";
				listElement = driver.findElements(By.xpath(listXpath));
			}
			if (listElement == null || listElement.size() == 0) {
				listXpath = "//div[contains(@class,\"sf-combo-listselect\")]//ul/li[contains(@role,\"option\")]";
				listElement = driver.findElements(By.xpath(listXpath));
			}
			if (listElement == null || listElement.size() == 0) {
				listXpath = "//div[contains(@class,\"ac-content\")]//ul/li[contains(@role,\"option\") and not(contains(@style, \"display: none\"))]";
				listElement = driver.findElements(By.xpath(listXpath));
			}
			if (listElement == null || listElement.size() == 0) {
				listXpath = "//div[@class=\"sapMPopoverCont\" and contains(@style, \"overflow: auto;\")]/following::td[@class=\"sapMListTblCell\"]";
				listElement = driver.findElements(By.xpath(listXpath));
			}
			if (listElement == null || listElement.size() == 0) {
				listXpath = "//div[@id=\"phSearchInput_autoCompleteBoxId\"]//ul/li[contains(@role,\"presentation\") and contains(@onmouseover,\"phSearchInput\")]";
				listElement = driver.findElements(By.xpath(listXpath));
			}
			boolean flag=false;
			if (listElement == null || listElement.size() == 0) {
				listXpath = "//div[contains(@id,':container')]//div[@class='personACWrapper']/a";
				listElement = driver.findElements(By.xpath(listXpath));
				if(listElement.size()>0){
				   flag=true; 
				}
			}
			
			String innerText = "";
			for (WebElement el: listElement) {
				innerText = el.getText().trim();
				if (innerText.contains("\n")) {
				    innerText = innerText.substring(0, innerText.indexOf("\n"));
				}
				if(flag) {
                	innerText = el.getAttribute("title").trim(); 
                	if (innerText.contains(",")) {               	    
                		innerText = innerText.substring(0, innerText.indexOf(","));
                	}
				}
				System.out.println("Innertext is " + innerText + "and value is " +value );
				if (innerText.equalsIgnoreCase(value.trim())) {
					el.click();
					System.out.println("Element" + innerText + "is" + "clicked");
					found = true;
					break;
				}
				Thread.sleep(250);
			}
Thread.sleep(2000);

		} catch (Exception e) {
           found = false;
		}

		return found;

}

	
}
