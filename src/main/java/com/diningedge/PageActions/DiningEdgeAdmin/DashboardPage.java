package com.diningedge.PageActions.DiningEdgeAdmin;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

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

	/*----------------------DiningEdgeAdmin Locators --------------------------------------*/
	@FindBy(xpath = "//div//a[@title='Diningedge Admin']")
	private WebElement dashboardTitle;

	@FindBy(xpath = "//div//a[contains(text(),'Add Companies')]")
	private WebElement addCompany;

	@FindBy(xpath = "//div//a[contains(text(),'Add Users')]")
	private WebElement addUser;

	@FindBy(id = "companies_name")
	private WebElement companyName;

	@FindBy(id = "_submit")
	private WebElement loginBtn;

	@FindBy(id = "companies_homeOffice")
	private WebElement homeOffice;

	@FindBy(id = "companies_orderUnengagedItemsAllowed")
	private WebElement orderUnengaged;

	@FindBy(xpath = "//button[contains(.,'Save changes')]")
	private WebElement saveCompany;

	@FindBy(xpath = "//aside//section//ul//li//a//span[text()='Companies']")
	private WebElement companies;

	@FindBy(xpath = "(//aside//section//ul//li//a//span[text()='Companies'])[2]")
	private WebElement innerCompanies;

	@FindBy(xpath = "//aside//section//ul//li//a//span[text()='Locations']")
	private WebElement locations;

	@FindBy(xpath = "//aside//section//ul//li//a//span[text()='Users']")
	private WebElement users;

	@FindBy(xpath = "//div//a[contains(.,'Add Locations')]")
	private WebElement addLocation;

	@FindBy(id = "locations_name")
	private WebElement locationName;

	@FindBy(id = "select2-locations_company-container")
	private WebElement selectLocations;

	@FindBy(id = "select2-users_company-container")
	private WebElement selectUsers;

	@FindBy(className = "select2-search__field")
	private WebElement serchBar;

	String selecteCompany = "//span//li[contains(text(),'$')]";

	@FindBy(id = "locations_baseLocation")
	private WebElement baseLocation;

	@FindBy(id = "users_firstName")
	private WebElement userFirstName;

	@FindBy(id = "users_username")
	private WebElement userName;

	@FindBy(id = "users_email")
	private WebElement userEmail;

	@FindBy(id = "users_plainPassword")
	private WebElement userPassword;

	@FindBy(id = "users_roles")
	private WebElement userRoles;

	@FindBy(id = "users_enabled")
	private WebElement userEnabled;

	@FindBy(xpath = "//tr//td[contains(.,'Automation Testing Company')]/..//td//a[@name='delete']")
	private WebElement deleteCompany;

	@FindBy(id = "modal-delete-button")
	private WebElement modalDelete;

	/*---------------------------DiningEdgeAdmin Methods-------------------------------------------*/
	public WebElement dynamicElements(String locator, String value) {
		return driver.findElement(By.xpath(locator.replace("$", value)));
	}

	public DashboardPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(120, 1));
	}

	public void getDashBoardTitle(String title) {
		wait.until(ExpectedConditions.visibilityOf(dashboardTitle));
		String titleFromUI = dashboardTitle.getText();
		Assert.assertEquals(title, titleFromUI, title + " is not same");
	}

	public void clickOnAddCompanyButton() {
		wait.until(ExpectedConditions.visibilityOf(addCompany));
		addCompany.click();
		logMessage("User clicks on the Add Companies Button!!");
	}

	public void enterCompanyName(String company) {
		wait.until(ExpectedConditions.visibilityOf(companyName));
		companyName.sendKeys(company);
		logMessage("USer enters the Company Name = " + company);
	}

	public void clickOnCheckBox() {
		wait.until(ExpectedConditions.visibilityOf(homeOffice));
		homeOffice.click();
		logMessage("User select Home office and Order unengaged items allowed check boxes !!");
	}

	public void clickOnCompanySaveButton() {
		wait.until(ExpectedConditions.visibilityOf(saveCompany));
		saveCompany.click();
		logMessage("User clicks on save continue Button !!");
	}

	public void clickOnCompanies() {
		wait.until(ExpectedConditions.visibilityOf(companies));
		companies.click();
		logMessage("User clicks on companies Button !!");
	}

	public void clickOnLocations() {
		wait.until(ExpectedConditions.visibilityOf(locations));
		locations.click();
		logMessage("User clicks on Locations Button !!");
	}

	public void clickOnAddLocations() {
		wait.until(ExpectedConditions.visibilityOf(addLocation));
		addLocation.click();
		logMessage("User clicks on Add Locations Button !!");
	}

	public void clickOnAddUsers() {
		wait.until(ExpectedConditions.visibilityOf(addUser));
		addUser.click();
		logMessage("User clicks on Add Users Button !!");
	}

	public void enterLocationName(String location) {
		wait.until(ExpectedConditions.visibilityOf(locationName));
		locationName.sendKeys(location);
		logMessage("USer enters the Location Name = " + location);
	}

	public void selectCompanyName(String companyName) {
		wait.until(ExpectedConditions.visibilityOf(selectLocations));
		selectLocations.click();
		wait.until(ExpectedConditions.visibilityOf(serchBar));
		serchBar.sendKeys(companyName);
		dynamicElements(selecteCompany, companyName).click();
		logMessage("User selects the Company = " + companyName);
	}

	public void selectCompanyNameForUser(String companyName) {
		wait.until(ExpectedConditions.visibilityOf(selectUsers));
		selectUsers.click();
		wait.until(ExpectedConditions.visibilityOf(serchBar));
		serchBar.sendKeys(companyName);
		dynamicElements(selecteCompany, companyName).click();
		logMessage("User selects the Company = " + companyName);
	}

	public void selectBaseLocation() {
		wait.until(ExpectedConditions.visibilityOf(baseLocation));
		baseLocation.click();
		logMessage("User select clicks on the base location checkbox !!");
	}

	public void selectMultipleRoles() {
		Actions builder = new Actions(driver);
		List<WebElement> options = userRoles.findElements(By.tagName("option"));

		System.out.println(options.size());
		Action multipleSelect = builder.keyDown(Keys.CONTROL).click(options.get(1)).build();

		multipleSelect.perform();
	}

	public void clickOnTheUser() {
		users.click();
	}

	public void enterUserDetails() {
		userFirstName.sendKeys("Automation Testing");
		userName.sendKeys("Automation");
		userEmail.sendKeys("testprav59@gmail.com");
		userPassword.sendKeys("Password123");
		userEnabled.click();
	}

	public void clickOnInnerCompany() {
		innerCompanies.click();
	}

	public void deleteCompany() {
		wait.until(ExpectedConditions.visibilityOf(deleteCompany));
		deleteCompany.click();
		logMessage("User clicks on the delete company icon !!");
	}
	
	public void deleteCompanyFromPopUp() {
		wait.until(ExpectedConditions.visibilityOf(modalDelete));
		modalDelete.click();
		logMessage("User clicks on the delete company icon form Poup !!");
	}
}
