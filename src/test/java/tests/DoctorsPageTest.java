package tests;

import java.util.ArrayList;
import java.util.Arrays;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import base.BaseTest;
import pages.DoctorsPage;
import utility.ScreenshotUtils;

public class DoctorsPageTest extends BaseTest {

    DoctorsPage doctorsPage;

    @Test(priority = 0, description = "Verify navigation to the Doctors page after selecting location and category.")
    public void verifyDoctorPage() {
        // Create extent report entry for this test
        test = extent.createTest("verifyDoctorPage");
        test.log(Status.INFO, "Selecting Bangalore and Doctor category");

        // Localized SoftAssert for clean results
        SoftAssert sa = new SoftAssert();

        // Pass the shared wait instance from BaseTest into the page object
        doctorsPage = new DoctorsPage(driver, wait);

        doctorsPage.selectLocation();
        doctorsPage.selectDoctor();

        // Take a screenshot once the doctor results page loads
        String path = ScreenshotUtils.captureScreenshot(driver, "verifyDoctorPage_results");
        test.addScreenCaptureFromPath(path);
        test.log(Status.INFO, "Doctor search results page loaded");

        String actual = doctorsPage.getDataForVerify(1);
        String expected = "doctors";

        // Check the results heading contains the word doctors
        try {
            sa.assertTrue(actual.toLowerCase().contains(expected), "Page category mismatch: Expected 'doctors' in URL/Text.");
            sa.assertAll();
            test.log(Status.PASS, "Doctor page heading verified: " + actual);
        } catch (AssertionError e) {
            String failPath = ScreenshotUtils.captureScreenshot(driver, "verifyDoctorPage_fail");
            test.addScreenCaptureFromPath(failPath);
            test.log(Status.FAIL, "Doctor page heading mismatch: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 1, description = "Verify that at least one doctor is listed in the search results.")
    public void verifyHospitalsCount() {
        // Create extent report entry for this test
        test = extent.createTest("verifyDoctorsCount");
        test.log(Status.INFO, "Checking doctor result count is greater than zero");

        SoftAssert sa = new SoftAssert();
        String count = doctorsPage.getDataForVerify(0);

        // Parse out the number and confirm we got at least one result
        try {
            int countInt = Integer.parseInt(count.replaceAll("[^0-9]", ""));
            sa.assertTrue(countInt > 0, "Doctor count should be greater than zero.");
            sa.assertAll();
            test.log(Status.PASS, "Doctor count verified: " + countInt);
        } catch (NumberFormatException e) {
            // Screenshot if parsing goes wrong
            String path = ScreenshotUtils.captureScreenshot(driver, "verifyDoctorsCount_parseFail");
            test.addScreenCaptureFromPath(path);
            test.log(Status.FAIL, "Failed to parse doctor count: " + count);
            sa.fail("Failed to parse doctor count: " + count);
            sa.assertAll();
        } catch (AssertionError e) {
            String path = ScreenshotUtils.captureScreenshot(driver, "verifyDoctorsCount_fail");
            test.addScreenCaptureFromPath(path);
            test.log(Status.FAIL, "Doctor count assertion failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 2, description = "Verify that the search results match the selected location (Bangalore).")
    public void verifyDoctorsLocation() {
        // Create extent report entry for this test
        test = extent.createTest("verifyDoctorsLocation");
        test.log(Status.INFO, "Checking search results are showing for Bangalore");

        SoftAssert sa = new SoftAssert();
        String actual = doctorsPage.getDataForVerify(4);
        String expected = "Bangalore";

        // Confirm the results heading shows Bangalore
        try {
            sa.assertEquals(actual, expected, "Search results are showing for the wrong city.");
            sa.assertAll();
            test.log(Status.PASS, "Location verified: " + actual);
        } catch (AssertionError e) {
            String path = ScreenshotUtils.captureScreenshot(driver, "verifyDoctorsLocation_fail");
            test.addScreenCaptureFromPath(path);
            test.log(Status.FAIL, "Location mismatch: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 3, description = "Extract doctor details and save them to DoctorsData.xlsx.")
    public void getDoctorsData() {
    	// Collect each type of data from visible doctor cards
    	doctorsPage.getDoctorsNames();
        doctorsPage.getDoctorsExperience();
        doctorsPage.getDocotrConsultationFee();
        doctorsPage.getDocortsSpeciality();

        // Create extent report entry for this test
        test = extent.createTest("getDoctorsData");
        test.log(Status.INFO, "Collecting doctor names, experience, fees and speciality");

    }

    @Test(priority = 4, description = "Apply various filters (Experience, Fee, Stories) to refine results.")
    public void applyFilter() {
        // Create extent report entry for this test
        test = extent.createTest("applyFilter");
        test.log(Status.INFO, "Applying patient stories, experience, fee and sort filters");

        doctorsPage.applyPatientStoriesFilter();
        doctorsPage.applyDoctorExperienceFilter();
        doctorsPage.applyDoctorsFeeFilter();
        doctorsPage.applySortByFilter();

        // Take a screenshot after all filters are applied
        String path = ScreenshotUtils.captureScreenshot(driver, "applyFilter_done");
        test.addScreenCaptureFromPath(path);
        test.log(Status.PASS, "All filters applied successfully");
    }

    @Test(priority = 5, description = "Extract and log doctor details after filters have been applied.")
    public void getDataAfterFilter() {
        // Create extent report entry for this test
        test = extent.createTest("getDataAfterFilter");
        test.log(Status.INFO, "Collecting filtered doctor data after all filters are active");

        
        
     
     // Re-collect data now that the filters have narrowed down the list
        ArrayList<String> doctorNames = doctorsPage.getDoctorsNames();
        ArrayList<String> experience = doctorsPage.getDoctorsExperience();
        ArrayList<String> fees = doctorsPage.getDocotrConsultationFee();
        ArrayList<String> speciality = doctorsPage.getDocortsSpeciality();

        // Build header and data list for Excel just like HospitalPageTest does
        ArrayList<String> header = new ArrayList<>(
                Arrays.asList("Doctor Names", "Experience", "Consultation Fee", "Speciality"));
        ArrayList<ArrayList<String>> d = new ArrayList<ArrayList<String>>();
        d.add(doctorNames);
        d.add(experience);
        d.add(fees);
        d.add(speciality);

        // Write all collected data to the Excel file
        excel.writeToExcel(objReader.getExcelPath()+"DoctorData.xlsx", d, header);
        test.log(Status.PASS, "Doctor data written to DoctorsData.xlsx successfully");
        wait.explicitWait();
        excel.readFromExcel(objReader.getExcelPath()+"DoctorData.xlsx");
        // Take a screenshot of the filtered results
        String path = ScreenshotUtils.captureScreenshot(driver, "getDataAfterFilter_results");
        test.addScreenCaptureFromPath(path);
        test.log(Status.PASS, "Filtered doctor data collected successfully");

        driver.navigate().back();
        driver.navigate().back();
    }
}
