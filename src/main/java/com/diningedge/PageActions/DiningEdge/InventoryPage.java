package com.diningedge.PageActions.DiningEdge;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.diningedge.Utilities.CustomFunctions;
import com.diningedge.common.CommonMethods;
import com.diningedge.resources.BaseUi;

public class InventoryPage extends BaseUi {
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

	public InventoryPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(120, 1));
	}

	public WebElement dynamicElements(String locator, String value) {
		return driver.findElement(By.xpath(locator.replace("$", value)));
	}

	@FindBy(xpath = "//ul//li[text()='Inventory']")
	private WebElement inventoryBtn;

	@FindBy(xpath = "//button[@title='DiningEdge']")
	private WebElement headerIcon;

	@FindBy(xpath = "//button[@title='Filters']")
	private WebElement filterIcon;

	@FindBy(xpath = "//div[@label='Product']")
	private WebElement productDropDown;

	@FindBy(xpath = "//div[@role='option']//input")
	private WebElement productInput;

	@FindBy(xpath = "//div[@role='option']/../li")
	private WebElement selectProduct;

	@FindBy(xpath = "//button//span[text()='Apply & Filter']")
	private WebElement applyFilter;

	@FindBy(xpath = "//span[text()='New Inventory']/..")
	private WebElement createInv;
	
	@FindBy(xpath = "(//button//span[text()='Complete']/..)[2]")
	private WebElement complete;

	private String getValues = "(//th[text()='$']/../th)[#]";

	private String selectGrid = "//div//p[text()='$']/..";
	
	private String eyeBtn="((//div//p[text()='Automation']/../../..//p[text()='$']/..//div)[5]/button)[1]";
	
	private String eyeBtnhover="((//div//p[text()='Automation']/../../..//p[text()='$']/..//div)[3]//div)[1]";
	
	private String qyt1="(//span[text()='Name']/../../../../..//div//tr//th//p[text()='$']/../..//th)[3]//input";
	private String qyt2="(//span[text()='Name']/../../../../..//div//tr//th//p[text()='$']/../..//th)[5]//input";
	private String qyt3="(//span[text()='Name']/../../../../..//div//tr//th//p[text()='$']/../..//th)[7]//input";
	
	private String measure1="((//span[text()='Name']/../../../../..//div//tr//th//p[text()='$']/../..//th)[4]//div//div)[3]";
	private String measure2="((//span[text()='Name']/../../../../..//div//tr//th//p[text()='$']/../..//th)[6]//div//div)[3]";
	private String measure3="((//span[text()='Name']/../../../../..//div//tr//th//p[text()='$']/../..//th)[8]//div//div)[3]";

	public WebElement dynamicElements(String locator, String value, String index) {
		return driver.findElement(By.xpath(locator.replace("$", value).replace("#", index)));
	}

	public void clickOnInventory() {
		CustomFunctions.hardWaitForScript();
		headerIcon.click();
		waitForElementToClickable(inventoryBtn);
		inventoryBtn.click();
		logMessage("User clicks on the Inventory button !!");
	}

	public void clickOnFilterIcon() {
		waitForElementToClickable(filterIcon);
		filterIcon.click();
		logMessage("User clicks on the Filter Icon !!");
	}

	public void clickAndSelectProduct(String productName) {
		productDropDown.click();
		CustomFunctions.hardWaitForScript();
		productInput.sendKeys(productName);
		selectProduct.click();
		applyFilter.click();
		logMessage("User search " + productName);
	}

	public String getTheValues(String invoice, String index) {
		return dynamicElements(getValues, invoice, index).getText();
	}

	public void clickOnCreateButton() {
		waitForElementToClickable(createInv);
		createInv.click();
		logMessage("User clicks on the Create invetory Button !!");
	}

	public void ClickOnTheGrid(String value) {
		waitForElementToClickable(dynamicElements(selectGrid, value));
		dynamicElements(selectGrid, value).click();
		logMessage("User selects the :: "+value);
	}
	
	public void enterQuntity(String qtyValue,String quntity) {
		dynamicElements(qyt1, qtyValue).sendKeys(quntity);
		dynamicElements(qyt2, qtyValue).sendKeys(quntity);
		dynamicElements(qyt3, qtyValue).sendKeys(quntity);
		
	}
	
	
	public List<String> getMeasuers(String value) {
		List<String> list= new ArrayList<>();
		list.add(dynamicElements(measure1, value).getText());
		list.add(dynamicElements(measure2, value).getText());
		list.add(dynamicElements(measure3, value).getText());
		return list;
	}
	
	public void clickOnCompleteButton() {
		waitForElementToClickable(complete);
		complete.click();
		logMessage("User clicks on the Complete Button !!");
	}
	
	public void clickOnEyeButton(String date) {
		CommonMethods.hoverAndClick(dynamicElements(eyeBtnhover, date),dynamicElements(eyeBtn, date), driver);
		logMessage("User clicks on the eye button !!");
	}
}
