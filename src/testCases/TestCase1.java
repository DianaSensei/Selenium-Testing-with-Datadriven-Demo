package testCases;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

public class TestCase1 {
	private String result;
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	FormulaEvaluator evaluator;
	
	public void execute(String url, String build_version, WebDriver driver, String data_path) {
		int passed = 0;
		int executed = 0;
		File src = new File(data_path);
		FileInputStream fis;
		try {
			fis = new FileInputStream(src);
			workbook = new XSSFWorkbook(fis);
			evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			sheet = workbook.getSheet("Sheet2");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		XSSFRow row0 = sheet.getRow(0);
		System.out.println(sheet.getLastRowNum());
		String build_element = String.format("//option[. = '%s']", build_version);
		for (int i = 3; i <= sheet.getLastRowNum(); i++) {
			driver.get(url);
			driver.manage().window().setSize(new Dimension(1051, 846));
			XSSFRow row = sheet.getRow(i);
			
			{
				driver.findElement(By.id("selectBuild")).click();
				WebElement dropdown = driver.findElement(By.id("selectBuild"));
				dropdown.findElement(By.xpath(build_element)).click();
				driver.findElement(By.id("selectBuild")).click();
			}

			XSSFCell cell0 = row.getCell(0);
			cell0.setCellType(CellType.STRING);

			driver.findElement(By.id("number1Field")).clear();
			driver.findElement(By.id("number1Field")).click();
			driver.findElement(By.id("number1Field")).sendKeys(cell0.getStringCellValue());

			XSSFCell cell1 = row.getCell(1);
			cell1.setCellType(CellType.STRING);

			driver.findElement(By.id("number2Field")).clear();
			driver.findElement(By.id("number2Field")).click();
			driver.findElement(By.id("number2Field")).sendKeys(cell1.getStringCellValue());

			XSSFCell cell2 = row.getCell(2);
			cell2.setCellType(CellType.STRING);
			String operator = String.format("//option[. = '%s']", cell2.getStringCellValue());
			
			driver.findElement(By.id("selectOperationDropdown")).click();
			WebElement dropdown = driver.findElement(By.id("selectOperationDropdown"));
			dropdown.findElement(By.xpath(operator)).click();

			XSSFCell cell3 = row.getCell(3);
			cell3.setCellType(CellType.BOOLEAN);
			if (cell3.getBooleanCellValue()) {
				driver.findElement(By.id("integerSelect")).click();
			}

			XSSFCell cell4 = row.getCell(4);
			driver.findElement(By.id("calculateButton")).click();
			
			String error = driver.findElement(By.id("errorMsgField")).getText();
			XSSFCell cell5 = row.getCell(5);
			
			
			String result_ex;
			if(cell5 == null)
				result_ex ="";
			else {
				cell5.setCellType(CellType.STRING);
				result_ex = cell5.getStringCellValue();
			}
			
			String value = driver.findElement(By.id("numberAnswerField")).getAttribute("value");

			boolean b1 = value.equals(result_ex);
			boolean b2 = error.equals(cell4.getStringCellValue());
			if (b1 && b2) {
				result = "PASS";
				passed++;
			} else
				result = "FAIL";
			executed++;
			// To write data in the excel
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(src);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			// Write Actual error
			row.createCell(6).setCellValue(error);
			// Write Actual result
			row.createCell(7).setCellValue(value);
			// Write Result
			row.createCell(8).setCellValue(result);
			row0.createCell(4).setCellValue(executed);
			
			// Write Total
			
			
			// finally write content
			try {
				workbook.write(fos);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		row0.createCell(4).setCellValue(executed);
		row0.createCell(7).setCellValue(passed);
	}
}
