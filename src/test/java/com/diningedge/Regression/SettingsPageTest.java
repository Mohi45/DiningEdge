package com.diningedge.Regression;

import static com.diningedge.Utilities.ConfigPropertyReader.getProperty;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.diningedge.PageActions.DashboardPage;
import com.diningedge.PageActions.LoginPage;
import com.diningedge.PageActions.SettingsPage;
import com.diningedge.resources.BaseTest;

public class SettingsPageTest extends BaseTest {

	WebDriver driver;
	LoginPage login;
	DashboardPage dashboard;
	SettingsPage settingsPage;

	@BeforeMethod
	public void setup() {
		driver = getDriver();
		login = new LoginPage(driver);
		dashboard = new DashboardPage(driver);
		settingsPage= new SettingsPage(driver);
	}

	@Test
	public void verify_EDISettings_Feature() {
		logExtent = extent.startTest("verify_EDISettings_Feature");
		login.enterCredentials(getProperty("username"), getProperty("password"));
		login.clickOnLoginButton();
		dashboard.getDiningEdgeText("DiningEdge");
		dashboard.getDeshboardText("Dashboard");
		dashboard.clickOnTheOrderEdge("Order Edge");
		/*--------------------------------------------*/
		dashboard.clickOnSettingButton();
		settingsPage.searchVendorByName("Cheney");
		settingsPage.clickOnVendorName("Cheney");
		settingsPage.verifyTypeOfSettingOnPage();
	}
	

}
