package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import test.filehandling.FileHandlingUtilities;
import test.utilities.Constants;
import test.utilities.ThreadedItems;
import test.utilities.Utilities;

public class Corehelper {
	private static ThreadLocal<ThreadedItems> threadDriver;

	public static WebDriver getDriver(String ExecuteinBackground)
	{
		try {
			Utilities utilities = new Utilities();
			String browser = "chrome";
			String local = "true";
			initializeThreadLocal();
			if (threadDriver.get().getDriver() == null){
				if ("chrome".equals(browser)){
					String downloadFilePath = utilities.getFileDownloadPath();
					DesiredCapabilities capabilities = DesiredCapabilities.chrome();
					ChromeOptions options = new ChromeOptions();
					options.addArguments("--start-maximized");
					options.addArguments("disable-infobars");
					if(ExecuteinBackground.equalsIgnoreCase("Yes")) {
						options.addArguments("--headless");
					}
					Map<String, Object> prefs = new HashMap<String, Object>();
					prefs.put("credentials_enable_service", false);
					prefs.put("profile.password_manager_enabled", false);
					prefs.put("download.default_directory", downloadFilePath);
					prefs.put("profile.default_content_setting_values.plugins", 1);
					prefs.put("profile.content_settings.plugin_whitelist.adobe-flash-player", 1);
					prefs.put("profile.content_settings.exceptions.plugins.*,*.per_resource.adobe-flash-player", 1);
					// Enable Flash for this site
					prefs.put("PluginsAllowedForUrls","");
					options.setExperimentalOption("prefs", prefs);
					capabilities.setCapability(ChromeOptions.CAPABILITY, options);
					
					FileHandlingUtilities fn = new FileHandlingUtilities();
					Properties p = fn.fnReadPropertyFile();
					String chromePath = System.getProperty("user.home").replaceAll("\\\\", "/") +p.getProperty("ChromeDriver");
					System.setProperty("webdriver.chrome.driver", chromePath);
					
					//System.setProperty("webdriver.chrome.driver", "C:\\LiquidAutomation\\driver\\chromedriver.exe");
					if (!"false".equals(local))	{
						threadDriver.get().setDriver(new ChromeDriver(capabilities));
//						threadDriver.get().setDriver(new RemoteWebDriver(new URL("http://192.168.0.101:4444/wd/hub"), capabilities));
					}
				}
			}

		}
		catch(Exception e) {
			if (threadDriver.get().getDriver() == null){
				getDriver(ExecuteinBackground);
			}
			System.out.println("Exception Caught in GetDriver Method " +e.getLocalizedMessage());
			return threadDriver.get().getDriver();
		}
		return threadDriver.get().getDriver();
	}

	public static void wait(int seconds){
		try{
			Thread.sleep(seconds);
		}
		catch (Exception e){
			System.out.println("Exception Caught in Wait command " +e.getLocalizedMessage());
		}
	}

	private static void initializeThreadLocal(){
		try {
			if (threadDriver == null){
				threadDriver = new ThreadLocal<ThreadedItems>();
			}
			if (threadDriver.get() == null){
				threadDriver.set(new ThreadedItems());
			}
		}
		catch(Exception e) {
			System.out.println("Failed to intialize Thread local  " +e.getLocalizedMessage());
		}
	}

	public static void initializeWebDriver(String ExecuteinBackground){
		try {
			killBogusProcesses();
			initializeThreadLocal();
			getDriver(ExecuteinBackground).manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
		catch(Exception e) {
			threadDriver=null;
			getDriver(ExecuteinBackground).manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
	}

	public static void killWebDriver()
	{
		try {
		threadDriver.get().getDriver().quit();
		threadDriver.get().setDriver(null);
		}
		catch(Exception e) {
			
		}
		
		//killProcess("EXCEL.EXE");
	}

	private static void killProcess(String name){
		try{
			if (processIsRunning(name)) {
				Runtime.getRuntime().exec(Constants.KILL + name);
			}
		}
		catch (IOException ioe){
		}
	}

	private static boolean processIsRunning(String name) throws IOException{
		Process p = Runtime.getRuntime().exec(Constants.TASKLIST);
		BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		while ((line = reader.readLine()) != null){
			System.out.println(line);
			if (line.contains(name)){
				return true;
			}
		}
		return false;
	}

	private static void killBogusProcesses(){
		String processName = "";
		String browser = "chrome";
		if ("chrome".equals(browser)){
			processName = "chromedriver.exe";
		}
		String[] processes = processName.split(",");
		for (String process : processes){
			killProcess(process);
		}
	}

}