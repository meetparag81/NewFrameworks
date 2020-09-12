package pages;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import frame.helper.IframeHelper;
import helper.Excelhelper.Exls_Reader;
import helper.TestUtil.TestUtil;
import helper.logger.LoggerHelper;
import helper.resorce.ResourceHelper;
import testBase.TestBase;

public class LibrariesPage extends TestBase {
	@FindBy(xpath="//a[text()='Add New'][1]")WebElement addNew;
	@FindBy(xpath="//iframe[@class='plateauIFrame ']")WebElement Frame1;
	@FindBy(xpath="//iframe[contains(@name,'PPCFrame')][1]")WebElement Frame2;
	@FindBy(xpath="//iframe[contains(@name,'PPCFrame')][1]")WebElement frame3;
	@FindBy(name="catalogID")WebElement Libraryid;
	@FindBy(xpath="//bdi[text()='Curricula']//ancestor::button[1]")WebElement curricula;
	@FindBy(xpath="//button[@title='Add Curricula']")WebElement addCurricula;
	@FindBy(xpath="(//input[@name='submitbutton'])[1]") WebElement submitbutton;
	@FindBy(xpath="//span[text()='Keyword:']//following::input[2]")WebElement searchbuttoncirricula;
	@FindBy(xpath="")
	Logger log=LoggerHelper.GetLogger(LibrariesPage.class);
	Exls_Reader reader = new Exls_Reader(ResourceHelper.GetResourcePath("\\src\\main\\java\\helper\\exceldata\\LMS_TestData.xlsx"));

	public LibrariesPage(){

		PageFactory.initElements(driver, this);
	}

	public void CreateLibraries() throws InterruptedException {
		int count = reader.getRowCount("AddLibraries");
		for(int rowcounter=2;rowcounter<=count;rowcounter++){
			TestUtil.WaitTillclickable(Frame1);
			IframeHelper.SwitchToFrame(Frame1);
			TestUtil.WaitTillclickable(addNew).click();
			driver.switchTo().defaultContent();
			TestUtil.WaitTillclickable(Frame2);
			IframeHelper.SwitchToFrame(Frame2);
			String Libraryidname= reader.getCellData("AddLibraries", "LibraryID", rowcounter);
			Libraryid.sendKeys(Libraryidname+TestUtil.CurrentTimestamp());
			Thread.sleep(3000);
			submitbutton.click();
			AdCiricula( rowcounter);

		}

	}

	private void AdCiricula(int rowcounter) throws InterruptedException {
		TestUtil.WaitTillclickable(curricula).click();
		addCurricula.click();
		Thread.sleep(1000);
		IframeHelper.SwitchToFrame(frame3);
		Thread.sleep(1000);
		searchbuttoncirricula.click();
		Thread.sleep(2000);
		List<WebElement>Cirriculumnames=driver.findElements(By.xpath("//table[@class='ResultsTable']//tr/td[1]"));
		String Curriculum_data = reader.getCellData("AddLibraries", "Add Curriculum", rowcounter);
		String[] Curriculumname = Curriculum_data.split(";");
		for(int Curriculumcounter=0;Curriculumcounter<Curriculumname.length;Curriculumcounter++){

			for(int namecounter=0;namecounter< Cirriculumnames.size();namecounter++){
				boolean flag = Curriculumname[Curriculumcounter].toString().trim().equalsIgnoreCase(Cirriculumnames.get(namecounter).getText().toString().trim());
				if(Curriculumname[Curriculumcounter].toString().trim().equalsIgnoreCase(Cirriculumnames.get(namecounter).getText().toString().trim())){
					driver.findElement(By.xpath("//table[@class='ResultsTable']//tr["+namecounter+"]/td[4]//input[1]")).click();
					log.info("the input box for"+Cirriculumnames.get(namecounter).getText()+" option is clicked");
					break;
				}

			}

		}

	}
}
