package configurations;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ObjectReader {
	
	// Creating a properties object to hold the data
		Properties pro = null;

		public ObjectReader() {
			pro = new Properties();
			try {
			// This points to the file we just edited to read the keys
			FileInputStream fis = new FileInputStream("./Object Repository/objects.properties");
			pro.load(fis);
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		public String getURL() {
			return pro.getProperty("baseURL");
		}
		
		// Returns the folder path for saving screenshots
		public String getScreenshotPath() {
			return pro.getProperty("screenShotPath");
		}
		
		// Returns the location of our Excel data sheet
		public String getExcelPath() {
			return pro.getProperty("excelPath");
		}
		
		// Returns the path where the HTML report will be generated
		public String getReportsPath() {
			return pro.getProperty("reportsPath");
		}

		public String getPath(String key) {
			return pro.getProperty(key);
		}
}
