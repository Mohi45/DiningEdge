package com.diningedge.Rest;

import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

public class RestTest {
	@Test
	public void validateGetResponse() {
		Response res = given().baseUri("https://49bc7f79-bbe8-44d5-aa80-34a03fa54225.mock.pstmn.io")
				.header("x-mock-response-code", "200").log().all().when().get("/get").then().log().all().assertThat()
				.statusCode(200).extract().response();
		System.out.println("Message= " + res.path("msg"));
		String msg = res.path("msg").toString();
		Assert.assertEquals(msg, "successful");
	}
}