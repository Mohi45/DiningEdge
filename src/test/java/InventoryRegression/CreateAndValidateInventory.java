package InventoryRegression;

import static com.diningedge.Utilities.ConfigPropertyReader.getProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.diningedge.PageActions.DiningEdge.DashboardPage;
import com.diningedge.PageActions.DiningEdge.InventoryPage;
import com.diningedge.PageActions.DiningEdge.LoginPage;
import com.diningedge.PageActions.DiningEdge.ManageItemsPage;
import com.diningedge.PageActions.DiningEdge.OrderEdgePage;
import com.diningedge.PageActions.DiningEdge.SettingsPage;
import com.diningedge.Utilities.CustomFunctions;
import com.diningedge.common.CommonMethods;
import com.diningedge.resources.BaseTest;

public class CreateAndValidateInventory extends BaseTest {
	Random random = new Random();
	WebDriver driver;
	LoginPage login;
	DashboardPage dashboard;
	OrderEdgePage orderEdge;
	ManageItemsPage manageItemPage;
	String productName = "Automation Product " + CustomFunctions.generateRandomNumber();
	String size = String.valueOf(CustomFunctions.generateRandomUnitSize());
	SettingsPage settingsPage;
	InventoryPage inventoryPage;
	public int numberOfUnits = random.nextInt(4) + 1;
	protected String vendorName;
	protected String location = "loc10";

	@BeforeMethod
	public void setup() {
		driver = getDriver();
		login = new LoginPage(driver);
		dashboard = new DashboardPage(driver);
		orderEdge = new OrderEdgePage(driver);
		manageItemPage = new ManageItemsPage(driver);
		settingsPage = new SettingsPage(driver);
		inventoryPage = new InventoryPage(driver);
	}

	@Test
	public void Test01_BasicFlowLogin() {
		/*-------------------------Basic Flow----------------------------------*/
		logExtent = extent.startTest("Test01_BasicFlowLogin");
		login.enterCredentials(getProperty("username"), getProperty("password"), logExtent);
		login.clickOnLoginButton();
		dashboard.getDiningEdgeText("DiningEdge", logExtent);
		dashboard.getDeshboardText("Dashboard", logExtent);
		dashboard.clickOnTheOrderEdge("Order Edge", logExtent);
		dashboard.clickOnTheSelectLoaction(logExtent);
		createInventory();
	}

	public void createInventory() {
		List<String> list = new ArrayList<>();
		List<String> list1 = new ArrayList<>();
		List<String> list2 = new ArrayList<>();
		List<String> listV = new ArrayList<>();
		inventoryPage.clickOnInventory();
		inventoryPage.clickOnCreateButton();
		inventoryPage.ClickOnTheGrid("Grid Order");
		for (int i = 0; i < inventoryPage.getVendorNames().size(); i++) {
			inventoryPage.enterQuntity(inventoryPage.getVendorNames().get(i), String.valueOf(numberOfUnits));
			list = inventoryPage.getMeasuers(inventoryPage.getVendorNames().get(i));
			list1.addAll(list);
		}
		orderEdge.clickOnSaveAndCancel("Complete");
		inventoryPage.clickOnCompleteButton();
		inventoryPage.clickOnEyeButton(CommonMethods.getDate());
		for (int i = 0; i < inventoryPage.getVendorNames().size(); i++) {
			listV = inventoryPage.getMeasuersFromValidation(inventoryPage.getVendorNames().get(i));
			list2.addAll(listV);
		}
		System.out.println(list1 + "         " + list2);
		Assert.assertEquals(list1, list2, "Data is not same !!");
	}
	
	@AfterSuite
	public void mailTriggerInCaseOfUI() {
		sendReport(vendorName, location, "OrderFromOG");
	}

}
