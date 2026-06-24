package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import utility.WaitUtils;

public class CorporateWellnessPage {
    WebDriver driver = null;
    WaitUtils wait;

    // All locators stored here as page object fields instead of scattered in methods
    By corporateMenuBtn = By.xpath("//*[@id=\"root\"]/div/div/div[1]/div[1]/div[2]/div/div[3]/div[1]/span/span[2]");
    By corporateLink = By.xpath("//*[@id=\"root\"]/div/div/div[1]/div[1]/div[2]/div/div[3]/div[1]/span/div/div[1]/a");
    By nameField = By.xpath("//*[@id='name' or @name='name']");
    By orgNameField = By.xpath("//*[@id='organizationName' or @name='organizationName']");
    By contactField = By.xpath("//*[@id='contactNumber' or @name='contactNumber']");
    By emailField = By.xpath("//*[@id='officialEmailId' or @name='officialEmailId']");
    By orgSizeField = By.xpath("//*[@id='organizationSize' or @name='organizationSize']");
    By interestField = By.xpath("//*[@id='interestedIn' or @name='interestedIn']");
    By scheduleButton = By.xpath("//*[@id='app']/div/div/header[2]/div[2]/div/form/button");

    // Constructor accepts WaitUtils from BaseTest just like other page classes
    public CorporateWellnessPage(WebDriver driver, WaitUtils wait) {
        this.driver = driver;
        this.wait = wait;
    }

    // Click the corporate menu item and return the current URL
    public String urlCorporateWellnessPage() {
        driver.findElement(corporateMenuBtn).click();
        driver.findElement(corporateLink).click();
        String acUrl = driver.getCurrentUrl();
        // Create Actions instance
        Actions actions = new Actions(driver);

        // Scroll down by 200 pixels vertically
        actions.scrollByAmount(0, 100).perform();
        return acUrl;
    }

    // Wait for the name field to appear then fill it in
    public void inputName(String name) throws InterruptedException {
        System.out.println(driver.getCurrentUrl());
        Thread.sleep(5000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement nameEl = wait.until(ExpectedConditions.visibilityOfElementLocated(nameField));
        nameEl.clear();
        nameEl.sendKeys(name);
    }

    // Clear the org name field and type the new value
    public void inputOrgName(String org) {
        WebElement organization = driver.findElement(orgNameField);
        organization.clear();
        organization.sendKeys(org);
    }

    // Clear the contact number field and enter the phone number
    public void inputContactNumber(String contactInfo) {
        WebElement contact = driver.findElement(contactField);
        contact.clear();
        contact.sendKeys(contactInfo);
    }

    // Clear the email field and type the email address
    public void inputEmail(String email) {
        WebElement e_mail = driver.findElement(emailField);
        e_mail.clear();
        e_mail.sendKeys(email);
    }

    // Pick org size from the dropdown by visible text
    public void selectOrgSize(String size) {
        WebElement orgSize = driver.findElement(orgSizeField);
        Select select = new Select(orgSize);
        select.selectByVisibleText(size);
    }

    // Pick the interest option from the dropdown by visible text
    public void selectInterest(String interest) {
        WebElement interestSelect = driver.findElement(interestField);
        Select select = new Select(interestSelect);
        select.selectByVisibleText(interest);
    }

    // Return true if the schedule button is enabled, false if disabled
    public boolean scheduleBtnIsClickable() {
        WebElement scheduleBtn = driver.findElement(scheduleButton);
        return scheduleBtn.isEnabled();
    }
}
