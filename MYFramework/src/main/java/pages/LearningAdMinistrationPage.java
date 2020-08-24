package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import helper.Excelhelper.Exls_Reader;
import helper.TestUtil.TestUtil;
import helper.logger.LoggerHelper;
import helper.resorce.ResourceHelper;
import testBase.TestBase;
import frame.helper.IframeHelper;

public class LearningAdMinistrationPage extends TestBase {
	
	@FindBy(xpath="//div[text()='Learning Activities']//ancestor::li//span")WebElement LearningActivities;
	@FindBy(xpath="//div[text()='Classes']//ancestor::li")WebElement classes;
	private  Logger log=LoggerHelper.GetLogger(LearningAdMinistrationPage.class);
	Exls_Reader reader = new Exls_Reader(ResourceHelper.GetResourcePath("\\src\\main\\java\\helper\\exceldata\\Frameworkworksheet.xlsx"));
	private@FindBy(xpath="//td[contains(@class,'BackgroundAlignLeft')][1]//child::span[contains(text(),'Classes')]") WebElement ClassesText;
	private @FindBy(xpath="(//iframe[contains(@name,'iframe')])[2]")WebElement classesFrameelement;
	private @FindBy(xpath="(//a[text()='Add New'])[1]")WebElement AddNewlink;
	private	@FindBy(xpath="//div[text()='Manage User Learning']//ancestor::li//span")WebElement ManageUserLearning;
	private @FindBy(xpath="//div[text()='Assignment Profiles']//ancestor::li")WebElement AssignmentProfiles;
	LearningAdMinistrationPage()
	{
		PageFactory.initElements(driver, this);
	}

	
	public void ClickOnTheLearningActivities(){
		try{
			TestUtil.VisibleOn(driver, LearningActivities, 20);
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
			frame.helper.IframeHelper.SwitchToFrame(classesFrameelement);
			TestUtil.VisibleOn(driver, ClassesText, 5);
		}
		catch (Exception e) {
			log.info(e.getStackTrace());
		}
		return ClassesText.getText();
	}


	public AddNewClassPage AddNewClass() {
		IframeHelper.SwitchToFrame(classesFrameelement);
AddNewlink.click();
return new AddNewClassPage();
		
	}
	
	public void ClickOnTheManageUserLearning(){
		try{
			TestUtil.VisibleOn(driver, ManageUserLearning, 20);
		}
		catch (Exception e) {
			log.info(e.getStackTrace());
		}
		ManageUserLearning.click();
	}
	public AssignmentprofilePage ClickOnAssignMentProfiles(){
		try{
			TestUtil.VisibleOn(driver, AssignmentProfiles, 05);
		}
		catch (Exception e) {
			log.info(e.getStackTrace());
		}
		AssignmentProfiles.click();
		return new AssignmentprofilePage();
	}
	
}

