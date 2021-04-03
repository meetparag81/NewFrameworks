package pages;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import MYFramework.MYFramework.CreateCatlougeTest;
import helper.Excelhelper.Exls_Reader;
import helper.logger.LoggerHelper;
import helper.resorce.ResourceHelper;
import testBase.TestBase;

public class CreateCatlog extends TestBase {
	
	


	private String timestamp;
	Exls_Reader reader = new Exls_Reader(ResourceHelper.GetResourcePath("\\src\\main\\java\\helper\\exceldata\\LMS_TestData.xlsx"));
	Logger log=LoggerHelper.GetLogger(CreateCatlougeTest.class);

	public CreateCatlog(){
		PageFactory.initElements(driver, this);
	}
	
	public void CreateCatloge() throws InterruptedException{
		for(int rowcounter=2;rowcounter<=reader.getRowCount("AddLibraries");rowcounter++){
			
			//waitForJQueryLoad();
			WebElement LearningAdministration = (new WebDriverWait(driver, 50)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h3[text()='Learning Administration']//ancestor::div[2]")));
			LearningAdministration.click();
			Thread.sleep(30000);
			//waitForJQueryLoad();
			WebElement ManageUserLearning = (new WebDriverWait(driver, 80)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Learning Activities']//ancestor::li//span[1]")));
			Thread.sleep(30000);
			//waitForJQueryLoad();
			ManageUserLearning.click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//div[text()='Libraries']")).click();
			WebElement iframe1 = (new WebDriverWait(driver, 80)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//iframe[@class='plateauIFrame ']")));
			Thread.sleep(15000);
			driver.switchTo().frame(iframe1);
			driver.findElement(By.xpath("(//a[text()='Add New'])[1]")).click();
			Thread.sleep(2000);
			driver.switchTo().defaultContent();
			Thread.sleep(5000);
			WebElement iframe2= driver.findElement(By.xpath("//iframe[contains(@name,'PPCFrame')][1]"));
			driver.switchTo().frame(iframe2);
			timestamp =new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
			String Libraryidname= reader.getCellData("CatlougeData", "LibraryName", rowcounter);
			driver.findElement(By.name("catalogID")).sendKeys(Libraryidname+timestamp);
			Thread.sleep(2000);
			driver.findElement(By.xpath("(//input[@name='submitbutton'])[1]")).click();
			Thread.sleep(5000);
			driver.switchTo().defaultContent();
			Thread.sleep(5000);
			driver.quit();
			

		}

	}
	

	
	
	}

