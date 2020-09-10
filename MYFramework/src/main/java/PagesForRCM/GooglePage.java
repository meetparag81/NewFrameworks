package PagesForRCM;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import testBase.TestBase;

public class GooglePage extends TestBase {
	
	@FindBy(name="username")WebElement username;
	@FindBy(name="Password")WebElement password;
	@FindBy(xpath = "//input[@name='login'and @value='Sign in']")WebElement Login;

}
