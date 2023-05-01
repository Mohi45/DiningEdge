package com.diningedge.PageActions.DiningEdge;

import java.time.Duration;
import java.util.List;
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

	@FindBy(xpath = "//th//button[@title='Delete']")
	private WebElement deleteIcon;

	@FindBy(xpath = "//div//h2[text()='Manage Units']/../..//button")
	private WebElement cross;

	@FindBy(xpath = "//div//span[text()='Add product']/..")
	private WebElement addProduct;

	@FindBy(xpath = "//div//button//span[text()='Import']/..")
	private WebElement importBtn;

	@FindBy(xpath = "//div//button//span[text()='Upload']/..")
	private WebElement uploadBtn;

	@FindBy(xpath = "//div//button//span[text()='History']/../../..")
	private WebElement historyBtn;

	@FindBy(xpath = "//span[text()='Export']/..")
	private WebElement export;

	private String addCategory = "//li//p[text()='$']";

	private String inputCategory = "(//div//label[text()='$']/..//div//input)[2]";
	private String inputStorage = "(//div//label[text()='$']/..//div//input)";

	private String saveCategory = "(//div//label[text()='$']/../..//button)[1]";
	private String productBtn = "//tr//th[text()='$']";
	private String getValues = "(//th//p[text()='$']/../../../th)[#]";

	/*----------------------DiningEdge Methods---------------------------*/

	public WebElement dynamicElements(String locator, String value) {
		return driver.findElement(By.xpath(locator.replace("$", value)));
	}

	public WebElement dynamicElements(String locator, String value, String index) {
		return driver.findElement(By.xpath(locator.replace("$", value).replace("#", index)));
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

	public void clickOnHistoryButton() {
		CustomFunctions.hardWaitForScript();
		waitForElementToClickable(historyBtn);
		historyBtn.click();
		CustomFunctions.hardWaitForScript();
		logMessage("User clicks on the History Btn");
	}

	public void clickOnCrossIcon() {
		waitForElementToClickable(cross);
		CustomFunctions.hardWaitForScript();
		try {
			cross.click();
		} catch (Exception e) {
			logMessage("closing....");
			cross.click();
		}
		logMessage("User clicks on the cross icon");
	}

	public void clickOnAddProductButton() {
		waitForElementToClickable(addProduct);
		addProduct.click();
		logMessage("User clicks on the Add Product icon");
	}

	public static String getProductNames() {
		final String[] units = { "Milk Cheney", "Milk sysco", "US Food Milk", "PFG Milk" };
		Random random = new Random();
		int index = random.nextInt(units.length);
		System.out.println("Product Name = " + units[index]);
		return units[index];
	}

	public void uploadCsvFile() {
		importBtn.click();
		driver.findElement(By.xpath("//div//input[@type='file']")).sendKeys(
				System.getProperty("user.dir") + "/src/main/java/com/diningedge/testData/" + "productList.csv");
		logMessage("File Uploaded successfully");
		CustomFunctions.hardWaitForScript();
		uploadBtn.click();
		logMessage("User clicks on Upload btn");
	}

	public void clickOnDropdown(String label) {
		driver.findElement(By.xpath("//form//label[contains(text(),'" + label + "')]/..//div//div")).click();
		logMessage("User enters the value " + label + " Dropdown !!");
	}

	public void addProductCategary(String label, String categery, String inputLabel) {
		dynamicElements(addCategory, label).click();
		if (inputLabel.equalsIgnoreCase("storage")) {
			dynamicElements(inputStorage, inputLabel).sendKeys(categery);
			logMessage("User enters the Storage " + categery);
		} else {
			dynamicElements(inputCategory, inputLabel).sendKeys(categery);
			logMessage("User enters the category " + categery);
		}
		dynamicElements(saveCategory, inputLabel).click();
	}

	public void clickOnTheProduct(String product) {
		waitForElementToClickable(dynamicElements(productBtn, product));
		dynamicElements(productBtn, product).click();
		logMessage("User select the " + product);
	}

	public String getTheValues(String invoice, String index) {
		return dynamicElements(getValues, invoice, index).getText();
	}

	public void clickOnExportBtn() {
		waitForElementToClickable(export);
		export.click();
		logMessage("User clicks on the export Button !!");
	}

}
