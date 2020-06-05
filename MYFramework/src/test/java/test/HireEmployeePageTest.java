package test;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import helper.Excelhelper.Exls_Reader;
import helper.logger.LoggerHelper;
import helper.resorce.ResourceHelper;
import testBase.TestBase;
import pages.HireEmployeePage;
import pages.HomePage;
import pages.LoginPage;
//test
public class HireEmployeePageTest extends TestBase {
	
	private LoginPage LoginPage;
	private HomePage HomePage;
	private  Logger log=LoggerHelper.GetLogger(HireEmployeePage.class)	;
	Exls_Reader reader = new Exls_Reader(ResourceHelper.GetResourcePath("\\src\\main\\java\\helper\\exceldata\\Frameworkworksheet.xlsx"));
	private HireEmployeePage HireEmployeePage;
	
	public HireEmployeePageTest()
	{
		super();
	}
	
	@BeforeMethod
	public void SetUp() throws InterruptedException
	{
		String un= reader.getCellData("Login", "Username", 3);
		String psw= reader.getCellData("Login", "Password", 3);
		
		TestBase.initalization();
		LoginPage = new LoginPage();
		HomePage = LoginPage.login(un,psw);
		HomePage.ProxywithUserWiithValue("HomePage", "value", 3);
		Thread.sleep(5000);
		 HireEmployeePage = HomePage.SelectAddEmployee();
	}
	
	@Test
	public void HireEmployeePageTest()
	{
		HireEmployeePage.AddMandetoryfields();
	}
	
	@AfterMethod
	public void TearDown()
	{
		driver.quit();
	}
	

}
