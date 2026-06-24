package configurations;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class BrowserFactory {
	
	// Creating and returning a new ChromeDriver instance
		public static WebDriver getChromeDriver() {
			return new ChromeDriver();
		}
		
		// Creating and returning a new EdgeDriver instance
		public static WebDriver getEdgeDriver() {
			return new EdgeDriver();
		}

}
