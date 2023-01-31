package com.diningedge.Regression;

import static com.diningedge.Utilities.ConfigPropertyReader.getProperty;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.diningedge.PageActions.DiningEdge.DashboardPage;
import com.diningedge.PageActions.DiningEdge.LoginPage;
import com.diningedge.PageActions.DiningEdge.OrderEdgePage;
import com.diningedge.resources.BaseTest;

public class OrderEdgeProductFlow extends BaseTest {
	WebDriver driver;
	LoginPage login;
	DashboardPage dashboard;
	OrderEdgePage orderEdge;
	@BeforeMethod
	public void setup() {
		driver = getDriver();
		login = new LoginPage(driver);
		dashboard = new DashboardPage(driver);
		orderEdge = new OrderEdgePage(driver);
	}

	@Test
	public void Test01_BasicFlowLogin() {
		/*-------------------------Basic Flow----------------------------------*/
		logExtent = extent
				.startTest("Test01_BasicFlowLogin");
		login.enterCredentials(getProperty("username"), getProperty("password"), logExtent);
		login.clickOnLoginButton();
		dashboard.getDiningEdgeText("DiningEdge", logExtent);
		dashboard.getDeshboardText("Dashboard", logExtent);
		dashboard.clickOnTheOrderEdge("Order Edge", logExtent);
		dashboard.clickOnTheSelectLoaction(logExtent);
		createNewProduct();
	}
	
	public void createNewProduct() {
		orderEdge.clickOnAddProduct();
		orderEdge.varifyPopupForCreateProduct("Create product");
		orderEdge.enterDetailsOnCreateProduct("Name", "product1");
		orderEdge.enterDetailsOnCreateProduct("Size", "12");
		orderEdge.selcetValueFromDropDown("Unit *", "LB");
		orderEdge.selcetValueFromDropDown("Primary Category *", "PFG");
		orderEdge.selcetValueFromDropDown("Storage", "Solid");
		orderEdge.clickOnSaveAndCancel("Save");
	}

}
