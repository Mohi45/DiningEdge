package com.diningedge.Regression;

import static com.diningedge.Utilities.ConfigPropertyReader.getProperty;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.diningedge.PageActions.CheckoutPage;
import com.diningedge.PageActions.DashboardPage;
import com.diningedge.PageActions.LoginPage;
import com.diningedge.PageActions.OrderEdgePage;
import com.diningedge.PageActions.SettingsPage;
import com.diningedge.resources.BaseTest;

public class DiningEdgeLoginTest extends BaseTest {

	WebDriver driver;
	LoginPage login;
	DashboardPage dashboard;
	OrderEdgePage orderEdge;
	CheckoutPage checkoutPage;
	SettingsPage settingsPage;

	@BeforeClass
	public void setup() {
		driver = getDriver();
		login = new LoginPage(driver);
		dashboard = new DashboardPage(driver);
		orderEdge = new OrderEdgePage(driver);
		checkoutPage = new CheckoutPage(driver);
		settingsPage = new SettingsPage(driver);
	}

	@Test
	public void Test001_VerifyLoginPage() {
		logExtent = extent.startTest("Test001_VerifyLoginPage");
		login.enterCredentials(getProperty("username"), getProperty("password"));
		login.clickOnLoginButton();
	}

	@Test(dependsOnMethods = "Test001_VerifyLoginPage")
	public void Test002_VerifyDashboardPage() {
		logExtent = extent.startTest("Test002_VerifyDashboardPage");
		dashboard.getDiningEdgeText("DiningEdge");
		dashboard.getDeshboardText("Dashboard");

	}

	@Test(dependsOnMethods = "Test002_VerifyDashboardPage")
	public void Test003_VerifyOrdersGuidePage() throws InterruptedException {
		logExtent = extent.startTest("Test003_VerifyOrdersGuidePage");
		dashboard.clickOnTheOrderEdge();
		dashboard.verifyOrderEdgePage("Order Edge");
		orderEdge.clickOnPreviousAfterButton("Next Page");
		
	}

	// @AfterClass
	public void tearDown() {
		driver.close();
	}

}
