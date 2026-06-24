package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import base.BaseTest;
import pages.CorporateWellnessPage;
import utility.ScreenshotUtils;

public class CorporateWellnessTest extends BaseTest {

    CorporateWellnessPage cWP;

    @Test(priority = 1)
    public void verifyCorporatePageLanding() {
        // Create extent report entry for this test
        test = extent.createTest("verifyCorporatePageLanding");
        test.log(Status.INFO, "Navigating to the corporate wellness page");

        // Go directly to the corporate URL and set up the page object
//        driver.get("https://www.practo.com/plus/corporate");
//        driver.manage().window().maximize();

        // Pass the shared wait from BaseTest into the page object
        cWP = new CorporateWellnessPage(driver, wait);

        String expectedUrl = "https://www.practo.com/plus/corporate";

        // Take a screenshot of the landed page
        String path = ScreenshotUtils.captureScreenshot(driver, "verifyCorporatePageLanding_page");
        test.addScreenCaptureFromPath(path);
        test.log(Status.INFO, "Corporate page loaded, checking URL");

        // Confirm we are on the correct URL
        try {
            Assert.assertEquals(cWP.urlCorporateWellnessPage(), expectedUrl, "The landing URL is incorrect.");
            test.log(Status.PASS, "Corporate page URL verified");
        } catch (AssertionError e) {
            String failPath = ScreenshotUtils.captureScreenshot(driver, "verifyCorporatePageLanding_fail");
            test.addScreenCaptureFromPath(failPath);
            test.log(Status.FAIL, "URL mismatch: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 2)
    public void verifyInvalidEmailValidation() throws InterruptedException {
        // Create extent report entry for this test
        test = extent.createTest("verifyInvalidEmailValidation");
        test.log(Status.INFO, "Filling form with an invalid email to check button stays disabled");

        // Fill all fields with valid data except use a bad email format
        cWP.inputName("Dev User");
        cWP.inputOrgName("Cognizant");
        cWP.inputContactNumber("9876543210");
        cWP.inputEmail("invalid-email-format");
        cWP.selectOrgSize("501-1000");
        cWP.selectInterest("Taking a demo");

        // Take a screenshot with the invalid email entered
        String path = ScreenshotUtils.captureScreenshot(driver, "verifyInvalidEmailValidation_form");
        test.addScreenCaptureFromPath(path);
        test.log(Status.INFO, "Form filled with invalid email, checking schedule button state");

        // The button should remain disabled when email is invalid
        try {
            Assert.assertFalse(cWP.scheduleBtnIsClickable(), "Schedule button should be disabled for invalid email.");
            test.log(Status.PASS, "Schedule button correctly disabled for invalid email");
        } catch (AssertionError e) {
            String failPath = ScreenshotUtils.captureScreenshot(driver, "verifyInvalidEmailValidation_fail");
            test.addScreenCaptureFromPath(failPath);
            test.log(Status.FAIL, "Button was enabled with invalid email: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 3)
    public void verifyInvalidPhoneValidation() {
        // Create extent report entry for this test
        test = extent.createTest("verifyInvalidPhoneValidation");
        test.log(Status.INFO, "Entering a short phone number to check button stays disabled");

        // Enter a too-short phone number and a valid email
        cWP.inputContactNumber("123");
        cWP.inputEmail("dev@gmail.com");

        // Take a screenshot showing the short phone number in the form
        String path = ScreenshotUtils.captureScreenshot(driver, "verifyInvalidPhoneValidation_form");
        test.addScreenCaptureFromPath(path);
        test.log(Status.INFO, "Short phone entered, checking schedule button state");

        // Button should still be disabled because the phone is too short
        try {
            Assert.assertFalse(cWP.scheduleBtnIsClickable(), "Schedule button should be disabled for invalid phone number.");
            test.log(Status.PASS, "Schedule button correctly disabled for short phone number");
        } catch (AssertionError e) {
            String failPath = ScreenshotUtils.captureScreenshot(driver, "verifyInvalidPhoneValidation_fail");
            test.addScreenCaptureFromPath(failPath);
            test.log(Status.FAIL, "Button was enabled with invalid phone: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 4)
    public void verifyFormSubmissionReady() throws InterruptedException {
        // Create extent report entry for this test
        test = extent.createTest("verifyFormSubmissionReady");
        test.log(Status.INFO, "Filling form with all valid data to check button becomes enabled");

        // Fill all fields with correct valid values this time
        cWP.inputName("Dev User");
        cWP.inputOrgName("Cognizant");
        cWP.inputContactNumber("9876543210");
        cWP.inputEmail("dev@gmail.com");
        cWP.selectOrgSize("501-1000");
        cWP.selectInterest("Taking a demo");

        // Wait briefly for the UI to react to all the inputs
        wait.explicitWait();

        // Take a screenshot showing the fully filled valid form
        String path = ScreenshotUtils.captureScreenshot(driver, "verifyFormSubmissionReady_form");
        test.addScreenCaptureFromPath(path);
        test.log(Status.INFO, "All valid data entered, checking schedule button state");

        // Button should now be enabled since all data is valid
        try {
            Assert.assertTrue(cWP.scheduleBtnIsClickable(), "Schedule button should be enabled when all fields are valid.");
            test.log(Status.PASS, "Schedule button correctly enabled with all valid data");
        } catch (AssertionError e) {
            String failPath = ScreenshotUtils.captureScreenshot(driver, "verifyFormSubmissionReady_fail");
            test.addScreenCaptureFromPath(failPath);
            test.log(Status.FAIL, "Button was still disabled with valid data: " + e.getMessage());
            throw e;
        }
    }
}
