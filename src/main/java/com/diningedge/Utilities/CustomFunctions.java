package com.diningedge.Utilities;

import java.io.File;
import java.io.FileFilter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.diningedge.resources.BaseUi;

public class CustomFunctions extends BaseUi {

	/**
	 * This is used to timestamp with date and time
	 * 
	 * @return
	 */
	public static String getCurrentTime() {
		DateFormat dateFormat = new SimpleDateFormat("[yyyy-MM-dd] [HH:mm:ss:SSS]");
		Date dt = new Date();
		return dateFormat.format(dt);
	}

	/**
	 * This is used to timestamp with date and time
	 * 
	 * @return
	 */
	public static String getCurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat(" [yyyy-MM-dd]");
		Date dt = new Date();
		return dateFormat.format(dt);
	}

	/**
	 * This method is used to get latest file from specific path
	 * 
	 * @param dirPath
	 * @param fileType
	 * @return
	 */
	public static File getLatestFilefromDir(String dirPath, String fileType) {
		File getLatestFilefromDir = null;
		File dir = new File(dirPath);
		FileFilter fileFilter = new WildcardFileFilter("*." + fileType);
		File[] files = dir.listFiles(fileFilter);

		if (files.length > 0) {
			/** The newest file comes first **/
			Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
			getLatestFilefromDir = files[0];
		}

		return getLatestFilefromDir;
	}

	public static void hardWaitForScript() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static boolean isIframePresent(WebDriver driver) throws InterruptedException {
		Thread.sleep(3000);
		// List to get & store frame
		List<WebElement> ele = driver.findElements(By.tagName("iframe"));
		System.out.println("Number of frames in a page :" + ele.size());
		if (ele.size() == 0) {
			System.out.println("No frames on this page");
			return false;
		} else {
			System.out.println("Frames present on this page, Below are the details -");

			for (WebElement el : ele) {
				System.out.println("Frame Id :" + el.getAttribute("id"));
				System.out.println("Frame name :" + el.getAttribute("name"));
			}
			return true; // frames present
		}

	}

	public static void acceptAlert(WebDriver driver) {
		try {
			driver.switchTo().alert().accept();
		} catch (NoAlertPresentException e) {
			logMsg("No Alert found on page !!!");
		}
	}

	public static void dismissAlert(WebDriver driver) {
		try {
			driver.switchTo().alert().dismiss();
		} catch (NoAlertPresentException e) {
			logMsg("No Alert found on page !!!");
		}
	}

	public static void dismissPopUp(WebDriver driver) {
		try {
			driver.switchTo().frame("intercom-modal-frame");
			driver.findElement(By.xpath("//div[@class='modal-content modal-lg']")).click();
			driver.switchTo().defaultContent();
		} catch (Exception e) {
			System.out.println("error no pop-up or issue closing pop up");
		}
	}

	public static String getSheetName() {
		final String[] sheets = { "Cheney", "US_Food", "Sysco", "PFG" };
		Random random = new Random();
		int index = random.nextInt(sheets.length);
		System.out.println("SheetName= " + sheets[index]);
		return sheets[index];
	}

	public static int generateRandomNumber() {
		Random random = new Random();
		return random.nextInt(999)+1;
	}

}
