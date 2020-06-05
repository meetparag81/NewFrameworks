package testBase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.testng.annotations.Listeners;

import helper.TestUtil.TestUtil;
import helper.listner.TestNGListners;
import helper.logger.LoggerHelper;
import helper.resorce.ResourceHelper;

@Listeners(TestNGListners.class)
public class TestBase {
	public static WebDriver driver;
	public static Properties prop;
	public static WebDriverEventListener e_driver;
	private static  Logger log=LoggerHelper.GetLogger(TestBase.class)	;

	public TestBase() {

		prop = new Properties();
		FileInputStream ip;
		try {
			prop = new Properties();
			
			ip = new FileInputStream(ResourceHelper.GetResourcePath("\\src\\main\\resorces\\configfile\\config.proerties"));
			try {
				prop.load(ip);
			} catch (IOException e) {

				log.info("FileNotFoundException is seen");
			}
		} catch (FileNotFoundException e) {

			log.info("FileNotFoundException is seen");
		}

	}

	public static void initalization() {

		String browsername = prop.getProperty("browser");

		if (browsername.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver",ResourceHelper.GetResourcePath("\\src\\main\\resorces\\driver\\geckodriver.exe"));
			driver = new FirefoxDriver();
		} else if (browsername.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver",ResourceHelper.GetResourcePath("\\src\\main\\resorces\\driver\\chromedriver.exe"));
		
			driver = new ChromeDriver(); 
			log.info(" chrome driver initalized");
			}
			else if (browsername.equals("IE")) {
				System.setProperty("\"webdriver. ie. driver", ResourceHelper.GetResourcePath("\\src\\main\\resorces\\driver\\IEDriverServer.exe"));
				driver = new InternetExplorerDriver();
				log.info(" InternetExplorerDriver initalized");
			}
			try {
				driver.manage().window().maximize();
				log.info(" Window is miximized");
				
			} 
			catch (WebDriverException e) {
				log.info("Webdriver exception seen");
			}
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
			try {
				driver.get(prop.getProperty("url"));
				log.info("url is launched");
			} catch (Exception e) {
				log.info("url is not launched");

			}

		}
	
	
	public static void main(String[] args) {
		
		System.out.println(ResourceHelper.GetResourcePath("\\src\\main\\resorces\\configfile\\config.proerties"));
		
	}
}
