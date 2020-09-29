package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import actions.Actionhelper;
import helper.Excelhelper.Exls_Reader;
import helper.TestUtil.CustomMethodhelper;
import helper.resorce.ResourceHelper;
import testBase.TestBase;

public class ActionclassPage extends TestBase {
	@FindBy(id="nav-link-accountList")WebElement AccountAndList;
	@FindBy(xpath="(//span[text()='Sign in'])[1]")WebElement Signin;
	@FindBy(xpath="//input[@name='email']")WebElement username;
	@FindBy(xpath="//input[@id='continue']")WebElement continuebutton;
	private WebElement password;
	
	static Exls_Reader reader = new Exls_Reader(ResourceHelper.GetResourcePath("\\src\\main\\java\\helper\\exceldata\\LMS_TestData.xlsx"));
	
	


	
	
	public ActionclassPage(){
		PageFactory.initElements(driver, this);
	}
	
	public void MovetoAccountList(){
		Actionhelper.Movetelement(AccountAndList);
	}
	
	public HomePage SignintotheSite(){
		CustomMethodhelper.click(Signin);
		
		if( CustomMethodhelper.iselementExists(username)){
			String username=reader.getCellData("ActionTest", "username", 2);
			CustomMethodhelper.enterText(username,"9850049989" );
			if( CustomMethodhelper.iselementExists(continuebutton)){
				continuebutton.click();
			}
			 String pass = reader.getCellData("ActionTest", "password", 2);
			//CustomMethodhelper.enterText(password,pass));
		}
		return new HomePage(); 
		
	}

}
