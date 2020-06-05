package test;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.github.javafaker.Faker;

	public class AddNewEmployeeTest extends Corehelper {

		//variables 
		static Faker faker=new Faker();
		protected static WebDriver driver;
		static Logger logger = Logger.getLogger(AddNewEmployeeTest.class.getName());
		static int templateCounter = 1;
		static InputStream inpFile;
		static Workbook workbook;
		static Row row=null;
		static Sheet sheet;
		static int lastRow=0;
		static int inputcounter=0;
		static ArrayList<String> heading=new ArrayList<String>();
		static boolean displayed =false;
		static boolean enabled =false;
		static boolean editable =false;
		static String inputLabel="";
		static String inputLabelvalue="";
		static String previousInputLabelValue="";
		static boolean tableFound=false;
		static String[] tableLabel;
		static int tableLabelCounter;
		static String firstName="";
		static String middleName="";
		static String lastName="";
		static String address1="";
		static String address2="";
		static String address3="";
		static String city="";
		static String selectedCountry="";
		static String DOB="";
		static boolean countrySelected=false;
		//static String[] Country = {"India","United States","Germany","United Kingdom","Taiwan","Australia","China","Canada","Switzerland","Belgium","Brazil","Argentina","Portugal","France","Philippines","Ireland","Italy","Norway"};
		static String[] Country = {"Mexico"};
		static int ContinueCounter=0;
		static boolean selectionFlag=true;
		static String nationalIDType="";
		static ArrayList<String> FieldHeaderArray=new ArrayList<String>();
		static ArrayList<String> FieldValueArray=new ArrayList<String>();
		static ArrayList<String> FieldLabelArray=new ArrayList<String>();
		static int labelIteration;
		static String result="";
		static boolean testPassed=true;
		static String errorMessage="";
		static int totalPassed=0;
		static int totalFailed=0;
		static int totalrunrecords=0;

		public static  void main(String[] args) throws EncryptedDocumentException, InvalidFormatException, IOException, ParseException {

			//Call the HireNewEmployee Script
			//executeScript("C:\\Users\\parag.borawake\\RESOURCES\\FMS\\20200421184720\\OutputHireMyConcerto.xlsx","https://performancemanager8.successfactors.com/sf/home?bplte_company=BPOCUSTOM10#Shell-home","C:\\WorkingfolderPB\\DSGdata\\CompanycodeTemplates\\Template_Hire_MyConcerto.xlsx","HireEmployee","Hire", "", "bparag", "pBorprg@81", "No", "No");

			//executeScript("C://Users//akshay.jagannath//RESOURCES//FMS//OutputHirePeloton.xlsx","https://performancemanager4.successfactors.com/","C://Users//akshay.jagannath//git//sfautotoolcde//Templates//Template_Hire_Pelaton.xlsx","HireEmployee","Hire", "pelotonintD", "anshul.makan@accenture.com", "Akshay@1234","No","No");
			//executeScript("C:\\\\Users\\\\parag.borawake\\RESOURCES\\FMS\\20200421184720\\OutputHireMyConcerto.xlsx","https://pm8-cncdn.successfactors.com/login","C:\\WorkingfolderPB\\DSGdata\\CompanycodeTemplates\\Template_Hire_ITA_V2.xlsx","HireEmployee","Hire", "PepsiCoECSAND", "RPAUSER", "Akshay@1234","No","No");
		}

		public static String executeScript(String filePath,String sURL, String templateFile,String templateName,String templateSheet, String CompanyID,String userName,String password,String ExecuteinBackground,String SavetoInstance) throws EncryptedDocumentException, InvalidFormatException, IOException, ParseException {

			result="";
			testPassed=true;
			errorMessage="";
			totalPassed=0;
			totalFailed=0;
			totalrunrecords=0;
			try {
				//Initialize template
				testPassed=InitializeTemplate(templateFile,templateSheet);


				//Loop for all the data in template
				for(int rowcounter=0;rowcounter<lastRow;rowcounter++) {

					try {
						//Loop for next set of data
						if(rowcounter!=0) {
							templateCounter=templateCounter+1;
							row=sheet.getRow(templateCounter);
						}

						//Initialize driver
						initializeWebDriver(ExecuteinBackground);
						driver=getDriver(ExecuteinBackground);

						//Initialize Custom method class
						CustomMethods helper = new CustomMethods(driver);

						//Login and Search for add new employee
						LoginPage login = new LoginPage(driver);
						login.LoadURL(sURL)
						.EnterCompanyID(CompanyID).Login(userName, password);

						//To capture total run records
						totalrunrecords=totalrunrecords+Integer.valueOf(getcellValue("No of Records"));

						//Loop for Number of records Hire to be executed
						for(int headercount=0;headercount<Integer.valueOf(getcellValue("No of Records"));headercount++) {

							//Navigate to add new employee page
							HomePage homepage = new HomePage(driver);
							homepage.searchAddNewEmployee().waitforload();
							AddNewEmployeePage addnewEmployee = new AddNewEmployeePage(driver);

							//re-initialize all Variables
							inputLabel="";
							inputLabelvalue="";
							previousInputLabelValue="";
							tableFound=false;
							tableLabelCounter=0;;
							firstName="";
							lastName="";
							middleName="";
							address1="";
							address2="";
							address3="";
							city="";
							DOB="";
							tableLabel = null;
							selectedCountry="";
							countrySelected=false;
							ContinueCounter=0;
							selectionFlag=true;
							nationalIDType="";
							FieldHeaderArray.clear();
							FieldValueArray.clear();
							FieldLabelArray.clear();
							labelIteration=0;
							heading.clear();

							//Find all the headers in the add new employee
							List<WebElement> allheaderElements = driver.findElements(By.xpath(addnewEmployee.Header));
							System.out.println(allheaderElements.size());
							for ( WebElement e : allheaderElements ) {
								heading.add(e.getText().trim());
							}
							System.out.println("Add New Employee - Headers : "+heading);

							//Iterate across each section for input
							for(int headeriteration=0;headeriteration<heading.size();headeriteration++) {
								//Reinitialize variables
								tableLabelCounter=0;
								tableFound=false;
								inputcounter=0;

								//To handle more fields
								List<WebElement> showbutton = driver.findElements(By.xpath("//*[contains(text(),'more fields')]"));
								for ( WebElement moreFields : showbutton ) {
									if(moreFields.isDisplayed() && moreFields.isEnabled()) {
										helper.click(moreFields);
										wait(500);
									}
								}


								//To handle National ID portlet

								if(heading.get(headeriteration).equals("Identity")) {
									//National ID portlet
									if(helper.iselementExists(addnewEmployee.NationalIDportlet)) {
										helper.click(addnewEmployee.NationalIDAdd);
										wait(500);
									}
								}

								//To handle Email and Phone Information portlet 
								else if(heading.get(headeriteration).equals("Personal Information")) {
									//Email Information portlet
									if(helper.iselementExists(addnewEmployee.EmailInformationportlet)) {
										helper.click(addnewEmployee.EmailAdd);
										wait(500);
									}

									//Phone Information portlet
									if(helper.iselementExists(addnewEmployee.PhoneInformationportlet)) {
										helper.click(addnewEmployee.PhoneAdd);
										wait(500);
									}

									//Add Emergency Contact
									if(!helper.iselementExists(addnewEmployee.EmergencyContactPortlet)) {
										helper.click(addnewEmployee.AddEmergencyContact);
										wait(500);
									}
								}

								//To handle Job Information portlets
								else if(heading.get(headeriteration).equals("Job Information")) {
									//Email Information portlet
									if(!helper.iselementExists(addnewEmployee.AddWorkPermitPortlet)) {
										helper.click(addnewEmployee.WorkPermitAdd);
										wait(500);
									}
								}

								//To handle Compensation Section
								else if(heading.get(headeriteration).equals("Compensation Information")) {
									//Email Information portlet
									if(helper.iselementExists(addnewEmployee.Compensationportlet)) {
										helper.click(addnewEmployee.CompensationAdd);
										wait(500);
									}
								}

								//find all the input elements in Identity / Personal Information/Job Information /Compensation Information
								List<WebElement> allChildElements = driver.findElements(By.xpath("//*[text()='"+heading.get(headeriteration)+"']/following::input"));
								System.out.println("Total Input Elements For '" + heading.get(headeriteration) + "' section is "+allChildElements.size());

								//iterate across each input
								for(int elementCounter=0;elementCounter<allChildElements.size();elementCounter++) {
									//for (WebElement element : allChildElements ) {
									displayed=false;
									enabled=false;
									editable=false;
									inputcounter=inputcounter+1;
									WebElement element;
									try {
										element= driver.findElement(By.xpath("(//*[text()='"+heading.get(headeriteration)+"']/following::input)["+inputcounter+"]"));
										displayed = element.isDisplayed();
										enabled = element.isEnabled();
										editable=helper.isEditable(element);
										if((!editable) && tableFound) {
											if(tableLabelCounter==(tableLabel.length-1)) {
												tableFound=false;
												tableLabelCounter=0;
											}
										}
									}
									catch(Exception elementnotdisplayed) {
										try {
											wait(100);
											element= driver.findElement(By.xpath("(//*[text()='"+heading.get(headeriteration)+"']/following::input)["+inputcounter+"]"));
											displayed = element.isDisplayed();
											enabled = element.isEnabled();
											editable=helper.isEditable(element);
										}
										catch(Exception elementnotavailable) {
											continue;
										}
									}

									//Perform operation on displayed each inputs
									if(displayed && enabled && editable) {
										try {
											try {
												helper.waitForJQueryLoad();
												boolean elementinteractable=helper.waitForClickable(element);
												while(!elementinteractable) {
													System.out.println("Waiting for element to be interactable");
													wait(1000);
													elementinteractable=helper.waitForClickable(element);
												}
												System.out.println("Element is in interactable State");
											}
											catch(Exception elementnotinteractable) {
												wait(1000);
												element= driver.findElement(By.xpath("(//*[text()='"+heading.get(headeriteration)+"']/following::input)["+inputcounter+"]"));
											}

											//find the label of all input and table elements
											String attributeValue=element.findElement(By.xpath("preceding::div[1]/span")).getAttribute("class").toString();
											if((element.findElement(By.xpath("preceding::div[1]/span")).getAttribute("class").contains("ColumnHeader")) || (element.findElement(By.xpath("preceding::div[1]/span")).getAttribute("class").contains("sapUiHiddenPlaceholder"))) {
												if(!tableFound) {
													inputLabel="";
													String xpath="//*[text()='"+heading.get(headeriteration)+"']/following::input["+inputcounter+"]/preceding::tr[1]";
													inputLabel = driver.findElement(By.xpath(xpath)).getText();
												}
												if((!inputLabel.isEmpty()) && (!tableFound)) {
													tableFound=true;
													tableLabel=inputLabel.split("\n");
												}
												if(tableFound) {
													if(tableLabelCounter==tableLabel.length) {
														tableFound=false;
														tableLabelCounter=0;
														break;
													}
													else {
														inputLabel=tableLabel[tableLabelCounter];
														tableLabelCounter=tableLabelCounter+1;
														if(tableLabelCounter==tableLabel.length) {
															tableFound=false;
															tableLabelCounter=0;
														}
													}
												}
											}
											//find the label not within Table
											else {
												//Iterating next set of table labels
												try {
													if(tableFound) {
														if(tableLabelCounter==tableLabel.length) {
															tableFound=false;
															tableLabelCounter=0;
															break;
														}
														else {
															inputLabel=tableLabel[tableLabelCounter];
															tableLabelCounter=tableLabelCounter+1;
															if(tableLabelCounter==tableLabel.length) {
																tableFound=false;
																tableLabelCounter=0;
															}
														}
													}