package helper.resorce;

import org.apache.log4j.Logger;

import helper.logger.LoggerHelper;



public class ResourceHelper {
	private Logger log = LoggerHelper.GetLogger(ResourceHelper.class);
	
	public static String  GetResourcePath(String path)
	{
		String BasePath = System.getProperty("user.dir");
				
				//System.out.println("Base Src folder location is"+ BasePath);
				return BasePath + path;
		
	}
	
	public static void main(String[] args) 
	{
		System.out.println(ResourceHelper.GetResourcePath("\\src\\main\\resorces\\configfile\\log4j.properties"));
		
	}
	

}
