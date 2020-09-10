package MYFramework;

import org.testng.annotations.BeforeMethod;

import pages.HomePage;
import pages.LoginPage;
import testBase.TestBase;

public class LMSCatlogTest extends TestBase   {
	
	
	public LMSCatlogTest(){
		super();
	}
	
	private LoginPage LoginPage;
	private HomePage HomePage;

	@BeforeMethod
	public void Setup(){
		TestBase.initalization();
		LoginPage = new LoginPage();
		HomePage=LoginPage.login(prop.getProperty("Username"), "Password");
	
	

}
}
