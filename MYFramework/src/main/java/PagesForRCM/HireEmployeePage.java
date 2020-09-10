package PagesForRCM;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import helper.Excelhelper.Exls_Reader;
import helper.Pick.Pickhelper;
import helper.TestUtil.TestUtil;
import helper.logger.LoggerHelper;
import helper.resorce.ResourceHelper;
import testBase.TestBase;

public class HireEmployeePage extends TestBase 
{
	@FindBy(xpath = "//*[text()=\"Add New Employee\"]/following::*[text()=\"Last Start Date\"][1]/following::input[1]")WebElement StartDate;
	@FindBy(xpath = "//*[text()=\"Company\"][1]/following::input[1]")WebElement company;
	@FindBy(xpath = "//*[text()=\"Event Reason\"][1]/following::input[1]")WebElement eventreasoning;
	@FindBy(xpath  ="//*[text()=\"Add New Employee\"]/following::*[text()=\"First Name\"][1]/following::input[1]")WebElement FirstName ;
	private  Logger log=LoggerHelper.GetLogger(HireEmployeePage.class)	;
	Exls_Reader reader = new Exls_Reader(ResourceHelper.GetResourcePath("\\src\\main\\java\\helper\\exceldata\\Frameworkworksheet.xlsx"));
	
	
	
public HireEmployeePage()
{
	PageFactory.initElements(driver, this);
}

public void AddMandetoryfields()
{
	try
	{
	String date= TestUtil.DateForCompare();
	TestUtil.VisibleOn(driver, StartDate, 30);
	StartDate.clear();
	StartDate.sendKeys(date);
	String com=reader.getCellData("AddNewEmployee", "companyname", 2);
	Pickhelper.picktheElement(com, company);
	Thread.sleep(2000);
	String EventReason=reader.getCellData("AddNewEmployee", "Event", 2);
	Pickhelper.picktheElement(EventReason, eventreasoning);
	String Name=reader.getCellData("AddNewEmployee", "Event", 2);
	FirstName.sendKeys(Name);
	}
	catch(Exception e)
	{
		log.info(e.getCause());
	}
	
	
}
}
