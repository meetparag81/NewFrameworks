package MYFramework.MYFramework;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.net.UrlChecker.TimeoutException;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import LMSTest.LMSTest;
import frame.helper.IframeHelper;
import helper.Excelhelper.Exls_Reader;
import helper.Pick.PickHelpernew;
import helper.Pick.Pickhelper;
import helper.TestUtil.TestUtil;
import helper.logger.LoggerHelper;
import helper.resorce.ResourceHelper;
import testBase.TestBase;

public class AssignmentProfileTest {
	private static WebDriver driver;
	private static Properties prop;
	private static WebDriverEventListener e_driver;
	private static int k=0;
	private static  Logger log=LoggerHelper.GetLogger(LMSTest.class);
	private static String Libraryrule="";
	private static String SecurityDomainrule="";
	private static List<WebElement> NameofLibraries;
	static Exls_Reader reader = new Exls_Reader(ResourceHelper.GetResourcePath("\\src\\main\\java\\helper\\exceldata\\LMS_TestData.xlsx"));
	static Exls_Reader validationreader = new Exls_Reader(ResourceHelper.GetResourcePath("\\src\\main\\java\\helper\\exceldata\\LMS_TestDatavalidation.xlsx"));
	private static String timestamp="";
	private static String empty="";
	private static JavascriptExecutor jsExec;
	protected static WebDriverWait wait;







	public static void main(String[] args) throws Exception  {




		int count = reader.getRowCount("LMSData");
		for(int rowcounter=2;rowcounter<=reader.getRowCount("LMSData");rowcounter++){
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
			//waitForJQueryLoad();
			WebElement LearningAdministration = (new WebDriverWait(driver, 50)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h3[text()='Learning Administration']//ancestor::div[2]")));
			LearningAdministration.click();
			Thread.sleep(30000);
			//waitForJQueryLoad();
			WebElement ManageUserLearning = (new WebDriverWait(driver, 80)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Manage User Learning']//ancestor::li//span")));
			Thread.sleep(30000);
			//waitForJQueryLoad();
			ManageUserLearning.click();
			Thread.sleep(2000);
			TestUtil.VisibleOn(driver, driver.findElement(By.xpath("//div[text()='Assignment Profiles']//ancestor::li")), 20);
			Thread.sleep(2000);
			driver.findElement(By.xpath("//div[text()='Assignment Profiles']//ancestor::li")).click();
			Thread.sleep(10000);
			//waitForJQueryLoad();
			WebElement iframe1 = (new WebDriverWait(driver, 80)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//iframe[@class='plateauIFrame ']")));

			Thread.sleep(10000);
			driver.switchTo().frame(iframe1);
			driver.findElement(By.xpath("(//a[text()='Add New'])[1]")).click();
			Thread.sleep(2000);
			driver.switchTo().defaultContent();
			Thread.sleep(5000);
			WebElement iframe2= driver.findElement(By.xpath("//iframe[contains(@name,'PPCFrame')][1]"));
			driver.switchTo().frame(iframe2);
			String Assignment_Profile_ID = reader.getCellData("LMSData", "Assignment Profile ID", rowcounter);
			Thread.sleep(2000);
			timestamp =new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
			driver.findElement(By.name("assignmentProfileId")).sendKeys("TestProfile"+timestamp);
			Thread.sleep(2000);
			driver.findElement(By.xpath("(//input[@name='submitbutton'])[2]")).click();
			Thread.sleep(5000);
			driver.switchTo().defaultContent();
			Thread.sleep(5000);		
			driver.findElement(By.xpath("//bdi[text()='Libraries']//ancestor::button[1]")).click();

			Thread.sleep(3000);
			driver.findElement(By.xpath("//button[@title='Add Libraries']")).click();
			Thread.sleep(3000);
			WebElement iframe3 = (new WebDriverWait(driver, 20)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//iframe[contains(@name,'PPCFrame')][1]")));
			driver.switchTo().frame(iframe3);
			driver.findElement(By.xpath("//span[text()='Add/Remove Criteria']//following::input[1]")).click();
			Thread.sleep(2000);
			driver.switchTo().defaultContent();
			Thread.sleep(2000);
			WebElement iframeLibraryoptions = driver.findElement(By.xpath("//iframe[contains(@name,'PPCFrame')][1]"));
			driver.switchTo().frame(iframeLibraryoptions);
			Thread.sleep(2000);Thread.sleep(2000);

			List<WebElement>Libraryid = driver.findElements(By.xpath("//table[@name='layoutTable']//following::table[@class='BodyTable']//following::table[@class='ResultsTable']//tr//td"));
			//Selection of options as per Excel 
			String Libraries_data = reader.getCellData("LMSData", "Libraries", rowcounter).trim();
			String[] Libraryname=Libraries_data.split(";");
			ArrayList<String> LibraryList= new ArrayList<>();
			for(int n=0; n<Libraryname.length;n++){
				LibraryList.add(Libraryname[n]);
			}

			for(int p=0;p<Libraryname.length;p++){

				for(int Librarycounter =0;Librarycounter<Libraryid.size();Librarycounter++ ){
					boolean flag = false;
					//System.out.println("the vauefromExcel is = "+LibraryList.get(p) +" and "+ "element text= "+Libraryid.get(m).getText().toString().trim() );
					boolean flag1 = Libraryname[p].toString().trim().equalsIgnoreCase(Libraryid.get(Librarycounter).getText().toString().trim());
					if(flag1==true){
						Thread.sleep(1000);
						driver.findElement(By.xpath("((//table[@class='ResultsTable'][1]//tr//td)["+(Librarycounter+1)+"]//following::input)[1]")).click();
						log.info("the input box for"+Libraryid.get(Librarycounter).getText()+" option is clicked");

						break;

					}

				}


			}
			Thread.sleep(3000);
			driver.findElement(By.xpath("(//input[@name='submitbutton'])[2]")).click();
			Thread.sleep(2000);
			driver.switchTo().defaultContent();
			Thread.sleep(3000);
			driver.findElement(By.xpath("//bdi[text()='Rules']//ancestor::button")).click();
			String SecurityDomains = reader.getCellData("LMSData", "Security Domains", rowcounter).trim();
			String[] NoSecurityDomains = SecurityDomains.split(";");
			boolean flag2=false;
			for(int securitydomaincounter=0;securitydomaincounter<NoSecurityDomains.length;securitydomaincounter++){

				String SecurityDomainsname = NoSecurityDomains[securitydomaincounter];
				int counter=0;
				String inputvalue=ValidateSecurityDomains(SecurityDomainsname, rowcounter);
				if(!inputvalue.isEmpty()){
					Thread.sleep(5000);
				driver.findElement(By.xpath("//bdi[text()='Security Domains']//following::input[1]")).sendKeys(inputvalue);
				Thread.sleep(5000);
				driver.findElement(By.xpath("//bdi[text()='Security Domains']")).click();
				flag2=true;
				}
				else{
					flag2=false;
				}
				if(flag2==false){
					break;
				}
			
			}
			//if both condition of security group istrue
			if(flag2==true){
				Thread.sleep(2000);
				driver.switchTo().defaultContent();
				Thread.sleep(3000);
				WebElement CreateGroup= driver.findElement(By.xpath("//bdi[text()='Create Group']//ancestor::button"));
				String CreateGroupAttribute = reader.getCellData("LMSData", "Create Group1", rowcounter);
				String[] Securitygroup = CreateGroupAttribute.split(";");
				int totalgroups= clickontheAddgroup(CreateGroup, Securitygroup.length);
				Thread.sleep(3000);
				for(int groupcounter = 1;groupcounter<=totalgroups;groupcounter++){
				driver.findElement(By.xpath("(//input[@placeholder='Enter Group Name'])["+groupcounter+"]")).sendKeys("Group1_"+groupcounter+"_"+timestamp);
				//System.out.println("(//input[@placeholder='Enter Group Name'])["+groupcounter+"]");
				String xpath="(//input[@placeholder='Enter Group Name'])["+groupcounter+"]";
				//Method to create Grouprule
				Grouprules(xpath,rowcounter,groupcounter);

				}
				Thread.sleep(2000);
				driver.findElement(By.xpath("//bdi[text()='Save']//ancestor::button")).click();
				k++;
				Thread.sleep(10000);
				log.info("Testcase execute for"+ k + " time");
				driver.quit();

			}
			else{
				log.info("securitydomainnotfound");
				Thread.sleep(10000);
				
				driver.quit();
			}
			

			
			
		}
	}









	
	/*	public static void waitForJQueryLoad() {
			try {
				ExpectedCondition<Boolean> jQueryLoad = driver -> ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
				boolean jqueryReady = (Boolean) jsExec.executeScript("return jQuery.active==0");
				if (!jqueryReady) {
					wait.until(jQueryLoad);
				} 
			}
			catch (Exception ignored) {
			}
		}*/
		
	









	private static void CreateSecurityGroups(int rowcounter) throws InterruptedException {
		WebElement CreateGroup= driver.findElement(By.xpath("//bdi[text()='Create Group']//ancestor::button"));
		String CreateGroupAttribute = reader.getCellData("LMSData", "Create Group1", rowcounter);
		String[] Securitygroup = CreateGroupAttribute.split(";");
		int totalgroups= clickontheAddgroup(CreateGroup, Securitygroup.length);
		Thread.sleep(3000);
		for(int groupcounter = 1;groupcounter<=Securitygroup.length;groupcounter++){
			driver.findElement(By.xpath("(//input[@placeholder='Enter Group Name'])["+groupcounter+"]")).sendKeys("TestGroup_groupcounter"+"_"+groupcounter+"_"+timestamp);
			String xpath="(//input[@placeholder='Enter Group Name'])["+groupcounter+"]";
			//For Groupruleloop
			//Grouprules(xpath, groupcounter, rowcounter);
		}
		// TODO Auto-generated method stub

	}

	private static String ValidateSecurityDomains(String SecurityDomainsname,int rowcounter) {
		String result="";
		for(int securityvalidationcounter=2;securityvalidationcounter<validationreader.getRowCount("SecurityDomain");securityvalidationcounter++){
			String validationvalue=validationreader.getCellData("SecurityDomain", "Names", securityvalidationcounter);
			if(SecurityDomainsname.equalsIgnoreCase(validationvalue)){
				result=	validationvalue;
				break;
			}
			else{

				result="";
			}
		}
		return result;

	}


	public static void Grouprules(String xpath,int rowcounter,int groupcounter) throws InterruptedException{
		System.out.println("The group attribue name is" +"Group"+groupcounter+ "Attribute");
		String GroupAttribute = reader.getCellData("LMSData", "Group"+groupcounter+ "Attribute", rowcounter);
		String[] Country_Region=GroupAttribute.split(";");
		int Rulegroup=0;
		WebElement Addnewbutton= driver.findElement(By.xpath("(//a[text()='Add Rule'])"+"["+groupcounter+"]"));
		int totalrules= clickontheAddnew(Addnewbutton, Country_Region.length);
		for(int countrycounter=1;countrycounter<=Country_Region.length;countrycounter++){
			String Countryrule = Country_Region [Rulegroup].trim();
			String Country_CityNames = reader.getCellData("LMSData", "Group"+countrycounter+ "Value", rowcounter);
			String[] Countryname=Country_CityNames.split(";");
			String CountrynameRule = Countryname [Rulegroup].trim();
			Thread.sleep(2000);
			//System.out.println(xpath+"//following::input[@placeholder='Select Attribute']["+countrycounter+"]");
			WebElement country= driver.findElement(By.xpath(xpath+"//following::input[@placeholder='Select Attribute']["+(countrycounter)+"]"));
			ActionForMovetoElement(country);
			PickHelpernew.pick(driver, country, Countryrule, "(//input[@placeholder='Select Attribute'])["+(countrycounter)+"]");
			Thread.sleep(2000);
			String conditionlist = reader.getCellData("LMSData", "Group1 Condition", rowcounter);
			String[] Matches=conditionlist.split(";");
			String coditionrule = Matches [Rulegroup].trim();
			WebElement Condition=driver.findElement(By.xpath(xpath+"//following::input[@placeholder='Select Operator']["+(countrycounter)+"]"));
			PickHelpernew.pick(driver, Condition, coditionrule, "(//input[@placeholder='Select Operator'])["+(countrycounter)+"]");

			driver.findElement(By.xpath(xpath+"//following::input[@placeholder='Select Operator']["+(countrycounter)+"]"+"//following::input[1]")).sendKeys(CountrynameRule);
			Rulegroup++;
		}

	}



	private static int clickontheAddgroup(WebElement element,int count) {
		int counter=0;
		for(int i=1;i<=count;i++){
			element.click();
			counter++;

		}
		return counter;

	}




	private static void ActionForMovetoElement(WebElement element) {
		Actions act1 = new Actions(driver);
		act1.moveToElement(element);
	}




	private static int clickontheAddnew(WebElement element,int count) {
		int counter=0;
		for(int i=1;i<count;i++){
			element.click();
			counter++;

		}
		return counter;

	}




	public static boolean waitForLoad(WebDriver driver) {
		ExpectedCondition<Boolean> pageLoadCondition = new
				ExpectedCondition<Boolean>() {
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
