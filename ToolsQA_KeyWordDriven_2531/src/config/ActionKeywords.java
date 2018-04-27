package config;
 
import java.util.concurrent.TimeUnit;
 
//import static executionEngine.DriverScript.OR;
import static executionEngine.NewTest.OR;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import executionEngine.DriverScript;
import utility.Log;
 
public class ActionKeywords 
{
 
	public static WebDriver driver;
	private static Logger oLog = Logger.getLogger(ActionKeywords.class.getName());
	public static void openBrowser(String object,String data)
	{		
		oLog.info("Opening Browser");
		try
		{				
			if(data.equals("Mozilla"))
			{
				driver=new FirefoxDriver();
				Log.info("Mozilla browser started");				
			}
			else if(data.equals("IE"))
			{
				//Dummy Code, Implement you own code
				System.setProperty("webdriver.ie.driver", "./resources/IEDriverServer.exe");
				DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
				caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				driver=new InternetExplorerDriver(caps);
				oLog.info("IE browser started");
			}
			else if(data.equals("Chrome"))
			{
				//Dummy Code, Implement you own code
				driver=new ChromeDriver();
				oLog.info("Chrome browser started");
			}
 
			int implicitWaitTime=(10);
			driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
		}
		catch (Exception e)
		{
			Log.info("Not able to open the Browser --- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}
 
	public static void navigate(String object, String data)
	{
		try
		{
			oLog.info("Navigating to URL");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get(Constants.URL);
		}
		catch(Exception e)
		{
			Log.info("Not able to navigate --- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}
 
	public static void click(String object, String data)
	{
		try
		{
			oLog.info("Clicking on Webelement "+ object);
			driver.findElement(By.xpath(OR.getProperty(object))).click();
		}
		catch(Exception e)
		{
 			Log.error("Not able to click --- " + e.getMessage());
 			DriverScript.bResult = false;
         }
	}
 
	public static void input(String object, String data)
	{
		try
		{
			oLog.info("Entering the text in " + object);
			System.out.println(driver.getTitle());
			
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
		}
		catch(Exception e)
		{
			 Log.error("Not able to Enter UserName --- " + e.getMessage());
			 DriverScript.bResult = false;
		}
	}
 
	public static void waitFor(String object, String data) throws Exception
	{
		try
		{
			oLog.info("Wait for 5 seconds");
			Thread.sleep(5000);
		}catch(Exception e)
		{
			 Log.error("Not able to Wait --- " + e.getMessage());
			 DriverScript.bResult = false;
		}
	}
 
	public static void closeBrowser(String object, String data)
	{
		try
		{
			oLog.info("Closing the browser");
			driver.quit();
		}catch(Exception e)
		{
			 Log.error("Not able to Close the Browser --- " + e.getMessage());
			 DriverScript.bResult = false;
        }
	}
	
	public static void printText(String object, String data)
	{
		try
		{
			oLog.info("Printing Text present in : "+object);
			String LoggedInUser = driver.findElement(By.xpath(OR.getProperty(object))).getText();
			oLog.info(LoggedInUser);
		}
		catch(Exception e)
		{
 			Log.error("Not able to click --- " + e.getMessage());
 			DriverScript.bResult = false;
        }
	}
}