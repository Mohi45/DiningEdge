package com.diningedge.Regression;

import static com.diningedge.Utilities.ConfigPropertyReader.getProperty;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.diningedge.PageActions.DiningEdge.DashboardPage;
import com.diningedge.PageActions.DiningEdge.LoginPage;
import com.diningedge.PageActions.DiningEdge.ManageItemsPage;
import com.diningedge.PageActions.DiningEdge.OrderEdgePage;
import com.diningedge.PageActions.DiningEdge.SettingsPage;
import com.diningedge.Utilities.CustomFunctions;
import com.diningedge.resources.BaseTest;

public class OrderEdgeProductFlow extends BaseTest {
	WebDriver driver;
	LoginPage login;
	DashboardPage dashboard;
	OrderEdgePage orderEdge;
	ManageItemsPage manageItemPage;
	String productName = "Automation Product " + CustomFunctions.generateRandomNumber();
	String size = String.valueOf(CustomFunctions.generateRandomNumber());
	SettingsPage settingsPage;

	@BeforeMethod
	public void setup() {
		driver = getDriver();
		login = new LoginPage(driver);
		dashboard = new DashboardPage(driver);
		orderEdge = new OrderEdgePage(driver);
		manageItemPage = new ManageItemsPage(driver);
		settingsPage = new SettingsPage(driver);
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
		createNewProduct();
		//addComparabls();
	}

	public void createNewProduct() {
		orderEdge.clickOnAddProduct();
		orderEdge.varifyPopupForCreateProduct("Create product");
		orderEdge.enterDetailsOnCreateProduct("Name", productName);
		orderEdge.enterDetailsOnCreateProduct("Size", size);
		orderEdge.selcetValueFromDropDown("Unit *", orderEdge.getUnitType());
		orderEdge.selcetValueFromDropDown("Primary Category *", orderEdge.getPrimaryCategory());
		orderEdge.selcetValueFromDropDown("Storage", orderEdge.getStorageType());
		orderEdge.clickOnSaveAndCancel("Save");
		settingsPage.clickOnSnackBarCloseButton();
	}

	public void addComparabls() {
		orderEdge.clickOnComparabls(productName);
		orderEdge.enterProductIdAndSelectComparabls("Cheney", "008");
		orderEdge.clickOnSaveAndCancel("Save and Close");	
		manageItemPage.clickOnCrossIcon();
		dashboard.clickOnHeader();
	}

	/*--------------------------------------------------------------------------------------*/
	public void ManangeProducts() {
		manageItemPage.selectItemTypeFromList("Manage Items");
		manageItemPage.selectItemTypeFromList("Products List");
		manageItemPage.clickOnTheSearchIconEndEnterValue(productName);
		manageItemPage.clickOnDeleteIcon();
	}

	//@AfterClass
	public void dataCleanUp() {
		ManangeProducts();
	}

}
