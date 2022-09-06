package com.diningedge.Regression;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.diningedge.PageActions.DashboardPage;
import com.diningedge.PageActions.LoginPage;
import com.diningedge.resources.BaseTest;

public class DiningEdgeLoginTest extends BaseTest {

	WebDriver driver;
	LoginPage login;
	DashboardPage dashboard;

	@BeforeClass
	public void setup() {
		driver = getDriver();
		login = new LoginPage(driver);
		dashboard = new DashboardPage(driver);
	}

	@Test
	public void Test001_VerifyLoginPage() {
		login.enterCredentials("ocrtestonly", "123456");
		login.clickOnLoginButton();
	}

	@Test(dependsOnMethods = "Test001_VerifyLoginPage")
	public void Test002_VerifyDashboardPage() {
		dashboard.getDiningEdgeText("DiningEdge");
		dashboard.getDeshboardText("Dashboard1");

	}

	@AfterClass
	public void tearDown() {
		driver.close();
	}

}
