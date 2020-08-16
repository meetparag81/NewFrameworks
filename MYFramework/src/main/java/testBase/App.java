package testBase;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import helper.Excelhelper.Exls_Reader;
import helper.resorce.ResourceHelper;
import pages.LoginPage; 

public class App extends TestBase
{
	LoginPage LoginPage;
	Exls_Reader reader = new Exls_Reader(ResourceHelper.GetResourcePath("\\java\\com_Milan_TestData\\Milandata.xlsx"));

	@BeforeMethod
	public void SetUp()
	{
		TestBase.initalization();
		LoginPage = new LoginPage();
		String username = reader.getCellData("Login", "Username", 2);
		String password = reader.getCellData("Login", "Password", 2);
		LoginPage.login(username, password);
	}
  @Test(priority=0)
  public void AppTestForPassCondition(){
	  Assert.assertEquals(true, true, "testcasePassed");
	  
  }
  @Test(priority=1)
  public void AppTestForFailCondition(){
	  Assert.assertEquals(true, false, "testcaseFailed");
	  
	  
  }
  
  @AfterMethod
	public void TerDown()
	{
		driver.quit();
	}
  
}
