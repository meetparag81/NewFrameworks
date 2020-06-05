package helper.Analyzer;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

	int counter = 0;
	int retrylimit=3;
	@Override
	public boolean retry(ITestResult arg0)
	{
		if(counter < retrylimit)
		{
			counter++;
			return true;
		}
		return false;
	}
	

}
