package LMSTest;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import PagesForRCM.AddNewClassPage;
import helper.Excelhelper.Exls_Reader;
import helper.resorce.ResourceHelper;
import pages.HomePage;
import pages.LearningAdMinistrationPage;
import pages.LearningUserPage;
import pages.LoginPage;
import testBase.TestBase;

public class RequestForClassTest extends TestBase  {	 private LoginPage LoginPage;
private HomePage Homepage;
private LearningAdMinistrationPage LearningAdminPage;
Exls_Reader reader = new Exls_Reader(ResourceHelper.GetResourcePath("\\src\\main\\java\\helper\\exceldata\\Frameworkworksheet.xlsx"));
private AddNewClassPage NewClassPage;
private LearningUserPage LearningPage;
private LearningUserPage LearningUserPage;

	
	public RequestForClassTest(){
		super();
	}
	
	@BeforeMethod
	public void SetUp() throws InterruptedException
	{
		
		TestBase.initalization();
		LoginPage = new LoginPage();
		System.out.println();
		String username = reader.getCellData("Login", "Username", 2);
		String password = reader.getCellData("Login", "Password", 2);
		Homepage= LoginPage.login(username, password);
		LearningUserPage = Homepage.ClickOnTheLearning();
		
		
	}
  @Test(priority=1,enabled=true)
  public void RequestTheClassTest(){
	  
	  LearningUserPage.ClickonTheRequestClass();
	  
	  
  }
	

}
