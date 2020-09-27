package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import PagesForRCM.AddNewClassPage;
import helper.Excelhelper.Exls_Reader;
import helper.TestUtil.CustomMethodhelper;
import helper.TestUtil.TestUtil;
import helper.logger.LoggerHelper;
import helper.resorce.ResourceHelper;
import testBase.TestBase;
import frame.helper.IframeHelper;

public class LearningAdMinistrationPage extends TestBase {
	
	
	
	@FindBy(xpath="//div[text()='Learning Activities']//ancestor::li//span")WebElement LearningActivities;
	@FindBy(xpath="//div[text()='Classes']//ancestor::li")WebElement classes;
	Exls_Reader reader = new Exls_Reader(ResourceHelper.GetResourcePath("\\src\\main\\java\\helper\\exceldata\\Frameworkworksheet.xlsx"));
	private@FindBy(xpath="//td[contains(@class,'BackgroundAlignLeft')][1]//child::span[contains(text(),'Classes')]") WebElement ClassesText;
	private @FindBy(xpath="(//iframe[contains(@name,'iframe')])[2]")WebElement classesframe_element;
	private @FindBy(xpath="(//a[text()='Add New'])[1]")WebElement AddNewlink;
	private	@FindBy(xpath="//div[text()='Manage User Learning']//ancestor::li//span")WebElement manageuserlearning;
	private @FindBy(xpath="//div[text()='Assignment Profiles']//ancestor::li")WebElement assignmentprofiles;
	private @FindBy(xpath="//div[text()='Libraries']//ancestor::li")WebElement Libraries;
	private  Logger log=LoggerHelper.GetLogger(LearningAdMinistrationPage.class);
	LearningAdMinistrationPage()
	{
		PageFactory.initElements(driver, this);
	}

	
	public void ClickOnTheLearningActivities(){
		try{
			TestUtil.WaitTillclickable(LearningActivities);
		}
		catch (Exception e) {
			log.info(e.getStackTrace());
		}
		LearningActivities.click();
	}
	public void ClickOnTheClasses(){
		try{
			TestUtil.VisibleOn(driver, classes, 5);
		}
		catch (Exception e) {
			log.info(e.getStackTrace());
		}
		classes.click();
	}
	public String GetTitleClasses(){
		try{
			frame.helper.IframeHelper.SwitchToFrame(classesframe_element);
			TestUtil.VisibleOn(driver, ClassesText, 5);
		}
		catch (Exception e) {
			log.info(e.getStackTrace());
		}
		return ClassesText.getText();
	}


	public AddNewClassPage AddNewClass() {
		IframeHelper.SwitchToFrame(classesframe_element);
AddNewlink.click();
return new AddNewClassPage();
		
	}
	public void clickOnTheLibraries(){
		try{
			TestUtil.WaitTillclickable(Libraries);
		}
		catch (Exception e) {
			log.info(e.getStackTrace());
		}
		Libraries.click();
	}
	
	public void ClickOnTheManageUserLearning(){
		try{
			TestUtil.VisibleOn(driver, manageuserlearning, 20);
		}
		catch (Exception e) {
			log.info(e.getStackTrace());
		}
		manageuserlearning.click();
	}
	public AssignmentprofilePage ClickOnAssignMentProfiles(){
		try{
			CustomMethodhelper.WaitTillElementisnotclickable(assignmentprofiles).click();
		}
		catch (Exception e) {
			log.info(e.getStackTrace());
		}
		return new AssignmentprofilePage();
		
	}
	
	public LibrariesPage ClickonLibraries(){
		ClickOnTheLearningActivities();
		clickOnTheLibraries();
		return new LibrariesPage();
		
	}
	
}

