package executionEngine;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import org.apache.log4j.xml.DOMConfigurator;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import config.ActionKeywords;
import config.Constants;
import utility.ExcelUtils;
import utility.Log;
import utility.Screenshot;

public class NewTest 
{
	public static Properties OR;
	public static ActionKeywords actionKeywords;
	public static String sActionKeyword;
	public static String sPageObject;
	public static Method method[];
 
	public static int iTestStep;
	public static int iTestLastStep;
	public static String sTestCaseID;
	public static String sRunMode;
	public static String sData;
	public static boolean bResult;
	
	public static ExtentReports extent;
	public static ExtentTest test;
	public    ITestResult result;
	
	static int i=0;
	
	static
	{
		System.out.println("From Static");
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		System.out.println("From Static");
		System.out.println("./reports/"+formater.format(calendar.getTime())+".html");
		extent = new ExtentReports("./reports/"+formater.format(calendar.getTime())+".html",false);
		System.out.println("From Static");
	}
	
	
	@BeforeTest
	public void BeforeTest() throws Exception 
	{
		System.out.println("In Before Method");
		ExcelUtils.setExcelFile(Constants.Path_TestData);
    	DOMConfigurator.configure("./src/config/log4j.xml");
    	String Path_OR = Constants.Path_OR;
		FileInputStream fs = new FileInputStream(Path_OR);
		OR= new Properties(System.getProperties());
		OR.load(fs);
		
		actionKeywords = new ActionKeywords();
		method = actionKeywords.getClass().getMethods();	
	}
	
	public void getResult(ITestResult result) throws IOException
	{
		if(result.getStatus()==ITestResult.SUCCESS)
		{
			test.log(LogStatus.PASS, result.getName()+" : test is pass");
		}
		else if(result.getStatus()==ITestResult.SKIP)
		{
			test.log(LogStatus.SKIP, result.getName()+" : test is skip and skip reason is : "+result.getThrowable());
		}
		else if(result.getStatus()==ITestResult.FAILURE)
		{
			test.log(LogStatus.FAIL, result.getName()+" : test is failed "+result.getThrowable());
			String screen = Screenshot.getScreenShot("");
			test.log(LogStatus.FAIL, test.addScreenCapture(screen));
		}
		else if(result.getStatus()==ITestResult.STARTED)
		{
			test.log(LogStatus.INFO, result.getName()+" : test is Started");
		}
	}
	
	@AfterMethod
	public void afterMethod(ITestResult result) throws IOException
	{
		getResult(result);
	}
	
	@BeforeMethod
	public void beforeMethod(Method result)
	{
		test = extent.startTest(result.getName());
		test.log(LogStatus.INFO, result.getName()+" test Started");
	}
	
	@AfterClass(alwaysRun=true)
	public void endTest()
	{
		extent.endTest(test);
		extent.flush();
	}
	//@Test
	public void Test_01() throws Exception 
	{
		int iTotalTestCases = ExcelUtils.getRowCount(Constants.Sheet_TestCases);
		for(int iTestcase=1;iTestcase<iTotalTestCases;iTestcase++)
		{
			bResult = true;
			sTestCaseID = ExcelUtils.getCellData(iTestcase, Constants.Col_TestCaseID, Constants.Sheet_TestCases); 
			sRunMode = ExcelUtils.getCellData(iTestcase, Constants.Col_RunMode,Constants.Sheet_TestCases);
			if (sRunMode.equals("Yes"))
			{
				Log.startTestCase(sTestCaseID);
				iTestStep = ExcelUtils.getRowContains(sTestCaseID, Constants.Col_TestCaseID, Constants.Sheet_TestSteps);
				iTestLastStep = ExcelUtils.getTestStepsCount(Constants.Sheet_TestSteps, sTestCaseID, iTestStep);
				bResult=true;
				for (;iTestStep<iTestLastStep;iTestStep++)
				{
		    		sActionKeyword = ExcelUtils.getCellData(iTestStep, Constants.Col_ActionKeyword,Constants.Sheet_TestSteps);
		    		sPageObject = ExcelUtils.getCellData(iTestStep, Constants.Col_PageObject, Constants.Sheet_TestSteps);
		    		sData = ExcelUtils.getCellData(iTestStep, Constants.Col_DataSet, Constants.Sheet_TestSteps);
		    		execute_Actions();
					if(bResult==false)
					{
						ExcelUtils.setCellData(Constants.KEYWORD_FAIL,iTestcase,Constants.Col_Result,Constants.Sheet_TestCases);
						Log.endTestCase(sTestCaseID);
						break;
					}						
				}
				if(bResult==true)
				{
					ExcelUtils.setCellData(Constants.KEYWORD_PASS,iTestcase,Constants.Col_Result,Constants.Sheet_TestCases);
					Log.endTestCase(sTestCaseID);	
				}					
			}
		}	
	}
	
	private static void execute_Actions() throws Exception 
	{
		for(int i=0;i<method.length;i++)
		{
			if(method[i].getName().equals(sActionKeyword))
			{
				method[i].invoke(actionKeywords,sPageObject, sData);
				if(bResult==true)
				{
					ExcelUtils.setCellData(Constants.KEYWORD_PASS, iTestStep, Constants.Col_TestStepResult, Constants.Sheet_TestSteps);
					break;
				}
				else
				{
					ExcelUtils.setCellData(Constants.KEYWORD_FAIL, iTestStep, Constants.Col_TestStepResult, Constants.Sheet_TestSteps);
					ActionKeywords.closeBrowser("","");
					break;
				}
			}
		}
	}

	@DataProvider(name="testData")
	public Object[][] dataSource()
	{
		return ExcelUtils.getExcelData("./src/dataEngine/DataEngine.xlsx", "Test Cases");
	}
	
	@Test(dataProvider="testData")
	public void testLogic(String TestCaseId, String Description , String RunMode , String Result) throws Exception
	{
		i++;
		System.out.println("Number : "+i);
		
		System.out.println("Test Case ID : "+TestCaseId);
		System.out.println("Test Case Description : "+Description);
		System.out.println("Test Case RunMode : "+RunMode);
		System.out.println("Test Case Result : "+Result);
		
		int iTotalTestCases = ExcelUtils.getRowCount(Constants.Sheet_TestCases);
		//for(int iTestcase=1;iTestcase<iTotalTestCases;iTestcase++)
		//{
			bResult = true;
			//sTestCaseID = ExcelUtils.getCellData(iTestcase, Constants.Col_TestCaseID, Constants.Sheet_TestCases); 
			sTestCaseID = TestCaseId; 
			//sRunMode = ExcelUtils.getCellData(iTestcase, Constants.Col_RunMode,Constants.Sheet_TestCases);
			sRunMode = RunMode;
			if (sRunMode.equals("Yes"))
			{
				Log.startTestCase(sTestCaseID);
				iTestStep = ExcelUtils.getRowContains(sTestCaseID, Constants.Col_TestCaseID, Constants.Sheet_TestSteps);
				iTestLastStep = ExcelUtils.getTestStepsCount(Constants.Sheet_TestSteps, sTestCaseID, iTestStep);
				bResult=true;
				for (;iTestStep<iTestLastStep;iTestStep++)
				{
		    		sActionKeyword = ExcelUtils.getCellData(iTestStep, Constants.Col_ActionKeyword,Constants.Sheet_TestSteps);
		    		sPageObject = ExcelUtils.getCellData(iTestStep, Constants.Col_PageObject, Constants.Sheet_TestSteps);
		    		sData = ExcelUtils.getCellData(iTestStep, Constants.Col_DataSet, Constants.Sheet_TestSteps);
		    		execute_Actions();
					if(bResult==false)
					{
						ExcelUtils.setCellData(Constants.KEYWORD_FAIL,i,Constants.Col_Result,Constants.Sheet_TestCases);
						Log.endTestCase(sTestCaseID);
						break;
					}						
				}
				if(bResult==true)
				{
					ExcelUtils.setCellData(Constants.KEYWORD_PASS,i,Constants.Col_Result,Constants.Sheet_TestCases);
					Log.endTestCase(sTestCaseID);	
				}					
			}
		//}
	}
}
