package flicker.mobile.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import flicker.base.BaseTestPage;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;

public class MobileBaseTestPage extends BaseTestPage{
	
	public static AppiumDriver driver;
	public static int implicitWait;
	private static 	String serverURL=null, platform = null,platformVersion=null,deviceName=null,automationName=null,app=null;
	public static Properties locatorRepo = new Properties();
	
	static {
		
		//load capabilities
		checkAndLoadCapabilities();
		
		//initialize driver
		DesiredCapabilities capabilities;
		if(platform.equals("iOS")) {
			capabilities = new DesiredCapabilities();
			capabilities.setCapability("platformName", platform);
			capabilities.setCapability("platformVersion", platformVersion);
			capabilities.setCapability("deviceName", deviceName);
			capabilities.setCapability("automationName", automationName);
			capabilities.setCapability("app", app);
			URL appiumServerURL = null;
			try {
				appiumServerURL = new URL(serverURL);
			} catch (MalformedURLException e) {
				logger.error("Failed to parse appium server URL, exception"+e.toString());
				e.printStackTrace();
				System.exit(0);
		
			}
			driver=new IOSDriver(appiumServerURL,capabilities);
			Duration duration= Duration.ofSeconds(20);
			driver.manage().timeouts().implicitlyWait(duration);

		}else if(platform.equalsIgnoreCase("ANDROID")){
			
			
		}else {
			
			
		}
		
		loadLocatorRepo(platform);		
		
	}
	
	private static void checkAndLoadCapabilities() {
		
		//Add validation here for null values
		
		serverURL= applicationProps.getProperty("serverURL");
	
		platform = applicationProps.getProperty("platform");
		platformVersion = applicationProps.getProperty("platformVersion");
		deviceName = applicationProps.getProperty("deviceName");
		automationName = applicationProps.getProperty("automationName");
		app = applicationProps.getProperty("app");
	
		implicitWait = Integer.parseInt(applicationProps.getProperty("implicitWait"));
	
	}
	
	private static void loadLocatorRepo(String platform) {
		//Load all properties 
		
		String folderPath = null;
		if(platform.equalsIgnoreCase("iOS")) {
			folderPath="resources/locators/iOS/search_page_loc.properties";
		}else if(platform.equalsIgnoreCase("Android")) {
			folderPath="resources/locators/android/search_page_loc.properties";
		}
		
		InputStream propIO = null;
		try {
			 propIO = new FileInputStream(folderPath);
			
		} catch (FileNotFoundException e) {
			System.out.println("application.properties => File not found");
			e.printStackTrace();
		}
		
		try {
			locatorRepo.load(propIO);
			
		} catch (IOException e) {
			System.out.println("Failed to load properties");
			e.printStackTrace();
		}
		
		
	}
	
	
	public static WebElement getWebelement(String key) {
		
		String locValue=locatorRepo.getProperty(key);

		String[] list=locValue.split(":");
		WebElement element = null;
		switch (list[0].toUpperCase()) {
		
		case "ACCESSIBILITY_ID":
			element=driver.findElement(AppiumBy.accessibilityId(list[1]));
			break;

		default:
			break;
		}
		
		return element;
		
	}

}
