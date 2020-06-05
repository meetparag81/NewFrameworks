package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import helper.Excelhelper.Exls_Reader;
import helper.logger.LoggerHelper;
import helper.resorce.ResourceHelper;
import testBase.TestBase;

public class LoginPage extends  TestBase{
	@FindBy(name="username")WebElement username; 
	@FindBy(name="password")WebElement password;
	@FindBy(xpath="//bdi[text()='Log in']//ancestor::span[1]")WebElement SignIn;
	private  Logger log=LoggerHelper.GetLogger(LoginPage.class)	;
	Exls_Reader reader = new Exls_Reader(ResourceHelper.GetResourcePath("\\src\\main\\java\\helper\\exceldata\\Frameworkworksheet.xlsx"));
	public LoginPage()	{
		PageFactory.initElements(driver, this); 
		
	}
	
	public HomePage login(String un, String psw) {
	
		
		username.clear();
		username.sendKeys(un);
		password.sendKeys(psw);
		try
		{
		SignIn.click();
		//JavascriptHelper.clickTheElement(SignIn);
		}
		catch(Exception e)
		{
			log.info(e.getMessage());
			driver.navigate().refresh();
		}
		return new HomePage();
		
		
	}
	

}


