package flickr.mobile.pages;

import org.openqa.selenium.By;

import flicker.mobile.base.MobileBaseTestPage;
import io.appium.java_client.MobileBy;

public class SearchPage extends MobileBaseTestPage {

	public void searchForKeyword(String key) {
		getWebelement("search_input").sendKeys(key);
	}
}
