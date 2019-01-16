package AppleCar.ProblemTesla;

import org.testng.annotations.BeforeClass;

import io.restassured.RestAssured;
import utils.LoadProp;

public class BaseClass {
	
	@BeforeClass
	public static void setUpC() throws Exception {		

		
		//loading properties specific to env
		LoadProp.loadProperties();
		RestAssured.baseURI=LoadProp.prop.getProperty("env_url");
		RestAssured.basePath=LoadProp.prop.getProperty("basePath");
	}

}
