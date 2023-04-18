package com.diningedge.PageActions.DiningEdge;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.diningedge.resources.BaseUi;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

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

	/*---------------------------DiningEdge Methods-------------------------------------------*/

	@SuppressWarnings("deprecation")
	public LoginPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);  
		wait = new WebDriverWait(driver, Duration.ofSeconds(120, 1));
	}

	public void enterCredentials(String User, String pass, ExtentTest logExtents) {
		wait.until(ExpectedConditions.elementToBeClickable(userName));
		userName.sendKeys(User);
		logExtents.log(LogStatus.INFO, "Step1: User enters below credentials and clicks on the Login button !!");
		logExtents.log(LogStatus.INFO, "User enters Name = " + User);
		logMessage("User enters Name = " + User);
		password.sendKeys(pass);
		logExtents.log(LogStatus.INFO, "User enters password = " + pass);
		logMessage("User enters Password =" + pass);
	}

	public void clickOnLoginButton() {
		loginBtn.click();
		logMessage("User clicks on the Login Button !!!");
	}
	
	public boolean elementDisplay() {
		return userName.isDisplayed();
	}

}
