package test;

import java.io.IOException;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.botsftool.dsg.utilities.*;

public class HomePage extends CustomMethods {
	
	public HomePage(WebDriver driver){
		super(driver);
	}

	@FindBy(xpath = "//input[@id='bizXSearchField-I']")
	public WebElement search;
	
	@FindBy(id = "sap-ui-blocklayer-popup")
	public WebElement sapPopup;
	
	public AddNewEmployeePage searchAddNewEmployee(){
		wait(12000);
		try {
		//waitForClickable(sapPopup);
		executeJavaScript("arguments[0].click();",sapPopup);
		wait(1000);
		}
		catch(Exception noPopupBlocker) {
			wait(10);
		}
		waitForClickable(search);
		/*Constants.LabelArray.add("bizXSearchField-I");
		Constants.LabelValue.add("Add New Employee");*/
		pick(search, "Add New Employee", "//input[@id='bizXSearchField-I']");
		wait(10000);
		return new AddNewEmployeePage(driver);
	}
	
	public ManageDataPage searchManageData(){
		wait(15000);
		try {
		//waitForClickable(sapPopup);
		executeJavaScript("arguments[0].click();",sapPopup);
		wait(1000);
		}
		catch(Exception noPopupBlocker) {
			wait(10);
		}
		waitForClickable(search);
		/*try {
			String Label = getElementLabel(driver, search);
			Constants.LabelArray.add(Label);
			Constants.LabelValue.add("Manage Data");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Failed to retrieve label for Company ID");
		}*/
		/*
		enterText(search, "Manage Data");
		wait(5000);
		sendKeyPress(search, Keys.ENTER);
		wait(1000);
		sendKeyPress(search, Keys.ENTER);*/
		pick(search, "Manage Data", "//input[@id='bizXSearchField-I']");
		wait(10000);
		return new ManageDataPage(driver);
	}

	
}
