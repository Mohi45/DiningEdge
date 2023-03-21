package com.diningedge.Regression;

import static com.diningedge.Utilities.ConfigPropertyReader.getProperty;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.diningedge.PageActions.DiningEdge.CheckoutPage;
import com.diningedge.PageActions.DiningEdge.ManageItemsPage;
import com.diningedge.PageActions.DiningEdge.OrderEdgePage;
import com.diningedge.PageActions.DiningEdge.Order_OGPage;
import com.diningedge.PageActions.DiningEdge.SettingsPage;
import com.diningedge.PageActions.DiningEdgeAdmin.DashboardPage;
import com.diningedge.PageActions.DiningEdgeAdmin.LoginPage;
import com.diningedge.Utilities.CustomFunctions;
import com.diningedge.Utilities.ReadEmailUtility;
import com.diningedge.resources.BaseTest;

public class CreateCompany_E2E extends BaseTest {

	WebDriver driver;
	LoginPage login;
	DashboardPage dashboardPage;
	OrderEdgePage orderEdge;
	SettingsPage settingPage;
	com.diningedge.PageActions.DiningEdge.DashboardPage dashboard;
	Order_OGPage orderog;
	String size = String.valueOf(CustomFunctions.generateRandomUnitSize());
	Map<Integer, String> map = Order_OGPage.getVendors();
	static String companyName = "Automation Testing Comapny" + CustomFunctions.generateRandomNumber();
	static String location = "Automation Street" + CustomFunctions.generateRandomNumber();
	static String productName = "Automation " + CustomFunctions.getSheetName();
	ManageItemsPage manageItemPage;
	CheckoutPage checkoutPage;
	String locationFromUI, locationFromGmail, orderDateFromUI, orderDateFromGmail, orderNumberFromUI,
			orderNumberFromGmail, totalAmountFromUI, totalAmountFromGmail;

	List<String> details;
	ReadEmailUtility rd = new ReadEmailUtility();
	protected String vendorName = "Cheney";
	boolean status = false;

	@BeforeMethod
	public void setup() {
		driver = getDriver(getProperty("diningEdgeAdmin"));
		login = new LoginPage(driver);
		dashboardPage = new DashboardPage(driver);
		manageItemPage = new ManageItemsPage(driver);
		orderEdge = new OrderEdgePage(driver);
		settingPage = new SettingsPage(driver);
		orderog = new Order_OGPage(driver);
		dashboard = new com.diningedge.PageActions.DiningEdge.DashboardPage(driver);
		checkoutPage = new CheckoutPage(driver);
	}

	@Test
	public void loginInToAdminPage() {
		logExtent = extent.startTest("Login InTo Admin Page Test");
		login.enterCredentials(getProperty("adminUserName"), getProperty("adminPassword"));
		login.clickOnLoginButton();
		dashboardPage.getDashBoardTitle("Diningedge Admin");
		enterCompanyDetails();
		enterCompanyLocations();
		dashboardPage.createProductList(companyName, productName);
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
}
