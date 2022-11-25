package com.diningedge.Regression;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.diningedge.PageActions.DashboardPage;
import com.diningedge.PageActions.LoginPage;
import com.diningedge.PageActions.OrderEdgePage;
import com.diningedge.resources.BaseTest;

public class DiningEdgeLoginTest extends BaseTest {

	WebDriver driver;
	LoginPage login;
	DashboardPage dashboard;
	OrderEdgePage orderEdge;

	@BeforeClass
	public void setup() {
		driver = getDriver();
		login = new LoginPage(driver);
		dashboard = new DashboardPage(driver);
		orderEdge=new OrderEdgePage(driver);
	}

	@Test
	public void Test001_VerifyLoginPage() {
		logExtent=extent.startTest("Test001_VerifyLoginPage");
		login.enterCredentials("ocrtestonly", "123456");
		login.clickOnLoginButton();
	}

	@Test(dependsOnMethods = "Test001_VerifyLoginPage")
	public void Test002_VerifyDashboardPage() {
		logExtent=extent.startTest("Test002_VerifyDashboardPage");
		dashboard.getDiningEdgeText("DiningEdge");
		dashboard.getDeshboardText("Dashboard");

	}
	@Test(dependsOnMethods = "Test002_VerifyDashboardPage")
	public void Test003_VerifyOrdersGuidePage() throws InterruptedException {
		logExtent=extent.startTest("Test003_VerifyOrdersGuidePage");
		dashboard.clickOnTheOrderEdge();
		dashboard.verifyOrderEdgePage("Order  Edge");
		orderEdge.enterUnits("4","Test product","Cheney","CBI FARM");
		dashboard.clickOnHeader();
		Thread.sleep(3000);
		orderEdge.clickOnAddToCartButton();
	}

	@AfterClass
	public void tearDown() {
		driver.close();
	}

}
