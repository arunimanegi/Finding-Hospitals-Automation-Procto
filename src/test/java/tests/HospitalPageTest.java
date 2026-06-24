package tests;

import java.util.ArrayList;
import java.util.Arrays;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import base.BaseTest;
import pages.HospitalPage;
import utility.ScreenshotUtils;

public class HospitalPageTest extends BaseTest {

    HospitalPage hospitalPage;

    @Test(priority = 0, description = "Verify navigation to the Hospitals listing page.")
    public void verifyHospitalPage() {
        // Create extent report entry for this test
        test = extent.createTest("verifyHospitalPage");
        test.log(Status.INFO, "Selecting Bangalore and Hospital category");

        // Localized SoftAssert so failures here don't leak into other tests
        SoftAssert sa = new SoftAssert();
        hospitalPage = new HospitalPage(driver, wait, excel);

        hospitalPage.selectLocation();
        hospitalPage.selectHospitals();

        // Take a screenshot once the results page loads
        String path = ScreenshotUtils.captureScreenshot(driver, "verifyHospitalPage_results");
        test.addScreenCaptureFromPath(path);
        test.log(Status.INFO, "Hospital search results page loaded");

        String actual = hospitalPage.getDataForVerify(1);
        String expected = "Hospitals";

        // Check the category label in the heading
        try {
            sa.assertTrue(actual.equalsIgnoreCase(expected), "Page category mismatch: Expected 'Hospitals' but found: " + actual);
            sa.assertAll();
            test.log(Status.PASS, "Hospital category heading verified: " + actual);
        } catch (AssertionError e) {
            String failPath = ScreenshotUtils.captureScreenshot(driver, "verifyHospitalPage_fail");
            test.addScreenCaptureFromPath(failPath);
            test.log(Status.FAIL, "Hospital category heading mismatch: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 1, description = "Verify that the hospital search returns at least one result.")
    public void verifyHospitalsCount() {
        // Create extent report entry for this test
        test = extent.createTest("verifyHospitalsCount");
        test.log(Status.INFO, "Checking hospital result count is greater than zero");

        SoftAssert sa = new SoftAssert();
        String count = hospitalPage.getDataForVerify(0);

        // Parse the count number and check it is not zero
        try {
            int countInt = Integer.parseInt(count.replaceAll("[^0-9]", ""));
            sa.assertTrue(countInt > 0, "Hospital count should be greater than zero, but found: " + countInt);
            sa.assertAll();
            test.log(Status.PASS, "Hospital count verified: " + countInt);
        } catch (NumberFormatException e) {
            // Screenshot if the count string couldn't be parsed
            String path = ScreenshotUtils.captureScreenshot(driver, "verifyHospitalsCount_parseFail");
            test.addScreenCaptureFromPath(path);
            test.log(Status.FAIL, "Failed to parse hospital count from string: " + count);
            sa.fail("Failed to parse hospital count from string: " + count);
            sa.assertAll();
        } catch (AssertionError e) {
            String path = ScreenshotUtils.captureScreenshot(driver, "verifyHospitalsCount_fail");
            test.addScreenCaptureFromPath(path);
            test.log(Status.FAIL, "Hospital count assertion failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 2, description = "Verify that search results are filtered by the correct location.")
    public void verifyHospitalLocation() {
        // Create extent report entry for this test
        test = extent.createTest("verifyHospitalLocation");
        test.log(Status.INFO, "Checking search results are showing for Bangalore");

        SoftAssert sa = new SoftAssert();
        String actual = hospitalPage.getDataForVerify(3);
        String expected = "Bangalore";

        // Confirm Bangalore appears in the heading
        try {
            sa.assertEquals(actual, expected, "Location mismatch: Results are not showing for " + expected);
            sa.assertAll();
            test.log(Status.PASS, "Location verified: " + actual);
        } catch (AssertionError e) {
            String path = ScreenshotUtils.captureScreenshot(driver, "verifyHospitalLocation_fail");
            test.addScreenCaptureFromPath(path);
            test.log(Status.FAIL, "Location mismatch: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 3, description = "Extract and log detailed data for the listed hospitals.")
    public void getHospitalsData() {
        // Create extent report entry for this test
        test = extent.createTest("getHospitalsData");
        test.log(Status.INFO, "Extracting hospital names, timing, fees and doctor count");

        // Collect all visible hospital data from the cards
        ArrayList<String> hospitalNames = hospitalPage.getHospitalNames();
        ArrayList<String> time = hospitalPage.getHospitalAvailableTime();
        ArrayList<String> doctors = hospitalPage.getHospitalAvailableDoctor();
        ArrayList<String> cFees = hospitalPage.getHospitalAvailableFees();
        driver.navigate().back();

        // Build the header row and data columns for the Excel file
        ArrayList<String> header = new ArrayList<>(
                Arrays.asList("Hospital Names", "Timing", "Fees", "Doctor Count"));
        ArrayList<ArrayList<String>> d = new ArrayList<ArrayList<String>>();
        d.add(hospitalNames);
        d.add(time);
        d.add(cFees);
        d.add(doctors);

        // Write the collected data into an Excel file
        excel.writeToExcel(objReader.getExcelPath()+"HospitalData.xlsx", d, header);
        test.log(Status.PASS, "Hospital data written to HospitalData.xlsx successfully");
        wait.explicitWait();
        excel.readFromExcel(objReader.getExcelPath()+"HospitalData.xlsx");
        wait.explicitWait();
        System.out.println();
    }
}
