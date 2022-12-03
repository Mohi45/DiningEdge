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

public class CheckoutPage extends BaseUi {

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
	private String selectProductCheckBox = "//tbody//th[contains(text(),'$')]/..//th/span";

	private String selecctYesNo = "//button//span[text()='$']";

	private String orderDetails = "//div//span[text()='$']/..//p";

	@FindBy(xpath = "//table//th//span[text()='Product']/../..//th[1]")
	private WebElement productCheckBox;

	@FindBy(xpath = "//span/..//button[@title='Delete']")
	private WebElement delete;

	/*----------------------DiningEdge Methods---------------------------*/

	public WebElement dynamicElements(String locator, String value) {
		return driver.findElement(By.xpath(locator.replace("$", value)));
	}

	public CheckoutPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(120, 1));
	}

	public void clickOnProductCheckBox() {
		waitForElementToClickable(productCheckBox);
		productCheckBox.click();
		logMessage("User clicks on the all product checkbox !!");
	}

	public void clickOnProductToBeSelected(String value) {
		wait.until(ExpectedConditions.visibilityOf(dynamicElements(selectProductCheckBox, value)));
		dynamicElements(selectProductCheckBox, value).click();
		logMessage("User clicks on the " + value + " checkBox !!");
	}

	public void deleteProductFromList() {
		waitForElementToClickable(delete);
		delete.click();
		logMessage("User clicks on delete icon !!");
	}

	public void selecctSumbitAll(String value) {
		wait.until(ExpectedConditions.visibilityOf(dynamicElements(selecctYesNo, value)));
		dynamicElements(selecctYesNo, value).click();
		logMessage("User clicks on the " + value + " Button !!");
	}

	public String getOrderDetails(String value) {
		wait.until(ExpectedConditions.visibilityOf(dynamicElements(orderDetails, value)));
		String getOrderDetails = dynamicElements(orderDetails, value).getText();
		System.out.println("Order Details = "+getOrderDetails);
		return getOrderDetails;
	}
}
