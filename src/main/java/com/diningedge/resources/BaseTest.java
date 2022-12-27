package com.diningedge.resources;

import static com.diningedge.Utilities.ConfigPropertyReader.getProperty;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.diningedge.Utilities.ReadEmailUtility;
import com.diningedge.Utilities.SendEmailUtility;
import com.diningedge.Utilities.TakeScreenshot;
import com.diningedge.common.CommonMethods;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

	static String className;
	private static List<WebDriver> driverPool = new ArrayList<WebDriver>();
	WebDriver driver;
	TakeScreenshot takeScreenshot;
	public ExtentTest logExtent;
	public ExtentReports extent;
	ReadEmailUtility rd = new ReadEmailUtility();
	boolean status = false;
	public String filePath;
	// @BeforeSuite
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		WebDriverManager.edgedriver().setup();
	}

	@BeforeTest
	public void setExtent() {
		extent = new ExtentReports(System.getProperty("user.dir") + "/target/ExtentReport.html", true);
		extent.addSystemInfo("User Name", "Automation Testing by Ⓜ️");
		extent.addSystemInfo("Environment", "QA Automation");
	}

	public WebDriver getDriver(BrowserType type, String baseUrl) {
		WebDriverManager.chromedriver().setup();
		WebDriverManager.edgedriver().setup();
		driver = DriverFactory.getInstance().getDriver(type);
		takeScreenshot = new TakeScreenshot(this.getClass().getSimpleName(), driver);
		driverPool.add(driver);
		driver.get(baseUrl);
		driver.manage().window().maximize();
		return driver;
	}

	public WebDriver getDriver() {
		return getDriver(BrowserType.CHROME, getProperty("diningEdge"));
	}

	@BeforeMethod
	public void testMethodName(Method method) {
		stepStartMessage(method.getName());
		CommonMethods.deleteFiles(System.getProperty("user.home") + "/Downloads", ".csv");
	}

	public void stepStartMessage(String testStepName) {
		Reporter.log(" ", true);
		Reporter.log("***** STARTING TEST STEP:- " + testStepName + " *****", true);
		Reporter.log(" ", true);
	}

	@SuppressWarnings("unused")
	private void testInitiator(String testname) {
		Reporter.log("***** STARTING TEST CLASS:- " + testname + " *****", true);
		takeScreenshot = new TakeScreenshot(testname, this.driver);
	}

	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result) {
		takeScreenshot.takeScreenShotOnException(result);

		if (result.getStatus() == ITestResult.FAILURE) {
			status = true;
			logExtent.log(LogStatus.FAIL, "Test Case Failed :: " + result.getName());
			logExtent.log(LogStatus.FAIL, "Error is " + result.getThrowable());
			filePath = takeScreenshot.takeScreenshot();
			logExtent.log(LogStatus.FAIL, logExtent.addScreenCapture(filePath));// to add screenshot in extent report
		} else if (result.getStatus() == ITestResult.SKIP) {
			logExtent.log(LogStatus.SKIP, "Test Case Skipped :: " + result.getName());
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			logExtent.log(LogStatus.PASS, "Test Case PASSED :: " + result.getName());
		}
		extent.endTest(logExtent);
		driver.quit();
	}


	public void sendReport(String vendor,String location) {
		if (status) {
			System.out.println("Sending email in case of UI failure...");
			SendEmailUtility.sendReports("Automation Testing :: Order Submission Failed ❌",
					vendor,location,filePath);
		}
	}

	@AfterTest
	public void endReport() {
		extent.flush();
		extent.close();
	}
}