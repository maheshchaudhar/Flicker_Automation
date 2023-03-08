package flickr.mobile.pages;

import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import flicker.mobile.base.MobileBaseTestPage;
import flicker.mobile.base.MobileCommonUtils;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.qameta.allure.Step;

public class SearchPage extends MobileBaseTestPage {

	public WebElement getSearchInputElement() {
	
		return getWebElement("search_input");
	}

	public WebElement getWelcomeMessageLabel() {
		return getWebElement("welcome_message_label");
	}

	public WebElement getClearTextButton() {
		return getWebElement("clear_text_button");
	}

	public WebElement getCancelButton() {
		return getWebElement("cancel_button");
	}

	public WebElement getNoResultMessageLabel() {
		return getWebElement("no_result_message");
	}

	public List<WebElement> getResultList() {
		return getWebElements("search_result_list");
	}
	public List<WebElement> getResultIamgeTitleElementsList() {
		return getWebElements("search_result_title_list");
	}
	
	public WebElement getVirticalScrollBarButton() {
		return getWebElement("vertical_scroll_bar");
	}

	@Step("Search for some keyword")
	public void searchForKeyword(String key) {
		WebElement searchInput = getSearchInputElement();
		waitUntilVisibilityOfElement(searchInput, defaultTimeout);
		searchInput.clear();
		searchInput.click();
		searchInput.sendKeys(key+"\n");
		Actions actions = new Actions(driver);
	    actions.sendKeys(Keys.ENTER);
	    actions.perform();
		// wait for search result to display
		int maxCount = 0;
		while (getSearchResultCount() == 0 && maxCount++ < 10) {
			if (getNoResultMessageLabel().isDisplayed())
				break;
		}

	}

	/**
	 * Scroll result page and check results count is updated correctly.
	 * @return
	 */
	@Step("Scroll result page and check results count is updated correctly.")
	public boolean scrollAndCheckPageCountIsUpdated() {

		int infiniteScrollIterations = 0;
		String scrollLabelWithPageCount, updatedScrollLabelWithPageCount;

		while (infiniteScrollIterations++ <= 3) {
			scrollLabelWithPageCount = getVirticalScrollBarButton().getAttribute("label");
			int scrollTimes = 0;
			while (scrollTimes++ < 7) {
				MobileCommonUtils.scrollDown("Fast");
				sleep(2000);
			}
			updatedScrollLabelWithPageCount = getVirticalScrollBarButton().getAttribute("label");

			if (updatedScrollLabelWithPageCount.equals(scrollLabelWithPageCount)) {
				return false;
			}
		}
		return true;
	}

	@Step("Scroll results page and check actual results images are updated")
	public boolean scrollAndCheckPageResultsUpdated() {
		int scrollTimes = 5;
		WebElement prevImageElement, updatedImageElement;
		String prevResultTitleText, updateResultTitleText;
		
		
		if(isiOSDevice()) {
			while (scrollTimes++ < 7) {
				prevImageElement = getResultList().get(getResultList().size() - 1);
				MobileCommonUtils.scrollDown("Fast");
				sleep(2000);
				updatedImageElement = getResultList().get(getResultList().size() - 1);
				if (prevImageElement.equals(updatedImageElement)) 
					return false;
			}
			
		}else {
			//Added different logic for android as there is functional difference in search result screen. Result titles for images is shown in android but not in iOS
			while (scrollTimes++ < 7) {
				prevResultTitleText = getResultIamgeTitleElementsList().get(getResultIamgeTitleElementsList().size() - 1).getText();
				MobileCommonUtils.scrollDown("Fast");
				sleep(2000);
				updateResultTitleText = getResultIamgeTitleElementsList().get(getResultIamgeTitleElementsList().size() - 1).getText();
				if (prevResultTitleText.equals(updateResultTitleText)) 
					return false;
			}
		}
		return true;
	}

	@Step("fetch search result count")
	public int getSearchResultCount() {
		return getResultList().size();
	}
}
