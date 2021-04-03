package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import helper.TestUtil.CustomMethodhelper;
import helper.logger.LoggerHelper;
import testBase.TestBase;

public class AssignmentprofilePage extends TestBase {
	
	private @FindBy(xpath="(//a[text()='Add New'])[1]")WebElement AddNewlink;
	private static  Logger log=LoggerHelper.GetLogger(AssignmentprofilePage.class);
	
	public AssignmentprofilePage(){
		
		PageFactory.initElements(driver, this);
	}
	
	public void ClickonAddNew() throws InterruptedException
	{
		CustomMethodhelper.WaitTillElementisnotclickable(AddNewlink);
		if(CustomMethodhelper.iselementExists(AddNewlink)==true){
			AddNewlink.click();
		}
		
		
	}

	public void FillTheMandetoryDetails(String profileid) {

		
	}
	
	

}
