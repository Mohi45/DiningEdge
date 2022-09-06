package com.diningedge.Regression;

import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.diningedge.PageActions.DashboardPage;
import com.diningedge.PageActions.LoginPage;
import com.diningedge.Utilities.CustomFunctions;
import com.diningedge.Utilities.SendEmailUtility;
import com.diningedge.common.CommonMethods;
import com.diningedge.resources.BaseTest;
import com.diningedge.resources.BaseUi;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class SyscoShopFlow extends BaseTest {

	WebDriver driver;
	LoginPage login;
	DashboardPage dashboard;
	public static String project = "SyscoShop";
	public static int rowIndex;
	public static String emailMessageExport = "";
	XSSFCell cell1, cell2;

	@BeforeSuite
	public static void setExtentReporting() throws IOException {
		CommonMethods.setExtentsReport();
	}

	@BeforeClass
	public void setup() {
		driver = getDriver();
		login = new LoginPage(driver);
		dashboard = new DashboardPage(driver);
	}

	@BeforeTest
	public void readData() throws Exception {
		CommonMethods.CountNumberOfSpecificColumn();
	}

	@Test(dataProvider = "userData")
	public void exportFileFlow(String active, String accountID, String purveyor, String restaurant_name,
			String username, String password, String listname, String accountNumber, String exportstatus,
			String detailedstatus) {
		Boolean result = false;
		boolean loginFlag = false;
		try {
			requiredDetailsForReadExcel(exportstatus, detailedstatus, restaurant_name);
			if (active.equalsIgnoreCase("Yes")) {
				BaseUi.log(restaurant_name + " for purveryor " + purveyor + " is Active !!");
				CommonMethods.et.log(LogStatus.INFO, restaurant_name + " and purveryor " + purveyor);
				login.enterSyscoUserName(username);
				login.clickOnNextButton();
				login.enterSyacoUserCredentials(username, password);
				loginFlag = login.clickOnSyscoLoginButton();
				if (loginFlag) {
					result = dashboard.stepsToExport(accountID, accountNumber, listname.trim());

					if (result.equals(true)) {
						emailMessageExport = "Pass";
						exportstatus = "Pass";
						detailedstatus = "OG exported succesfully";
						CommonMethods.et.log(LogStatus.PASS, detailedstatus);
						Thread.sleep(5000);
						SendEmailUtility.sendMailAction(purveyor.trim(), restaurant_name.trim(), "csv");
					} else {
						emailMessageExport = "Failed";
						exportstatus = "Failed";
						detailedstatus = "OG export Failed";
						CommonMethods.et.log(LogStatus.FAIL, detailedstatus);
					}
				} else {
					BaseUi.log("Login status - " + loginFlag);
					throw new Exception();
				}

			} else {
				BaseUi.log(restaurant_name + " for purveryor " + purveyor + " is not Active !!");
				exportstatus = "Not Active";
				CommonMethods.et.log(LogStatus.SKIP, detailedstatus);
			}
			cell1.setCellValue(exportstatus);
			cell2.setCellValue(detailedstatus);

			BaseUi.log("Exiting test method");

		} catch (Exception e) {
			e.printStackTrace();
			exportstatus = "Failed";
			if (!loginFlag) {
				detailedstatus = "Invalid login credentials";
			} else {
				detailedstatus = "Some technical issue ocurred during export";
			}
			cell1.setCellValue(exportstatus);
			cell2.setCellValue(detailedstatus);
			BaseUi.log("Technical issue occured during export for restaurant - " + restaurant_name);
			CommonMethods.et.log(LogStatus.FAIL, exportstatus + " - " + detailedstatus);
		}
		BaseUi.log(emailMessageExport.trim());
		// Assert.assertTrue(false);
	}

	@DataProvider(name = "userData")
	public static Object[][] userData() throws Exception {
		Object[][] td = null;
		return td = CommonMethods.testData();
	}

	 @AfterMethod
	public void writeExcelData() throws IOException {
		CommonMethods.writeExcel();
	}

	 @AfterTest
	public void closeAllResources() throws IOException {
		CommonMethods.closeResources();
	}

	 @AfterClass
	public void tearDown() {
		driver.close();
	}

	 @AfterSuite
	public void sendMailToTheUser() {
		CommonMethods.sendMail();
	}

	public void requiredDetailsForReadExcel(String exportstatus, String detailedstatus, String restaurant_name) {
		BaseUi.log("Inside OG Export : Started exporting OG for different accounts");
		// BaseUi.log("exportstatus =" + exportstatus);
		// BaseUi.log("detailedstatus =" + detailedstatus);
		BaseUi.log("Resturant Name =" + restaurant_name);

		System.out.println(CommonMethods.acno + "-----" + CommonMethods.totalNoOfRows);
		SyscoShopFlow.rowIndex = Math.floorMod(CommonMethods.acno, CommonMethods.totalNoOfRows) + 1;

		BaseUi.log("Test Case test #" + SyscoShopFlow.rowIndex);
		cell1 = CommonMethods.exportworkbook.getSheet(project).getRow(SyscoShopFlow.rowIndex)
				.createCell(CommonMethods.AcColStatus);
		cell1.setCellValue("");
		cell2 = CommonMethods.exportworkbook.getSheet(project).getRow(SyscoShopFlow.rowIndex)
				.createCell(CommonMethods.AcColdetail);
		cell2.setCellValue("");

		exportstatus = cell1.getStringCellValue();
		detailedstatus = cell2.getStringCellValue();
		CommonMethods.et = CommonMethods.er.startTest(restaurant_name);
	}

}
