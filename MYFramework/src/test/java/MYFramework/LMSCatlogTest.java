package MYFramework;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import helper.Excelhelper.Exls_Reader;
import helper.TestUtil.TestUtil;
import helper.resorce.ResourceHelper;
import pages.HomePage;
import pages.LearningAdMinistrationPage;
import pages.LibrariesPage;
import pages.LoginPage;
import testBase.TestBase;


public class LMSCatlogTest extends TestBase   {
	
	
	public LMSCatlogTest(){
		super();
	}
	
	private LoginPage LoginPage;
	private HomePage HomePage;
	private LearningAdMinistrationPage LearningAdMinnPage;
	private LibrariesPage LibrariesPage;
	static Exls_Reader reader = new Exls_Reader(ResourceHelper.GetResourcePath("\\src\\main\\java\\helper\\exceldata\\LMS_TestData.xlsx"));

	@BeforeMethod
	public void Setup() throws InterruptedException{
		System.out.println();
		TestBase.initalization();
		LoginPage = new LoginPage();
		String username = reader.getCellData("AddLibraries", "username", 2);
		String password = reader.getCellData("AddLibraries", "password", 2);
		HomePage=LoginPage.login(username,password);
		LearningAdMinnPage=HomePage.ClickOnTheTile();
	
}
	@Test
	public void CreateCatlogueTest() throws InterruptedException{
		LibrariesPage=LearningAdMinnPage.ClickonLibraries();
		LibrariesPage.CreateLibraries();
	}
	
	@AfterMethod
	public void Teardown()
	{
		driver.quit();
	}
}
