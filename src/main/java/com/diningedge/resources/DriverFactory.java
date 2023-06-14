package com.diningedge.resources;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;

public class DriverFactory {
	static String downloadFilepath = System.getProperty("user.dir") + "\\downloads\\";
	Path path = Paths.get(System.getProperty("user.dir") + "\\downloads\\product_list.csv");
	private static DriverFactory instance = new DriverFactory();

	private DriverFactory() {
	}

	public static DriverFactory getInstance() {
		return instance;
	}

	private static ThreadLocal<WebDriver> threadLocal = new ThreadLocal<WebDriver>();

	public WebDriver getDriver(BrowserType type) {
		try {
			Files.deleteIfExists(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ChromeOptions options = new ChromeOptions();
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", downloadFilepath);
		options.setExperimentalOption("prefs", chromePrefs);
		//options.addArguments("--headless");
		//options.addArguments("--window-size=1920,1200");
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
