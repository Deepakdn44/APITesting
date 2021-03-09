package cucumber.Options;



import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;




@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/java/feature",
		plugin="json:target/jsonReports/cucumber-report.json",
		glue="stepDefinition")
public class TestRunner {

}
