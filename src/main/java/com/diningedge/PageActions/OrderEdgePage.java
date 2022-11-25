package com.diningedge.PageActions;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.diningedge.resources.BaseUi;

public class OrderEdgePage extends BaseUi {

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
	@FindBy(xpath = "//div//button[@title='Cart']")
	private WebElement addToCart;
	
	@FindBy(xpath = "//div//span[text()='Checkout']")
	private WebElement checkout;

	/*----------------------DiningEdge Methods---------------------------*/
	public OrderEdgePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(120, 1));
	}

	public void enterUnits(String units, String productName, String vendor, String unitType) {
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@id='root']//h3[contains(text(),'"
				+ productName + "')]/../..//parent::div/../../../..//parent::div//div[@id='grid-header']//p[text()='"
				+ vendor + "']/../../../../following::div//div//p[contains(text(),'" + unitType + "')]/../../..//input"))));
		
		driver.findElement(By.xpath("//div[@id='root']//h3[contains(text(),'" + productName
				+ "')]/../..//parent::div/../../../..//parent::div//div[@id='grid-header']//p[text()='" + vendor
				+ "']/../../../../following::div//div//p[contains(text(),'" + unitType + "')]/../../..//input"))
				.sendKeys(units);
		logMessage("User added number of units = " + units);
	}
	
	public void clickOnAddToCartButton() {
		wait.until(ExpectedConditions.visibilityOf(addToCart));
		addToCart.click();
		logMessage("User clicks on the Add Cart Button !!");
		wait.until(ExpectedConditions.visibilityOf(checkout));
		checkout.click();
		logMessage("User clicks on the Checkout Button !!");
	}
	
	
}
