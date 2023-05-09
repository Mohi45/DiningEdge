package InventoryRegression;

import static com.diningedge.Utilities.ConfigPropertyReader.getProperty;

import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.diningedge.PageActions.DiningEdge.DashboardPage;
import com.diningedge.PageActions.DiningEdge.InventoryPage;
import com.diningedge.PageActions.DiningEdge.LoginPage;
import com.diningedge.PageActions.DiningEdge.ManageItemsPage;
import com.diningedge.PageActions.DiningEdge.OrderEdgePage;
import com.diningedge.PageActions.DiningEdge.SettingsPage;
import com.diningedge.Utilities.CustomFunctions;
import com.diningedge.resources.BaseTest;

public class InventoryOnHandEdit extends BaseTest {
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
		editOnHandInventory();
	} 

	private void editOnHandInventory() {
		inventoryPage.clickOnInventory();
		inventoryPage.clickOnCreateButton();
		manageItemPage.selectItemTypeFromList("Reports");
		manageItemPage.selectItemTypeFromList("Inventory Onhand");
		inventoryPage.EditProductItem("Milk");
		for (int i = 0; i < inventoryPage.getVendorNames().size(); i++) {
			
		}
		
		
	}

}
