package com.diningedge.PageActions.DiningEdge;

import java.time.Duration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.diningedge.Utilities.CustomFunctions;
import com.diningedge.common.CommonMethods;
import com.diningedge.resources.BaseUi;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class OrderEdgePage extends BaseUi {

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
	private String beforeafter = "//button[@aria-label='$']";

	@FindBy(xpath = "//div//button[@title='Cart']")
	private WebElement addToCart;

	@FindBy(xpath = "//div//p[text()='Best price & quality']/..//span//input/..")
	private WebElement bestPriceToggel;

	@FindBy(xpath = "//p[text()='Product']//button//span")
	private WebElement addProduct;

	@FindBy(xpath = "//div/h2[contains(text(),'Create')]")
	private WebElement popupHeader;

	private String checkBoxComparabls= "//div//p[text()='$']/..//div//span/input";

	/*-------------------------Dynamic Locators------------------*/
	private String checkout = "//div//span[text()='$']";
	private String Comparabls = "//div//h3[contains(.,'$')]/../..//a//button//span//*[local-name()='svg' and @role='presentation']";
	private String searchComarabls = "//div//label[text()='$']/..//input";

	/*----------------------DiningEdge Methods---------------------------*/
	public OrderEdgePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(120, 1));
	}

	public WebElement dynamicElements(String locator, String value) {
		return driver.findElement(By.xpath(locator.replace("$", value)));
	}

	// Will continue
	public void enterUnitsInProducts(String units, String productName, String vendor, String unitType,
			ExtentTest logExtent) {
		if (verifyProductAtOrderEdgePage(productName, vendor, unitType)) {
			enterUnits(units, productName, vendor, unitType, logExtent);
		} else {
			clickOnComparabls(productName);
		}

	}

	public void enterUnits(String units, String productName, String vendor, String unitType, ExtentTest logExtent) {
		CommonMethods.hardwait(1500);
		CommonMethods.scrollIntoView(driver,
				driver.findElement(By.xpath("//div[@id='root']//h3[contains(text(),'" + productName
						+ "')]/../..//parent::div/../../../..//parent::div//div[@id='grid-header']//p[text()='" + vendor
						+ "']/../../../../following::div//div//div//p[contains(.,'" + unitType
						+ "')]/../../..//input[@type='text']")));

		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@id='root']//h3[contains(text(),'"
				+ productName + "')]/../..//parent::div/../../../..//parent::div//div[@id='grid-header']//p[text()='"
				+ vendor + "']/../../../../following::div//div//div//p[contains(.,'" + unitType
				+ "')]/../../..//input[@type='text']"))));

		driver.findElement(By.xpath("//div[@id='root']//h3[contains(text(),'" + productName
				+ "')]/../..//parent::div/../../../..//parent::div//div[@id='grid-header']//p[text()='" + vendor
				+ "']/../../../../following::div//div//div//p[contains(.,'" + unitType
				+ "')]/../../..//input[@type='text']")).sendKeys(units);
		logMessage("User added number of units = " + units);
		logExtent.log(LogStatus.INFO, "Step3: User enters below details");
		logExtent.log(LogStatus.INFO, "User added number of units = " + units + " for combination of " + productName
				+ " :: " + vendor + " :: " + unitType);

	}

	public void enterUnitsAfterBestPrices(String units, String productName, String vendor, String unitType) {
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@id='root']//h3[contains(text(),'"
				+ productName + "')]/../..//parent::div/../../../../following-sibling::div//h3[contains(text(),'"
				+ unitType + "')]/../ ::div//h3[contains(text(),'" + vendor
				+ "')]/../../following-sibling::div[2]//div/div/div/input"))));

		driver.findElement(By.xpath("//div[@id='root']//h3[contains(text(),'" + productName
				+ "')]/../..//parent::div/../../../../following-sibling::div//h3[contains(text(),'" + unitType
				+ "')]/../following-sibling::div//h3[contains(text(),'" + vendor
				+ "')]/../../following-sibling::div[2]//div/div/div/input")).sendKeys(units);

		logMessage("User added number of units = " + units);
	}

	public void clickOnAddToCartButton(ExtentTest logExtent) {
		wait.until(ExpectedConditions.visibilityOf(addToCart));
		waitForElementToClickable(addToCart);
		addToCart.click();
		logMessage("User clicks on the Add Cart Button !!");
		logExtent.log(LogStatus.INFO, "Step04: User clicks on the Add Cart Button !!");
		wait.until(ExpectedConditions.visibilityOf(dynamicElements(checkout, "Checkout")));
		CommonMethods.hardwait(2000);
		dynamicElements(checkout, "Checkout").click();
		logMessage("User clicks on the Checkout Button !!");
		logExtent.log(LogStatus.INFO, "Step05: User clicks on the Checkout Button !!");
	}

	public void clickOnBesrPriceToggelButton() {
		waitForElementToClickable(bestPriceToggel);
		bestPriceToggel.click();
		logMessage("User clicks on the Best price & quality Button !!");
	}

	public void clickOnAddProduct() {
		waitForElementToClickable(addProduct);
		addProduct.click();
		logMessage("User clicks on the add Product Button !!");
	}

	public void varifyPopupForCreateProduct(String expected) {
		wait.until(ExpectedConditions.visibilityOf(popupHeader));
		String actual = popupHeader.getText();
		Assert.assertEquals(actual, expected,
				"Assertion Failed: Actual Value :" + actual + " is not same as expected that is : " + expected);
		logMessage("Assertion Passed: " + expected + " is present on the popup");

	}

	public void enterDetailsOnCreateProduct(String label, String value) {
		varifyPopupForCreateProduct("Create product");
		driver.findElement(By.xpath("//form//label[text()='" + label + "']//following-sibling::div//input"))
				.sendKeys(value);
		logMessage("User enters the value " + value + " in " + label);
	}

	public void clickOnDropdown(String label) {
		driver.findElement(By.xpath("//form//label[contains(text(),'" + label + "')]/..//div//div")).click();
		logMessage("User enters the value " + label + " Dropdown !!");
	}

	public void selcetValueFromDropDown(String label, String value) {
		clickOnDropdown(label);
		WebElement element = driver.findElement(By.xpath("//div//ul//li[text()='" + value + "']"));
		CommonMethods.scrollIntoView(driver, element);
		CustomFunctions.hardWaitForScript();
		element.click();
		logMessage("User selects the value " + value + " From the Dropdown !!");
	}

	public void addNewItemInDropDownList(String label, String value) {
		clickOnDropdown(label);
		driver.findElement(By.xpath("//div//ul//li//p[contains(text(),'" + label + "')]")).click();
		driver.findElement(By.xpath("//label[contains(text(),'" + label + "')]/..//div//input")).sendKeys(value);
		logMessage("User enters the value " + value + " in " + label);
		clickOnSaveIconForCategory(label);
	}

	public void clickOnSaveIconForCategory(String label) {
		driver.findElement(By.xpath("//label[contains(text(),'" + label + "')]/../following-sibling::button[1]"))
				.click();
		logMessage("User clicks on the save icon for " + label);
	}

	public void clickOnCheckBoxes(String label) {
		driver.findElement(By.xpath("//span[contains(text(),'" + label + "')]")).click();
		logMessage("User click on the " + label + " checkbox");
	}

	public void clickOnSaveAndCancel(String value) {
		driver.findElement(By.xpath("//div//button//span[text()='" + value + "']/..")).click();
		logMessage("User click on the " + value + " button");
	}

	public void clickOnComparabls(String productName) {
		waitForElementToClickable(dynamicElements(Comparabls, productName));
		dynamicElements(Comparabls, productName).click();
		logMessage("User clicks on the Open Manage Comparabls Button !!");
	}

	public void clickOnPreviousAfterButton(String value) {
		waitForElementToClickable(dynamicElements(beforeafter, value));
		dynamicElements(beforeafter, value).click();
		logMessage("User clicks on the " + value + " Button !!");
	}

	public boolean verifyProductAtOrderEdgePage(String productName, String vendor, String unitType) {
		try {
			wait.until(ExpectedConditions
					.visibilityOf(driver.findElement(By.xpath("//div[@id='root']//h3[contains(text(),'" + productName
							+ "')]/../..//parent::div/../../../..//parent::div//div[@id='grid-header']//p[text()='"
							+ vendor + "']/../../../../following::div//div//p[contains(text(),'" + unitType
							+ "')]/../../..//input"))));
			return true;
		} catch (Exception e) {
			logMessage("Product is alreday present in the Order Edge Page !!!");
			return false;
		}
	}

	public void enterProductIdAndSelectComparabls(String productName, String value) {
		waitForElementToClickable(dynamicElements(searchComarabls, productName));
		dynamicElements(searchComarabls, productName).sendKeys(value);
		CustomFunctions.hardWaitForScript();
		dynamicElements(checkBoxComparabls,value).click();
		logMsg("User adds comparabls !!");
	}

	public String getUnitType() {
		final String[] units = { "CUP", "DOZ", "G", "LB", "KG", "MG", "ML" };
		Random random = new Random();
		int index = random.nextInt(units.length);
		return units[index];
	}

	public String getStorageType() {
		final String[] units = { "Solid", "Liquid", "FREEZE", "Bread" };
		Random random = new Random();
		int index = random.nextInt(units.length);
		return units[index];
	}

	public String getPrimaryCategory() {
		final String[] units = { "Cheney", "GFS", "PFG", "Sysco", "test" };
		Random random = new Random();
		int index = random.nextInt(units.length);
		return units[index];
	}
	
}
