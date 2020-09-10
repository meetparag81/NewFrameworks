package pages;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import frame.helper.IframeHelper;
import helper.Excelhelper.Exls_Reader;
import helper.TestUtil.TestUtil;
import helper.logger.LoggerHelper;
import helper.resorce.ResourceHelper;
import testBase.TestBase;

public class LibrariesPage extends TestBase {
	@FindBy(xpath="//a[text()='Add New'][1]")WebElement addNew;
	@FindBy(xpath="//iframe[@class='plateauIFrame ']")WebElement Frame1;
	@FindBy(xpath="//iframe[contains(@name,'PPCFrame')][1]")WebElement Frame2;
	@FindBy(name="catalogID")WebElement Libraryid;
	private@FindBy(xpath="//iframe[contains(@name,'PPCFrame')][1]") WebElement submitbutton;
	Logger log=LoggerHelper.GetLogger(LibrariesPage.class);
	Exls_Reader reader = new Exls_Reader(ResourceHelper.GetResourcePath("\\src\\main\\java\\helper\\exceldata\\LMS_TestData.xlsx"));
	
	public LibrariesPage(){

		PageFactory.initElements(driver, this);
	}

	public void CreateLibraries() throws InterruptedException {
		int count = reader.getRowCount("AddLibraries");
		for(int rowcounter=2;rowcounter<=count;rowcounter++){
			TestUtil.WaitTillclickable(Frame1);
			IframeHelper.SwitchToFrame(Frame1);
			TestUtil.WaitTillclickable(addNew);
			addNew.click();
			driver.switchTo().defaultContent();
			TestUtil.WaitTillclickable(Frame2);
			IframeHelper.SwitchToFrame(Frame2);
			String Libraryidname= reader.getCellData("CatlougeData", "LibraryName", rowcounter);
			Libraryid.sendKeys(Libraryidname+TestUtil.CurrentTimestamp());
			Thread.sleep(3000);
			submitbutton.click();

		}

	}
}
