package utility;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtils {
	
	WebDriverWait wait;
	WebDriver driver=null;
	// Constructor to set up the wait object with a specific timeout
	public WaitUtils(WebDriver driver, long seconds) {
		this.driver=driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
	}
	
	// Waiting until an element is actually visible on the screen
	public WebElement waitForVisibility(By element) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(element));
	}
	
	// Waiting until an element is ready to be clicked
	public WebElement waitForClickable(By element) {
		return wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	// A custom way to force a pause until the timeout hits
	public void explicitWait() {
		try {
			wait.until(d -> false);
		} catch (TimeoutException e) {
			// Catching this so the program doesn't crash during the pause
		}
	}
	
	public void implicitWait(long sec) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(sec));
	}
}