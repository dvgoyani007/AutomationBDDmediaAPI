package MavenProject.Automation;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import DataUtility.BaseSetUp;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.Method;

public class BaseStepDefinition extends BaseSetUp {

	@Given("User loads the {string}")
	public void userLoadsThe(String RampManifestingBaseURI) throws IOException {
		RestAssured.baseURI = prop.getProperty(RampManifestingBaseURI);
		request = RestAssured.given();
	}

	@When("User hits {string} request of {string} api")
	public void userHitsRequestOfApi(String method, String apiPath) {
		if (method.equalsIgnoreCase("GET")) {
			response = request.request(Method.GET, prop.getProperty(apiPath));
		}
		if (method.equalsIgnoreCase("POST")) {
			response = request.request(Method.POST, prop.getProperty(apiPath));
		}
		if (method.equalsIgnoreCase("PUT")) {
			response = request.request(Method.PUT, prop.getProperty(apiPath));
		}
		if (method.equalsIgnoreCase("DELETE")) {
			response = request.request(Method.DELETE, prop.getProperty(apiPath));
		}
	}

	@Then("Response status code should be {string}")
	public void responseStatusCodeShouldBe(String statusCode) {
		Assert.assertEquals(response.getStatusCode(), Integer.parseInt(statusCode));
		js = response.jsonPath();

	}

	@Then("User verifies the response time should be below {int}")
	public void userVerifiesTheResponseTimeShouldBeBelow(Integer requiredTime) {
		int responseTime = (int) response.getTimeIn(TimeUnit.MILLISECONDS);
		Assert.assertTrue(responseTime < requiredTime);
	}

	@Then("User verifies {string} field is never null or empty in response")
	public void userVerifiesFieldIsNeverNullOrEmptyInResponse(String fieldname) {
		Object object = responseToObjectConversion();
		JSONObject jsonObjectResponse = (JSONObject) object;
		JSONArray jsonArrayResponse = (JSONArray) jsonObjectResponse.get("data");
		for (int i = 0; i < jsonArrayResponse.size(); i++) {
			jsonObjectResponse = (JSONObject) jsonArrayResponse.get(i);
			switch (fieldname) {
			case "id":
				Assert.assertFalse(jsonObjectResponse.get(fieldname).toString().isEmpty());
				Assert.assertFalse(jsonObjectResponse.get(fieldname) == null);
				break;
			case "primary":
				jsonObjectResponse = (JSONObject) jsonObjectResponse.get("title_list");
				Assert.assertFalse(jsonObjectResponse.get(fieldname).toString().isEmpty());
				Assert.assertFalse(jsonObjectResponse.get(fieldname) == null);
				break;
			}
		}
	}

	@Then("User verifies value of response field {string} should be {string}")
	public void userVerifiesValueOfResponseFieldShouldBe(String fieldName, String fieldValue) {
		Object object = responseToObjectConversion();
		JSONObject jsonObjectResponse = (JSONObject) object;
		JSONArray jsonArrayResponse = (JSONArray) jsonObjectResponse.get("data");
		for (int i = 0; i < jsonArrayResponse.size(); i++) {
			jsonObjectResponse = (JSONObject) jsonArrayResponse.get(i);
			Assert.assertEquals(jsonObjectResponse.get(fieldName).toString().trim(), fieldValue);
		}
	}

	@Then("User verifies only one track in the list has {string} field in {string} as true in response")
	public void userVerifiesOnlyOneTrackInTheListHasFieldInAsTrueInResponse(String fieldName, String fieldLocation) {
		Object object = responseToObjectConversion();
		JSONObject jsonObjectResponse = (JSONObject) object;
		JSONArray jsonArrayResponse = (JSONArray) jsonObjectResponse.get("data");
		int count = 0;
		for (int i = 0; i < jsonArrayResponse.size(); i++) {
			jsonObjectResponse = (JSONObject) jsonArrayResponse.get(i);
			jsonObjectResponse = (JSONObject) jsonObjectResponse.get(fieldLocation);
			System.out.println(jsonObjectResponse.get(fieldName));
			boolean result = (boolean) jsonObjectResponse.get(fieldName);
			if (result == true) {
				count++;
			}
			if (count > 1) {
				throw new RuntimeException();
			} else {
				continue;
			}
		}
	}

	@Then("User verifies {string} header as {string} in response")
	public void userVerifiesHeaderAsInResponse(String fieldName, String fieldValue) {
		Assert.assertEquals(response.getHeader(fieldName), fieldValue);
	}

}