package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import helper.TestUtil.TestUtil;
import testBase.TestBase;

public class AssignmentprofilePage extends TestBase {
	
	private @FindBy(xpath="(//a[text()='Add New'])[1]")WebElement AddNewlink;
	
	public AssignmentprofilePage(){
		
		PageFactory.initElements(driver, this);
	}
	
	public void ClickonAddNew()
	{
		TestUtil.VisibleOn(driver, AddNewlink, 10);
		AddNewlink.click();
		
	}

	public void FillTheMandetoryDetails(String profileid) {

		
	}
	
	

}
