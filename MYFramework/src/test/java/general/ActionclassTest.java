package general;

import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import actions.Actionhelper;
import helper.TestUtil.CustomMethodhelper;
import testBase.TestBase;

public class ActionclassTest extends TestBase {
	
	ActionclassTest(){
		super();
	}
	
	@ BeforeMethod
	public void Setup(){
		TestBase.initalization("https://www.amazon.in/");
	}
	
	@Test
	public void MoveToElement(){
		CustomMethodhelper.click(driver.findElement(By.xpath("(//*[contains(@class,'nav')])[31]")));
		Actionhelper.Movetelement(driver.findElement(By.xpath("(//*[contains(@class,'nav')])[31]")));
		driver.findElement(By.xpath("//span[text()='Your Account']//parent::a")).click();
		
	}
	
	@ AfterMethod
	public void Teardown(){
		driver.quit();
	}

}
