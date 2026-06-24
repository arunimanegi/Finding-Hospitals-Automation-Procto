package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

	// Method to take our data array and save it into an Excel file
	public void writeToExcel(String location, ArrayList<ArrayList<String>> data,ArrayList<String> header) {
		try (XSSFWorkbook workbook = new XSSFWorkbook()) {
			// Creating a new sheet in the workbook
			XSSFSheet sheet = workbook.createSheet();

			// Creating the header row at index 0
			Row row = sheet.createRow(0);
			for(int i=0;i<header.size();i++) {
				row.createCell(i).setCellValue(header.get(i));
			}
			

			// Looping through the data array to fill the rows
			for (int i = 0; i < data.get(0).size(); i++) {
				Row nr = sheet.createRow(i + 1);
				for(int j=0;j<data.size();j++) {					
					nr.createCell(j).setCellValue(data.get(j).get(i));
//					System.out.print(data.get(j).get(i)+" ");
				}
//				System.out.println("");
//					System.out.println(productName.get(i).getText()+" "+productPrice.get(i).getText());
			}

			// Actually writing the file to the specified location
			workbook.write(new FileOutputStream(location));

		} catch (Exception e) {
			System.out.println("Writing to Excel failed " + e.getMessage());
		}
	}

	// Method to read data from an existing Excel file and print it
	public void readFromExcel(String filePath) {
		try (FileInputStream fis = new FileInputStream(new File(filePath));
				XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

			// Getting the first sheet from the file
			XSSFSheet sheet = workbook.getSheetAt(0);
			DataFormatter f = new DataFormatter();
			Row r = sheet.getRow(0);
			int rowNum = sheet.getPhysicalNumberOfRows();
			int colNum = r.getPhysicalNumberOfCells();
			String[][] data = new String[rowNum][colNum];
			// Going through each row one by one
			for (int i = 0; i < rowNum; i++) {
				// Going through each cell in that row
				Row row = sheet.getRow(i);
				for (int j = 0; j < colNum; j++) {
					System.out.printf("%s \t", f.formatCellValue(row.getCell(j)));
					data[i][j] = f.formatCellValue(row.getCell(j));
				}
				// Printing a new line after each row is done
				System.out.println();
			}

		} catch (Exception e) {
			System.out.println("Reading from Excel failed: " + e.getMessage());
		}
	}
}
