package MYFramework.MYFramework;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import helper.Excelhelper.Exls_Reader;
import helper.TestUtil.TestUtil;
import helper.logger.LoggerHelper;
import helper.resorce.ResourceHelper;



public class CreateCatlougeTest   {
	private static  Logger log=LoggerHelper.GetLogger(CreateCatlougeTest.class);
	static Exls_Reader reader = new Exls_Reader(ResourceHelper.GetResourcePath("\\src\\main\\java\\helper\\exceldata\\LMS_TestData.xlsx"));
	private static WebDriver driver;
	private static String timestamp;
	public static void main(String[] args) throws InterruptedException  {



		int count = reader.getRowCount("CatlougeData");
		for(int rowcounter=2;rowcounter<=count;rowcounter++){
			Thread.sleep(10000);
			System.setProperty("webdriver.chrome.driver",ResourceHelper.GetResourcePath("\\src\\main\\resorces\\driver\\chromedriver.exe")); 
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			log.info(" Window is miximized");			
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);			
			driver.get("https://performancemanager8.successfactors.com/login?company=BPOCUSTOM10#/login");			
			log.info("url is launched");
			String username = reader.getCellData("AddLibraries", "username", 2);
			String password = reader.getCellData("AddLibraries", "password", 2);	

			driver.findElement(By.xpath("//input[@name='username']")).sendKeys(username);
			Thread.sleep(2000);
			driver.findElement(By.xpath("//input[@name='password']")).sendKeys(password);
			Thread.sleep(2000);
			driver.findElement(By.xpath("//bdi[text()='Log in']//ancestor::span[1]")).click();
			Thread.sleep(30000);
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
