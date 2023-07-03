package com.diningedge.Regression;

import static com.diningedge.Utilities.ConfigPropertyReader.getProperty;
import static org.testng.Assert.assertEquals;

import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.diningedge.PageActions.DiningEdge.CheckoutPage;
import com.diningedge.PageActions.DiningEdge.DashboardPage;
import com.diningedge.PageActions.DiningEdge.LoginPage;
import com.diningedge.PageActions.DiningEdge.ManageItemsPage;
import com.diningedge.PageActions.DiningEdge.OrderEdgePage;
import com.diningedge.PageActions.DiningEdge.Order_OGPage;
import com.diningedge.PageActions.DiningEdge.PurchasePage;
import com.diningedge.PageActions.DiningEdge.SettingsPage;
import com.diningedge.Utilities.CustomFunctions;
import com.diningedge.Utilities.ReadEmailUtility;
import com.diningedge.Utilities.SendEmailUtility;
import com.diningedge.resources.BaseTest;
import com.relevantcodes.extentreports.LogStatus;

public class ValidatePurchaseHistory extends BaseTest {

	Random random = new Random();
	public int numberOfUnits = random.nextInt(2) + 1;
	String productName = ManageItemsPage.getProductNames();
	public static XSSFWorkbook exportworkbook;
	public static XSSFSheet inputsheet;
	WebDriver driver;
	LoginPage login;
	DashboardPage dashboard;
	OrderEdgePage orderEdge;
	CheckoutPage checkoutPage;
	SettingsPage settingsPage;
	ManageItemsPage manageItemsPage;
	PurchasePage purchasePage;
	Order_OGPage orderOg;
	public String invoice = "Invoice" + CustomFunctions.generateRandomNumber();
	ReadEmailUtility rd = new ReadEmailUtility();
	protected String vendorName;
	protected String location = "loc10";
	boolean status = false;
	private String vendor;
	List<String> details;
	String locationFromUI, locationFromGmail, orderDateFromUI, orderDateFromGmail, orderNumberFromUI,
			orderNumberFromGmail, totalAmountFromUI, totalAmountFromGmail;
	String chargeFromPH, chargeFromInvoice, extedePriceFromPH, extedePriceFromInvoice, chargePerUnit, qty, productN;
	public String extedePrice;

	@BeforeMethod
	public void setup() {
		driver = getDriver();
		login = new LoginPage(driver);
		dashboard = new DashboardPage(driver);
		orderEdge = new OrderEdgePage(driver);
		checkoutPage = new CheckoutPage(driver);
		settingsPage = new SettingsPage(driver);
		manageItemsPage = new ManageItemsPage(driver);
		orderOg = new Order_OGPage(driver);
		purchasePage = new PurchasePage(driver);
	}

	@Test
	public void sendOrderFromOG() throws Exception {
		logExtent = extent
				.startTest("Test0" + SendOGTest.rowIndex + "_sendOrderGuideAndValidateFromEmail for :: " + vendor);
		login.enterCredentials(getProperty("username"), getProperty("password"), logExtent);
		login.clickOnLoginButton();
		dashboard.getDiningEdgeText("DiningEdge", logExtent);
		dashboard.getDeshboardText("Dashboard", logExtent);
		dashboard.clickOnTheOrderEdge("Order from OG", logExtent);
		manageItemsPage.clickOnTheSearchIconEndEnterValue(productName);
		orderOg.clickOnUpdatePackButton(productName);
		orderOg.enterPack_SizeAndPreicesValues("Pack", String.valueOf(numberOfUnits));
		orderOg.enterPack_SizeAndPreicesValues("Size", String.valueOf(numberOfUnits));
		orderEdge.clickOnSaveAndCancel("Save");
		settingsPage.clickOnSnackBarCloseButton();
		orderOg.clickOnUpdatePriceButton(productName);
		orderOg.enterPack_SizeAndPreicesValues("Price", String.valueOf(numberOfUnits));
		orderEdge.clickOnSaveAndCancel("Save");
		orderOg.clickOnUpdatePriceButton(productName);
		settingsPage.clickOnSnackBarCloseButton();
		orderOg.selectQuantity(productName, String.valueOf(numberOfUnits));
		dashboard.clickOnHeader();
		orderEdge.clickOnAddToCartButton(logExtent);
		checkoutPage.selecctSumbitAll("Submit All", logExtent);
		settingsPage.clickOnSnackBarCloseButton();
		verifyOrderFromEmail(productName);
		enterInvoiceAndApprove();
		velidateFromPurchaseHistory(invoice, productN);

	}

	public void verifyOrderFromEmail(String vendor) throws Exception {
		vendorName = vendor;
		locationFromUI = "DiningEdgeAutomation";// checkoutPage.getOrderDetails("Location:");
		orderDateFromUI = checkoutPage.getOrderDetails("Order Date:").split(" ")[0];
		orderNumberFromUI = checkoutPage.getOrderDetails("Order Name/PO Number:").split("/")[3].trim();
		totalAmountFromUI = checkoutPage.getTotalAmount(vendor);
		//totalAmountFromUI = StringUtils.chop(totalAmountFromUI);
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

	public void enterInvoiceAndApprove() {
		dashboard.clickOnTheOrderEdge("Purchases", logExtent);
		purchasePage.selectItemWithPONumber(orderNumberFromUI);
		purchasePage.enterInvoiceNumber(invoice);
		purchasePage.selectInvoiceDate();
		CustomFunctions.hardWaitForScript();
		orderEdge.clickOnSaveAndCancel("OK");
		settingsPage.clickOnSnackBarCloseButton();
		CustomFunctions.hardWaitForScript();
		chargePerUnit = purchasePage.getCheragePerUnit().split("/ ")[0];
		chargePerUnit += "/" + purchasePage.getCheragePerUnit().split("/ ")[1];
		productN = purchasePage.getProductName().split(":")[1].trim();
		extedePrice = purchasePage.getTotal();
		extedePrice = extedePrice.split("\\.")[0];
		orderEdge.clickOnSaveAndCancel("Approve");
	}

	public void velidateFromPurchaseHistory(String invoiceNumber, String product) {
		manageItemsPage.selectItemTypeFromList("Manage Items");
		manageItemsPage.selectItemTypeFromList("Products List");
		manageItemsPage.clickOnTheSearchIconEndEnterValue(product);
		manageItemsPage.clickOnTheProduct(product);
		manageItemsPage.clickOnHistoryButton();
		chargeFromPH = manageItemsPage.getTheValues(invoiceNumber, "8").trim();
		extedePriceFromPH = manageItemsPage.getTheValues(invoiceNumber, "9").trim();
		Assert.assertEquals(chargeFromPH, chargePerUnit, "Charge per unit is not matched !!");
		Assert.assertEquals(extedePrice, extedePriceFromPH, "Extended price not matched !!");
	}

	/*-------------------------------------Sending Reports-------------------------*/

	@AfterMethod
	public void sendEmailReport() {

		if (status) {
			//SendEmailUtility.sendReport("Automation Testing :: Order Submission Failed ❌", vendorName);
		} else {
			System.out.println("----------------------------");
		}
	}

	@AfterSuite
	public void mailTriggerInCaseOfUI() {
		sendReport(vendorName, location, "OrderFromOG");
	}

}
