package com.diningedge.DiningEdgeAdmin;

import static com.diningedge.Utilities.ConfigPropertyReader.getProperty;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.diningedge.PageActions.DiningEdgeAdmin.DashboardPage;
import com.diningedge.PageActions.DiningEdgeAdmin.LoginPage;
import com.diningedge.resources.BaseTest;

public class CreateCompanyTest extends BaseTest {

	WebDriver driver;
	LoginPage login;
	DashboardPage dashboardPage;

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
		//enterUserDetails();
		
	}

	public void enterCompanyDetails() {
		dashboardPage.clickOnAddCompanyButton();
		dashboardPage.enterCompanyName("Automation Testing Company");
		dashboardPage.clickOnCheckBox();
		dashboardPage.clickOnCompanySaveButton();
		dashboardPage.clickOnCompanies();
	}
	
	public void enterCompanyLocations() {
		dashboardPage.clickOnLocations();
		dashboardPage.clickOnAddLocations();
		dashboardPage.enterLocationName("Automation Street 123 by M");
		dashboardPage.selectCompanyName("Automation Testing Company");
		dashboardPage.selectBaseLocation();
		dashboardPage.clickOnCompanySaveButton();
	}
	
	public void enterUserDetails() {
		dashboardPage.clickOnTheUser();
		dashboardPage.clickOnAddUsers();
		dashboardPage.selectCompanyNameForUser("Automation Testing Company");
		dashboardPage.enterUserDetails();
		dashboardPage.selectMultipleRoles();
		dashboardPage.clickOnCompanySaveButton();
		dashboardPage.clickOnCompanies();
		dashboardPage.clickOnInnerCompany();
	}
	
	//@AfterClass
	public void dataCleanUp() throws InterruptedException {
		Thread.sleep(4000);
		dashboardPage.deleteCompany();
		dashboardPage.deleteCompanyFromPopUp();
	}

}
