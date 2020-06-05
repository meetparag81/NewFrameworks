package test;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import helper.Excelhelper.Exls_Reader;
import helper.resorce.ResourceHelper;
import pages.HomePage;
import pages.LoginPage;
import testBase.TestBase;

public class HomePageTest extends TestBase
{
	public HomePageTest()
	{
		super();
	}
	
	private LoginPage LoginPage;
	Exls_Reader reader = new Exls_Reader(ResourceHelper.GetResourcePath("\\src\\main\\java\\helper\\exceldata\\Frameworkworksheet.xlsx"));
	private HomePage HomePage;
	@BeforeMethod
	public void SetUp()
	{
		TestBase.initalization();
		LoginPage = new LoginPage();
		String un= reader.getCellData("Login", "Username", 2);
		String psw= reader.getCellData("Login", "Password", 2);
		
		HomePage = LoginPage.login(un, psw);
		
	}
	@Test(priority=0,enabled=true)
	public void HomepageLogoTest()
	{
		String act = HomePage.ClicktheAdd();
		String exp= reader.getCellData("Login", "Logo",2);
		Assert.assertEquals(act, exp);
	}
	
	@Test(priority=1,enabled=true)
	public void ProxywithUser()
	{
		HomePage.ClicktheAdd();
		HomePage.ProxywithUser();
		String act= HomePage.ClicktheAdd();
		String exp= reader.getCellData("Login", "Logo",2);
		Assert.assertEquals(act, exp);
		
	}
	
	
	@AfterMethod
	public void TearDown()
	{
		driver.quit();
	}

}
