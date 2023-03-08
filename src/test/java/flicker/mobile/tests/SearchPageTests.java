package flicker.mobile.tests;

import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import flicker.mobile.base.MobileBaseTestPage;
import flickr.mobile.pages.SearchPage;

public class SearchPageTests extends MobileBaseTestPage {

	@Test(description = "Validate search page details, welcome message")
	public void test01ValidateSearchPage() {
	
		SoftAssert softAssert= new SoftAssert();
		SearchPage searchPage= new SearchPage();
		softAssert.assertTrue(searchPage.getSearchInputElement().isDisplayed(), "Search input is not displayed on Search page.");
		if(isiOSDevice())
			softAssert.assertTrue(searchPage.getWelcomeMessageLabel().isDisplayed(), "Welcome message is not displayed on Search page.");
		softAssert.assertAll();
	}
	
	
	@Test(dataProvider="data-provider-postive-keywords", description =  "Validate search result shown for valid search keyword")
	public void test02ValidatePositiveSearch(String searchKeyword) {

		SearchPage searchPage= new SearchPage();
		searchPage.searchForKeyword(searchKeyword);
		Assert.assertTrue("Search results not displayed for searched keyword",searchPage.getSearchResultCount()>5);
		
	}
	
	@Test(dataProvider="data-provider-invalid-keywords", description =  "Validate search result are not shown for invalid search keyword.")
	public void test03ValidateInvalidSearch(String searchKeyword) {

		SearchPage searchPage= new SearchPage();
		searchPage.searchForKeyword(searchKeyword);
		Assert.assertTrue("No result found error message label is not displayed",searchPage.getNoResultMessageLabel().isDisplayed());
		
	}
	
	@Test(description =  "Validate infinite scrolling for result list and search results are getting updated as we scroll.")
	public void test04ValidateInfiniteScrolling() {

		SearchPage searchPage= new SearchPage();
		searchPage.searchForKeyword("Kittens");
		Assert.assertTrue("Search results not displayed for searched keyword",searchPage.getSearchResultCount()>5);
		if(isiOSDevice())
			Assert.assertTrue("Search results are not updated after scrolling, assertion based on page number.",searchPage.scrollAndCheckPageCountIsUpdated());
		Assert.assertTrue("Search results are not updated after scrolling, assertion based on result image.",searchPage.scrollAndCheckPageResultsUpdated());

	}
	
	 @DataProvider (name = "data-provider-postive-keywords")
     public Object[][] dpPositiveSearchKeywords(){
	 return new Object[][] {{"Kittens"}, {"Dogs"}, {"Laptops"}};
     }
	 
	 @DataProvider (name = "data-provider-invalid-keywords")
     public Object[][] dpInvalidSearchKeywords(){
	 return new Object[][] {{"DFgh34sdf"}, {"#$%@#"}};
     }
}
