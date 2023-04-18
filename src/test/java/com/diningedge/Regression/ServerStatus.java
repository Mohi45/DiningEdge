package com.diningedge.Regression;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.diningedge.PageActions.DiningEdge.LoginPage;
import com.diningedge.resources.BaseTest;

public class ServerStatus extends BaseTest {
	WebDriver driver;
	LoginPage login;
	boolean status=false;
	
	@BeforeMethod
	public void setup() {
		driver = getDriver();
		login = new LoginPage(driver);
	}
	
	@Test
	public void validateService() {
		logExtent = extent.startTest("Test01_BasicFlowLogin");
		status=login.elementDisplay();
	}
	
	@AfterSuite
	public void sendAlert() {
		if(status) {
			System.out.println("Everything is working fine !!");
		}else {
			sendReportAlert();
		}
	}

}
