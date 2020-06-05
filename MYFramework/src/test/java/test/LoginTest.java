package test;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import helper.Excelhelper.Exls_Reader;
import helper.resorce.ResourceHelper;
import pages.LoginPage;
import testBase.TestBase;

public class LoginTest extends TestBase {
	
	private LoginPage LoginPage;
	Exls_Reader reader = new Exls_Reader(ResourceHelper.GetResourcePath("\\src\\main\\java\\helper\\exceldata\\Frameworkworksheet.xlsx"));
	public LoginTest(){
		super();
	}
	
	@BeforeMethod
	public void SetUp()
	{
		TestBase.initalization();
		LoginPage= new LoginPage();
		
	}
	
	@Test
	public void LoginTest()
	{
		String un= reader.getCellData("Login", "Username", 2);
		String psw= reader.getCellData("Login", "Password", 2);
		LoginPage.login(un, psw);
	}
	
	@AfterMethod
	public void TearDown()
	{
		driver.quit();
	}

}
