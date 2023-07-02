package Test;


import org.testng.annotations.BeforeClass;
import DataUtility.BaseSetUp;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(features = "src/test/resources/features/", 
				 glue = "MavenProject.Automation",
				 plugin = {	"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" },
				 monochrome = true)

public class TestRunner extends AbstractTestNGCucumberTests {
	BaseSetUp base = new BaseSetUp();

	@BeforeClass
	/**
	 * This method is used to set intialsetup
	 * 
	 * @throws MalformedURLException
	 */
	public void initialSetUp(){
		base.initialSetup();
	}

}
