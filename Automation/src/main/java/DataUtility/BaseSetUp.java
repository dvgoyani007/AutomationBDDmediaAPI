package DataUtility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.json.simple.parser.JSONParser;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseSetUp {
	public static Properties prop;
	public static Response response;
	public static RequestSpecification request;
	public static JsonPath js;	
	public static JSONParser parser;
	/**
	 * This method Load Properties Files
	 */
	public void loadPropertiesFile() {
		prop = new Properties();
		try {
			FileInputStream fis = new FileInputStream("application.yml");
			prop.load(fis);
		} catch (IOException e) {
			System.out.println("not able to fetch the file");
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * This method sets initial setup
	 */
	public void initialSetup() {
		loadPropertiesFile();
		parser = new JSONParser();
		}
	
	/**
	 * This method converts the response into object
	 * @return object
	 */
	public static Object responseToObjectConversion() {
		Object object = null;
		try {
			object = parser.parse(response.asString());
		} catch (Exception e) {
			System.out.println("Response String to Object Conversion Failed");
			throw new RuntimeException(e);
		}
		return object;
	}
}
