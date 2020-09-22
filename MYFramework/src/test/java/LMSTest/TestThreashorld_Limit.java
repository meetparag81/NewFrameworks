package LMSTest;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import helper.Excelhelper.Exls_Reader;
import helper.TestUtil.TestUtil;
import helper.logger.LoggerHelper;
import helper.resorce.ResourceHelper;

public class TestThreashorld_Limit {
	
	
	private static WebDriver driver;
	private static  Logger log=LoggerHelper.GetLogger(TestThreashorld_Limit.class);
	static Exls_Reader reader = new Exls_Reader(ResourceHelper.GetResourcePath("\\src\\main\\java\\helper\\exceldata\\LMS_TestData.xlsx"));
	public static void main(String[] args) throws InterruptedException {
		
		System.setProperty("webdriver.chrome.driver",ResourceHelper.GetResourcePath("\\src\\main\\resorces\\driver\\chromedriver.exe")); 
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		log.info(" Window is miximized");			
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);			
		driver.get("https://performancemanager8.successfactors.com/login?company=BPOCUSTOM10#/login");			
		log.info("url is launched");
		String username = reader.getCellData("LMSData", "Username", 2);
		String password = reader.getCellData("LMSData", "Password", 2);	

		driver.findElement(By.xpath("//input[@name='username']")).sendKeys(username);
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys(password);
		Thread.sleep(2000);
		driver.findElement(By.xpath("//bdi[text()='Log in']//ancestor::span[1]")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("(//bdi[text()='Home']//ancestor::span)[1]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//a[text()='Learning']")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//*[text()='Switch to List View'][1]/following::input[1]")).sendKeys("passport");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//a[@title='Show more actions for Passport Training']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//a[text()='Request a Class']//parent::li")).click();
		Thread.sleep(5000);
	WebElement Frame1 = driver.findElement(By.xpath("(//iframe[@name='scheduleRequestBuffer'])[1]"));
		driver.switchTo().frame(Frame1);
		Actions act = new Actions(driver);
		act.moveToElement(driver.findElement(By.xpath("//input[@name='requiredDate']")));
		driver.findElement(By.xpath("//input[@name='requiredDate']")).sendKeys("09/22/2020");
	}
	public static WebElement WaitTillclickable(WebElement element) throws InterruptedException{
		WebDriverWait wait = new WebDriverWait(driver, 60);
		WebElement desireelement = wait.until(ExpectedConditions.elementToBeClickable(element));
		while((desireelement==null)){
			Thread.sleep(5);
		}
		return desireelement;
	}

}
