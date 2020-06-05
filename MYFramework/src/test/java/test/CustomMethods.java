package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.Charsets;
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
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import test.filehandling.FileHandlingUtilities;
import test.utilities.Constants;

import com.google.common.io.Files;


public class CustomMethods extends Corehelper {
	static Logger logger = Logger.getLogger(CustomMethods.class.getName());
	protected WebDriver driver;
	protected WebDriverWait wait;
	private static JavascriptExecutor jsExec;

	public CustomMethods(WebDriver driver){
		this.driver = driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, Constants.webdriverTimeout);
		jsExec = (JavascriptExecutor) driver;
	}


	public void enterText(WebElement input, String text){
		if (text == null || text.isEmpty())
			return;
		waitForClickable(input);
		input.clear();
		if (text.equals("NULL"))
			return;
		input.sendKeys(text);
	}

	public void click(WebElement elem){
		waitForClickable(elem);
		elem.click();
	}

	public boolean iselementExists (WebElement element)
	{
		boolean result=false;;
		try{
			if(element.isDisplayed())
				result=true;
		}
		catch(Exception e){
			result=false;
		}
		return result;
	}

	public boolean waitForClickable(WebElement element){
		boolean result;
		try {
			wait = new WebDriverWait(driver, Constants.webdriverTimeout);
			WebElement elm=wait.until(ExpectedConditions.elementToBeClickable(element));
			if(elm.equals(null)) {
				result = false;
			}
			else {
				result=true;
			}
		}
		catch(Exception elementnotfound) {
			result=false;
		}
		return result;
	}

	public void waitForJQueryLoad() {
		try {
			ExpectedCondition<Boolean> jQueryLoad = driver -> ((Long) ((JavascriptExecutor) this.driver).executeScript("return jQuery.active") == 0);
			boolean jqueryReady = (Boolean) jsExec.executeScript("return jQuery.active==0");
			if (!jqueryReady) {
				wait.until(jQueryLoad);
			} 
		}
		catch (Exception ignored) {
		}
	}

	public boolean isEditable(WebElement element){
		boolean result=true;
		try {
			String attribute=element.getAttribute("readonly");
			if(attribute.equals("true")) {
				result=false;
			}
		}
		catch(Exception propertynotfound) {
			return result;
		}
		return result;
	}

	public void sendKeyPress(WebElement elm, Keys k){
		if (elm != null)
			elm.sendKeys(k);
	}

	public boolean isalertexists()
	{
		boolean result=false;
		try {
			Alert alert = driver.switchTo().alert();
			result=true;
		}
		catch(Exception noalertfound) {
			return result;
		}
		return result;
	}

	public void acceptAlert()
	{
		if (wait.until(ExpectedConditions.alertIsPresent()) != null){
			Alert alert = driver.switchTo().alert();
			alert.accept();
		}
	}


	public static String ReadFromExcel(String excelFile, String sheetName,
			String keyColumn, String key, String columnHeader) throws FileNotFoundException, IOException, EncryptedDocumentException, InvalidFormatException {
		String returnValue = "";
		InputStream inpFile=new FileInputStream(new File(excelFile));
		Workbook workbook=WorkbookFactory.create(inpFile);
		Sheet sheet= workbook.getSheet(sheetName);
		int refCol=findCol(sheet,keyColumn);
		int destCol=findCol(sheet,columnHeader);
		if(refCol!=-1 && destCol!=-1){
			int lastRow=sheet.getLastRowNum();
			Row row;
			for(int i=1;i<lastRow;i++){
				row=sheet.getRow(i);
				if(row.getCell(refCol).toString().equalsIgnoreCase(key)){
					Cell cel=row.getCell(destCol);
					returnValue = cel.getStringCellValue();
					break;
				}
			}
			workbook.write(new FileOutputStream(excelFile));
			workbook.close();
		}else{
			System.out.println("");
		}
		return returnValue;
	}

	public static int findCol(Sheet sheet, String colName) {
		Row row = null;            
		int colCount=0;
		row=sheet.getRow(0);
		if(!(row== null)){
			colCount=row.getLastCellNum();
		}
		for(int j=0;j<colCount;j++){
			if(!( row.getCell(j)==null)){
				if(row.getCell(j).toString().trim().equalsIgnoreCase(colName)){
					return j;
				}
			}
		}
		return -1;
	}
	
	public static String getElementLabel(WebDriver driver,WebElement element) throws IOException {
		String fileContents = Files.toString(new File("C:\\Users\\akshay.jagannath\\git\\sfautotoolcde\\CustomJS\\successfactors1.js"), Charsets.UTF_8);
		JavascriptExecutor js = (JavascriptExecutor)driver; 
		List<String> elementInfo = null;
		String FieldLabel="";
		try {
			elementInfo =(List<String>)js.executeScript(fileContents+" return createRelativeXPathFromElement(arguments[0]);",element);
		}
		catch(Exception Elementnotfound) {
		}
		if(!(elementInfo.isEmpty() || elementInfo.equals(null))) {
			if(elementInfo.get(1).trim().contains("  ")) {
				FieldLabel=elementInfo.get(1).trim().replace("  ", "");
			}
			else {
				FieldLabel=elementInfo.get(1).trim();
			}
			System.out.println("Label is "+ FieldLabel.replace(" ", "_").replace("'", "")); 
			FieldLabel=FieldLabel.replace(" ", "_").replace("'", "").trim();
		}
		return FieldLabel;
	}

	public String pick(WebElement ele,String value,String xpath) {
		String result="";
		try {

			if(!ele.getAttribute("value").equalsIgnoreCase(""))
			{   JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].value = '';", ele);
			}
			if (value.split("\\(")[0].trim().length() >= 27) {
				ele.sendKeys((value.split("\\(")[0].trim()).substring(0, 27).trim());
			} else {
				ele.sendKeys(value.split("\\(")[0].trim());
			}
			waitForTime(3000);
			ele=driver.findElement(By.xpath(xpath));
			//String listXpath = "//div[contains(@class,'sapMComboBoxBasePicker-CTX') and contains(@style,'visibility: visible')]//ul/li[not(contains(@class, 'sapMSelectListItemBaseInvisible'))]";
			String listXpath = "//div[contains(@class,'sapMComboBoxBasePicker-CTX') and contains(@style,'visibility: visible')]//ul/li[not(contains(@class, 'sapMSelectListItemBaseInvisible')) and not(contains(@id, 'sap-ui-invisible'))]";
			List<WebElement> listElement = driver.findElements(By.xpath(listXpath));
			System.out.println("List 1 " + listElement.size());
			if (listElement == null || listElement.size() == 0) {
				listXpath = "//div[contains(@class,'sapMPopoverScroll')]//ul[contains(@class,'sapMSelectList')]/li";
				listElement = driver.findElements(By.xpath(listXpath));
				System.out.println("List 2 " + listElement.size());
			}
			if (listElement == null || listElement.size() == 0) {
				listXpath = "//div[contains(@class,'sf-combo-listselect')]//ul/li[contains(@role,'option')]";
				listElement =driver.findElements(By.xpath(listXpath));
				System.out.println("List 3 " + listElement.size());
			}
			if (listElement == null || listElement.size() == 0) {
				listXpath = "//div[@class='sapMPopoverCont' and contains(@style, 'overflow: auto;')]/following::td[@class='sapMListTblCell']";
				listElement = driver.findElements(By.xpath(listXpath));
				System.out.println("List 4 " + listElement.size());
			}
			if (listElement == null || listElement.size() == 0) {
				listXpath = "//div[@id='phSearchInput_autoCompleteBoxId']//ul/li[contains(@role,'presentation') and contains(@onmouseover,'phSearchInput')]";
				listElement = driver.findElements(By.xpath(listXpath));
				System.out.println("List 5 " + listElement.size());
			}
			result = "";
			String innerText = "";
			for (WebElement el: listElement) {
				ele.sendKeys(Keys.DOWN);
				waitForTime(1000);
				innerText = el.getText().trim();
				System.out.println("innertext is "+innerText);
				if (innerText.contains(value)) {
					ele.sendKeys(Keys.ENTER);
					result=innerText;
					break;
				}
				waitForTime(250);
			}
			waitForTime(2000);

		} catch (Exception e) {
			//failureReason = e.getMessage();
			return result;
			//throw e;
		}
		return result;
	}

	public static void waitForTime(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			//failureReason = e.getMessage();
			e.printStackTrace();
		}
	}

	public static By objectName(String objectName, String type) {

		if (objectName != null) {
			type = (type == null || type.trim().isEmpty()) ? "XPATH" : type;
			switch (type.toLowerCase()) {

			case "css":
				return By.cssSelector(objectName);

			case "id":
				return By.id(objectName);

			case "name":
				return By.name(objectName);

			case "lt":
				return By.linkText(objectName);

			case "plt":
				return By.partialLinkText(objectName);

			case "xpath":
				return By.xpath(objectName);

			case "tagname":
				return By.tagName(objectName);

			case "class":
				return By.className(objectName);

			default:
				if ("classname".equals(type.toLowerCase())) {
					return By.className(objectName);
				} else if ("linktext".equals(type.toLowerCase())) {
					return By.linkText(objectName);
				} else if ("partiallinktext".equals(type.toLowerCase())) {
					return By.partialLinkText(objectName);
				} else {
					return By.xpath(objectName);
				}
			}
		}		
		return null;
	}

	public void executeJavaScript(String scriptToExecute,WebElement element)
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(scriptToExecute,element);
	}

	//Method to Create and write the data to excel
	//return type changed to boolean to handle exception by Girija
	public boolean Writetoexcel(String filepath, String SheetName, ArrayList<String> header, ArrayList<String> value) throws FileNotFoundException
	{
		boolean result = true;
		try {
			File file = new File(filepath); 
			XSSFWorkbook wb = null;
			XSSFSheet sheet;
			Row headerrow;
			int RowCount = 0;
			Cell headercell;
			if(!file.exists()) {
				wb=new XSSFWorkbook();
				sheet = wb.createSheet(SheetName);
				headerrow=sheet.createRow(RowCount);
				for(int i=0;i<header.size();i++) {
					headercell = headerrow.createCell(i);
					headercell.setCellValue(header.get(i));
				}
				RowCount=RowCount+1;
				headerrow=sheet.createRow(RowCount);
				for(int j=0;j<value.size();j++) {
					headercell=headerrow.createCell(j);
					headercell.setCellType(XSSFCell.CELL_TYPE_STRING);
					headercell.setCellValue(value.get(j));
				}
				FileOutputStream fos=new FileOutputStream(filepath);
				wb.write(fos);
				fos.close();
				wb.close();
			}
			else {
				boolean found =false;
				FileInputStream fis= new FileInputStream(filepath);
				wb=new XSSFWorkbook(fis);
				sheet=wb.getSheet(SheetName);
				headerrow=sheet.getRow(RowCount);
				for(int i=0;i<header.size();i++) {
					found = false;
					String headerValue= header.get(i);
					for(int j=0;j<headerrow.getLastCellNum();j++) {
						if(headerValue.equals(headerrow.getCell(j).toString())) {
							found = true;
							break;
						}
					}
					if(!found) {
						headercell = headerrow.createCell(headerrow.getLastCellNum());
						headercell.setCellType(XSSFCell.CELL_TYPE_STRING);
						headercell.setCellValue(headerValue);
					}
				}
				int lastrowcount = sheet.getLastRowNum();
				headerrow=sheet.createRow(lastrowcount+1);
				for(int k=0;k<value.size();k++) {
					int colindex = 0;
					for(int l=0;l<sheet.getRow(RowCount).getLastCellNum();l++) {
						if(header.get(k).equals(sheet.getRow(RowCount).getCell(l).toString())) {
							colindex=l;
							break;
						}
					}
					headercell = headerrow.createCell(colindex);
					headercell.setCellValue(value.get(k));
				}
				FileOutputStream fos=new FileOutputStream(filepath);
				wb.write(fos);
				fos.close();
				wb.close();
				result=true;
			}

		}
		catch (Exception e) {
			// TODO: handle exception
			result=false;
		}
		return result;
	}
	
	public static String getCurrencyfrompropertyfile(String selectedCountry)
	{
		String Result="";
		try {
		String country=selectedCountry.replace(" ", "");
		FileHandlingUtilities fn = new FileHandlingUtilities();
		Properties p = fn.fnReadPropertyFile("/com/botsftool/resources/DSGCurrencyList.properties");
		Result = p.getProperty(country);
		}
		catch(Exception countrynotfound) {
			return Result.trim();
		}
		return Result.trim();		
	}
}
