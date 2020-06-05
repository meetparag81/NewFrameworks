package pages;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import frame.helper.IframeHelper;
import helper.Excelhelper.Exls_Reader;
import helper.Pick.Pickhelper;
import helper.TestUtil.TestUtil;
import helper.logger.LoggerHelper;
import helper.resorce.ResourceHelper;
import helper.select.SelectHelper;
import testBase.TestBase;

public class CreateRequisitionPage extends TestBase {
	@FindBy(xpath = "//img[@title='Accenture, LLP Logo']//following::span[2]")
	WebElement Home;
	@FindBy(xpath = "//a[text()='Recruiting']")
	WebElement Recruiter;
	@FindBy(xpath = "//a[contains(text(),'Create New')]")
	WebElement CreateNew;
	@FindBy(xpath = "//a[text()='Requisition - Standard']")
	WebElement Standard;
	@FindBy(xpath = "//a[text()='Create New Job Requisition From Blank Template']")
	WebElement BlankTemplate;
	@FindBy(xpath = "//input[@name='createNewJobReq_title']")
	WebElement Title;
	@FindBy(xpath = "//label[contains(text(),'Due Date ')]//following::input[1]")
	WebElement Date;
	@FindBy(xpath="//label[contains(text(),'Due Date')]")WebElement DateText;

	
	@FindBy(xpath = "//a[text()='Job Profile']")WebElement Jobprofile;
	@FindBy(xpath = "//input[@value='Update']" )WebElement  Update;
	@FindBy(xpath = "//iframe[@class='cke_wysiwyg_frame cke_reset']")WebElement frame;
	@FindBy(xpath = "/html/body/p/ancestor::body")WebElement Frametext;
	
	@FindBy(xpath = "//input[@value='Next']")
	WebElement Next;

	@FindBy(xpath = "//label[contains(text(),'Hiring Manager')]//following::input[2]")WebElement HiringMgr;
	@FindBy(xpath = "//label[contains(text(),'Recruiter')]//following::input[2]")WebElement RecruiterMgr;
	@FindBy(xpath = "//label[contains(text(),'Status')]//following::input[1]") WebElement Status;
	@FindBy(xpath="//label[contains(text(),'Position Number')]//following::input[1]")WebElement PositionNo;
	@FindBy(xpath="//label[contains(text(),'Addition/Replacement')]//following::input[1]")WebElement HiringType;
	@FindBy(xpath="//label[contains(text(),'Country')]//following::select[1]")WebElement positioncountry;
	@FindBy(xpath="//label[contains(text(),'State')]//following::select[1]")WebElement positionstate;
	@FindBy(xpath="//label[contains(text(),'City')]//following::input[1]")WebElement City;
	@FindBy(xpath="//label[contains(text(),'Company')]//following::input[1]")WebElement company;
	@FindBy (xpath="//label[contains(text(),'Division')]//following::input[1]")WebElement division;
	@FindBy(xpath="//label[contains(text(),'Start Date')]//following::input[1]")WebElement Date1;
	
	

	private Logger log = LoggerHelper.GetLogger(CreateRequisitionPage.class);
	Exls_Reader reader = new Exls_Reader(ResourceHelper.GetResourcePath("\\src\\main\\java\\helper\\exceldata\\Frameworkworksheet.xlsx"));
	  

	public CreateRequisitionPage() {
		PageFactory.initElements(driver, this);
	}

	public void EnterMandetoryFields(String TitleName,String HrManager,String Recruiter,String RequisionName, String status, String positionNo, String HiringType, String country1,String state, String city,String company1,String division1) {

		int rows = reader.getRowCount("CreateRequisition");
		for (int i = 2; i < rows; i++) {

			
			String xpath = "//a[contains(text()," + "'" + RequisionName + "'" + ")" + "]";

			WebElement requisionele = driver.findElement(By.xpath(xpath));
			String req = requisionele.getText().split("-")[1];
			requisionele.click();
			BlankTemplate.click();
			
			Title.sendKeys(TitleName);
			String currentDate = TestUtil.Date();
			Date.clear();
			Date.sendKeys(currentDate);
			DateText.click();
			
			if (req.contains("Standard")) {
				Pickhelper.picktheElement(HrManager, HiringMgr);
				Pickhelper.picktheElement(Recruiter, RecruiterMgr);
				Next.click();

				Jobprofile.click();
				Update.click();
				TestUtil.VisibleOn(driver, frame, 10);
				IframeHelper.SwitchToFrame(frame);
				Frametext.sendKeys("TTPTest");
				
				
				

				TestUtil.VisibleOn(driver, Status, 5);
				Pickhelper.picktheElement(status, Status);
				String currentDate1 = TestUtil.Date();
				Date1.clear();
				Date1.sendKeys(currentDate1);
				PositionNo.sendKeys(positionNo);
				Pickhelper.picktheElement(HiringType, this.HiringType);
				SelectHelper.selectByUsigVisibleText(positioncountry, country1);
				SelectHelper.selectByUsigVisibleText(positionstate, state);
				City.sendKeys(city);
				
				Pickhelper.picktheElement(company1, this.company);
				Pickhelper.picktheElement(division1, this.division);
				
					

				log.info("standard requision is created");
				rows--;
				break;
			} else if (req.contains("Pipeline")) {
				String hrgmgr = reader.getCellData("CreateRequisition", "HiringMgr", i);
				Pickhelper.picktheElement(hrgmgr, HiringMgr);
				String recruiter = reader.getCellData("CreateRequisition", "Recruiter", i);
				Pickhelper.picktheElement(recruiter, RecruiterMgr);
				Next.click();
				log.info("pipline requision is created");
				rows++;
				break;

			} else if (req.contains("Executives")) {
				String hrgmgr = reader.getCellData("CreateRequisition", "HiringMgr", i);
				Pickhelper.picktheElement(hrgmgr, HiringMgr);
				String recruiter = reader.getCellData("CreateRequisition", "Recruiter", i);
				Pickhelper.picktheElement(recruiter, RecruiterMgr);
				Next.click();
				log.info("Executives requision is created");
				rows++;
				break;

			}
			

			
		}

	}
	
	public void CommanMethod()
	{
		
	}
	
	public static ArrayList<Object[]> getdatafromExcel() 
	{
		Exls_Reader reader = null;

		ArrayList<Object[]> mydata = new ArrayList<Object[]>();
		try {
			reader = new Exls_Reader(ResourceHelper.GetResourcePath("\\src\\main\\java\\helper\\exceldata\\Frameworkworksheet.xlsx"));
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}

		int count1 = reader.getRowCount("CreateRequisition");
		for (int rows =2; rows <= count1; rows++) {

			String TitleName = reader.getCellData("CreateRequisition", 0, rows);
			String HrManager = reader.getCellData("CreateRequisition", 1, rows);
			String Recruiter = reader.getCellData("CreateRequisition", 2, rows);
			String RequisionName= reader.getCellData("CreateRequisition", 3, rows);
			String status= reader.getCellData("CreateRequisition", 4, rows);
			String positionNo= reader.getCellData("CreateRequisition", 5, rows);
			String HiringType= reader.getCellData("CreateRequisition", 6, rows);
			String country= reader.getCellData("CreateRequisition", 7, rows);
			String state= reader.getCellData("CreateRequisition", 8, rows);
			String city= reader.getCellData("CreateRequisition", 9, rows);
			String company= reader.getCellData("CreateRequisition", 10, rows);
			String division= reader.getCellData("CreateRequisition", 11, rows);

			Object[] obj = { TitleName, HrManager, Recruiter,RequisionName,status,positionNo,HiringType,country,state,city,company,division};
			mydata.add(obj);
		}

		return mydata;
	}
}
