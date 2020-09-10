package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import frame.helper.IframeHelper;
import helper.TestUtil.TestUtil;
import helper.logger.LoggerHelper;
import testBase.TestBase;

public class LibrariesPage extends TestBase {
	@FindBy(xpath="//a[text()='Add New'])[1]")WebElement New;
	@FindBy(xpath="//iframe[@class='plateauIFrame ']")WebElement Frame1;
	Logger log=LoggerHelper.GetLogger(LibrariesPage.class);
	public LibrariesPage(){
		
		PageFactory.initElements(driver, this);
	}

	public void CreateLibraries() throws InterruptedException {
		TestUtil.WaitTillclickable(Frame1);
		IframeHelper.SwitchToFrame(Frame1);
		New.click();
		
		
		
		
		
	}

}
