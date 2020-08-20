package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import frame.helper.IframeHelper;
import helper.Excelhelper.Exls_Reader;
import helper.TestUtil.TestUtil;
import helper.resorce.ResourceHelper;
import testBase.TestBase;

public class LearningUserPage extends TestBase {
	
	private @FindBy(xpath ="//input[@name='todoFilterText']") WebElement Searcbox;
	Exls_Reader reader = new Exls_Reader(ResourceHelper.GetResourcePath("\\src\\main\\java\\helper\\exceldata\\Frameworkworksheet.xlsx"));
	private @FindBy(xpath="((//a[text()='Passport Training']//ancestor::tr)[2]//a)[3]")WebElement TrainingDrop;
	private@FindBy(xpath= "//a[text()='Request a Class']//parent::li")WebElement RequestDrop;
	

	public LearningUserPage(){
		PageFactory.initElements(driver, this);
		
	}

	public void ClickonTheRequestClass() {
		
		TestUtil.VisibleOn(driver, Searcbox, 30);
		Searcbox.sendKeys(reader.getCellData("LearningPage", "SearchBox", 2));		
		TrainingDrop.click();
		RequestDrop.click();
	
	}
public void FillRegistrationForm() {
	
	IframeHelper
		
		TestUtil.VisibleOn(driver, Searcbox, 30);
		Searcbox.sendKeys(reader.getCellData("LearningPage", "SearchBox", 2));		
		TrainingDrop.click();
		RequestDrop.click();
	
	}
	
	 
	
	

}
