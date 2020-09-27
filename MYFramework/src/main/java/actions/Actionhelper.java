package actions;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import testBase.TestBase;

public class Actionhelper extends  TestBase {
	
	private static Actions act = new Actions(driver);
	
	
	public static void Movetelement(WebElement ele){
		 act = new Actions(driver);
		act.moveToElement(ele).build().perform();
	}
	
	public static void draganddrop(WebElement drag, WebElement drop){
		 act = new Actions(driver);
		act.dragAndDrop(drag, drop).build().perform();
	}
	
	public static void Sendtextincapitalletter(WebElement ele,String input){
		 act = new Actions(driver);
		act.click(ele).keyDown(Keys.SHIFT).sendKeys(input);
	}

}
