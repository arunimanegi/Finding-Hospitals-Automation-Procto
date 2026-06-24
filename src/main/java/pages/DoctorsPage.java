package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utility.WaitUtils;

public class DoctorsPage {
    WebDriver driver;
    WaitUtils wait;

    // Locators for search inputs
    By citySearchTab = By.xpath("//input[@placeholder='Search location']");
    By selectEntireBangalore = By.xpath("//div[contains(text(),'Bangalore')]");
    By doctorSearchTab = By.xpath("//input[@placeholder='Search doctors, clinics, hospitals, etc.']");
    By doctorSuggestion = By.xpath("//div[normalize-space()='Doctor']");
    By stringData = By.cssSelector(".u-xx-large-font.u-bold");

    // Locators for doctor card details
    By doctorName = By.cssSelector(".u-color--primary.uv2-spacer--xs-bottom");
    By doctorExperience = By.xpath("(//div[@data-qa-id='doctor_experience'])");
    By doctorFee = By.xpath("(//span[@data-qa-id='consultation_fee'])");
    By doctorSpeciality = By.xpath("//div[@class='u-grey_3-text']/div[@class='u-d-flex']/span");

    // Locators for filters
    By resetFilter = By.xpath("//button[@data-qa-id='Reset_Filters']/span");

    By patientStories = By.xpath("//span[@data-qa-id='doctor_review_count_selected']//i[@class='u-transition--transform u-d-inlineblock icon-ic_dropdown u-f-right']");
    By patientStories50 = By.xpath("//li[@aria-label='50+ Patient Stories']");

    By doctorExp = By.xpath("//span[@data-qa-id='years_of_experience_selected']//i[@class='u-transition--transform u-d-inlineblock icon-ic_dropdown u-f-right']");
    By doctorExp10 = By.xpath("//li[@aria-label='10+ Years of experience']");

    By allFilter = By.xpath("//i[@class='u-transition--transform u-d-inlineblock icon-ic_dropdown']");
    By fee = By.xpath("//label[@for='Fees0']");

    By sortBy = By.xpath("//span[contains(@class,'c-sort-dropdown__selected c-dropdown__selected')]");
    By sortByValue = By.xpath("//span[normalize-space()='Number of patient stories - High to low']");

    // Constructor accepts WaitUtils from BaseTest just like HospitalPage does
    public DoctorsPage(WebDriver driver, WaitUtils wait) {
        this.driver = driver;
        this.wait = wait;
    }

    // Search Bangalore in the city field and pick it
    public void selectLocation() {
        WebElement city = wait.waitForClickable(citySearchTab);
        city.clear();
        city.sendKeys("Bangalore");
        wait.waitForClickable(selectEntireBangalore).click();
    }

    // Type Doctor in search box and pick the suggestion
    public void selectDoctor() {
        WebElement hospital = wait.waitForClickable(doctorSearchTab);
        hospital.sendKeys("Doctor");
        wait.waitForClickable(doctorSuggestion).click();
    }

    // Parse the result heading to get count or city at a given word index
    public String getDataForVerify(int index) {
        WebElement data1 = wait.waitForVisibility(stringData);
        String data = data1.getText();
        String stringArr[] = data.split(" ");
        if (Integer.parseInt(stringArr[0]) > 999) {
            String[] num = stringArr[0].split(",");
            stringArr[0] = num[0] + num[1];
        }
        return stringArr[index];
    }

    // Collect all doctor names from the result cards and return them
    public ArrayList<String> getDoctorsNames() {
        List<WebElement> doctorElements = driver.findElements(doctorName);
        ArrayList<String> doctorNames = new ArrayList<>();
        for (WebElement doctor : doctorElements) {
            doctorNames.add(doctor.getText());
        }
//        System.out.println(doctorNames);
//        System.out.println("Size : " + doctorNames.size());
        return doctorNames;
    }

    // Collect all experience values and return them
    public ArrayList<String> getDoctorsExperience() {
        List<WebElement> doctorElements = driver.findElements(doctorExperience);
        ArrayList<String> doctorExperienceList = new ArrayList<>();
        for (WebElement doctor : doctorElements) {
            doctorExperienceList.add(doctor.getText());
        }
//        System.out.println(doctorExperienceList);
//        System.out.println("Size : " + doctorExperienceList.size());
        return doctorExperienceList;
    }

    // Collect all consultation fees and return them
    public ArrayList<String> getDocotrConsultationFee() {
        List<WebElement> hospitalElements = driver.findElements(doctorFee);
        ArrayList<String> doctorsFee = new ArrayList<>();
        for (WebElement doctor : hospitalElements) {
            doctorsFee.add(doctor.getText());
        }
//        System.out.println(doctorsFee);
//        System.out.println("Size : " + doctorsFee.size());
        return doctorsFee;
    }

    // Collect all speciality labels and return them
    public ArrayList<String> getDocortsSpeciality() {
        List<WebElement> hospitalElements = driver.findElements(doctorSpeciality);
        ArrayList<String> doctorsSpeciality = new ArrayList<>();
        for (WebElement doctor : hospitalElements) {
            doctorsSpeciality.add(doctor.getText());
        }
//        System.out.println(doctorsSpeciality);
//        System.out.println("Size : " + doctorsSpeciality.size());
        return doctorsSpeciality;
    }

    // Click the reset button to clear all active filters
    public void resetAllFilterFirst() {
        WebElement reset = wait.waitForClickable(resetFilter);
        reset.click();
    }

    // Open patient stories dropdown and pick 50+ option
    public void applyPatientStoriesFilter() {
        WebElement patientStory = wait.waitForClickable(patientStories);
        patientStory.click();
        WebElement patientStory50 = wait.waitForClickable(patientStories50);
        patientStory50.click();
    }

    // Open experience dropdown and pick 10+ years option
    public void applyDoctorExperienceFilter() {
        WebElement dExperience = wait.waitForClickable(doctorExp);
        dExperience.click();
        WebElement dExperience10 = wait.waitForClickable(doctorExp10);
        dExperience10.click();
    }

    // Open fee filter and pick the first fee range option
    public void applyDoctorsFeeFilter() {
        WebElement allFil = wait.waitForClickable(allFilter);
        allFil.click();
        WebElement feesRange = wait.waitForClickable(fee);
        feesRange.click();
    }

    // Open sort dropdown and pick high to low by patient stories
    public void applySortByFilter() {
        WebElement sort = wait.waitForClickable(sortBy);
        sort.click();
        WebElement sortAccordingValue = wait.waitForClickable(sortByValue);
        sortAccordingValue.click();
    }
}
