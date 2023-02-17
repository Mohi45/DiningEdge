package com.diningedge.Regression;

import static com.diningedge.Utilities.ConfigPropertyReader.getProperty;
import static org.testng.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.diningedge.PageActions.DiningEdge.CheckoutPage;
import com.diningedge.PageActions.DiningEdge.DashboardPage;
import com.diningedge.PageActions.DiningEdge.LoginPage;
import com.diningedge.PageActions.DiningEdge.ManageItemsPage;
import com.diningedge.PageActions.DiningEdge.OrderEdgePage;
import com.diningedge.PageActions.DiningEdge.SettingsPage;
import com.diningedge.Utilities.CustomFunctions;
import com.diningedge.Utilities.ReadEmailUtility;
import com.diningedge.Utilities.SendEmailUtility;
import com.diningedge.resources.BaseTest;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class E2E_Flow extends BaseTest {
	WebDriver driver;
	LoginPage login;
	DashboardPage dashboard;
	OrderEdgePage orderEdge;
	ManageItemsPage manageItemPage;
	String productName = CustomFunctions.getProductName();
	String size = String.valueOf(CustomFunctions.generateRandomNumber());
	SettingsPage settingsPage;
	CheckoutPage checkoutPage;
	String locationFromUI, locationFromGmail, orderDateFromUI, orderDateFromGmail, orderNumberFromUI,
			orderNumberFromGmail, totalAmountFromUI, totalAmountFromGmail;
	List<String> details;
	ReadEmailUtility rd = new ReadEmailUtility();
	protected String vendorName;
	protected String location = "loc10";
	boolean status = false;
	
	@BeforeMethod
	public void setup() {
		driver = getDriver();
		login = new LoginPage(driver);
		dashboard = new DashboardPage(driver);
		orderEdge = new OrderEdgePage(driver);
		manageItemPage = new ManageItemsPage(driver);
		settingsPage = new SettingsPage(driver);
		checkoutPage = new CheckoutPage(driver);
	}

	@Test
	public void Test01_BasicFlowLogin() throws Exception {
		/*-------------------------Basic Flow----------------------------------*/
		logExtent = extent.startTest("Test01_BasicFlowLogin");
		login.enterCredentials(getProperty("username"), getProperty("password"), logExtent);
		login.clickOnLoginButton();
		dashboard.getDiningEdgeText("DiningEdge", logExtent);
		dashboard.getDeshboardText("Dashboard", logExtent);
		dashboard.clickOnTheOrderEdge("Order Edge", logExtent);
		dashboard.clickOnTheSelectLoaction(logExtent);
		createNewProduct();
		addComparabls();
		addUnitsAndSendOG("Cheney", productName, "Automation002", logExtent);
		verifyOrderFromEmail("Cheney");
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
		settingsPage.clickOnSnackBarCloseButton();
	}

	public void addUnitsAndSendOG(String vendor, String productName, String unitType, ExtentTest logExtents) {
		dashboard.clickOnTheOrderEdge("Order Edge", logExtents);
		orderEdge.enterUnits(size, productName, vendor, unitType, logExtents);
		dashboard.clickOnHeader();
		orderEdge.clickOnAddToCartButton(logExtents);
		checkoutPage.selecctSumbitAll("Submit All", logExtents);
		settingsPage.clickOnSnackBarCloseButton();
	}

	public void verifyOrderFromEmail(String vendor) throws Exception {
		locationFromUI = "test automation";// checkoutPage.getOrderDetails("Location:");
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
			assertEquals(locationFromGmail, locationFromUI, "Assertion Failed :: As Location is not correct !!");
			logExtent.log(LogStatus.PASS,
					"Assertion Passed :: Location is found correct from Gmail as :: " + locationFromGmail);
			assertEquals(orderDateFromGmail, orderDateFromUI, "Assertion Failed :: As Order date is not correct !!");
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

	/*-------------------------------------Sending Reports-------------------------*/

	@AfterMethod
	public void sendEmailReport() {
		if (status) {
			SendEmailUtility.sendReport("Automation Testing :: Order Submission Failed !!", vendorName);
		} else {
			System.out.println("---------------------------------");
		}
	}

	@AfterSuite
	public void mailTriggerInCaseOfUI() {
		sendReport(vendorName, location);
	}

	@AfterClass
	public void dataCleanUp() {
		ManangeProducts();
	}

}
