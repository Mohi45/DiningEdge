package com.diningedge.Utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.alibaba.fastjson.JSONException;

public class Read {
	static List<HashMap<String, String>> excelData = new ArrayList<HashMap<String, String>>();

	public static void readWriteExcel(String sheetName)
			throws EncryptedDocumentException, InvalidFormatException, IOException, JSONException {
		File file = new File("C:\\Users\\Jyoti Singh\\Desktop\\Book.xlsx");
		FileInputStream inputStream = new FileInputStream(file);
		Workbook workbook = WorkbookFactory.create(inputStream);
		String key = "";
		String value = "";
		Sheet sheet = workbook.getSheet("SS");
		int rowCount = sheet.getLastRowNum();
		for (int i = 1; i <= rowCount; i++) {
			Row row = sheet.getRow(0);
			Map<String, String> rowData = new HashMap<>();
			for (int j = 0; j < row.getLastCellNum(); j++) {
				try {
					key = row.getCell(j).getStringCellValue();
					value = sheet.getRow(i).getCell(j).getStringCellValue();
				} catch (Exception e) {
					key = String.valueOf(row.getCell(j).getStringCellValue());
					value = String.valueOf(row.getCell(j).getStringCellValue());
				}

				rowData.put(key, value);
			}
			excelData.add((HashMap<String, String>) rowData);
		}
		FileWriter myWriter = new FileWriter(
				"C:\\Users\\Jyoti Singh\\Desktop\\DiningEdgeByM\\DiningEdge\\src\\main\\java\\com\\diningedge\\testData\\location.properties");
		BufferedWriter info = new BufferedWriter(myWriter);
		for (int i = 0; i < excelData.size(); i++) {
			info.write(String.format("#" + excelData.get(i).get("Resto") +"%n"+ excelData.get(i).get("locationId") + "="
					+ excelData.get(i).get("user") + "/" + excelData.get(i).get("pass") + "%n"));
		}
		info.close();
	}

	public static void main(String... strings)
			throws EncryptedDocumentException, InvalidFormatException, JSONException, IOException {
		readWriteExcel("");

	}
}
