package com.diningedge.Regression;

import static com.diningedge.Utilities.ConfigPropertyReader.getProperty;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.diningedge.PageActions.DiningEdge.DashboardPage;
import com.diningedge.PageActions.DiningEdge.LoginPage;
import com.diningedge.PageActions.DiningEdge.ManageItemsPage;
import com.diningedge.PageActions.DiningEdge.OrderEdgePage;
import com.diningedge.PageActions.DiningEdge.Order_OGPage;
import com.diningedge.PageActions.DiningEdge.SettingsPage;
import com.diningedge.Utilities.CustomFunctions;
import com.diningedge.resources.BaseTest;

public class NewVendorFlow extends BaseTest {
	WebDriver driver;
	LoginPage login;
	DashboardPage dashboard;
	ManageItemsPage manageItemPage;
	Order_OGPage orderog;
	OrderEdgePage orderEdge;
	String productName = "Automation Product " + CustomFunctions.generateRandomNumber();
	String size = String.valueOf(CustomFunctions.generateRandomNumber());
	String vendor=Order_OGPage.getVendorNames();
	SettingsPage settingsPage;

	@BeforeMethod
	public void setup() {
		driver = getDriver();
		login = new LoginPage(driver);
		dashboard = new DashboardPage(driver);
		manageItemPage = new ManageItemsPage(driver);
		settingsPage = new SettingsPage(driver);
		orderog = new Order_OGPage(driver);
		orderEdge = new OrderEdgePage(driver);
	}

	@Test
	public void Test01_BasicFlowLogin() {
		/*-------------------------Basic Flow----------------------------------*/
		logExtent = extent.startTest("Test01_BasicFlowLogin");
		login.enterCredentials(getProperty("username"), getProperty("password"), logExtent);
		login.clickOnLoginButton();
		dashboard.getDiningEdgeText("DiningEdge", logExtent);
		dashboard.getDeshboardText("Dashboard", logExtent);
		dashboard.clickOnTheOrderEdge("Order from OG", logExtent);
		createNewVendorItem();
	}

	public void createNewVendorItem() {
		orderog.clickOnNewVendor();
		orderog.enterNewVendorDetails(productName, productName, size);
		orderog.clickOnUnitAndSelectUnitType(orderEdge.getUnitType());
		orderog.clickOnVendorAndSelectVendor(vendor);
		orderEdge.clickOnSaveAndCancel("Save");
		settingsPage.clickOnSnackBarCloseButton();
		CustomFunctions.hardWaitForScript();
		orderog.enterVendorToAssign("Automation "+vendor);
		dashboard.clickOnTheOrderEdge("Order Edge", logExtent);
	}

}
