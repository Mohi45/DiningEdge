package com.diningedge.resources;

import org.testng.Reporter;

import com.diningedge.Utilities.CustomFunctions;

public class BaseUi {
	 protected void logMessage(String message) {
			Reporter.log(CustomFunctions.getCurrentTime() + " " + message, true);
		}

		public static void log(String message) {
			Reporter.log(CustomFunctions.getCurrentTime() + " " + message, true);
		}
		
		 protected static void logMsg(String message) {
				Reporter.log(CustomFunctions.getCurrentTime() + " " + message, true);
			}
}
