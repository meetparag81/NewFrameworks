package general;

import javax.xml.xpath.XPath;

import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import actions.Actionhelper;
import helper.TestUtil.CustomMethodhelper;
import pages.ActionclassPage;
import pages.HomePage;
import testBase.TestBase;

public class ActionclassTest extends TestBase {
	
	private ActionclassPage ActionclassPage;
	private HomePage Homepage;

	ActionclassTest(){
		super();
	}
	
	@ BeforeMethod
	public void Setup(){
		TestBase.initalization("https://www.amazon.in/");
		 ActionclassPage = new ActionclassPage();
		 ActionclassPage.MovetoAccountList();
		Homepage= ActionclassPage.SignintotheSite();
	}
	
	@Test
	public void ActionclassTest(){
		
		
		
	}
	
	@ AfterMethod
	public void Teardown(){
		driver.quit();
	}

}
