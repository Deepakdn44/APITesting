Feature: Validating PLace API's

@AddPlace @Regression
Scenario Outline: Verify if place is being Successfully added using AddPlaceAPI
	Given Add Place payload with "<name>" "<language>" "<address>"
	When user calls "AddPlaceAPI" with "POST" HTTP Request
	Then The API call got success with status code 200
	And "status" in response body is "OK"
	And "scope" in response body is "APP"
	And verify place_id created maps to "<name>" using "GetPlaceAPI"
	
Examples:
	|name    |  language  | address      |
	|A House | English-EN | Church street|
#	|B House | Spanish-SN | Pool Street  |

@DeletePlace @Regression
Scenario: Verify if delete place functionality is working
	Given DeletePlace payload
	When user calls "DeletePlaceAPI" with "POST" HTTP Request
	Then The API call got success with status code 200
	And "status" in response body is "OK"
	