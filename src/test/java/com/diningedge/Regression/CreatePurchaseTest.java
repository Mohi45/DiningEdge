package com.diningedge.Regression;

import static com.diningedge.Utilities.ConfigPropertyReader.getProperty;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.diningedge.PageActions.DiningEdge.DashboardPage;
import com.diningedge.PageActions.DiningEdge.LoginPage;
import com.diningedge.PageActions.DiningEdge.ManageItemsPage;
import com.diningedge.PageActions.DiningEdge.OrderEdgePage;
import com.diningedge.PageActions.DiningEdge.PurchasePage;
import com.diningedge.PageActions.DiningEdge.SettingsPage;
import com.diningedge.Utilities.CustomFunctions;
import com.diningedge.resources.BaseTest;

public class CreatePurchaseTest extends BaseTest {
	WebDriver driver;
	LoginPage login;
	DashboardPage dashboard;
	OrderEdgePage orderEdge;
	ManageItemsPage manageItemPage;
	PurchasePage purchasePage;
	SettingsPage settingPage;
	public String vendorN;

	@BeforeMethod
	public void setup() {
		driver = getDriver();
		login = new LoginPage(driver);
		dashboard = new DashboardPage(driver);
		orderEdge = new OrderEdgePage(driver);
		manageItemPage = new ManageItemsPage(driver);
		purchasePage = new PurchasePage(driver);
		settingPage = new SettingsPage(driver);
	}

	@Test
	public void Test01_BasicFlowLogin() {
		/*-------------------------Basic Flow----------------------------------*/
		logExtent = extent.startTest("Test01_BasicFlowLogin");
		login.enterCredentials(getProperty("username"), getProperty("password"), logExtent);
		login.clickOnLoginButton();
		dashboard.getDiningEdgeText("DiningEdge", logExtent);
		dashboard.getDeshboardText("Dashboard", logExtent);
		dashboard.clickOnTheOrderEdge("Order Edge", logExtent);
		dashboard.clickOnTheSelectLoaction(logExtent);
		createNewPurchases();
	}

	private void createNewPurchases() {
		vendorN = purchasePage.getPrimaryVendor();
		dashboard.clickOnTheOrderEdge("Purchases", logExtent);
		purchasePage.clickOnNewPurchaseAndSelectVendor(vendorN);
		orderEdge.clickOnSaveAndCancel("Save");
		settingPage.clickOnSnackBarCloseButton();
		addPurchase();
		addRequiredDetails();
	}

	public void addPurchase() {
		String[] items = purchasePage.getVendorName(vendorN);
		for (int i = 0; i < items.length; i++) {
			purchasePage.searchAndSelectItem(items[i]);
			orderEdge.clickOnSaveAndCancel("Save");
			settingPage.clickOnSnackBarCloseButton();
		}

	}

	public void addRequiredDetails() {
		purchasePage.enterInvoiceNumber("1231233");
		purchasePage.selectInvoiceDate();
		orderEdge.clickOnSaveAndCancel("OK");
		CustomFunctions.hardWaitForScript();
		settingPage.clickOnSnackBarCloseButton();
		orderEdge.clickOnSaveAndCancel("Save");
	}
}
