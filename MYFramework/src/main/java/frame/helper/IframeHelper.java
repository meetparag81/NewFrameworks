package frame.helper;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

import helper.logger.LoggerHelper;
import testBase.TestBase;

public class IframeHelper extends TestBase
{
	
private static Logger log = LoggerHelper.GetLogger(IframeHelper.class)	;
	


/**
 * This method will SwitchToFrame based on index
 * 
 * @param unit
 */
		
		

public void SwitchToFrame(int index)
{
	driver.switchTo().frame(index);
	log.info("Sitch to:"+ index+ "framme");
}

/**
 * This method will SwitchToFrame based on framename
 * 
 * @param unit
 */


public  static void SwitchToFrame(String  FrameName)
{
	driver.switchTo().frame(FrameName);
	log.info("Sitch to:"+ FrameName+ "framme");
}


/**
 * This method will SwitchToFrame based on webelement
 * 
 * @param unit
 */

public  static void SwitchToFrame(WebElement  frameelement)
{
	driver.switchTo().frame(frameelement);
	log.info("Sitch to:"+ frameelement+ "framme");
}



}
