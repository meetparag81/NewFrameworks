package PagesForRCM;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import helper.select.SelectHelper;
import testBase.TestBase;

public class AddNewClassPage extends TestBase {
	
	
	private @FindBy(xpath="//select[@name='componentTypeID']") WebElement ItemType;

	public AddNewClassPage()
	{
		PageFactory.initElements(driver, this);
	}
	
	public void FillTheRequiredFieldsinClassPage() {
		SelectHelper.selectByUsigValue(ItemType, "COURSE");
	}
	

}
