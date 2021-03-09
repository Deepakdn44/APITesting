package stepDefinition;

import cucumber.api.java.Before;

public class Hooks {

	@Before("@DeletePlace")
	public void beforeScenario() throws Throwable {
		
		StepDefinition sd = new StepDefinition();
		
		if(StepDefinition.place_id==null) {
		sd.add_Place_payload_with("Shetty", "English", "J-P Nagar");
		sd.user_calls_with_HTTP_Request("AddPlaceAPI", "POST");
		sd.verify_place_id_created_maps_to_using("Shetty", "GetPlaceAPI");
		}
		
	}
}
