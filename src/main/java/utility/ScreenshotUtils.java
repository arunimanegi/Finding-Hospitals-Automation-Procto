package utility;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtils {

	private static final String SCREENSHOT_FOLDER = "screenshots/";

	/**
	 * captureScreenshot()
	 */
	public static String captureScreenshot(WebDriver driver, String testName) {

		// Build the full file path where we'll save the screenshot
		// Example result:
		// "screenshots/TC_LOGIN_002_Incorrect_password_20240101_143022.png"
		String screenshotFileName = testName + ".png";
		String fullScreenshotPath = SCREENSHOT_FOLDER + screenshotFileName;

		try {
			// STEP 1: Cast the WebDriver to TakesScreenshot
			TakesScreenshot takesScreenshot = (TakesScreenshot) driver;

			// STEP 2: Take the screenshot and save it to a temporary File object
			File screenshotSourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
			// STEP 3: Create the destination file and ensure the folder exists
			File screenshotDestFile = new File(fullScreenshotPath);
			// I am creating the "screenshots/" folder if it doesn't exist yet.
			FileUtils.copyFile(screenshotSourceFile, screenshotDestFile);

//			System.out.println("[ScreenshotUtils] Screenshot saved at: " + screenshotDestFile.getAbsolutePath());
			return screenshotDestFile.getAbsolutePath();

		} catch (IOException e) {
			// If something goes wrong (disk full, no permission, etc.), print the error
			System.out.println("[ScreenshotUtils] ERROR: Could not save screenshot for: " + testName);
			System.out.println("[ScreenshotUtils] Reason: " + e.getMessage());
			return null;

		} catch (Exception e) {
			// This happens if any other exception occurs
			System.out.println("[ScreenshotUtils] ERROR: ");
			e.printStackTrace();
			return null;
		}
	}

}
