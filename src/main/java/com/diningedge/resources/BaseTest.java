package com.diningedge.resources;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.diningedge.Utilities.TakeScreenshot;
import com.diningedge.common.CommonMethods;

import io.github.bonigarcia.wdm.WebDriverManager;
import static com.diningedge.Utilities.ConfigPropertyReader.getProperty;
public class BaseTest {

	static String className;
	private static List<WebDriver> driverPool = new ArrayList<WebDriver>();
	WebDriver driver;
	TakeScreenshot takeScreenshot;

	@BeforeSuite
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		WebDriverManager.edgedriver().setup();
	}

	public WebDriver getDriver(BrowserType type, String baseUrl) {
		driver = DriverFactory.getInstance().getDriver(type);
		takeScreenshot = new TakeScreenshot(this.getClass().getSimpleName(), driver);
		driverPool.add(driver);
		driver.get(baseUrl);
		driver.manage().window().maximize();
		return driver;
	}

	public WebDriver getDriver() {
		return getDriver(BrowserType.CHROME, getProperty("sysoShop"));
	}

	@BeforeMethod
	public void testMethodName(Method method) {
		stepStartMessage(method.getName());
		CommonMethods.deleteFiles(System.getProperty("user.home") + "/Downloads",".csv");
	}

	public void stepStartMessage(String testStepName) {
		Reporter.log(" ", true);
		Reporter.log("***** STARTING TEST STEP:- " + testStepName + " *****", true);
		Reporter.log(" ", true);
	}

	private void testInitiator(String testname) {
		Reporter.log("***** STARTING TEST CLASS:- " + testname + " *****", true);
		takeScreenshot = new TakeScreenshot(testname, this.driver);
	}

	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result) {
		takeScreenshot.takeScreenShotOnException(result);
	}

}