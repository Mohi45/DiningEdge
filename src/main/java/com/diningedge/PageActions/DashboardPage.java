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
import org.testng.annotations.Factory;

import com.diningedge.Utilities.CustomFunctions;
import com.diningedge.common.CommonMethods;
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

	protected WebElement waitForElementToClickable(WebElement locator) {
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

	@FindBy(xpath = "//span[normalize-space()='Orders Guides']")
	private WebElement ordersGuide;

	@FindBy(id = "select-location")
	private WebElement location;

	private String listItems = "//span[text()='$']";

	@FindBy(xpath = "//div[@id='root']//div/div/header/div/h3")
	private WebElement verifyOrderEdge;

	@FindBy(xpath = "//div//a[@href='/settings']")
	private WebElement settingBtn;

	/*----------------------DiningEdge Methods---------------------------*/
	public DashboardPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(120, 1));
	}

	public WebElement dynamicElements(String locator, String value) {
		return driver.findElement(By.xpath(locator.replace("$", value)));
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

	public void clickOnOrdersGuideIcon() {
		wait.until(ExpectedConditions.visibilityOf(ordersGuide));
		ordersGuide.click();
		logMessage("User clicks on Orders Guide Page !!");
	}

	public void clickOnTheSelectLoaction() {
		wait.until(ExpectedConditions.visibilityOf(location));
		location.click();
		logMessage("User clicks on select Location dropDown !!");
	}

	public void clickOnTheOrderEdge(String value) {
		wait.until(ExpectedConditions.visibilityOf(dynamicElements(listItems, value)));
		dynamicElements(listItems, value).click();
		logMessage("User clicks on " + value + " Button !!");
	}

	public void verifyOrderEdgePage(String expected) {
		wait.until(ExpectedConditions.visibilityOf(verifyOrderEdge));
		String actual = verifyOrderEdge.getText();
		Assert.assertEquals(actual, expected,
				"Assertion Failed: Actual Value :" + actual + " is not same as expected that is : " + expected);
		logMessage("Assertion Passed: " + actual + " is present on the HomePage");
	}

	public void clickOnHeader() {
		CommonMethods.hardwait(2000);
		verifyOrderEdge.click();
	}

	public void clickOnSettingButton() {
		waitForElementToClickable(settingBtn);
		settingBtn.click();
		logMessage("User clicks on the Setting Button !!!");
	}
}
