package com.qa.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.qa.base.TestBase;

public class TestUtil extends TestBase{

	public static String currentDir = System.getProperty("user.dir");
	public static String TESTDATA_SHEET_PATH = currentDir + "\\src\\main\\java\\com\\qa\\testdata\\OrangeHRMTestData.xlsx";
	public static Workbook book;
	public static Sheet sheet;
	public static Row row;
	public static Cell cell;
	public static FileOutputStream outputStream;
	public static FileInputStream inputStream;
	public static String fileExtensionName = TESTDATA_SHEET_PATH.substring(TESTDATA_SHEET_PATH.indexOf("."));


	public static void takeSnapShot(String testMethodName) throws Exception{

		TakesScreenshot scrShot = ((TakesScreenshot) driver);
		File srcFile = scrShot.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(srcFile, new File(currentDir + "\\screenshots\\" + testMethodName+ ".png"));
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	public static Object[][] readTestData(String sheetName) throws IOException {
		//File file = new File(TESTDATA_SHEET_PATH);
		inputStream = new FileInputStream(TESTDATA_SHEET_PATH);

		if(fileExtensionName.equals(".xlsx")){
			book = new XSSFWorkbook(inputStream);
		} else if(fileExtensionName.equals(".xls")){
			book = new HSSFWorkbook(inputStream);
		}

		sheet= book.getSheet(sheetName);
		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];

		for ( int i = 0;i< sheet.getLastRowNum(); i++) {
			row = sheet.getRow(i+1);
			for(int j =0; j< row.getLastCellNum();j++) {
				cell = row.getCell(j);
				if(cell.getCellType() == CellType.STRING) { 
					data[i][j] = cell.toString();
				} else if(cell.getCellType() == CellType.NUMERIC) {					
					data[i][j] = (int)cell.getNumericCellValue();
				} else if (cell.getCellType() == CellType.BLANK) {
					data[i][j] = "";
				}
			}
		}
		return data;
	}
}
