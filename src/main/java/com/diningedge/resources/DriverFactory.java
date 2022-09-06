package com.diningedge.resources;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class DriverFactory {
	
	//Singleton design pattern
	
	private static DriverFactory instance= new DriverFactory();
	
	private DriverFactory() {}
	
	public static DriverFactory getInstance() {
		return instance;
	}
	
	//Factory Design pattern
	
	private static ThreadLocal<WebDriver> threadLocal=new ThreadLocal<WebDriver>();
	
	public WebDriver getDriver(BrowserType type) {
		
		switch (type) {
		case CHROME:
			threadLocal.set(new ChromeDriver());
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
