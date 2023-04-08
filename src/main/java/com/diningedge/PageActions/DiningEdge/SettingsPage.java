package com.diningedge.PageActions.DiningEdge;

import java.time.Duration;
import java.util.List;

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

public class SettingsPage extends BaseUi {
	@SuppressWarnings("unused")
	private static final String ExpectedCondition = null;
	private WebDriver driver;
	private WebDriverWait wait;
	List<String> settingTypelist;

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

	public WebElement dynamicElements(String locator, String value) {
		return driver.findElement(By.xpath(locator.replace("$", value)));
	}

	public List<WebElement> dynamicListOfElements(String locator, String value) {
		return driver.findElements(By.xpath(locator.replace("$", value)));
	}

	/*---------------------DiningEdge Locators-------------------*/
	private String vendor = "//tbody//tr//th[contains(text(),'$')]";

	private String selectSettingType = "//button//p[text()='$']";

	private String settingsOptions = "//div//span[text()='$']";

	private String removeEmail = "//div//span[text()='$']/..//*[local-name()='svg' and @role='presentation']";

	private String addItem = "//div//label[text()='$']/..//div//input";

	private String selectItem = "//div//p[contains(.,'$')]/..//div//input";

	private String selectVendor = "//div[contains(text(),'$')]";

	@FindBy(xpath = "//div//input[contains(@placeholder,'Search by vendor')]")
	private WebElement searchBar;

	@FindBy(xpath = "//div//p[text()='Active']/../../..//input")
	private WebElement activeCheckbox;

	@FindBy(xpath = "//div//ul//li[text()='Email']")
	private WebElement selectEmail;

	@FindBy(xpath = "//div//label[text()='Type']/..")
	private WebElement selectType;

	@FindBy(xpath = "//div//label[text()='Type']/..//div//div//input/..//div")
	private WebElement type;

	@FindBy(xpath = "//div//span[text()='Recipients']/..//div//div/div/div/input")
	private WebElement reciepients;

	@FindBy(xpath = "//button[@type='submit']//span[text()='Save']/..")
	private WebElement saveBtn;

	@FindBy(xpath = "/html/body/div[3]/div[2]/div/button")
	private WebElement crossBtn;

	@FindBy(xpath = "//span[@id='client-snackbar']//p")
	private WebElement snackbar;

	@FindBy(xpath = "//span[@id='client-snackbar']/../following-sibling::div//button")
	private WebElement closeSnackbar;

	@FindBy(xpath = "//button//span[text()='Save and Close']")
	private WebElement saveNclose;

	@FindBy(xpath = "//button//span[text()='Add Vendor']/..")
	private WebElement addVendor;

	@FindBy(xpath = "//div//a[@href='/settings']")
	private WebElement settingIcon;

	@FindBy(xpath = "//div//input[contains(@placeholder,'more characters')]")
	private WebElement input;

	@FindBy(xpath = "//div//label//span[text()='Add in All locations']/..//span//input/..")
	private WebElement addLocation;
	
	@FindBy(xpath = "//div//span[contains(.,'@gmail.com')]/..//*[local-name()='svg']")
	private WebElement emailCross;

	/*----------------------DiningEdge Methods---------------------------*/

	public SettingsPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(120, 1));
	}

	public void searchVendorByName(String vendor) {
		CustomFunctions.hardWaitForScript();
		waitForElementToClickable(searchBar);
		searchBar.clear();
		searchBar.sendKeys(vendor);
		logMessage("User enters " + vendor + " search bar !!");
	}

	public void clickOnVendorName(String value) {
		wait.until(ExpectedConditions.visibilityOf(dynamicElements(vendor, value)));
		dynamicElements(vendor, value).click();
		logMessage("User clicks on the " + value + " List item !!");
	}

	public void selectSettinsType(String settingType) {
		wait.until(ExpectedConditions.visibilityOf(dynamicElements(selectSettingType, settingType)));
		CommonMethods.hardwait(2000);
		dynamicElements(selectSettingType, settingType).click();
		logMessage("User clicks on the " + settingType + " Setting item !!");

	}

	public void verifyTypeOfSettingOnPage() {
		List<WebElement> list = driver.findElements(By.xpath("//button//p"));
		System.out.println(list.size());
		for (WebElement webElement : list) {
			String name = webElement.getText();
			System.out.println(name);
		}
	}

	public void selectOptonsItems(String value) {
		wait.until(ExpectedConditions.visibilityOf(dynamicElements(settingsOptions, value)));
		dynamicElements(settingsOptions, value).click();
		logMessage("User Select on the " + value + " Setting options !!");
	}

	public void clickOnActiveChecksBox() {
		if (CommonMethods.verifyChecboxIsSelected(activeCheckbox)) {
			System.out.println("CheckBox alreday Selected !!");
		} else {
			activeCheckbox.click();
			logMessage("User Clicks on Active checkobx !!");
		}
	}

	public void selectEmailFromDropDown() {
		clickOnSelcetType();
		waitForElementToClickable(selectEmail);
		selectEmail.click();
		logMessage("User clicks the Email DropDown");
	}

	public void clickOnSelcetType() {
		waitForElementToClickable(selectType);
		selectType.click();
		logMessage("User clicks the select Type DropDown");
	}

	public void verifyTypeField(String expected) {
		String atcual = type.getText();
		Assert.assertEquals(atcual, expected,
				"ASSERTION FAILED : Value is not as expeted that should be ::" + expected);
		logMessage("ASSERTION PASSED :: Value is expected as " + expected);
	}

	public void enterRecepientsEmails(String emails) {
		emailCross.click();
		waitForElementToClickable(reciepients);
		reciepients.clear();
		reciepients.sendKeys(emails);
		logMessage("User enter the following email ::" + emails);
	}

	public void clickOnSaveButton() {
		waitForElementToClickable(saveBtn);
		saveBtn.click();
		logMessage("User clicks on the Save button !!");
	}

	/*
	 * This method is used to close the popup
	 */
	public void clickOnCrossButton() {
		waitForElementToClickable(crossBtn);
		crossBtn.click();
		logMessage("User clicks on the cross button !!");
	}

	public void verifySnackbarMessage(String expected) {
		String atcual = snackbar.getText();
		Assert.assertEquals(atcual, expected,
				"ASSERTION FAILED : Value is not as expeted that should be ::" + expected);
		logMessage("ASSERTION PASSED :: Value is expected as " + expected);
	}

	public void clickOnEmailRemove(String email) {
		if (validateEmailPresent(email)) {
			dynamicElements(removeEmail, email).click();
			logMessage("User remove the email :: " + email);
		} else {
			logMessage("Email Removal not required !!!");
		}
	}

	public boolean validateEmailPresent(String email) {
		try {
			waitForElementToClickable(dynamicElements(removeEmail, email));
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public void addItemToDashboard(String value, String noOfUnits) {
		CommonMethods.scrollIntoView(driver, dynamicElements(addItem, value));
		waitForElementToClickable(dynamicElements(addItem, value));
		dynamicElements(addItem, value).click();
		dynamicElements(addItem, value).sendKeys(noOfUnits);
		logMessage("User cliks and enter the item number " + noOfUnits);

	}

	public void selectItemFromDashboard(String value) {
		CommonMethods.hardwait(1000);
		dynamicElements(selectItem, value).click();
		logMessage("User clicks on the add button !!");
	}

	public void clickOnTheSaveAndCloseButton() {
		waitForElementToClickable(saveNclose);
		saveNclose.click();
		logMessage("User clicks on the SaveAndClose button !!");
	}

	public void clickOnSnackBarCloseButton() {
		waitForElementToClickable(closeSnackbar);
		closeSnackbar.click();
		logMessage("User clicks on the Snack Bar cross button !!");
	}

	public void clickOnAddVendorButton() {
		waitForElementToClickable(addVendor);
		addVendor.click();
		logMessage("User clicks on the add Vendor button !!");
	}

	public void clickOnSettingIcon() {
		waitForElementToClickable(settingIcon);
		settingIcon.click();
		logMessage("User clicks on the setting Icon button !!");
	}

	public void enterAndSelectVendorName(String vendorName) {
		waitForElementToClickable(input);
		input.sendKeys(vendorName);
		dynamicElements(selectVendor, vendorName).click();
		logMessage("User eneter and select the vendorName = " + vendorName);
	}

	public void checkVendorLocation() {
		waitForElementToClickable(addLocation);
		addLocation.click();
		logMessage("User clicks on the setting Icon button !!");
	}

	public void refresh() {
		driver.navigate().refresh();
		CustomFunctions.hardWaitForScript();
	}
}
