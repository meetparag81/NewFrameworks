package helper.logger;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import helper.resorce.ResourceHelper;



public class LoggerHelper {
private static boolean root= false;
	
	public static Logger GetLogger(Class cls){
			if(root)
			{
			return Logger.getLogger(cls);
			}
			PropertyConfigurator.configure(ResourceHelper.GetResourcePath("\\src\\main\\resorces\\configfile\\log4j.properties"));
			root=true;
			return Logger.getLogger(cls);	
		
			}
	public static void main(String[] args)
	{
		
		Logger log = LoggerHelper.GetLogger(LoggerHelper.class);
		log.info("test");
		
	} 

	

}
