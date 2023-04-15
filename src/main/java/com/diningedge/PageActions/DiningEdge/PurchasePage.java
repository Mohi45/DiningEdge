package com.diningedge.PageActions.DiningEdge;

import java.time.Duration;
import java.util.Random;

import org.openqa.selenium.By;
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

	@FindBy(xpath="//button//span[text()='Purchases']")
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
	
	private String selectVendor="//div//ul/li[text()='$']";
	
	
	public void clickOnNewPurchaseAndSelectVendor(String vendor) {
		waitForElementToClickable(purchaseIcon);
		purchaseIcon.click();
		logMessage("User clicks on the create purchase icon !!");
		vendorloc.click();
		dynamicElements(selectVendor, vendor).click();
		logMessage("User selects the "+vendor +" from the list");
	}
	
	public void searchAndSelectItem(String item) {
		waitForElementToClickable(searchItem);
		searchItem.sendKeys(item);
		selectItem.click();
		logMessage("User select teh item code "+item);
	}
	
	public void selectInvoiceDate() {
		waitForElementToClickable(dateSelect);
		dateSelect.click();
		logMessage("User selects the current date");
	}
	
	public void enterInvoiceNumber(String invoiceNumber) {
		waitForElementToClickable(invoiceInput);
		invoiceInput.sendKeys(invoiceNumber);
		logMessage("User enters the invoice number "+invoiceNumber);
		CustomFunctions.hardWaitForScript();
	}
	public void clickOnInternalNote() {
		internalNote.click();
		CustomFunctions.hardWaitForScript();
	}
	
	public String[] getVendorName(String vendor) {
		if(vendor.equalsIgnoreCase("cheney")) {
			String[] cheneyItems= {"006","007","008","009"};
			return cheneyItems;
		}else if(vendor.equalsIgnoreCase("PFG")) {
			String[] pfgItems= {"012","013","014","015"};
			return pfgItems;
		}else {
			String[] syscoItems= {"003","017","018","019"};
			return syscoItems;
		}
	}
	public String getPrimaryVendor() {
		final String[] units = { "Cheney", "PFG", "Sysco"};
		Random random = new Random();
		int index = random.nextInt(units.length);
		return units[index];
	}
}
