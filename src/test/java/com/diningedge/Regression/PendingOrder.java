package com.diningedge.Regression;

import static com.diningedge.Utilities.ConfigPropertyReader.getProperty;

import java.util.List;
import java.util.Random;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.diningedge.PageActions.DiningEdge.CheckoutPage;
import com.diningedge.PageActions.DiningEdge.DashboardPage;
import com.diningedge.PageActions.DiningEdge.LoginPage;
import com.diningedge.PageActions.DiningEdge.ManageItemsPage;
import com.diningedge.PageActions.DiningEdge.OrderEdgePage;
import com.diningedge.PageActions.DiningEdge.Order_OGPage;
import com.diningedge.PageActions.DiningEdge.SettingsPage;
import com.diningedge.Utilities.ReadEmailUtility;
import com.diningedge.common.CommonMethods;
import com.diningedge.resources.BaseTest;

public class PendingOrder extends BaseTest {

	Random random = new Random();
	public int numberOfUnits = random.nextInt(2) + 1;
	String productName=ManageItemsPage.getProductNames();
	public static XSSFWorkbook exportworkbook;
	public static XSSFSheet inputsheet;
	WebDriver driver;
	LoginPage login;
	DashboardPage dashboard;
	OrderEdgePage orderEdge;
	CheckoutPage checkoutPage;
	SettingsPage settingsPage;
	ManageItemsPage manageItemsPage;
	Order_OGPage orderOg;
	ReadEmailUtility rd = new ReadEmailUtility();
	protected String vendorName;
	protected String location = "loc10";
	boolean status = false;
	private String vendor;
	List<String> details;
	String locationFromUI, locationFromGmail, orderDateFromUI, orderDateFromGmail, orderNumberFromUI,
			orderNumberFromGmail, totalAmountFromUI, totalAmountFromGmail;

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
	}
 
	@Test
	public void pendingOrderSendOG() {
		logExtent = extent
				.startTest("Test0" + SendOGTest.rowIndex + "_sendOrderGuideAndValidateFromEmail for :: " + vendor);
		login.enterCredentials(getProperty("username"), getProperty("password"), logExtent);
		login.clickOnLoginButton();
		dashboard.getDiningEdgeText("DiningEdge", logExtent);
		dashboard.getDeshboardText("Dashboard", logExtent);
		createPendingOrder();
		selectAndSumbitOrder();
	}
	
	public void selectAndSumbitOrder() {
		CommonMethods.hardwait(6000);
		dashboard.clickOnTheOrderEdge("Pending Orders", logExtent);
		orderEdge.clickOnPendingOrder();
		orderEdge.clickOnSaveAndCancel("Go to Cart");
		
	}
	
	public void createPendingOrder() {
		logExtent = extent
				.startTest("Test0" + SendOGTest.rowIndex + "_sendOrderGuideAndValidateFromEmail for :: " + vendor);
		dashboard.clickOnTheOrderEdge("Order Edge", logExtent);
		orderEdge.enterUnits(String.valueOf(numberOfUnits), "Ice Cream", "PFG", "ice cream butterscotch", logExtent);
		dashboard.clickOnHeader();
		CommonMethods.hardwait(2000);
		orderEdge.clickOnAddToCartButton(logExtent);
		checkoutPage.selecctSumbitAll("Continue Shoping", logExtent);
	}
	

}
