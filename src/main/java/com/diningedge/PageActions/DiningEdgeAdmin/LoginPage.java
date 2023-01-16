package com.diningedge.PageActions.DiningEdgeAdmin;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.diningedge.resources.BaseUi;

public class LoginPage extends BaseUi {

	private WebDriver driver;
	private WebDriverWait wait;

	/*----------------------DiningEdgeAdmin Locators --------------------------------------*/
	@FindBy(id = "username")
	private WebElement userName;

	@FindBy(id = "password")
	private WebElement password;

	@FindBy(id = "_submit")
	private WebElement loginBtn;

	/*---------------------------DiningEdgeAdmin Methods-------------------------------------------*/

	@SuppressWarnings("deprecation")
	public LoginPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, Duration.ofSeconds(120, 1));
	}

	public void enterCredentials(String User, String pass) {
		wait.until(ExpectedConditions.elementToBeClickable(userName));
		userName.sendKeys(User);
		logMessage("User enters Name = " + User);
		password.sendKeys(pass);
		logMessage("User enters Password = " + pass);
	}

	public void clickOnLoginButton() {
		loginBtn.click();
		logMessage("User clicks on the Login Button !!!");
	}

}
