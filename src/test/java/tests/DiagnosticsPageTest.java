package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import base.BaseTest;
import pages.DiagnosticsPage;
import utility.ScreenshotUtils;

public class DiagnosticsPageTest extends BaseTest {

    DiagnosticsPage dP;

    @Test(priority = 0, description = "Verify that the top cities section displays exactly 8 cities.")
    public void verifyTopCityCount() {
        // Create extent report entry for this test
        test = extent.createTest("verifyTopCityCount");
        test.log(Status.INFO, "Clicking Lab Tests and counting the top cities shown");

        // Pass the shared wait from BaseTest into the page object
        dP = new DiagnosticsPage(driver, wait);
        int topCityCount = dP.getTopCityCount();
        int expCityCount = 8;

        // Take a screenshot of the city list that appeared
        String path = ScreenshotUtils.captureScreenshot(driver, "verifyTopCityCount_cities");
        test.addScreenCaptureFromPath(path);

        // Confirm exactly 8 cities are visible
        try {
            Assert.assertEquals(topCityCount, expCityCount, "Count mismatch: The number of top cities displayed is not 8.");
            test.log(Status.PASS, "Top city count verified: " + topCityCount);
        } catch (AssertionError e) {
            String failPath = ScreenshotUtils.captureScreenshot(driver, "verifyTopCityCount_fail");
            test.addScreenCaptureFromPath(failPath);
            test.log(Status.FAIL, "City count mismatch: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 1, description = "Verify that the default selected city is Bangalore.")
    public void verifySelectedCity() {
        // Create extent report entry for this test
        test = extent.createTest("verifySelectedCity");
        test.log(Status.INFO, "Checking the default city in the location input field");

        String selectedCity = dP.getSelectedCity();
        String expCity = "Bangalore";

        // Confirm the location input shows Bangalore
        try {
            Assert.assertEquals(selectedCity, expCity, "City mismatch: The default city is not Bangalore.");
            test.log(Status.PASS, "Default city verified: " + selectedCity);
        } catch (AssertionError e) {
            String path = ScreenshotUtils.captureScreenshot(driver, "verifySelectedCity_fail");
            test.addScreenCaptureFromPath(path);
            test.log(Status.FAIL, "Default city mismatch: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 2, description = "Verify that the page header correctly identifies the Diagnostics section.")
    public void verifyDiagnosticsPage() {
        // Create extent report entry for this test
        test = extent.createTest("verifyDiagnosticsPage");
        test.log(Status.INFO, "Checking the main heading on the diagnostics page");

        String acHeading = dP.pageHeaderVerify();
        String expHeading = "Book Lab Tests Online";

        // Confirm the heading says the right thing
        try {
            Assert.assertEquals(acHeading, expHeading, "Header mismatch: Not on the Book Lab Tests page.");
            test.log(Status.PASS, "Page heading verified: " + acHeading);
        } catch (AssertionError e) {
            String path = ScreenshotUtils.captureScreenshot(driver, "verifyDiagnosticsPage_fail");
            test.addScreenCaptureFromPath(path);
            test.log(Status.FAIL, "Page heading mismatch: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 3, description = "Verify that searching for a test returns the correct detailed information.")
    public void verfiyTests() {
        // Create extent report entry for this test
        test = extent.createTest("verfiyTests");
        test.log(Status.INFO, "Searching for CBC Test and checking the result");

        // Type the test name and submit the search
        dP.enterTestName();

        // Take a screenshot of the search results
        String path = ScreenshotUtils.captureScreenshot(driver, "verfiyTests_results");
        test.addScreenCaptureFromPath(path);
        test.log(Status.INFO, "CBC Test search results loaded");

        String acTest = dP.verifyTestInfo();
        String expTest = "Complete Blood Count Automated Blood";

        // trim() helps prevent failures caused by trailing spaces in the HTML
        try {
            Assert.assertEquals(acTest.trim(), expTest, "Search Result mismatch: The test info returned does not match the expected value.");
            test.log(Status.PASS, "Test info verified: " + acTest);
        } catch (AssertionError e) {
            String failPath = ScreenshotUtils.captureScreenshot(driver, "verfiyTests_fail");
            test.addScreenCaptureFromPath(failPath);
            test.log(Status.FAIL, "Test info mismatch: " + e.getMessage());
            throw e;
        }
        wait.explicitWait();
        driver.navigate().back();
        driver.navigate().back();
    }
}
