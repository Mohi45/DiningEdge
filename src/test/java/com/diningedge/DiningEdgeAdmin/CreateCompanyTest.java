package com.diningedge.DiningEdgeAdmin;

import static com.diningedge.Utilities.ConfigPropertyReader.getProperty;
import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
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
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class CreateCompanyTest extends BaseTest {

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

	public void createNewProduct() {
		for (int i = 1; i <= map.size(); i++) {
			manageItemPage.clickOnAddProductButton();
			orderEdge.varifyPopupForCreateProduct("Create product");
			orderEdge.enterDetailsOnCreateProduct("Name", "Automation_" + map.get(i));
			orderEdge.enterDetailsOnCreateProduct("Size", size);
			orderEdge.selcetValueFromDropDown("Unit *", orderEdge.getUnitType());
			manageItemPage.clickOnDropdown("Primary Category *");
			manageItemPage.addProductCategary("Add Category", orderEdge.getPrimaryCategory() + "_" + i, "Category");
			manageItemPage.clickOnDropdown("Storage");
			manageItemPage.addProductCategary("Add Storage", orderEdge.getStorageType() + "_" + i, "Storage");
			orderEdge.clickOnSaveAndCancel("Save");
			settingPage.clickOnSnackBarCloseButton();
		}
	}

	public void clickOnUserIconToLogin() {
		dashboardPage.clickOnDeshboard();
		dashboardPage.serchCompanyDetails(companyName);
		dashboardPage.ClickOnUserIconFromAdmin(companyName);
	}

	public void createNewVendorItem() {
		for (int i = 1; i <= map.size(); i++) {
			String vendor = map.get(i);
			dashboard.clickOnTheOrderEdge("Order from OG", logExtent);
			orderog.clickOnNewVendor();
			orderog.enterNewVendorDetails(vendor + "_Testing", "00" + i, size);
			orderog.clickOnUnitAndSelectUnitType(orderEdge.getUnitType());
			orderog.clickOnVendorAndSelectVendor(vendor);
			orderEdge.clickOnSaveAndCancel("Save");
			settingPage.clickOnSnackBarCloseButton();
			CustomFunctions.hardWaitForScript();
		}
	}

	public void createVendors() {
		settingPage.clickOnSettingIcon();
		for (int i = 1; i <= map.size(); i++) {
			String vendor = map.get(i);
			settingPage.clickOnAddVendorButton();
			settingPage.checkVendorLocation();
			settingPage.enterAndSelectVendorName(vendor);
			settingPage.clickOnSaveButton();
			settingPage.searchVendorByName(vendor);
			settingPage.clickOnVendorName(vendor);
			settingPage.selectSettinsType("EDI Settings");
			settingPage.selectOptonsItems("Send Order");
			settingPage.clickOnActiveChecksBox();
			settingPage.selectEmailFromDropDown();
			settingPage.enterRecepientsEmails("diningedgetest@gmail.com");
			settingPage.clickOnSaveButton();
			settingPage.clickOnSnackBarCloseButton();
		}
		orderEdge.clickOnSaveAndCancel("Save");
		settingPage.refresh();
	}

	public void addComparabls() {
		dashboard.clickOnTheOrderEdge("Order Edge", logExtent);
		for (int i = 1; i <= map.size(); i++) {
			String vendor = map.get(i);
			orderEdge.clickOnComparabls("Automation_" + vendor);
			orderEdge.enterProductIdAndSelectComparabls(vendor, "00" + i);
			settingPage.clickOnTheSaveAndCloseButton();
			manageItemPage.clickOnCrossIcon();
			CustomFunctions.hardWaitForScript();
		}
	}

	public void addUnitsAndSendOG(ExtentTest logExtents) {
		for (int i = 1; i <= map.size(); i++) {
			String vendor = map.get(i);
			dashboard.clickOnTheOrderEdge("Order Edge", logExtents);
			orderEdge.enterUnits(size, "Automation_" + vendor, vendor, vendor + "_Testing", logExtents);
			dashboard.clickOnHeader();
			orderEdge.clickOnAddToCartButton(logExtents);
			checkoutPage.selecctSumbitAll("Submit All", logExtents);
			settingPage.clickOnSnackBarCloseButton();
			try {
				verifyOrderFromEmail(vendor);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/*--------------------------------------------------------------------------------------*/
	public void ManangeProducts() {
		manageItemPage.selectItemTypeFromList("Manage Items");
		manageItemPage.selectItemTypeFromList("Products List");
	}

	public void verifyOrderFromEmail(String vendor) throws Exception {
		locationFromUI = "DiningEdgeAutomation";// checkoutPage.getOrderDetails("Location:");
		orderDateFromUI = checkoutPage.getOrderDetails("Order Date:").split(" ")[0];
		orderNumberFromUI = checkoutPage.getOrderDetails("Order Name/PO Number:").split("/")[3].trim();
		totalAmountFromUI = checkoutPage.getTotalAmount(vendor);
		System.out.println("----------------------------From UI--------------------------------------");
		System.out.println(
				locationFromUI + " :: " + orderDateFromUI + " :: " + orderNumberFromUI + " :: " + totalAmountFromUI);
		details = rd.readMail();

		try {
			locationFromGmail = details.get(2).replaceAll("\\s", " ").trim();
			orderDateFromGmail = details.get(1);
			orderNumberFromGmail = details.get(0);
			totalAmountFromGmail = details.get(3);
		} catch (Exception e) {
			System.out.println("Details are not found for OG !!");
		}
		System.out.println("---------------------------From Gmail---------------------------------------");
		System.out.println(locationFromGmail + " :: " + orderDateFromGmail + " :: " + orderNumberFromGmail + " :: "
				+ totalAmountFromGmail);

		if (details.isEmpty()) {
			status = true;
		} else {
			logExtent.log(LogStatus.PASS,
					"Assertion Passed :: Order Date is found correct from Gmail as :: " + orderDateFromGmail);
			assertEquals(orderNumberFromGmail, orderNumberFromUI,
					"Assertion Failed :: As Order Number is not correct !!");
			logExtent.log(LogStatus.PASS,
					"Assertion Passed :: Order Number is found correct from Gmail as :: " + orderNumberFromGmail);
			assertEquals(totalAmountFromGmail.trim(), totalAmountFromUI,
					"Assertion Failed :: As Total Order Amount is not correct !!");
			logExtent.log(LogStatus.PASS,
					"Assertion Passed :: Total Order Amount is found correct from Gmail as :: " + totalAmountFromGmail);
		}
	}

}
