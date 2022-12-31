package com.diningedge.PageActions;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
	private String selectUnitType = "//div//ul//li[text()='$']//span";

	@FindBy(xpath = "//div//label[text()='Unit *']/..//div//div//input")
	private WebElement selectUnit;
	/*----------------------DiningEdge Methods---------------------------*/

	public WebElement dynamicElements(String locator, String value) {
		return driver.findElement(By.xpath(locator.replace("$", value)));
	}

	public Order_OGPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(120, 1));
	}

	public void clickOnUpdatePackButton() {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", dynamicElements(editPack, "Cheney Testing"));
		System.out.println("User clicks on the Update pack pancil button !!");
	}
	public void clickOnUpdatePriceButton() {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", dynamicElements(editPrice, "Cheney Testing"));
		System.out.println("User clicks on the Update price pancil button !!");
	}

	public void enterPack_SizeAndPreicesValues(String packOrSize, String value) {
		waitForElementToClickable(dynamicElements(pack_size, packOrSize));
		dynamicElements(pack_size, packOrSize).sendKeys(Keys.CONTROL + "a");
		dynamicElements(pack_size, packOrSize).sendKeys(Keys.DELETE);
		dynamicElements(pack_size, packOrSize).sendKeys(value);
		System.out.println("USer enters the value " + value + " for " + packOrSize);
	}
	
	public void selectQuantity(String packOrSize, String value) {
		waitForElementToClickable(dynamicElements(selectQyt, packOrSize));
		dynamicElements(selectQyt, packOrSize).sendKeys(Keys.CONTROL + "a");
		dynamicElements(selectQyt, packOrSize).sendKeys(Keys.DELETE);
		dynamicElements(selectQyt, packOrSize).sendKeys(value);
		System.out.println("USer enters the value " + value + " for " + packOrSize);
	}
	

	public void clickOnUnitAndSelectUnitType(String unitType) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", selectUnit);
		System.out.println("User clicks on the Unit * dropDown");
		dynamicElements(selectUnitType, unitType);
		System.out.println("User select the unit Type :: " + unitType);
	}

}
