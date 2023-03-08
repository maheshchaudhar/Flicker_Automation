package flicker.mobile.base;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;

import com.google.common.collect.ImmutableMap;

public class MobileCommonUtils extends MobileBaseTestPage {

	/**
	 * Method to scroll down on mobile app
	 * @param scrollSpeed
	 */
	public static void scrollDown(String scrollSpeed) {
		int fromY;
		Dimension windowSize = driver.manage().window().getSize();

		switch (scrollSpeed.toUpperCase()) {

		case "MEDIUM":
			fromY = windowSize.height / 2;
			break;

		case "FAST":
			fromY = windowSize.height - 100;
			break;
			
		default:
			fromY = 250;
			break;
		}
		try {
		if (isiOSDevice())
		{
			((JavascriptExecutor) driver).executeScript("mobile: dragFromToForDuration",
					ImmutableMap.of("fromX", 100, "fromY", fromY, "toX", 100, "toY", 150, "duration", 0.1));
			
		}else
		{
			((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", ImmutableMap.of(
				    "left", 100, "top", 100, "width", windowSize.width, "height", windowSize.height,
				    "direction", "down",
				    "percent", 3.0
				));
			
		}
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to scroll down" + e.toString());
		}
	}
}
