package com.diningedge.resources;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;

public class DriverFactory {

	// Singleton design pattern

	private static DriverFactory instance = new DriverFactory();

	private DriverFactory() {
	}

	public static DriverFactory getInstance() {
		return instance;
	}

	// Factory Design pattern

	private static ThreadLocal<WebDriver> threadLocal = new ThreadLocal<WebDriver>();

	public WebDriver getDriver(BrowserType type) {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		options.addArguments("--window-size=1920,1200");
		options.addArguments("--remote-allow-origins=*");
		options.addArguments("disable-infobars"); 
		options.addArguments("--disable-extensions"); 
		options.addArguments("--disable-dev-shm-usage");
		options.addArguments("--no-sandbox");
		switch (type) {
		case CHROME:
			threadLocal.set(new ChromeDriver(options));
			break;

		case EDGE:
			threadLocal.set(new EdgeDriver());
			break;

		default:
			break;
		}
		return threadLocal.get();

	}

}
