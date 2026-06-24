package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import base.BaseTest;
import pages.HomePage;
import utility.ScreenshotUtils;

public class HomePageTest extends BaseTest {

    HomePage hp;

    @Test(description = "Verify Practo Home Page loads correctly and navigate to Login")
    public void homePageVerify() {
        // Create a test entry in the extent report
        test = extent.createTest("homePageVerify");
        test.log(Status.INFO, "Starting home page verification");

        // Set up the home page object
        hp = new HomePage(driver, objReader, wait);

        // Check that the page title matches what we expect
        boolean isHomeDisplayed = hp.verifyHomePage();
        try {
            Assert.assertTrue(isHomeDisplayed, "Home Page failed to load or key elements are missing.");
            test.log(Status.PASS, "Home page title matched expected value");
        } catch (AssertionError e) {
            // Take a screenshot so we can see what went wrong
            String path = ScreenshotUtils.captureScreenshot(driver, "homePageVerify_titleFail");
            test.addScreenCaptureFromPath(path);  // change
            test.log(Status.FAIL, "Home page title mismatch: " + e.getMessage());
            throw e;
        }

        // Click the login button to go to the login page
        hp.clickLoginButton();

        // Take a screenshot to confirm we landed on the login page
        String path = ScreenshotUtils.captureScreenshot(driver, "homePageVerify_loginNav");
        test.addScreenCaptureFromPath(path);
        test.log(Status.INFO, "Clicked login button, checking URL now");

        // Verify the URL changed to the login page
        String currentUrl = driver.getCurrentUrl();
        try {
            Assert.assertTrue(currentUrl.contains("login"), "Failed to navigate to the Login page.");
            test.log(Status.PASS, "Navigated to login page successfully");
        } catch (AssertionError e) {
            // Screenshot on navigation failure
            String failPath = ScreenshotUtils.captureScreenshot(driver, "homePageVerify_navFail");
            test.addScreenCaptureFromPath(failPath);
            test.log(Status.FAIL, "Navigation to login page failed: " + e.getMessage());
            throw e;
        }
    }
}
