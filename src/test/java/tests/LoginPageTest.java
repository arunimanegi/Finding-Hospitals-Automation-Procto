package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import base.BaseTest;
import pages.LoginPage;
import utility.ScreenshotUtils;

public class LoginPageTest extends BaseTest {
    LoginPage lp;
    
    @Test(priority = 0, description = "Verify that the Login page loads with the correct heading.")
    public void verifyLoginPage() {
        // Start an extent report entry for this test
        test = extent.createTest("verifyLoginPage");
        test.log(Status.INFO, "Checking login page heading");

        lp = new LoginPage(driver, objReader, wait);

        // Read the heading text from the login page
        String acText = lp.openLoginPage();
        String expText = "Login";

        try {
            Assert.assertEquals(acText, expText, "Login page heading mismatch.");
            test.log(Status.PASS, "Login page heading matched: " + acText);
        } catch (AssertionError e) {
            // Take a screenshot to see what heading appeared instead
            String path = ScreenshotUtils.captureScreenshot(driver, "verifyLoginPage_fail");
            test.addScreenCaptureFromPath(path);
            test.log(Status.FAIL, "Login heading mismatch: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 1, description = "Verify input fields accept data.")
    public void inputData() {
        // Start an extent report entry for this test
        test = extent.createTest("inputData");
        test.log(Status.INFO, "Entering invalid credentials to test error handling");

        // Type in wrong credentials on purpose to trigger the error message
        lp.inputUserName("1111111111");
        lp.inputPassword("Test@1001");
        // No assertion here - we check the error in the next test
    }

    @Test(priority = 2, description = "Verify error handling for invalid credentials and retry with valid ones.")
    public void verifyLoginFlow() {
        // Start an extent report entry for this test
        test = extent.createTest("verifyLoginFlow");
        test.log(Status.INFO, "Submitting invalid credentials and checking for error message");

        lp.clickSubmit();

        // Wait for the error block to show up on the page
        WebElement errorMsg = wait.waitForVisibility(By.id("usernameErrorBlock"));

        try {
            Assert.assertTrue(errorMsg.isDisplayed(), "Error message was not displayed for invalid credentials.");
            test.log(Status.PASS, "Error message shown correctly for invalid login");
        } catch (AssertionError e) {
            // Take screenshot showing the missing error message
            String path = ScreenshotUtils.captureScreenshot(driver, "verifyLoginFlow_noError");
            test.addScreenCaptureFromPath(path);
            test.log(Status.FAIL, "Error message check failed: " + e.getMessage());
            throw e;
        }

        // Now enter the real credentials and log in
        test.log(Status.INFO, "Retrying login with valid credentials");
        lp.inputUserName("8799595987");
        lp.inputPassword("DevTest@1234");
        lp.clickSubmit();
        wait.explicitWait();
    }

    @Test(priority = 3, description = "Verify user profile name after successful login and return to Home.")
    public void verifySuccessfulLogin() {
        // Start an extent report entry for this test
        test = extent.createTest("verifySuccessfulLogin");
        test.log(Status.INFO, "Verifying logged in user profile name");
        
        // Click the logo to go back to home page using JS to avoid click interception
        WebElement logoLink = driver.findElement(By.xpath("//*[@class='practo_logo_new']/ancestor::a"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", logoLink);
        
     // Take a screenshot after going back to home
        String path = ScreenshotUtils.captureScreenshot(driver, "verifySuccessfulLogin_returnHome");
        test.addScreenCaptureFromPath(path);
        test.log(Status.INFO, "Clicked logo to return to home page");
        
     // Confirm we are back on the main site
        try {
            Assert.assertTrue(driver.getCurrentUrl().contains("practo.com"), "Clicking the logo did not return the user to the home page.");
            test.log(Status.PASS, "Returned to home page successfully");
        } catch (AssertionError e) {
            String failPath = ScreenshotUtils.captureScreenshot(driver, "verifySuccessfulLogin_homeFail");
            test.addScreenCaptureFromPath(failPath);
            test.log(Status.FAIL, "Home page navigation check failed: " + e.getMessage());
            throw e;
        }
        wait.explicitWait();
        // Read the profile name shown in the nav bar
        String acId = lp.verifySuccessfulLogin();
        String expId = "Devansh Dua";

        try {
            Assert.assertEquals(acId, expId, "The logged-in user name does not match the expected profile name.");
            test.log(Status.PASS, "Profile name matched: " + acId);
        } catch (AssertionError e) {
            // Screenshot so we can see what profile name appeared
            path = ScreenshotUtils.captureScreenshot(driver, "verifySuccessfulLogin_nameFail");
            test.addScreenCaptureFromPath(path);
            test.log(Status.FAIL, "Profile name mismatch: " + e.getMessage());
            throw e;
        }
    }
}
