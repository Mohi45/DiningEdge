package com.diningedge.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.diningedge.Utilities.CustomFunctions;
import com.diningedge.Utilities.ExcleReader;
import com.diningedge.Utilities.SendEmailUtility;
import com.diningedge.resources.BaseUi;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class CommonMethods extends BaseUi {
	public static int acno;
	public static XSSFWorkbook exportworkbook;
	public static XSSFSheet inputsheet;
	public static int AcColStatus, AcColdetail;
	public static FileOutputStream out;
	public static int totalNoOfRows;
	public static String folderDate;
	public static ExtentReports er;
	public static ExtentTest et;
	public static String project = "SyscoShop";
	public static String inputFile = System.getProperty("user.home") + "/Desktop/ExportEngineInput.xlsx";
	public static String reportFile = System.getProperty("user.home")
			+ "/Desktop/Reports/SyscoShop_OG_report/ExportSummary_SyscoShop_"
			+ new Date().toString().replace(":", "").replace(" ", "") + ".xlsx";
	public static String extentReport = System.getProperty("user.dir") + File.separator
			+ (new File(System.getProperty("user.dir") + "/extentsReport").mkdirs() ? "/extentsReport"
					: "/extentsReport")
			+ File.separator + "Report.html";

	/**
	 * This Method used to count number of column as per name in all the tabs in a
	 * single file
	 * 
	 * @throws Exception
	 */
	public static void CountNumberOfSpecificColumn() throws Exception {
		exportworkbook = ExcleReader.openFile(inputFile);
		logMsg("Test data read.");
		inputsheet = exportworkbook.getSheet(project);
		AcColStatus = ExcleReader.getColumnNumber("Export Status", inputsheet);
		AcColdetail = ExcleReader.getColumnNumber("Detailed Export Status", inputsheet);

		System.out.println(AcColStatus);
		System.out.println("***********************");
		System.out.println(AcColdetail);

		logMsg("Exiting before data.");
	}

	/**
	 * This method is used to write the data into excel sheet
	 * 
	 * @throws IOException
	 */
	public static void writeExcel() throws IOException {
		logMsg("Running Excel write method!");
		out = new FileOutputStream(new File(reportFile));
		exportworkbook.write(out);
		er.endTest(et);
		acno++;
	}

	public static Object[][] testData() throws Exception {
		CountNumberOfSpecificColumn();
		logMsg("Inside Dataprovider. Creating the Object Array to store test data inputs.");
		Object[][] td = null;
		try {
			// Get TestCase sheet data
			int totalNoOfCols = inputsheet.getRow(inputsheet.getFirstRowNum()).getPhysicalNumberOfCells();
			totalNoOfRows = inputsheet.getLastRowNum();
			logMsg(totalNoOfRows + " Accounts and Columns are: " + totalNoOfCols);
			td = new String[totalNoOfRows][totalNoOfCols];
			for (int i = 1; i <= totalNoOfRows; i++) {
				for (int j = 0; j < totalNoOfCols; j++) {
					td[i - 1][j] = ExcleReader.getCellValue(inputsheet, i, j);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logMsg("Test Cases captured in the Object Array. Exiting dataprovider.");
		return td;
	}

	public static void setExtentsReport() throws IOException {
		er = new ExtentReports(System.getProperty("user.dir") + File.separator + "extentsReport/Report.html", true);
		er.addSystemInfo("Host Name", "Edge").addSystemInfo("Environment", "Windows Server")
				.addSystemInfo("User Name", "Ashutosh Saxena").addSystemInfo("Project", project);
		er.loadConfig(new File(System.getProperty("user.dir") + File.separator + "extents-config.xml"));
		er.assignProject(project + " Online OG Export");
	}

	public static void sendMail() {
		try {
			er.flush();
			er.close();

			String emailMsg = "Daily " + project + " OG Export Status: " + CustomFunctions.getCurrentTime();

			SendEmailUtility.sendReports(emailMsg, reportFile, extentReport);
			logMsg("Email Sent with Attachment");
		} catch (Exception e) {
			logMsg("report sent failure!!!!");
			e.printStackTrace();
		}
	}

	public static void closeResources() throws IOException {
		logMsg("Closing the resources ....");

		if (out != null) {
			logMsg("Closing file output stream object!");
			out.close();
		}

		if (exportworkbook != null) {
			exportworkbook.close();
		}

	}

	public static void deleteFiles(String path, String extension) {
		File dir = new File(path);
		// FileUtils.cleanDirectory(dir);
		for (File file : dir.listFiles())
			if (!file.isDirectory() && file.getName().contains(extension))
				file.delete();
		System.out.println("All files deleted from folder :-" + path);
	}

	public static void scrollIntoView(WebDriver driver, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", element);
	}

	public static void hardwait(int timeOut) {
		try {
			Thread.sleep(timeOut);
		} catch (InterruptedException e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static boolean verifyChecboxIsSelected(WebElement element) {
		return element.isSelected();
	}
	

}
