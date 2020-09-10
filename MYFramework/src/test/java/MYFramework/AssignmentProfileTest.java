package MYFramework;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import helper.Excelhelper.Exls_Reader;
import helper.resorce.ResourceHelper;
import testBase.TestBase;
import pages.AssignmentprofilePage;
import pages.HomePage;
import pages.LearningAdMinistrationPage;
import pages.LoginPage;

public class AssignmentProfileTest extends TestBase {
	
	private LoginPage LoginPage;
	private HomePage HomePage;
	Exls_Reader reader = new Exls_Reader(ResourceHelper.GetResourcePath("\\src\\main\\java\\helper\\exceldata\\LMS_TestData.xlsx"));
	private LearningAdMinistrationPage LearningadminPage;
	private AssignmentprofilePage Assignment;
	
	
	

	public AssignmentProfileTest(){
		super();
	}
	
@BeforeMethod
public void SetUp() throws InterruptedException{
	System.out.println();
	TestBase.initalization();
	
	LoginPage = new LoginPage();
	//HomePage= LoginPage.login(prop.getProperty("username"), prop.getProperty("password"));
	String username = reader.getCellData("LMSData", "Username", 2);
	String password = reader.getCellData("LMSData", "Password", 2);
	HomePage= LoginPage.login(username, password);
	LearningadminPage=HomePage.ClickOnTheTile();
	Assignment=LearningadminPage.ClickOnAssignMentProfiles();
	
	
}

@Test
public void Test(String profileid){
	
	Assignment.ClickonAddNew();
	for(int i=2;i<reader.getRowCount("LMSData");
	profileid = reader.getCellData("LMSData", "Assignment_Profile_ID", i));
	Assignment.FillTheMandetoryDetails(profileid);
	
}



@AfterMethod
public void voidTearDown(){
	driver.quit();
	
}


	
	
}
