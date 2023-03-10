package com.diningedge.PageActions.DiningEdge;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.diningedge.Utilities.CustomFunctions;
import com.diningedge.resources.BaseUi;

public class Order_OGPage extends BaseUi {

	private WebDriver driver;
	private WebDriverWait wait;

	protected WebElement waitForElementToAppear(By locator) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	protected List<WebElement> waitForElementsToAppear(By locator) {
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	protected WebElement waitForElementToClickable(WebElement element) {
		return wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	protected WebElement waitForElementToBePresent(By locator) {
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	protected boolean waitForElementToDisappear(By locator) {
		return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}

	protected boolean waitForTextToDisappear(By locator, String text) {
		return wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(locator, text)));
	}

	/*---------------------DiningEdge Locators-------------------*/
	private String editPack = "//table//tr//td//p[text()='$']/../following-sibling::td[2]//div//button";
	private String editPrice = "//table//tr//td//p[text()='$']/../following-sibling::td[3]//div//button";
	private String selectQyt = "//table//tr//td//p[text()='$']/../following-sibling::td[4]//div//div//input";
	private String pack_size = "//div//label[text()='$']/..//div//input";
	private String selectUnitType = "//div//ul//li[text()='$']//span/..";
	private String assignVendor ="//div[text()='Add new Product']/..//div[text()='$']";

	@FindBy(xpath = "//div//label[text()='Unit *']/..//div//div//input")
	private WebElement selectUnit;
	
	@FindBy(xpath = "//div[@label='Vendor']//input")
	private WebElement selectVendor;
	
	@FindBy(xpath = "//span[text()='New Vendor Item']/..")
	private WebElement newVendor;
	
	@FindBy(xpath  = "//div//input[@id='integration-downshift-simple']")
	private WebElement simple;
	
	/*----------------------DiningEdge Methods---------------------------*/

	public WebElement dynamicElements(String locator, String value) {
		return driver.findElement(By.xpath(locator.replace("$", value)));
	}

	public Order_OGPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(120, 1));
	}

	public void clickOnUpdatePackButton(String productName) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", dynamicElements(editPack, productName));
		logMessage("User clicks on the Update pack pancil button !!");
	}
	public void clickOnUpdatePriceButton(String productName) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", dynamicElements(editPrice, productName));
		logMessage("User clicks on the Update price pancil button !!");
	}

	public void enterPack_SizeAndPreicesValues(String packOrSize, String value) {
		waitForElementToClickable(dynamicElements(pack_size, packOrSize));
		dynamicElements(pack_size, packOrSize).sendKeys(Keys.CONTROL + "a");
		dynamicElements(pack_size, packOrSize).sendKeys(Keys.DELETE);
		dynamicElements(pack_size, packOrSize).sendKeys(value);
		logMessage("USer enters the value " + value + " for " + packOrSize);
	}
	
	public void selectQuantity(String packOrSize, String value) {
		waitForElementToClickable(dynamicElements(selectQyt, packOrSize));
		dynamicElements(selectQyt, packOrSize).sendKeys(Keys.CONTROL + "a");
		dynamicElements(selectQyt, packOrSize).sendKeys(Keys.DELETE);
		dynamicElements(selectQyt, packOrSize).sendKeys(value);
		logMessage("USer enters the value " + value + " for " + packOrSize);
	}
	

	public void clickOnUnitAndSelectUnitType(String unitType) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", selectUnit);
		logMessage("User clicks on the Unit * dropDown");
		CustomFunctions.hardWaitForScript();
		dynamicElements(selectUnitType, unitType).click();
		logMessage("User select the unit Type :: " + unitType);
		CustomFunctions.hardWaitForScript();
	}
	
	public void clickOnVendorAndSelectVendor(String vendor) {
		CustomFunctions.hardWaitForScript();
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", selectVendor);
		logMessage("User clicks on the Vendor dropDown");
		CustomFunctions.hardWaitForScript();
		dynamicElements(selectUnitType, vendor).click();
		logMessage("User select the vendor :: " + vendor);
	}
	
	public void clickOnNewVendor() {
		waitForElementToClickable(newVendor);
		newVendor.click();
		logMessage("User clicks on the new vedor item button !!");
	}
	
	public void enterNewVendorDetails(String name,String vendorId,String packValue) {
		dynamicElements(pack_size, "Name").sendKeys(name);
		logMessage("User enters the name of vendor = "+name);
		dynamicElements(pack_size, "Vendor Item ID").sendKeys(vendorId);
		logMessage("User enters the vendorId = "+vendorId);
		dynamicElements(pack_size, "Pack").sendKeys(packValue);
		dynamicElements(pack_size, "Size").sendKeys(packValue);
		dynamicElements(pack_size, "Price").sendKeys(packValue);
		logMessage("User enters the other details = "+packValue);
	}
	public static String getVendorNames() {
		final String[] units = { "Cheney", "US Foods", "Sysco", "PFG"};
		Random random = new Random();
		int index = random.nextInt(units.length);
		System.out.println("Product Name = " + units[index]);
		return units[index];
	}
	
	public void enterVendorToAssign(String vendor) {
		waitForElementToClickable(simple);
		simple.click();
		simple.sendKeys(vendor);
		dynamicElements(assignVendor, vendor).click();
		logMessage("User clicks on the = "+vendor);
	}
	
	public static  Map<Integer, String> getVendors() {
		Map<Integer, String> map=new HashMap<>();
		map.put(1, "Cheney");
		map.put(2, "US Foods");
		map.put(3, "Sysco");
		map.put(4, "PFG");
		map.put(5, "Europan");
		map.put(6, "Micro Roots");
		return map;
	}
}
