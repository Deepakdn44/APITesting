package stepDefinition;



import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import org.junit.runner.RunWith;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.junit.Cucumber;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResource;
import resources.TestData;
import resources.Utils;



@RunWith(Cucumber.class)
public class StepDefinition extends Utils{

	ResponseSpecification res;
	RequestSpecification reqspec;
	Response response;
	TestData data = new TestData();
	static String place_id;
	@Given("^Add Place payload with \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
	public void add_Place_payload_with(String name, String language, String address) throws Throwable {
		
		reqspec = given().spec(requestSpecifications()).body(data.addPlacePayLoad(name, language, address));
    }


	@When("^user calls \"([^\"]*)\" with \"([^\"]*)\" HTTP Request$")
	public void user_calls_with_HTTP_Request(String resource, String method) throws Throwable {
		APIResource resourceAPI=APIResource.valueOf(resource);
		System.out.println(resourceAPI.getResource());
		
		
		res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		if(method.equalsIgnoreCase("POST"))
			response = reqspec.when().post(resourceAPI.getResource());
		if(method.equalsIgnoreCase("GET"))
			response = reqspec.when().get(resourceAPI.getResource());
		if(method.equalsIgnoreCase("DELETE"))
			response = reqspec.when().delete(resourceAPI.getResource());
    }

    @Then("^The API call got success with status code 200$")
    public void the_api_call_got_success_with_status_code_200() throws Throwable {
        assertEquals(response.getStatusCode(),200);
    }

    @And("^\"([^\"]*)\" in response body is \"([^\"]*)\"$")
    public void something_in_response_body_is_something(String keyValue, String expectedValue) throws Throwable {
        assertEquals(getJsonPath(response, keyValue), expectedValue);
    }
	@Then("^verify place_id created maps to \"([^\"]*)\" using \"([^\"]*)\"$")
    public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
		
		place_id = getJsonPath(response, "place_id");
		reqspec = given().spec(requestSpecifications()).queryParam("place_id", place_id);
		user_calls_with_HTTP_Request(resource, "GET");
		String actualName = getJsonPath(response, "name");
		assertEquals(actualName, expectedName);
    }
	
	@Given("^DeletePlace payload$")
	public void deleteplace_payload() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		reqspec = given().spec(requestSpecifications()).body(data.deletePlace(place_id));
	}
	
}
