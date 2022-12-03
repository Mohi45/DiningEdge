package com.diningedge.Regression;

import static com.diningedge.Utilities.ConfigPropertyReader.getProperty;
import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.diningedge.PageActions.CheckoutPage;
import com.diningedge.PageActions.DashboardPage;
import com.diningedge.PageActions.LoginPage;
import com.diningedge.PageActions.ManageItemsPage;
import com.diningedge.PageActions.OrderEdgePage;
import com.diningedge.PageActions.SettingsPage;
import com.diningedge.Utilities.ExcelFunctions;
import com.diningedge.Utilities.ReadEmailUtility;
import com.diningedge.common.CommonMethods;
import com.diningedge.resources.BaseTest;
import com.relevantcodes.extentreports.LogStatus;

import dev.failsafe.internal.util.Assert;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DiningEdgeLoginTest extends BaseTest {

	public static XSSFWorkbook exportworkbook;
	public static XSSFSheet inputsheet;
	WebDriver driver;
	LoginPage login;
	DashboardPage dashboard;
	OrderEdgePage orderEdge;
	CheckoutPage checkoutPage;
	SettingsPage settingsPage;
	ManageItemsPage manageItemsPage;
	ReadEmailUtility rd = new ReadEmailUtility();

	public static int acno;
	public static int totalNoOfRows;
	public static int AcColStatus;
	public static int AcColdetail;
	public static int totalNoOfCols;
	public static int rowIndex;
	String locationFromUI, locationFromGmail, orderDateFromUI, orderDateFromGmail, orderNumberFromUI,
			orderNumberFromGmail;
	List<String> details;

	@BeforeMethod
	public void setup() {
		driver = getDriver();
		login = new LoginPage(driver);
		dashboard = new DashboardPage(driver);
		orderEdge = new OrderEdgePage(driver);
		checkoutPage = new CheckoutPage(driver);
		settingsPage = new SettingsPage(driver);
		manageItemsPage = new ManageItemsPage(driver);
	}

	@BeforeTest
	public void dataSetUp() throws IOException {
		exportworkbook = ExcelFunctions.openFile("C:\\Users\\Joyti Singh\\Desktop\\Data.xlsx");
		inputsheet = exportworkbook.getSheet("test");
	}

	@Test(dataProvider = "testData")
	public void sendOrderGuideFromOrderEdge(String vendor, String productName, String unitType)
			throws InterruptedException {
		/*------------------------Data set up-------------------------*/

		XSSFCell cell1, cell2;
		DiningEdgeLoginTest.rowIndex = Math.floorMod(DiningEdgeLoginTest.acno, DiningEdgeLoginTest.totalNoOfRows) + 1;
		System.out.println("Test Case test #" + DiningEdgeLoginTest.rowIndex);
		cell1 = exportworkbook.getSheet("test").getRow(rowIndex).createCell(DiningEdgeLoginTest.AcColStatus);
		cell1.setCellValue("");
		cell2 = exportworkbook.getSheet("test").getRow(rowIndex).createCell(DiningEdgeLoginTest.AcColdetail);
		cell2.setCellValue("");

		/*-------------------------Basic Flow----------------------------------*/
		logExtent = extent.startTest(
				"Test0" + DiningEdgeLoginTest.rowIndex + "_sendOrderGuideAndValidateFromEmail for :: " + vendor);
		login.enterCredentials(getProperty("username"), getProperty("password"));
		login.clickOnLoginButton();
		dashboard.getDiningEdgeText("DiningEdge");
		dashboard.getDeshboardText("Dashboard");
		dashboard.clickOnTheOrderEdge("Order Edge");
		verifyAndEnterEmail(vendor);
		addUnitsAndSendOG(vendor, productName, unitType);
		verifyOrderFromEmail();
	}

	@AfterMethod
	public void tearDown() {
		acno++;
	}

	/*--------------------------Data Provider to provide data------------------*/

	@DataProvider(name = "testData")
	public static Object[][] testData() throws IOException {
		System.out.println("Inside Dataprovider. Creating the Object Array to store test data inputs.");
		Object[][] td = null;
		try {
			// Get TestCase sheet data
			totalNoOfCols = inputsheet.getRow(inputsheet.getFirstRowNum()).getPhysicalNumberOfCells();
			totalNoOfRows = inputsheet.getLastRowNum();
			System.out.println(totalNoOfRows + " Accounts and Columns are: " + totalNoOfCols);
			td = new String[totalNoOfRows][totalNoOfCols];
			for (int i = 1; i <= totalNoOfRows; i++) {
				for (int j = 0; j < totalNoOfCols; j++) {
					td[i - 1][j] = ExcelFunctions.getCellValue(inputsheet, i, j);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Test Cases captured in the Object Array. Exiting dataprovider.");
		return td;
	}

	/*-------------------Reusable Methods--------------*/

	public void verifyAndEnterEmail(String vendor) {
		dashboard.clickOnSettingButton();
		settingsPage.searchVendorByName(vendor);
		settingsPage.clickOnVendorName(vendor);
		settingsPage.selectSettinsType();
		settingsPage.clickOnSelcetType();
		settingsPage.selectEmailFromDropDown();
		settingsPage.clickOnEmailRemove(getProperty("sendGmail"));
		settingsPage.enterRecepientsEmails(getProperty("sendGmail"));
		settingsPage.clickOnSaveButton();
		settingsPage.clickOnSnackBarCloseButton();
	}

	public void addUnitsAndSendOG(String vendor, String productName, String unitType) {
		dashboard.clickOnTheOrderEdge("Order Edge");
		orderEdge.enterUnits("2", productName, vendor, unitType);
		dashboard.clickOnHeader();
		orderEdge.clickOnAddToCartButton();
		checkoutPage.selecctSumbitAll("Submit All");
		settingsPage.clickOnSnackBarCloseButton();
		CommonMethods.hardwait(5000);
	}

	public void verifyOrderFromEmail() {
		locationFromUI = checkoutPage.getOrderDetails("Location:");
		orderDateFromUI = checkoutPage.getOrderDetails("Order Date:").split(" ")[0];
		orderNumberFromUI = checkoutPage.getOrderDetails("Order Name/PO Number:").split("/")[3].trim();
		System.out.println("----------------------------From UI--------------------------------------");
		System.out.println(locationFromUI + " :: " + orderDateFromUI + " :: " + orderNumberFromUI);
		details = rd.readMail();
		locationFromGmail = details.get(2).replaceAll("\\s", " ").trim();
		orderDateFromGmail = details.get(1);
		orderNumberFromGmail = details.get(0);
		System.out.println("---------------------------From Gmail---------------------------------------");
		System.out.println(locationFromGmail + " :: " + orderDateFromGmail + " :: " + orderNumberFromGmail);
	
		assertEquals(locationFromGmail, locationFromUI, "Assertion Failed :: As Location is not correct !!");
		logExtent.log(LogStatus.INFO,
				"Assertion Passed :: Location is found correct from Gmail as :: " + locationFromGmail);
		assertEquals(orderDateFromGmail, orderDateFromUI, "Assertion Failed :: As Order date is not correct !!");
		logExtent.log(LogStatus.INFO,
				"Assertion Passed :: Order Date is found correct from Gmail as :: " + orderDateFromGmail);
		assertEquals(orderNumberFromGmail, orderNumberFromUI, "Assertion Failed :: As Order Number is not correct !!");
		logExtent.log(LogStatus.INFO,
				"Assertion Passed :: Order Number is found correct from Gmail as :: " + orderNumberFromGmail);
	}
}
