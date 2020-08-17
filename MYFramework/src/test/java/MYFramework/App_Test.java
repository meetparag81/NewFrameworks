package MYFramework;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import helper.Excelhelper.Exls_Reader;
import helper.resorce.ResourceHelper;
import pages.HomePage;
import pages.LoginPage;
import testBase.TestBase; 

public class App_Test extends TestBase
{
	LoginPage LoginPage;
	Exls_Reader reader = new Exls_Reader("C:\\Users\\Parag\\git\\newFramework\\MYFramework\\src\\main\\java\\helper\\exceldata\\Frameworkworksheet.xlsx");
	private HomePage Homepage;

	@BeforeMethod
	public void SetUp()
	{
		TestBase.initalization();
		LoginPage = new LoginPage();
		//String username = reader.getCellData("Login", "Username", 2);
		//String password = reader.getCellData("Login", "Password", 2);
		Homepage= LoginPage.login("bparag", "welcome123");
		Homepage.ClickOnTheTile();
		
	}
  @Test(priority=0)
  public void AppTestForPassCondition(){
	  Homepage.ClickOnTheLearningActivities();
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
