package LMSTest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import helper.Excelhelper.Exls_Reader;
import helper.TestUtil.TestUtil;
import helper.logger.LoggerHelper;
import helper.resorce.ResourceHelper;

public class LMSTest {
	private static WebDriver driver;
	private static Properties prop;
	private static WebDriverEventListener e_driver;
	private static int k=2;
	private static  Logger log=LoggerHelper.GetLogger(LMSTest.class);
	private static CharSequence Libraryrule;
	private static String SecurityDomainrule;
	private static List<WebElement>Libraries;






	public static void main(String[] args) throws Exception  {



		Exls_Reader reader = new Exls_Reader(ResourceHelper.GetResourcePath("\\src\\main\\java\\helper\\exceldata\\LMS_TestData.xlsx"));
int count = reader.getRowCount("LMSData");
		for(int i=2;i<=reader.getRowCount("LMSData");i++){
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
			String username = reader.getCellData("LMSData", "Username", 2);
			String password = reader.getCellData("LMSData", "Password", 2);	
			
			driver.findElement(By.xpath("//input[@name='username']")).sendKeys(username);
			Thread.sleep(2000);
			driver.findElement(By.xpath("//input[@name='password']")).sendKeys(password);
			Thread.sleep(2000);
			driver.findElement(By.xpath("//bdi[text()='Log in']//ancestor::span[1]")).click();
			Thread.sleep(30000);
			WebElement LearningAdministration = (new WebDriverWait(driver, 50)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h3[text()='Learning Administration']//ancestor::div[2]")));
			LearningAdministration.click();


			/*while(TestUtil.iselementExists(driver.findElement(By.xpath("//div[contains(@id,'shim')]")))) {

					 Thread.sleep(1000);

					 }*/
			 // waitForLoad(driver);
			Thread.sleep(30000);
			WebElement ManageUserLearning = (new WebDriverWait(driver, 80)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Manage User Learning']//ancestor::li//span")));
			Thread.sleep(30000);
			//driver.findElement(By.xpath("//div[text()='Manage User Learning']//ancestor::li//span")).click();
			ManageUserLearning.click();
			Thread.sleep(2000);
			TestUtil.VisibleOn(driver, driver.findElement(By.xpath("//div[text()='Assignment Profiles']//ancestor::li")), 20);
			Thread.sleep(2000);
			driver.findElement(By.xpath("//div[text()='Assignment Profiles']//ancestor::li")).click();
			Thread.sleep(10000);
			WebElement iframe1 = (new WebDriverWait(driver, 80)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//iframe[@class='plateauIFrame ']")));
			//WebElement iframe1= driver.findElement(By.xpath("//iframe[@class='plateauIFrame ']"));
			Thread.sleep(2000);
			driver.switchTo().frame(iframe1);
			driver.findElement(By.xpath("(//a[text()='Add New'])[1]")).click();
			driver.switchTo().defaultContent();
			Thread.sleep(5000);
			WebElement iframe2= driver.findElement(By.xpath("//iframe[contains(@name,'PPCFrame')][1]"));
			driver.switchTo().frame(iframe2);
			String Assignment_Profile_ID = reader.getCellData("LMSData", "Assignment Profile ID", i);
			StringBuffer assignmentprofile = new StringBuffer();
			assignmentprofile.append(Assignment_Profile_ID);
			assignmentprofile.append(k);
			 String timestamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
			driver.findElement(By.name("assignmentProfileId")).sendKeys("TestProfile"+timestamp);
			driver.findElement(By.xpath("(//input[@name='submitbutton'])[2]")).click();
			Thread.sleep(5000);	
			driver.switchTo().defaultContent();
			Thread.sleep(5000);		
			driver.findElement(By.xpath("//bdi[text()='Libraries']//ancestor::button[1]")).click();
			Thread.sleep(3000);
			driver.findElement(By.xpath("//button[@title='Add Libraries']")).click();
			String Libraries = reader.getCellData("LMSData", "Libraries", i).trim();
			String[] Libraryname=Libraries.split(";");
			if(k%2==0)
			{
			 Libraryrule=Libraryname[0];
			}
			else if (!((k%2)==0)) {
				 Libraryrule=Libraryname[1];
				
			}
			Thread.sleep(3000);
			WebElement iframe3 = (new WebDriverWait(driver, 20)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//iframe[contains(@name,'PPCFrame')][1]")));
			driver.switchTo().frame(iframe3);
			Thread.sleep(2000);
			driver.findElement(By.xpath("//input[@name='FULL_TEXT_SEARCH']")).sendKeys(Libraryrule);
			Thread.sleep(2000);
			driver.findElement(By.xpath("//span[text()='Add/Remove Criteria']//following::input[1]")).click();
			Thread.sleep(2000);
			driver.switchTo().defaultContent();
			Thread.sleep(2000);
			WebElement iframe4 = driver.findElement(By.xpath("//iframe[contains(@name,'PPCFrame')][1]"));
			driver.switchTo().frame(iframe4);
			Thread.sleep(3000);
			/*WebElement iframe5 = driver.findElement(By.xpath("//iframe[@title='Criteria Chooser' and @id='criteriaChooser']"));
			driver.switchTo().frame(iframe5);*/
			
			List<WebElement>LibraryTable = driver.findElements(By.xpath("//table[@name='layoutTable']//following::table[@class='BodyTable']//following::table[@class='ResultsTable']//tr//td"));
			for(int j=0;j<LibraryTable.size();j++){
				log.info("the otion"+ Libraryrule+"---->"+" text  is"+ LibraryTable.get(j).getText());

				if(LibraryTable.get(j).getText().contains(Libraryrule))
				{
					Thread.sleep(1000);
					driver.findElement(By.xpath("((//table[@class='ResultsTable'][1]//tr//td)["+j+"]//following::input)[1]")).click();
					log.info("the input box for"+Libraryrule+" option is clicked"  );
					break;
				}
				
			}
			Thread.sleep(3000);			
			driver.findElement(By.xpath("(//input[@name='submitbutton'])[2]")).click();
			driver.switchTo().defaultContent();
			Thread.sleep(3000);
			driver.findElement(By.xpath("//bdi[text()='Rules']//ancestor::button")).click();
			Thread.sleep(5000);
			String SecurityDomains = reader.getCellData("LMSData", "Security Domains", i).trim();
			String[] SecurityDomainsname=SecurityDomains.split(";");
			
			/*driver.findElement(By.xpath("//bdi[text()='Security Domains']//following::span[3]")).click();
			Thread.sleep(3000);			
			String SecurityDomainrule = SecurityDomainsname [0];			
			WebElement iframe6 = driver.findElement(By.xpath("//iframe[contains(@name,'PPCFrame')][1]"));
			driver.switchTo().frame(iframe6);
			Thread.sleep(2000);
			driver.findElement(By.xpath("//input[@name='FULL_TEXT_SEARCH']")).sendKeys(SecurityDomainrule);
			Thread.sleep(2000);
			driver.findElement(By.xpath("//span[text()='Add/Remove Criteria']//following::input[1]")).click();
			Thread.sleep(1000);
			driver.switchTo().defaultContent();
			Thread.sleep(3000);
			WebElement iframe7 = driver.findElement(By.xpath("//iframe[contains(@name,'PPCFrame')][1]"));
			Thread.sleep(1000);
			driver.switchTo().frame(iframe7);
			Thread.sleep(3000);
			WebElement iframe8= driver.findElement(By.xpath("(//iframe[@title='Criteria Chooser' and @id='criteriaChooser'])[2]"));
			driver.switchTo().frame(iframe8);
			List<WebElement>SecuritydomainTable = driver.findElements(By.xpath("//table[@class='ResultsTable'][1]//tr//td"));
			for(int k=0;k<LibraryTable.size();k++){
				if(LibraryTable.get(k).getText().contains(SecurityDomains))
				{
					Thread.sleep(1000);
					driver.findElement(By.xpath("(//table[@class='ResultsTable'][1]//tr//td)["+k+"]//following::input[3]")).click();
					break;
				}				
			}
			Thread.sleep(3000);	
			driver.findElement(By.xpath("(//input[@value='Add'])[2]")).click();*/
			if(k%2==0)
			{
				SecurityDomainrule=SecurityDomainsname[0].trim();
			}
			else if (!((k%2)==0)) {
				SecurityDomainrule=SecurityDomainsname[1].trim();
				
			}
			
			 //SecurityDomainrule = SecurityDomainsname [0].trim();;			
			driver.findElement(By.xpath("//bdi[text()='Security Domains']//following::input[1]")).sendKeys(SecurityDomainrule);
			
			driver.findElement(By.xpath("//bdi[text()='Security Domains']")).click();
			Thread.sleep(1000);
			driver.switchTo().defaultContent();
			Thread.sleep(3000);
			driver.findElement(By.xpath("//bdi[text()='Create Group']//ancestor::button")).click();
			Thread.sleep(3000);
			driver.findElement(By.xpath("//input[@placeholder='Enter Group Name']")).sendKeys(Assignment_Profile_ID+"_"+"group");
			Thread.sleep(3000);
			String RuleList1 = reader.getCellData("LMSData", "Group1 Attribute", i);
			String[] Country=RuleList1.split(";");
			String Countryrule = Country [0].trim();;
			driver.findElement(By.xpath("//input[@placeholder='Select Attribute']")).sendKeys(Countryrule);
			Thread.sleep(2000);
			driver.findElement(By.xpath("//span[text()='Country/Region']//ancestor::li")).click();
			Thread.sleep(2000);
			String RuleList2 = reader.getCellData("LMSData", "Group1 Condition", i);
			String[] Matches=RuleList2.split(";");
			String MatchesRule = Matches [0].trim();;
			driver.findElement(By.xpath("//input[@placeholder='Select Operator']")).sendKeys(MatchesRule);
			Thread.sleep(2000);
			driver.findElement(By.xpath("//span[text()='Matches']//ancestor::li")).click();
			Thread.sleep(2000);
			String RuleList3 = reader.getCellData("LMSData", "Group1 Value", i);
			String[] Countryname=RuleList3.split(";");
			String CountrynameRule = Countryname [0].trim();
			Thread.sleep(2000);
			driver.findElement(By.xpath("(//input[@class='sapMInputBaseInner'])[3]")).sendKeys(CountrynameRule);
			Thread.sleep(2000);
			driver.findElement(By.xpath("//bdi[text()='Save']//ancestor::button")).click();

			k++;
			Thread.sleep(10000);
			log.info("Testcase execute for"+ k + " time");
			
			
			driver.quit();
		}

	}

	public static boolean waitForLoad(WebDriver driver) {
		ExpectedCondition<Boolean> pageLoadCondition = new
				ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				 return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
			}
			
		};
		WebDriverWait wait = new WebDriverWait(driver, 30);
		return wait.until(pageLoadCondition);

	}
	
	/*public  boolean WaitforLoad() throws InterruptedException{
		boolean flag= true;
		 flag = ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
		if(flag=false)
		{
			WebDriverWait wait = new WebDriverWait(driver, 30);
			
		}
		
		
		return flag;
		
		
	}*/
}
