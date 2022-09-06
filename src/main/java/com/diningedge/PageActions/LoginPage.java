package com.diningedge.PageActions;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.diningedge.Utilities.CustomFunctions;
import com.diningedge.resources.BaseUi;

public class LoginPage extends BaseUi {
	private WebDriver driver;
	private WebDriverWait wait;

	/*----------------------DiningEdge Locators --------------------------------------*/

	@FindBy(xpath = "//div//label[text()='Username']/..//input")
	private WebElement userName;

	@FindBy(xpath = "//div//label[text()='Password']/..//input")
	private WebElement password;

	@FindBy(xpath = "//span[text()='Login']//../../button")
	private WebElement loginBtn;

	@FindBy(css = "h4[class*='header blue']")
	private WebElement loginInfoHeader;

	/*---------------------------SyscoShop Locators------------------------------------------*/

	@FindBy(xpath = "//input[@data-id='txt_login_email']")
	private WebElement syscoUserName;

	@FindBy(xpath = "//button[@data-id='btn_next']")
	private WebElement nextButton;

	@FindBy(id = "okta-signin-username")
	private WebElement syscoUserEmail;

	@FindBy(id = "okta-signin-password")
	private WebElement syscoUserPass;

	@FindBy(id = "okta-signin-submit")
	private WebElement syscoLogin;

	/*---------------------------DiningEdge Methods-------------------------------------------*/

	public LoginPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(120, 1));
	}

	public void enterCredentials(String User, String pass) {
		userName.sendKeys(User);
		logMessage("User enters Name= " + User);
		password.sendKeys(pass);
		logMessage("User enters Password =" + pass);
	}

	public void clickOnLoginButton() {
		loginBtn.click();
		logMessage("User clicks on the Login Button !!!");
	}

	/*---------------------------------SyscoShop Methods -----------------------------------*/

	public void enterSyscoUserName(String userName) {
		CustomFunctions.hardWaitForScript();
		wait.until(ExpectedConditions.elementToBeClickable(syscoUserName));
		syscoUserName.sendKeys(userName);
		logMessage("User Enters the user name -> " + userName);
	}

	public void clickOnNextButton() {
		wait.until(ExpectedConditions.elementToBeClickable(nextButton));
		nextButton.click();
		logMessage("User clicks to the Next Button !!!");
	}

	public void enterSyacoUserCredentials(String userEmail, String password) {
		CustomFunctions.hardWaitForScript();
		wait.until(ExpectedConditions.elementToBeClickable(syscoUserEmail));
		syscoUserEmail.clear();
		syscoUserEmail.sendKeys(userEmail);
		logMessage("User enters the user Email --> " + userEmail);
		syscoUserPass.sendKeys(password);
		logMessage("User enters the user Password --> " + password);

	}

	public boolean clickOnSyscoLoginButton() {
		wait.until(ExpectedConditions.elementToBeClickable(syscoLogin));
		syscoLogin.click();
		logMessage("User click on the Login Button !!!");
		return true;
	}

}
