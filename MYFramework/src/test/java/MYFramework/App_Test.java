package MYFramework;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import helper.Excelhelper.Exls_Reader;
import helper.resorce.ResourceHelper;
import pages.AddNewClassPage;
import pages.HomePage;
import pages.LearningAdMinistrationPage;
import pages.LoginPage;
import testBase.TestBase; 

public class App_Test extends TestBase
{
	 private LoginPage LoginPage;
	private HomePage Homepage;
	private LearningAdMinistrationPage LearningAdminPage;
	Exls_Reader reader = new Exls_Reader(ResourceHelper.GetResourcePath("\\src\\main\\java\\helper\\exceldata\\Frameworkworksheet.xlsx"));
	private AddNewClassPage NewClassPage;

	@BeforeMethod
	public void SetUp()
	{
		
		TestBase.initalization();
		LoginPage = new LoginPage();
		System.out.println();
		String username = reader.getCellData("Login", "Username", 2);
		String password = reader.getCellData("Login", "Password", 2);
		Homepage= LoginPage.login(username, password);
		LearningAdminPage=Homepage.ClickOnTheTile();
		LearningAdminPage.ClickOnTheLearningActivities();
		  LearningAdminPage.ClickOnTheClasses();
		
	}
  @Test(priority=0,enabled=false)
  public void ClassTitleTest(){
	  

	  String actual=LearningAdminPage.GetTitleClasses();
	  Assert.assertEquals(actual, reader.getCellData("LMS", "ClassesText", 2));
	  
  }
  @Test(priority=1, enabled=true)
  public void CreateClassTest(){
	  NewClassPage= LearningAdminPage.AddNewClass();
	  NewClassPage.FillTheRequiredFieldsinClassPage();
	  Assert.assertEquals(true, false, "testcaseFailed");
	  
	  
  }
  
  @AfterMethod
	public void TerDown()
	{
		driver.quit();
	}
  
}
