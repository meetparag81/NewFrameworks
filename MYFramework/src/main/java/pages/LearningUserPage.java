package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import frame.helper.IframeHelper;
import helper.Excelhelper.Exls_Reader;
import helper.TestUtil.TestUtil;
import helper.resorce.ResourceHelper;
import helper.select.SelectHelper;
import testBase.TestBase;

public class LearningUserPage extends TestBase {
	
	private @FindBy(xpath ="//input[@name='todoFilterText']") WebElement Searcbox;
	Exls_Reader reader = new Exls_Reader(ResourceHelper.GetResourcePath("\\src\\main\\java\\helper\\exceldata\\Frameworkworksheet.xlsx"));
	private @FindBy(xpath="((//a[text()='Passport Training']//ancestor::tr)[2]//a)[3]")WebElement TrainingDrop;
	private@FindBy(xpath= "//a[text()='Request a Class']//parent::li")WebElement RequestDrop;
	private@FindBy(xpath= "//iframe[@name='scheduleRequestBuffer']")WebElement scheduleRequestBufferFrame;
	private@FindBy(xpath= "//input[@name='requiredDate']")WebElement Date;
	private@FindBy(xpath= "//select[@name='regionID']")WebElement Region	;
	private@FindBy(xpath= "//select[@name='facilityID']")WebElement location;
	

	public LearningUserPage(){
		PageFactory.initElements(driver, this);
		
	}

	public void ClickonTheRequestClass() {
		try{
		TestUtil.VisibleOn(driver, Searcbox, 30);
		String LearningPagetext = reader.getCellData("LearningPage", "SearchBox", 2);
		Searcbox.sendKeys(LearningPagetext);		
		TrainingDrop.click();
		RequestDrop.click();
		FillRegistrationForm();
		}
		catch(Exception e){
			e.getStackTrace();
		}
	
	}
public void FillRegistrationForm() {
	IframeHelper.SwitchToFrame(scheduleRequestBufferFrame);
	TestUtil.ActionForMovetoElement(Date);
	Date.sendKeys(reader.getCellData("LearningPage", "NeedByDate", 2));
	SelectHelper.selectByUsigVisibleText(Region, reader.getCellData("LearningPage", "Region", 2));
	SelectHelper.selectByUsigVisibleText(Region, reader.getCellData("LearningPage", "Facility", 2));
	
		
		
	
	}
	
	 
	
	

}
