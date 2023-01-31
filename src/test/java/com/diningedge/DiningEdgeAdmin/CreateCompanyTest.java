package com.diningedge.DiningEdgeAdmin;

import static com.diningedge.Utilities.ConfigPropertyReader.getProperty;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.diningedge.PageActions.DiningEdgeAdmin.DashboardPage;
import com.diningedge.PageActions.DiningEdgeAdmin.LoginPage;
import com.diningedge.Utilities.CustomFunctions;
import com.diningedge.resources.BaseTest;

public class CreateCompanyTest extends BaseTest {

	WebDriver driver;
	LoginPage login;
	DashboardPage dashboardPage;
	static String companyName = "Automation Testing Comapny" + CustomFunctions.getCurrentDate()
			+ CustomFunctions.generateRandomNumber();
	static String location = "Automation Street" + CustomFunctions.getCurrentDate()
			+ CustomFunctions.generateRandomNumber();

	@BeforeMethod
	public void setup() {
		driver = getDriver(getProperty("diningEdgeAdmin"));
		login = new LoginPage(driver);
		dashboardPage = new DashboardPage(driver);
	}

	@Test
	public void loginInToAdminPage() {
		logExtent = extent.startTest("Login InTo Admin Page Test");
		login.enterCredentials(getProperty("adminUserName"), getProperty("adminPassword"));
		login.clickOnLoginButton();
		dashboardPage.getDashBoardTitle("Diningedge Admin");
		enterCompanyDetails();
		enterCompanyLocations();
		enterUserDetails();
		clickOnUserIconToLogin();

	}

	public void enterCompanyDetails() {
		dashboardPage.clickOnAddCompanyButton();
		dashboardPage.enterCompanyName(companyName);
		dashboardPage.clickOnCheckBox();
		dashboardPage.clickOnCompanySaveButton();
		dashboardPage.clickOnCompanies();
	}

	public void enterCompanyLocations() {
		dashboardPage.clickOnLocations();
		dashboardPage.clickOnAddLocations();
		dashboardPage.enterLocationName(location);
		dashboardPage.selectCompanyName(companyName);
		dashboardPage.selectBaseLocation();
		dashboardPage.clickOnCompanySaveButton();
	}

	public void enterUserDetails() {
		dashboardPage.clickOnTheUser();
		dashboardPage.clickOnAddUsers();
		dashboardPage.selectCompanyNameForUser(companyName);
		dashboardPage.enterUserDetails();
		dashboardPage.selectMultipleRoles();
		dashboardPage.clickOnCompanySaveButton();
	}

	public void clickOnUserIconToLogin() {
		dashboardPage.clickOnDeshboard();
		dashboardPage.serchCompanyDetails(companyName);
		dashboardPage.ClickOnUserIconFromAdmin(companyName);

	}

	// @AfterClass
	public void dataCleanUp() throws InterruptedException {
		Thread.sleep(4000);
		dashboardPage.clickOnLocations();
		dashboardPage.clickOnCompanies();
		dashboardPage.clickOnInnerCompany();
		dashboardPage.deleteCompany();
		dashboardPage.deleteCompanyFromPopUp();
	}
}
