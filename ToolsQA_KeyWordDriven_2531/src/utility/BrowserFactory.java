package utility;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class BrowserFactory 
{
	private static Map<String , WebDriver> drivers = new HashMap<String , WebDriver>();
	
	public static WebDriver getBrowser(String browserName)
	{
		WebDriver driver = null;
		
		switch(browserName)
		{
		case "IE":
			driver = drivers.get("IE");
			if(driver==null)
			{
				System.setProperty("webdriver.ie.driver", "./drivers/IEDriverServer.exe");
				DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
				caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				driver = new InternetExplorerDriver(caps);
				drivers.put("IE", driver);
			}
			
		}
		return driver;
	}
	
	public static void closeAllDriver() 
	{
		for (String key : drivers.keySet()) 
		{
			drivers.get(key).close();
			drivers.get(key).quit();
			drivers.put("IE", null);
		}
	}
}
