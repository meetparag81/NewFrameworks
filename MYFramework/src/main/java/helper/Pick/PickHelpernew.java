package helper.Pick;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PickHelpernew {
	
	public static String pick(WebDriver driver,WebElement ele,String value,String xpath) {
		String result="";
		boolean valueCleared=false;
		try {
			if(!ele.getAttribute("value").equalsIgnoreCase("")){   
				JavascriptExecutor js = (JavascriptExecutor)driver;
				js.executeScript("arguments[0].value = '';", ele);
				valueCleared=true;
			}
			if (value.split("\\(")[0].trim().length() >= 27) {
				if(valueCleared) {
					ele.sendKeys((value.split("\\(")[0].trim()).substring(0, 27).trim());
					JavascriptExecutor js = (JavascriptExecutor)driver;
					js.executeScript("arguments[0].value = '';", ele);
					ele.sendKeys((value.split("\\(")[0].trim()).substring(0, 27).trim());
				}
				else {
					ele.sendKeys((value.split("\\(")[0].trim()).substring(0, 27).trim());
				}
			} 
			else {
				if(valueCleared) {
					ele.sendKeys(value.split("\\(")[0].trim());
					JavascriptExecutor js = (JavascriptExecutor)driver;
					js.executeScript("arguments[0].value = '';", ele);
					ele.sendKeys(value.split("\\(")[0].trim());
				}
				else {
					ele.sendKeys(value.split("\\(")[0].trim());
				}
			}
			waitForTime(5000);
			ele=driver.findElement(By.xpath(xpath));
			//String listXpath = "//div[contains(@class,'sapMComboBoxBasePicker-CTX') and contains(@style,'visibility: visible')]//ul/li[not(contains(@class, 'sapMSelectListItemBaseInvisible'))]";
			String listXpath = "//div[contains(@class,'sapMComboBoxBasePicker-CTX') and contains(@style,'visibility: visible')]//ul/li[not(contains(@class, 'sapMSelectListItemBaseInvisible')) and not(contains(@id, 'sap-ui-invisible'))]";
			List<WebElement> listElement = driver.findElements(By.xpath(listXpath));
			System.out.println("List 1 " + listElement.size());
			if (listElement == null || listElement.size() == 0) {
				listXpath = "//div[contains(@class,'sapMPopoverScroll')]//ul[contains(@class,'sapMSelectList')]/li";
				listElement = driver.findElements(By.xpath(listXpath));
				System.out.println("List 2 " + listElement.size());
			}
			if (listElement == null || listElement.size() == 0) {
				listXpath = "//div[contains(@class,'sf-combo-listselect')]//ul/li[contains(@role,'option')]";
				listElement =driver.findElements(By.xpath(listXpath));
				System.out.println("List 3 " + listElement.size());
			}
			if (listElement == null || listElement.size() == 0) {
				listXpath = "//div[@class='sapMPopoverCont' and contains(@style, 'overflow: auto;')]/following::td[@class='sapMListTblCell']";
				listElement = driver.findElements(By.xpath(listXpath));
				System.out.println("List 4 " + listElement.size());
			}
			if (listElement == null || listElement.size() == 0) {
				listXpath = "//div[@id='phSearchInput_autoCompleteBoxId']//ul/li[contains(@role,'presentation') and contains(@onmouseover,'phSearchInput')]";
				listElement = driver.findElements(By.xpath(listXpath));
				System.out.println("List 5 " + listElement.size());
			}
			result = "";
			String innerText = "";
			int counter=0;
			int elementCounter=0;
			for (WebElement el: listElement) {
				//ele.sendKeys(Keys.DOWN);
				elementCounter=elementCounter+1;
				counter=0;
				waitForTime(500);
				innerText = el.getText().trim();
				while(innerText.contains("Loading")) {
					waitForTime(3000);
					innerText = el.getText().trim();
					System.out.println("Innertext is "+innerText);
					counter=counter+1;
					if(counter>=15) {
						innerText="";
					}
				}
				waitForTime(500);
				try {
					innerText = el.getText().trim();
					System.out.println("Innertext is "+innerText);
				}
				catch(Exception innerTextnotfound) {
					innerText="";
				}
				if(innerText.isEmpty() || innerText==null || innerText.contains("Loading")) {
					try {
						el=driver.findElement(By.xpath("("+listXpath+")["+elementCounter+"]"));
						waitForTime(500);
						innerText = el.getText().trim();
						System.out.println("Innertext is "+innerText);
					}
					catch(Exception elementnotfound) {
						System.out.println("Failed to perform pick selection for field ");
					}
				}
				if (innerText.contains(value)) {
					waitForTime(1000);
					//ele.sendKeys(Keys.ENTER);
					el.click();
					result=innerText;
					break;
				}
				else if(value.contains(innerText)) {
					waitForTime(1000);
					//ele.sendKeys(Keys.ENTER);
					el.click();
					result=innerText;
					break;
				}
				waitForTime(250);
			}
			waitForTime(2000);

		} catch (Exception e) {
			//failureReason = e.getMessage();
			return result;
			//throw e;
		}
		return result;
	}
	
	private static void waitForTime(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			//failureReason = e.getMessage();
			e.printStackTrace();
		}
	}

}
