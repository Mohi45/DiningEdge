package com.diningedge.PageActions.DiningEdge;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.diningedge.Utilities.CustomFunctions;
import com.diningedge.resources.BaseUi;

public class PurchasePage extends BaseUi {

	private WebDriver driver;
	private WebDriverWait wait;

	public PurchasePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(120, 1));
	}

	protected WebElement waitForElementToClickable(WebElement element) {
		return wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public WebElement dynamicElements(String locator, String value) {
		return driver.findElement(By.xpath(locator.replace("$", value)));
	}

	@FindBy(xpath = "//button//span[text()='Purchases']")
	private WebElement purchaseIcon;

	@FindBy(id = "select-locationVendor")
	private WebElement vendorloc;

	@FindBy(xpath = "//div//label[text()='Add new purchase item']/..//div//input")
	private WebElement searchItem;

	@FindBy(xpath = "//div[text()='Add new Vendor Product']/following-sibling::div")
	private WebElement selectItem;

	@FindBy(xpath = "//div//input[@placeholder='Select invoice date']")
	private WebElement dateSelect;

	@FindBy(xpath = "//div//input[@placeholder='Enter invoice number']")
	private WebElement invoiceInput;

	@FindBy(xpath = "//div//input[@placeholder='Enter note']")
	private WebElement internalNote;

	@FindBy(xpath = "(//th//p[text()='Charge']/../../../../../../../../..//input)[10]")
	private WebElement chargeInput;

	@FindBy(xpath = "(//th//p[text()='Charge']/../../../../../../../../..//input)[9]")
	private WebElement unitInput;

	@FindBy(xpath = "//div//span[text()='Product:']/..")
	private WebElement product;

	private String chregePrUnit = "(//div//p//span[contains(text(),'Item')]/../../../..//th)[7]//div//p";

	private String selectVendor = "//div//ul/li[text()='$']";

	private String selectItemViaPO = "//div//p[text()='$']/../../..";

	@FindBy(xpath = "//div//p[text()='Total:']/../..//h3")
	private WebElement total;

	@FindBy(id = "U")
	private WebElement uBtn;

	private String unit = "((//th//div[contains(.,'$')])[2]/../..//th)[7]//input";

	private String dateselect = "(//button//span[text()='2']/..)[1]";

	public void clickOnNewPurchaseAndSelectVendor(String vendor) {
		waitForElementToClickable(purchaseIcon);
		purchaseIcon.click();
		logMessage("User clicks on the create purchase icon !!");
		vendorloc.click();
		dynamicElements(selectVendor, vendor).click();
		logMessage("User selects the " + vendor + " from the list");
	}

	public void searchAndSelectItem(String item) {
		waitForElementToClickable(searchItem);
		searchItem.sendKeys(item);
		selectItem.click();
		logMessage("User select the item code " + item);
	}

	public void addChargeUnits(String charge) {
		waitForElementToClickable(chargeInput);
		chargeInput.sendKeys(Keys.CONTROL + "a");
		chargeInput.sendKeys(Keys.DELETE);
		chargeInput.sendKeys(charge);
		logMessage("User select the charge code " + charge);
	}

	public void addUnitUnits(String unit) {
		waitForElementToClickable(chargeInput);
		unitInput.sendKeys(Keys.CONTROL + "a");
		unitInput.sendKeys(Keys.DELETE);
		unitInput.sendKeys(unit);
		logMessage("User select the unit code " + unit);
	}

	public void selectInvoiceDate() {
		waitForElementToClickable(dateSelect);
		dateSelect.click();
		logMessage("User selects the current date");
	}

	public void addDate() {
		LocalDate currentDate = LocalDate.now();
		LocalDate nextDay = currentDate.plusDays(2);
		String d=nextDay.toString().split("-")[2];
		int n= Integer.parseInt(d);
		String ss=String.valueOf(n);
		dynamicElements(dateselect, ss).click();
		logMessage("select date as :: "+ss);
	}

	public void enterInvoiceNumber(String invoiceNumber) {
		waitForElementToClickable(invoiceInput);
		invoiceInput.sendKeys(invoiceNumber);
		logMessage("User enters the invoice number " + invoiceNumber);
		CustomFunctions.hardWaitForScript();
	}

	public void clickOnInternalNote() {
		internalNote.click();
		CustomFunctions.hardWaitForScript();
	}

	public String getCheragePerUnit() {
		List<WebElement> list = driver.findElements(By.xpath(chregePrUnit));
		String charePerUnit = "";
		for (int i = 0; i < list.size(); i++) {
			charePerUnit += list.get(i).getText().trim();
		}
		return charePerUnit.trim();
	}

	public String[] getVendorName(String vendor) {
		if (vendor.equalsIgnoreCase("cheney")) {
			String[] cheneyItems = { "201", "205", "209", "213", "217" };
			return cheneyItems;
		} else if (vendor.equalsIgnoreCase("PFG")) {
			String[] pfgItems = { "204", "207", "210", "214", "218" };
			return pfgItems;
		} else {
			String[] syscoItems = { "202", "206", "211", "215" };
			return syscoItems;
		}
	}

	public String getPrimaryVendor() {
		final String[] units = { "Cheney", "PFG", "Sysco" };
		Random random = new Random();
		int index = random.nextInt(units.length);
		return units[index];
	}

	public void selectItemWithPONumber(String poNumber) {
		dynamicElements(selectItemViaPO, poNumber).click();
		logMessage("User clicks on the PO Number = " + poNumber);
	}

	public String getProductName() {
		return product.getText();
	}

	public String getTotal() {
		return total.getText();
	}

	public String getUnits(String vendor) {
		return dynamicElements(unit, vendor).getAttribute("value");
	}

	public void clickOnUBtn() {
		uBtn.click();
		logMessage("User clicks on the U btton!!");
	}
	
}
