package com.diningedge.Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFunctions {

	protected static XSSFWorkbook workbook;

	/**
	 * 1 Class: ExcelFunctions Method: openFile To open excel file
	 */
	public static XSSFWorkbook openFile(String filepath) throws IOException {
		try {
			File file = new File(filepath);
			FileInputStream fIP = new FileInputStream(file);
			// Get the workbook instance for XLSX file
			workbook = new XSSFWorkbook(fIP);
			if (file.isFile() && file.exists()) {
				System.out.println(filepath + " file open successfully.");
			} else {
				System.out.println("Error to open file: " + filepath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return workbook;
	}

	/**
	 * 2 Class: ExcelFunctions Method: writeandcloseFile To write and close
	 * excel file
	 */
	public void writeandcloseFile(String filepath) {
		try {
			FileOutputStream outFile = new FileOutputStream(new File(filepath));
			workbook.write(outFile);
			outFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 3 Class: ExcelFunctions Method: getColumnNumber Helper Function: To get
	 * the Column number against the column name
	 */
	public static int getColumnNumber(String colValue, XSSFSheet xssfSheet) {
		boolean bFlag = false;
		Cell cell = null;
		String colval = null;
		int retindex = 0;
		Row row = null;
		int i = 0;
		Iterator<Cell> cellIterator = null;
		while (bFlag == false) {
			do {
				row = xssfSheet.getRow(i);
				// For each row, iterate through each columns
				try {
					cellIterator = row.cellIterator();
					if (cellIterator != null)
						break;
				} catch (NullPointerException e) {
					i++;
				}
			} while (row == null);
			while (cellIterator.hasNext()) {
				cell = cellIterator.next();
				if (cell != null) {
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						colval = cell.getStringCellValue();
						break;
					case Cell.CELL_TYPE_NUMERIC:
						if (DateUtil.isCellDateFormatted(cell)) {
							Date dDate = cell.getDateCellValue();
							colval = dDate.toString();
						} else {
							double nDate = cell.getNumericCellValue();
							colval = "" + nDate;
						}
						break;
					case Cell.CELL_TYPE_FORMULA:
						colval = cell.getCellFormula();
						break;
					}
					if (colval != null && colval.trim().toUpperCase().equals(colValue.trim().toUpperCase())) {
						bFlag = true;
						retindex = cell.getColumnIndex();
						// System.out.println(colval + " column Index is: " +
						// retindex);
						// i++;
						break;
					}
				}

			}
			i++;
		}
		return retindex;
	}

	/**
	 * 4 Class: ExcelFunctions Method: getRowNumber Helper Function: To get the
	 * Row number against the column name
	 */
	public static int getRowNumber(String Value, XSSFSheet xssfSheet) {
		boolean bFlag = false;
		Cell cell = null;
		String colval = null;
		int retindex = 0;
		Row row = null;
		int i = 0;
		Iterator<Cell> cellIterator = null;
		while (bFlag == false) {
			// fetching header value
			do {
				row = xssfSheet.getRow(i);
				// For each row, iterate through each columns
				try {
					cellIterator = row.cellIterator();
					if (cellIterator != null)
						break;
				} catch (NullPointerException e) {
					// System.out.println(e);
					i++;
				}
			} while (row == null);
			while (cellIterator.hasNext()) {
				cell = cellIterator.next();
				if (cell != null) {
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						colval = cell.getStringCellValue();
						// System.out.println(colval);
						break;
					case Cell.CELL_TYPE_NUMERIC:
						if (DateUtil.isCellDateFormatted(cell)) {
							Date dDate = cell.getDateCellValue();
							colval = dDate.toString();
						} else {
							double nDate = cell.getNumericCellValue();
							colval = "" + nDate;
						}
						break;
					case Cell.CELL_TYPE_FORMULA:
						colval = cell.getCellFormula();
						break;
					}
					if (colval != null && colval.trim().toUpperCase().equals(Value.trim().toUpperCase())) {
						bFlag = true;
						retindex = i;
						// System.out.println(colval + " row Index is: " +
						// retindex);
						// i++;
						break;
					}
				}
			}
			i++;
		}
		return retindex;
	}

	/**
	 * 5 Class: ExcelFunctions Method: getCellValue Helper Function: Get Value
	 * of the cell
	 */
	public static Object getCellValue(XSSFSheet sheet, int row, int col) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		Cell cell = sheet.getRow(row).getCell(col);
		Object cellValue = null;
		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				cellValue = cell.getStringCellValue();
				// System.out.println(colval);
				break;
			case Cell.CELL_TYPE_NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					Date dDate = cell.getDateCellValue();
					cellValue = sdf.format(dDate);// .toString();
				} else {
					double nDate = cell.getNumericCellValue();
					cellValue = "" + nDate;
				}
				break;
			case Cell.CELL_TYPE_FORMULA:
				cellValue = cell.getCellFormula();
				break;
			}
		}
		return cellValue;
	}

	public static XSSFWorkbook copySheet(XSSFWorkbook toBeCopied, XSSFWorkbook resultingExcel, String sheetName)
			throws Exception {

		XSSFSheet outsheet = resultingExcel.getSheet(sheetName);
		XSSFSheet sheet = toBeCopied.getSheet(sheetName);

		int lastRow = sheet.getLastRowNum();
		int lastCol = 0;
		while (sheet.getRow(0).getCell(lastCol) != null) {
			lastCol++;
		}

		for (int i = 0; i < lastRow; i++) {
			Row newRow = outsheet.createRow(i);
			for (int j = 0; j < lastCol; j++) {
				try {
					Cell oldCell = sheet.getRow(i).getCell(j);
					Cell newCell = newRow.createCell(j);
					if (!oldCell.equals(null)) {
						switch (oldCell.getCellType()) {
						case XSSFCell.CELL_TYPE_STRING:
							newCell.setCellValue(oldCell.getStringCellValue());
							break;
						case XSSFCell.CELL_TYPE_NUMERIC:
							newCell.setCellValue(oldCell.getNumericCellValue());
							break;
						case XSSFCell.CELL_TYPE_BOOLEAN:
							newCell.setCellValue(oldCell.getBooleanCellValue());
							break;
						case XSSFCell.CELL_TYPE_ERROR:
							newCell.setCellErrorValue(oldCell.getErrorCellValue());
							break;
						case XSSFCell.CELL_TYPE_FORMULA:
							newCell.setCellType(XSSFCell.CELL_TYPE_FORMULA); // Add
																				// this
																				// line
							newCell.setCellFormula(oldCell.getCellFormula());
							break;
						default:
							break;
						}
					}
				} catch (NullPointerException e) {
					break;
				}
			}
		}

		return resultingExcel;
	}



}
