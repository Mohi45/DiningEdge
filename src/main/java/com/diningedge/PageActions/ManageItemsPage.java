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

public class ManageItemsPage extends BaseUi {

	private WebDriver driver;
	private WebDriverWait wait;

	protected WebElement waitForElementToAppear(By element) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(element));
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
	private String itemTypes = "//div//span[text()='$']";

	@FindBy(xpath = "//div//button[@title='Search']")
	private WebElement seacrhIcon;

	@FindBy(id = "adornment-search")
	private WebElement searchBox;

	@FindBy(id = "//th//button[@title='Delete']")
	private WebElement deleteIcon;

	/*----------------------DiningEdge Methods---------------------------*/

	public WebElement dynamicElements(String locator, String value) {
		return driver.findElement(By.xpath(locator.replace("$", value)));
	}

	public ManageItemsPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(120, 1));
	}

	public void selectItemTypeFromList(String value) {
		waitForElementToClickable(dynamicElements(itemTypes, value));
		dynamicElements(itemTypes, value).click();
		logMessage("User selects the " + value + " From the list");
	}

	public void clickOnTheSearchIconEndEnterValue(String value) {
		waitForElementToClickable(seacrhIcon);
		seacrhIcon.click();
		logMessage("User clicks on the Search Icon on Product List Page");
		searchBox.sendKeys(value);
		logMessage("User enters the " + value + " To be searched");
	}

	public void clickOnDeleteIcon() {
		waitForElementToClickable(deleteIcon);
		deleteIcon.click();
		logMessage("User clicks on the Delete icon");
	}

}
