package com.diningedge.Regression;

import static com.diningedge.Utilities.ConfigPropertyReader.getProperty;

import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.diningedge.PageActions.DiningEdge.DashboardPage;
import com.diningedge.PageActions.DiningEdge.InventoryPage;
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
	InventoryPage inventoryPage;
	SettingsPage settingPage;
	public String vendorN;
	public String invoice = "Invoice" + CustomFunctions.generateRandomNumber();
	public int chargeUnit = CustomFunctions.generateRandomUnitSize();
	public int unit = CustomFunctions.generateRandomUnitSize();
	String productName;
	String chargeFromPH, chargeFromInvoice, extedePriceFromPH, extedePriceFromInvoice, chargePerUnit, qty;
	public int extedePrice = chargeUnit * unit;
	protected String location = "loc10";

	@BeforeMethod
	public void setup() {
		driver = getDriver();
		login = new LoginPage(driver);
		dashboard = new DashboardPage(driver);
		orderEdge = new OrderEdgePage(driver);
		manageItemPage = new ManageItemsPage(driver);
		purchasePage = new PurchasePage(driver);
		settingPage = new SettingsPage(driver);
		inventoryPage = new InventoryPage(driver);
	}

	@Test
	public void Test01_CreatePurchasesAndValidate() {
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
		velidateFromPurchaseHistory(invoice, productName);
		validateFromPurchaseReport(invoice, productName);
	}

	public void addPurchase() {
		String[] items = purchasePage.getVendorName(vendorN);
		Random random = new Random();
		int index = random.nextInt(items.length);
		purchasePage.searchAndSelectItem(items[index]);
		orderEdge.clickOnSaveAndCancel("Save");
		settingPage.clickOnSnackBarCloseButton();
		purchasePage.addUnitUnits(String.valueOf(unit));
		purchasePage.addChargeUnits(String.valueOf(chargeUnit));
		CustomFunctions.hardWaitForScript();
		productName = purchasePage.getProductName().split(":")[1].trim();
	}

	public void addRequiredDetails() {
		purchasePage.enterInvoiceNumber(invoice);
		purchasePage.selectInvoiceDate();
		orderEdge.clickOnSaveAndCancel("OK");
		CustomFunctions.hardWaitForScript();
		settingPage.clickOnSnackBarCloseButton();
		CustomFunctions.hardWaitForScript();
		chargePerUnit = purchasePage.getCheragePerUnit().split("/ ")[0];
		chargePerUnit += "/" + purchasePage.getCheragePerUnit().split("/ ")[1];
		orderEdge.clickOnSaveAndCancel("Approve");
	}

	public void velidateFromPurchaseHistory(String invoiceNumber, String product) {
		manageItemPage.selectItemTypeFromList("Manage Items");
		manageItemPage.selectItemTypeFromList("Products List");
		manageItemPage.clickOnTheSearchIconEndEnterValue(product);
		manageItemPage.clickOnTheProduct(product);
		manageItemPage.clickOnHistoryButton();
		chargeFromPH = manageItemPage.getTheValues(invoiceNumber, "8").trim();
		extedePriceFromPH = manageItemPage.getTheValues(invoiceNumber, "9").trim();
		Assert.assertEquals(chargeFromPH, chargePerUnit, "Charge per unit is not matched !!");
		Assert.assertEquals("$" + String.valueOf(extedePrice), extedePriceFromPH, "Extended price not matched !!");
	}

	public void validateFromPurchaseReport(String invoiceNumber, String product) {
		inventoryPage.clickOnInventory();
		manageItemPage.selectItemTypeFromList("Reports");
		manageItemPage.selectItemTypeFromList("Purchase Report");
		inventoryPage.clickOnFilterIcon();
		inventoryPage.clickAndSelectProduct(product);
		qty = inventoryPage.getTheValues(invoiceNumber, "8");
		chargeFromInvoice = inventoryPage.getTheValues(invoiceNumber, "11");	
		extedePriceFromInvoice = inventoryPage.getTheValues(invoiceNumber, "12");
		Assert.assertEquals(chargeFromInvoice, "$" + chargeUnit + ".000", "Charge per unit is not matched !!");
		Assert.assertEquals("$" + String.valueOf(extedePrice) + ".000", extedePriceFromInvoice,
				"Extended price not matched !!");
		Assert.assertEquals(qty, String.valueOf(unit), "Quantity is not matched !!");
	}

	
	@AfterSuite
	public void mailTriggerInCaseOfUI() {
		sendReport(vendorN, location,"Purchases");
	}
}
