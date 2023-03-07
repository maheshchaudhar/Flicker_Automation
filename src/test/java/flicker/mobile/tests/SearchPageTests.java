package flicker.mobile.tests;

import org.testng.annotations.Test;

import flickr.mobile.pages.SearchPage;

public class SearchPageTests {

	
	@Test
	public void testScenarion1() {

		SearchPage searchPage= new SearchPage();
		searchPage.searchForKeyword("dogs");
	}
}
