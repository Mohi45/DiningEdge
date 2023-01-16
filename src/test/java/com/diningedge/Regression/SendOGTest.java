package com.diningedge.Regression;

import static com.diningedge.Utilities.ConfigPropertyReader.getProperty;
import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.diningedge.PageActions.DiningEdge.CheckoutPage;
import com.diningedge.PageActions.DiningEdge.DashboardPage;
import com.diningedge.PageActions.DiningEdge.LoginPage;
import com.diningedge.PageActions.DiningEdge.ManageItemsPage;
import com.diningedge.PageActions.DiningEdge.OrderEdgePage;
import com.diningedge.PageActions.DiningEdge.SettingsPage;
import com.diningedge.Utilities.CustomFunctions;
import com.diningedge.Utilities.ExcelFunctions;
import com.diningedge.Utilities.ReadEmailUtility;
import com.diningedge.Utilities.SendEmailUtility;
import com.diningedge.common.CommonMethods;
import com.diningedge.resources.BaseTest;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class SendOGTest extends BaseTest {
	int i = 0;
	Random random = new Random();
	public int numberOfUnits = random.nextInt(2) + 1;
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
	protected String vendorName;
	protected String location = "loc10";
	boolean status = false;

	public static int acno;
	public static int totalNoOfRows;
	public static int AcColStatus;
	public static int AcColdetail;
	public static int totalNoOfCols;
	public static int rowIndex;
	String locationFromUI, locationFromGmail, orderDateFromUI, orderDateFromGmail, orderNumberFromUI,
			orderNumberFromGmail, totalAmountFromUI, totalAmountFromGmail;
	List<String> details;
	public String sheetName;

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
		sheetName = CustomFunctions.getSheetName();
		exportworkbook = ExcelFunctions
				.openFile(System.getProperty("user.dir") + "/src/main/java/com/diningedge/testData/" + "OGData.xlsx");
		inputsheet = exportworkbook.getSheet(sheetName);
	}

	@Test(dataProvider = "testData", priority = 1)
	public void sendOrderGuideFromOrderEdge(String vendor, String productName, String unitType) throws Exception {
		/*------------------------Data set up-------------------------*/
		vendorName = vendor;
		/*------------------------------------------------------------*/
		XSSFCell cell1, cell2;
		SendOGTest.rowIndex = Math.floorMod(SendOGTest.acno, SendOGTest.totalNoOfRows) + 1;
		System.out.println("Test Case test #" + SendOGTest.rowIndex);
		cell1 = exportworkbook.getSheet(sheetName).getRow(rowIndex).createCell(SendOGTest.AcColStatus);
		cell1.setCellValue("");
		cell2 = exportworkbook.getSheet(sheetName).getRow(rowIndex).createCell(SendOGTest.AcColdetail);
		cell2.setCellValue("");

		/*-------------------------Basic Flow----------------------------------*/
		logExtent = extent
				.startTest("Test0" + SendOGTest.rowIndex + "_sendOrderGuideAndValidateFromEmail for :: " + vendor);
		login.enterCredentials(getProperty("username"), getProperty("password"), logExtent);
		login.clickOnLoginButton();
		dashboard.getDiningEdgeText("DiningEdge", logExtent);
		dashboard.getDeshboardText("Dashboard", logExtent);
		dashboard.clickOnTheOrderEdge("Order Edge", logExtent);
		dashboard.clickOnTheSelectLoaction(logExtent);
		addUnitsAndSendOG(vendor, productName, unitType, logExtent);
		verifyOrderFromEmail(vendor);
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

	public void addUnitsAndSendOG(String vendor, String productName, String unitType, ExtentTest logExtents) {
		dashboard.clickOnTheOrderEdge("Order Edge", logExtents);
		orderEdge.enterUnits(String.valueOf(numberOfUnits), productName, vendor, unitType, logExtents);
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
			SendEmailUtility.sendReport("Automation Testing :: Order Submission Failed ❌", vendorName);
		} else {
			System.out.println("----------------------------");
		}
	}

	@AfterSuite
	public void mailTriggerInCaseOfUI() {
		sendReport(vendorName, location);
	}
}
