package test;

import java.util.ArrayList;
import java.util.Iterator;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import helper.Excelhelper.Exls_Reader;
import helper.resorce.ResourceHelper;
import pages.CreateRequisitionPage;
import pages.HomePage;
import pages.LoginPage;
import testBase.TestBase;

public class CreateRequisitionTest extends TestBase {
	Exls_Reader reader = new Exls_Reader(ResourceHelper.GetResourcePath("\\src\\main\\java\\helper\\exceldata\\Frameworkworksheet.xlsx"));
	private LoginPage LoginPage;
	private HomePage HomePage;
	private CreateRequisitionPage CreateRequisitionPage;
	
	public CreateRequisitionTest()
	{
		super();
	}
	
	@BeforeMethod
	public void SetUP(){
		TestBase.initalization();
		LoginPage = new LoginPage();
		String un= reader.getCellData("Login", "Username", 2);
		String psw= reader.getCellData("Login", "Password", 2);
		HomePage = LoginPage.login(un, psw);
		HomePage.ClicktheAdd();		
		CreateRequisitionPage=HomePage.clickonCreareRequisition();
		
	}
	@Test(priority=0, groups = {"functional" },dataProvider= "getTestData",enabled= true)
	public void EnterMandetoryFieldTest(String TitleName,String HrManager,String Recruiter, String RequisionName, String status, String positionNo, String HiringType, String country,String state,String city, String company,String division)
	{
		int rows = reader.getRowCount("CreateRequisition");
		for(int i = 2; i < rows; i++)
		{
			
			CreateRequisitionPage.EnterMandetoryFields(TitleName, HrManager, Recruiter, RequisionName, status, positionNo, HiringType, country, state, city, company, division );
			rows--;
			if(i==rows)
			{
				break;
			}
		
		}
			
		}
	
	
	@DataProvider
	public  Iterator<Object[]> getTestData()
	{
		ArrayList<Object[]>	Mandatoryfields= pages.CreateRequisitionPage.getdatafromExcel();	
	return Mandatoryfields.iterator();
	}
		
	
	@AfterMethod
	public void TearDown()
	{
		driver.quit();
	}

}
