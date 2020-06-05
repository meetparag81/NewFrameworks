package test;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import test.utilities.*;

public class LoginPage extends CustomMethods {

	public LoginPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(xpath = "//input[@name='username']")
	public WebElement userName;

	@FindBy(xpath = "//input[@name='password']")
	public WebElement password;

	@FindBy(xpath = "//*[text()='Log in']/ancestor::button[1]")
	public WebElement LogIn;

	@FindBy(xpath = "//input[@type='submit']")
	public WebElement SignIn;

	@FindBy(xpath = "//input[@placeholder='Enter Company ID']")
	public WebElement CompanyID;

	@FindBy(xpath = "//button[@title='Submit']")
	public WebElement Submit;



	public LoginPage LoadURL(String URL){

		driver.navigate().to(URL);
		/*Constants.LabelArray.add("url");
			Constants.LabelValue.add(URL);*/
		wait(5000);
		return this;
	}

	public LoginPage EnterCompanyID(String companyID){
		if(iselementExists(CompanyID)){
			enterText(CompanyID, companyID);
			/*try {
					String Label = getElementLabel(driver, CompanyID);
					Constants.LabelArray.add(Label);
					Constants.LabelValue.add(companyID);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("Failed to retrieve label for Company ID");
				}*/
			click(Submit);
			wait(5000);
		}
		return this;
	}

	public HomePage Login(String sUsername,String sPassword){
		wait(3000);
		waitForClickable(userName);
		enterText(userName, sUsername);
		/*try {
				String Label = getElementLabel(driver, userName);
				Constants.LabelArray.add(Label);
				Constants.LabelValue.add(sUsername);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Failed to retrieve label for UserName");
			}*/
		enterText(password, sPassword);
		/*try {
				String Label = getElementLabel(driver, password);
				Constants.LabelArray.add(Label);
				Constants.LabelValue.add(sPassword);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Failed to retrieve label for Password");
			}*/

		if(iselementExists(SignIn)) {
			click(SignIn);
		}
		else if(iselementExists(LogIn)) {
			click(LogIn);
		}
		wait(5000);
		return new HomePage(driver);
	}

	
}



