package com.diningedge.PageActions;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.diningedge.Utilities.CustomFunctions;
import com.diningedge.resources.BaseUi;

public class DashboardPage extends BaseUi {
	private WebDriver driver;
	private WebDriverWait wait;

	protected WebElement waitForElementToAppear(By locator) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	protected List<WebElement> waitForElementsToAppear(By locator) {
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	protected WebElement waitForElementToClickable(By locator) {
		return wait.until(ExpectedConditions.elementToBeClickable(locator));
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
	@FindBy(xpath = "//div//h3")
	private WebElement diningedgeText;

	@FindBy(xpath = "(//div/h1)[1]")
	private WebElement dashText;

	/*---------------------SyscoShop Locators-------------------*/

	@FindBy(xpath = "//div[@data-id='list_dropdown_wrapper']")
	private WebElement dropdownIcon;

	@FindBy(xpath = "//button[@data-id='more-actions-btn']")
	private WebElement moreAction;

	@FindBy(xpath = "//li[@data-id='export-list-btn']")
	private WebElement exportList;

	@FindBy(xpath = "//input[@data-id='export-list-modal-price-toggle']/following-sibling::div")
	private WebElement includePrices;

	@FindBy(xpath = "//input[@data-id='export-list-modal-file-name-input']")
	private WebElement inputFileName;

	@FindBy(xpath = "//button[@data-id='export-list-modal-export-btn']")
	private WebElement exportButton;

	@FindBy(xpath = "//div[@data-id='topPanel-dropdown-button-globalCustomerSelection']/button")
	private WebElement accountBtn;

	@FindBy(xpath = "//input[contains(@placeholder,'Search for Customer or OpCo')]")
	private WebElement accountBtnSearch;

	@FindBy(xpath = "//div[@data-id='globalCustomerSelectFlyout-customerList-label-customerInfoCard']//p[normalize-space()='#accountName']")
	private WebElement accountNumber;

	/*----------------------DiningEdge Methods---------------------------*/
	public DashboardPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(120, 1));
	}

	public void getDiningEdgeText(String diningText) {
		wait.until(ExpectedConditions.visibilityOf(diningedgeText));
		diningedgeText.getText();
		Assert.assertEquals(diningText, "DiningEdge", "Assertion Failed: DigingEdge text is not Same as expected");
		logMessage("Assertion Passed: " + diningText + " is present on the HomePage");
	}

	public void getDeshboardText(String dashboardText) {
		wait.until(ExpectedConditions.visibilityOf(diningedgeText));
		dashText.getText();
		Assert.assertEquals(dashboardText, "Dashboard", "Assertion Failed: Dashboard text is not Same as expected");
		logMessage("Assertion Passed: " + dashboardText + " is present on the HomePage");
	}

	/*----------------------SyscoShop Methods---------------------------*/

	public void selectList(String listName) throws Exception {
		System.out.println("Here");
		driver.get("https://shop.sysco.com/app/lists");
		Thread.sleep(3000);

		List<WebElement> elements = null;

		if (isListDdlPresent()) {
			//wait.until(ExpectedConditions.elementToBeClickable(dropdownIcon));
			Thread.sleep(1000);
			elements = driver.findElements(By.xpath("//span[contains(@data-id,'listDropdownItem-')"));
		} else {
			elements = driver
					.findElements(By.xpath("//button[@data-id='my-lists-toggle-button']/following-sibling::*//button"));
		}

		for (WebElement element : elements) {
			String eleText = element.getText().toLowerCase();
			if (eleText.contains(listName.toLowerCase())) {
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
				Thread.sleep(200);
				element.click();
				return;
			}
		}
	}

	public boolean isListDdlPresent() {
		try {
			//wait.until(ExpectedConditions.visibilityOf(dropdownIcon));
			dropdownIcon.isDisplayed();
			return true;
		} catch (Exception e) {
			logMessage("no list drop down found on the page");
			return false;
		}
	}

	public void exportList(String restName) throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOf(moreAction));
		moreAction.click();
		wait.until(ExpectedConditions.visibilityOf(exportList));
		exportList.click();
		CustomFunctions.hardWaitForScript();
		wait.until(ExpectedConditions.visibilityOf(includePrices));
		includePrices.click();
		Thread.sleep(100);
		wait.until(ExpectedConditions.visibilityOf(inputFileName));
		inputFileName.click();
		((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", inputFileName);
		Thread.sleep(200);
		inputFileName.sendKeys(restName.replaceAll("[^A-Za-z]+", "") + "_" + Instant.now().getEpochSecond());
		wait.until(ExpectedConditions.visibilityOf(exportButton));
		exportButton.click();
	}

	public void selectAccount(String accountName) throws Exception {
		Thread.sleep(3000);

		wait.until(ExpectedConditions.elementToBeClickable(accountBtn));
		try {
			// search and finf acc.
			wait.until(ExpectedConditions.visibilityOf(accountBtnSearch));
			accountBtnSearch.sendKeys(accountName);
			Thread.sleep(100);
			wait.until(ExpectedConditions.visibilityOf(includePrices));
			waitForElementToAppear(By.xpath(
					"//div[@data-id='globalCustomerSelectFlyout-customerList-label-customerInfoCard']//p[normalize-space()='#accountName']"
							.replace("accountName", accountName))).click();
		} catch (Exception ex) {
			logMessage("failed to select account");
			ex.printStackTrace();
		}

	}

	public boolean verifyAccount(String accountName) {
		return waitForElementToBePresent(By.xpath(
				"//div[@data-id='topPanel-dropdown-button-globalCustomerSelection']/label/div[contains(text(),'acNum')]"
						.replace("acNum", accountName))).isDisplayed();
	}

	public boolean stepsToExport(String restName, String accountName, String listName) throws Exception {
		try {
			if (CustomFunctions.isIframePresent(driver)) {
				CustomFunctions.dismissPopUp(driver);
			}
			CustomFunctions.dismissAlert(driver);
			if (accountName != null && !accountName.equalsIgnoreCase("")) {
				selectAccount(accountName);
				Thread.sleep(5000);
				if (!verifyAccount(accountName)) {
					throw new Exception(
							String.format("account selection mismatch, expected account {} not selected", accountName));
				}
			}
			if (CustomFunctions.isIframePresent(driver)) {
				CustomFunctions.dismissPopUp(driver);
			}
			CustomFunctions.dismissAlert(driver);

			if (listName != null && !listName.equalsIgnoreCase("")) {
				selectList(listName);
			}
			if (CustomFunctions.isIframePresent(driver)) {
				CustomFunctions.dismissPopUp(driver);
			}
			CustomFunctions.dismissAlert(driver);
			exportList(restName);
			Thread.sleep(3000);
			doLogout();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

	}

	public void doLogout() {
		try {
			waitForElementToBePresent(By.xpath("//div[@data-id='profile-button-container']"));
			new Actions(driver).clickAndHold(driver.findElement(By.xpath("//div[@data-id='profile-button-container']")))
					.build().perform();
			Thread.sleep(1000);
			waitForElementToBePresent(By.xpath("//li[normalize-space()='Log Out']")).click();
			Thread.sleep(1000);
			waitForElementToBePresent(By.xpath("//button[@id='cart-alert-yes-button']")).click();
		} catch (Exception ex) {
			logMessage("failed to logout");
		}
	}

}
