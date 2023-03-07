package flicker.api.tests;

import java.io.File;

import org.testng.annotations.Test;


import api.commonutils.CommonUtils;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ResponseBody;


public class SampleRestAssuredTests extends flicker.base.BaseTestPage{
	
	public static void main(String[] args) {
	

		RestAssured.given().when().get("https://gorest.co.in/public/v2/users").then().log()
		  .all();
	}

	@Test

	public static void Test_Create_User() {
		ResponseBody body = RestAssured.given().when().get("https://gorest.co.in/public/v2/users").getBody();
		System.out.println("Bodyyyyyyy "+body.asString());
	}
	
	
	@Test
	public static void validateGetUserAPI() {
		logger.error("Test Logger=======");
		
		RestAssured.baseURI = "https://reqres.in/api/users?page=2";
		RestAssured.given().
		when().
		get().
		then().
		assertThat().	
		body(JsonSchemaValidator.matchesJsonSchema(CommonUtils.fetchSchemaFile("userSchema.json")));
		logger.info("Test Logger=======");
		
	}
}
