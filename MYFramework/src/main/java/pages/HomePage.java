package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import PagesForRCM.CreateRequisitionPage;
import PagesForRCM.HireEmployeePage;
import helper.Excelhelper.Exls_Reader;
import helper.Pick.Pickhelper;
import helper.TestUtil.TestUtil;
import helper.javascriptexecutor.JavascriptHelper;
import helper.logger.LoggerHelper;
import helper.resorce.ResourceHelper;
import testBase.TestBase;
import helper.TestUtil.CustomMethodhelper;

public class HomePage extends TestBase {
	@FindBy(xpath="//div[@id='sap-ui-blocklayer-popup' or @class='sapUiBLy sfMFloatingPlusButtonTransparentBlk']")WebElement Add;
	@ FindBy(xpath="//img[@title='Accenture, LLP Logo']")WebElement Logo;
	@FindBy(xpath="//img[contains(@title, 'gpatidar')]")WebElement proxyuser;
	@FindBy(xpath="//a[contains(text(),'Proxy Now')]")WebElement ProxyNow;
	@FindBy(xpath = "//span[text()='Select Target User']//following::bdi[text()='OK']//ancestor::button")WebElement OK;
	@FindBy(xpath="//bdi[contains(text(),'Please enter target user name:')]//following::input[1]")WebElement proxyusername;
	@FindBy(xpath="(//bdi[text()='Home'])[1]//ancestor::span[1]")WebElement Home;
	@FindBy(xpath="//a[text()='Recruiting']")WebElement Recruiter;
	@FindBy(xpath="//a[contains(text(),'Create New')]")WebElement CreateNew;
	@FindBy(id="bizXSearchField-I")WebElement searchbox;
	@FindBy(xpath ="//h3[text()='Learning Administration']//ancestor::div[2]")WebElement LearningAdminstratorTile;
	private WebElement addemoloyeeoption;
	private @FindBy(xpath="(//*[text()='Learning'])[1]") WebElement Learning;
	private  Logger log=LoggerHelper.GetLogger(HomePage.class)	;
	Exls_Reader reader = new Exls_Reader(ResourceHelper.GetResourcePath("\\src\\main\\java\\helper\\exceldata\\Frameworkworksheet.xlsx"));
	
	public HomePage(){
		PageFactory.initElements(driver, this);
	}
	
	public String ClicktheAdd()
	{
		try
		{
			TestUtil.VisibleOn(driver, Add, 10);
			if(Add.isDisplayed())
			{
				JavascriptHelper.jsClick(driver, Add);
				log.info("ckicked on Addpopup");
			}
			
			
		}
		catch(Exception e)
		{
		log.info("Timeoutexceptionseen");
		
		}
		
		
		
		String logotitle=Logo.getAttribute("Title");
		return logotitle;
		
	}

	public void ProxywithUserWiithValue(String sheetname,String columnname, int rowval ) {
		if(proxyuser.isDisplayed()){
			//ClicktheAdd();
			proxyuser.click();
			ProxyNow.click();
					
		}
		else
		{
			ClicktheAdd();
			proxyuser.click();
			ProxyNow.click();
			
		}
		
		String val = reader.getCellData(sheetname, columnname, rowval);
		Pickhelper.Pick(val, proxyusername);
		OK.click();
	}
	
	public boolean HomeIsDisplay()
	{
		boolean flag= false;
		if(!Home.isDisplayed())
		{
			return flag;
		}
		 flag=true;
		return flag;
	}
	
	public CreateRequisitionPage clickonCreareRequisition()
	{
		
		ProxywithUser();
		ClicktheAdd();
		
		if(!HomeIsDisplay())
		{
		ClicktheAdd();
		}
		try
		{
			TestUtil.VisibleOn(driver, Home, 20);
			ClicktheAdd();
			Home.click();
			Recruiter.click();
			TestUtil.VisibleOn(driver, CreateNew, 10);
			CreateNew.click();
			log.info("clicked on Home button");
			Recruiter.click();
			TestUtil.VisibleOn(driver, CreateNew, 10);
			CreateNew.click();
		}
		catch(Exception e)
		{
			e.fillInStackTrace();
			log.info(e.getMessage());
			
		}
		
		return new CreateRequisitionPage();
		}
	public void ProxywithUser() {
		String val = reader.getCellData("Homepage", "value", 2);
		Pickhelper.Pick(val, proxyusername);
		OK.click();
		
	}

	public HireEmployeePage SelectAddEmployee() throws InterruptedException
	{
		int rows = 2;
		
			try {
				TestUtil.VisibleOn(driver, searchbox, 30);
				searchbox.click();searchbox.click();
				String searchemp=  reader.getCellData("AddNewEmployee", "Search",rows);
				searchbox.sendKeys(searchemp);
				Thread.sleep(2000);
				addemoloyeeoption.click();
				
			}
			catch(Exception e)
			{
		
				log.info(e.getCause());
			}
		return new HireEmployeePage() ;
	}
	
	public LearningUserPage ClickOnTheLearning() {
		try{
			TestUtil.VisibleOn(driver, Home, 30);
			Home.click();
			Learning.click();		
		}
		catch (Exception e) {
			log.info("EitherElement is not seen in defined waittime"+ "or"+ e.getCause());
		}
		return new LearningUserPage();		
		
	}
	
	
	

	public LearningAdMinistrationPage ClickOnTheTile() throws InterruptedException {
		CustomMethodhelper.WaitTillElementisnotclickable(LearningAdminstratorTile);
		TestUtil.ActionForMovetoElement(LearningAdminstratorTile);
		LearningAdminstratorTile.click();
		log.info("LearningAdminstratorTile"+ "is clciked");
		return new LearningAdMinistrationPage();		
		
	}
	

}
