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
	@FindBy(xpath="//bdi[text()='Log in']//ancestor::span[1]")WebElement login;
	private  Logger log=LoggerHelper.GetLogger(LoginPage.class)	;
	Exls_Reader reader = new Exls_Reader(ResourceHelper.GetResourcePath("\\src\\main\\java\\helper\\exceldata\\Frameworkworksheet.xlsx"));
	public LoginPage()	{
		PageFactory.initElements(driver, this); 
		
	}
	
	public HomePage login(String un, String psw) throws InterruptedException {
	
		
		
		username.sendKeys(un);
		Thread.sleep(2000);
		password.sendKeys(psw);
		Thread.sleep(2000);
		login.click();
		
		//JavascriptHelper.clickTheElement(SignIn);
		
		
		return new HomePage();
		
	}
	

}


