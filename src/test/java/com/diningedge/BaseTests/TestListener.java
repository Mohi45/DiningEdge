package com.diningedge.BaseTests;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import static com.diningedge.Utilities.ConfigPropertyReader.getProperty;

import com.diningedge.listener.ExtentManager;
import com.diningedge.listener.ExtentTestManager;
import com.diningedge.resources.BaseTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestListener extends BaseTest implements ITestListener {
	private static String healing = System.getProperty("healing");
	// private WebDriver driver;
	// String baseUrl;

	public TestListener() {
		
	}

	private static String getTestMethodName(ITestResult iTestResult) {
		return iTestResult.getMethod().getConstructorOrMethod().getName();
	}

	@Override
	public void onStart(ITestContext iTestContext) {
		/*
		 * System.out.println("I am in onStart method " + iTestContext.getName());
		 * System.out.println(test);
		 */
		iTestContext.setAttribute("WebDriver", getDriver());
	}

	@Override
	public void onFinish(ITestContext iTestContext) {
		System.out.println("I am in onFinish method " + iTestContext.getName());
		// Do tier down operations for extentreports reporting!
		ExtentTestManager.endTest();
		ExtentManager.getReporter().flush();
	}

	@Override
	public void onTestStart(ITestResult iTestResult) {
		System.out.println("I am in onTestStart method " + getTestMethodName(iTestResult) + " start");
		// Start operation for extentreports.
//		ExtentTestManager.startTest(className, "");
		String className = iTestResult.getInstanceName();
		ExtentTestManager.startTest(className, "");
		ExtentTestManager.startTest(iTestResult.getMethod().getMethodName(), "");
	}

	@Override
	public void onTestSuccess(ITestResult iTestResult) {
		System.out.println("I am in onTestSuccess method " + getTestMethodName(iTestResult) + " succeed");
		// Extentreports log operation for passed tests.

		ExtentTestManager.getTest().log(LogStatus.PASS, "Test passed");
	}

	@Override
	public void onTestFailure(ITestResult iTestResult) {
		System.out.println("I am in onTestFailure method " + getTestMethodName(iTestResult) + " failed");
		if (healing == null || healing.isEmpty() || healing.equals(""))
			healing = getProperty("healing");
		if (!(healing.equals("true"))) {

			// Get driver from BaseTest and assign to local webdriver variable.
			Object testClass = iTestResult.getInstance();
			WebDriver webDriver = ((BaseTest) testClass).getDriver();

			// Take base64Screenshot screenshot.

			String base64Screenshot = "data:image/jpg;base64,"
					+ ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BASE64);

			// Extentreports log and screenshot operations for failed tests.
			/*
			 * ExtentTestManager.getTest().log(LogStatus.FAIL,"Test Failed",
			 * ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot));
			 */
			ExtentTestManager.getTest().log(LogStatus.FAIL, "Test Failed",
					ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot));
		}
	}

	@Override
	public void onTestSkipped(ITestResult iTestResult) {
		System.out.println("I am in onTestSkipped method " + getTestMethodName(iTestResult) + " skipped");
		// Extentreports log operation for skipped tests.
		ExtentTestManager.getTest().log(LogStatus.SKIP, "Test Skipped");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
		System.out.println("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
	}

}
