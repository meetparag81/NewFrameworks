package testBase;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Hello world!
 *
 */
public class App extends TestBase
{
	@BeforeMethod
	public void SetUp()
	{
		TestBase.initalization();
	}
  @Test(priority=0)
  public void AppTestForPassCondition(){
	  Assert.assertEquals(true, true, "testcasePassed");
	  
  }
  @Test(priority=1)
  public void AppTestForFailCondition(){
	  Assert.assertEquals(true, false, "testcaseFailed");
	  
	  
  }
  
  @AfterMethod
	public void TerDown()
	{
		driver.quit();
	}
  
}
