package flicker.mobile.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import flicker.base.BaseTestPage;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public class MobileBaseTestPage extends BaseTestPage {

	public static AppiumDriver driver;
	public static int implicitWait;
	public static int defaultTimeout = 20;

	private static String serverURL = null, platform = null, platformVersion = "", deviceName = null,
			automationName = null, app = null,appActivity=null;
	public static Properties locatorRepo = new Properties();

	static {

		// load capabilities
		checkAndLoadCapabilities();

		// initialize driver
		DesiredCapabilities capabilities;
		if (platform.equals("iOS")) {
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
				logger.error("Failed to parse appium server URL, exception" + e.toString());
				e.printStackTrace();
				Assert.fail("Failed to parse appium server URL. Please provide correct URL");
			}
			driver = new IOSDriver(appiumServerURL, capabilities);
			Duration duration = Duration.ofSeconds(20);
			driver.manage().timeouts().implicitlyWait(duration);

		} else if (platform.equalsIgnoreCase("ANDROID")) {
			capabilities = new DesiredCapabilities();
			capabilities.setCapability("platformName", platform);
 			capabilities.setCapability("deviceName", deviceName);
			capabilities.setCapability("automationName", automationName);
			capabilities.setCapability("app", app);
			capabilities.setCapability("appPackage", appActivity);
			
			URL appiumServerURL = null;
			try {
				appiumServerURL = new URL(serverURL);
			} catch (MalformedURLException e) {
				logger.error("Failed to parse appium server URL, exception" + e.toString());
				e.printStackTrace();
				Assert.fail("Failed to parse appium server URL. Please provide correct URL");
			}
			driver = new AndroidDriver(appiumServerURL, capabilities);
			Duration duration = Duration.ofSeconds(20);
			driver.manage().timeouts().implicitlyWait(duration);

		} else {

		}

		loadLocatorRepo(platform);

	}

	/**
	 * method to check and load appium capabilities from applciation properties file
	 */
	private static void checkAndLoadCapabilities() {
		// Capabilities
		serverURL = applicationProps.getProperty("serverURL");
		platform = applicationProps.getProperty("platform");
		platformVersion = applicationProps.getProperty("platformVersion");
		deviceName = applicationProps.getProperty("deviceName");
		automationName = applicationProps.getProperty("automationName");
		app = applicationProps.getProperty("app");
		appActivity =applicationProps.getProperty("appActivity");
		if (serverURL.isEmpty() || platform.isEmpty() || platformVersion.isEmpty() || deviceName.isEmpty()
				|| automationName.isEmpty() || app.isEmpty()) {
			logger.error(
					"Please fill all required details in application.properties. Refer readme file for reference.");
			Assert.fail("Please fill all required details in application.properties");

		}
		// Timeouts
		implicitWait = Integer.parseInt(applicationProps.getProperty("implicitWait"));
		defaultTimeout = Integer.parseInt(applicationProps.getProperty("defaultTimeout"));

	}

	/**
	 * Load locators from repository files
	 * 
	 * @param platform
	 */
	private static void loadLocatorRepo(String platform) {
		// Load all properties

		String folderPath = null;
		if (platform.equalsIgnoreCase("iOS")) {
			folderPath = "resources/locators/iOS/";
		} else if (platform.equalsIgnoreCase("Android")) {
			folderPath = "resources/locators/android/";
		}

		File resource_dir;
		InputStream propIO = null;
		try {
			resource_dir = new File(folderPath);
			File[] listLocFiles = resource_dir.listFiles();
			for (File file : listLocFiles) {
				propIO = new FileInputStream(file);
				locatorRepo.load(propIO);
			}

		} catch (Exception e) {
			logger.error("Failed to load locator files.");
			e.printStackTrace();
			Assert.fail("Failed to load locator Files.");
		}

	}

	/**
	 * Get webelement
	 * 
	 * @param key
	 * @return
	 */
	public static WebElement getWebElement(String key) {

		String locValue = locatorRepo.getProperty(key);

		String[] list = locValue.split("=>");
		WebElement element = null;
		switch (list[0].toUpperCase()) {

		case "ACCESSIBILITY_ID":
			element = driver.findElement(AppiumBy.accessibilityId(list[1]));
			break;

		case "NAME":
			element = driver.findElement(AppiumBy.name(list[1]));
			break;

		case "IOS_CLASS_CHAIN":
			element = driver.findElement(AppiumBy.iOSClassChain(list[1]));
			break;

		case "ID":
			element = driver.findElement(AppiumBy.id(list[1]));
			break;

		case "XPATH":
			element = driver.findElement(AppiumBy.id(list[1]));
			break;

		default:
			break;
		}

		return element;

	}

	/**
	 * Get list of webelements
	 * 
	 * @param key
	 * @return
	 */
	public static List<WebElement> getWebElements(String key) {

		String locValue = locatorRepo.getProperty(key);

		String[] list = locValue.split("=>");
		List<WebElement> element = null;
		switch (list[0].toUpperCase()) {

		case "ACCESSIBILITY_ID":
			element = driver.findElements(AppiumBy.accessibilityId(list[1]));
			break;

		case "NAME":
			element = driver.findElements(AppiumBy.name(list[1]));
			break;

		case "IOS_CLASS_CHAIN":
			element = driver.findElements(AppiumBy.iOSClassChain(list[1]));
			break;

		case "ID":
			element = driver.findElements(AppiumBy.id(list[1]));
			break;

		case "XPATH":
			element = driver.findElements(AppiumBy.xpath(list[1]));
			break;

		default:
			break;
		}

		return element;

	}

	/**
	 * Wait unitl element is visible
	 * 
	 * @param element
	 * @param timeout
	 * @return
	 */
	public static WebElement waitUntilVisibilityOfElement(WebElement element, int timeout) {
		Duration duration = Duration.ofSeconds(timeout);
		WebDriverWait wait = new WebDriverWait(driver, duration);
		return wait.until(ExpectedConditions.visibilityOf(element));
	}

	/**
	 * Method to sleep execution for defined time
	 * 
	 * @param miliseconds
	 */
	public void sleep(int miliseconds) {
		try {
			Thread.sleep(miliseconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean isiOSDevice() {
		if(platform.toUpperCase().equals("IOS"))
			return true;
		else 
			return false;
	}

}
