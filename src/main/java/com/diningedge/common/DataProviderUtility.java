package com.diningedge.common;

import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.diningedge.Utilities.ExcelFunctions;
import com.diningedge.resources.BaseUi;

public class DataProviderUtility extends BaseUi {

	    public static Object[][] testData(XSSFSheet inputsheet) throws IOException {
	        log("Inside Dataprovider. Creating the Object Array to store test data inputs.");
	        Object[][] td = null;
	        try {
	            // Get TestCase sheet data
	            int totalNoOfCols = inputsheet.getRow(inputsheet.getFirstRowNum()).getPhysicalNumberOfCells();
	            int totalNoOfRows = inputsheet.getLastRowNum();
	            log(totalNoOfRows + " Accounts and Columns are: " + totalNoOfCols);
	            td = new String[totalNoOfRows][totalNoOfCols];
	            for (int i = 1; i <= totalNoOfRows; i++) {
	                for (int j = 0; j < totalNoOfCols; j++) {
	                    td[i - 1][j] = ExcelFunctions.getCellValue(inputsheet, i, j);
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        log("Test Cases captured in the Object Array. Exiting dataprovider.");
	        return td;
	    }

}
