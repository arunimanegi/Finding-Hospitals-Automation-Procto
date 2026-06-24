package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utility.WaitUtils;

public class DiagnosticsPage {
    WebDriver driver = null;
    WaitUtils wait;

    // Constructor now accepts WaitUtils from BaseTest like other page classes do
    public DiagnosticsPage(WebDriver driver, WaitUtils wait) {
        this.driver = driver;
        this.wait = wait;
    }

    // Click Lab Tests link, print city options, click Bangalore and return the city count
    public int getTopCityCount() {
        driver.findElement(By.linkText("Lab Tests")).click();
        WebElement city = wait.waitForVisibility(By.xpath("//div[@class='u-margint--standard o-f-color--primary'][normalize-space()='Bangalore']"));
        List<WebElement> cities = driver.findElements(By.xpath("//div[@class='u-margint--standard o-f-color--primary']"));
        for(WebElement e:cities) {
        	System.out.println(e.getText());
        }
        wait.explicitWait();
        city.click();
        return cities.size();
    }

    // Get the city name currently selected in the location input
    public String getSelectedCity() {
        WebElement selectedCity = wait.waitForVisibility(By.id("locationInput"));
        return selectedCity.getAttribute("value");
    }

    // Read and return the main heading on the diagnostics page
    public String pageHeaderVerify() {
        WebElement heading = wait.waitForVisibility(By.xpath("//*[@id=\"root-app\"]/div/div/div[2]/div[1]/div/div[1]/h1"));
        return heading.getText();
    }

    // Type CBC Test in the search box and press Enter to search
    public void enterTestName() {
        WebElement searchField = driver.findElement(By.id("omniSearch"));
        searchField.sendKeys("CBC Test");
        wait.explicitWait();
        searchField.sendKeys(Keys.ENTER);
    }

    // Return the name of the first test shown in results
    public String verifyTestInfo() {
        WebElement name = wait.waitForVisibility(By.xpath("//*[@id=\"root-app\"]/div/div/div[2]/div/div[2]/div/div/div/div[2]/div/div/div[2]/div/div[1]/span/span[2]"));
        System.out.println(name.getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"root-app\"]/div/div/div[2]/div/div[2]/div/div/div/div[2]/div/div/div[2]/div/div[2]/div[1]/div/div[2]/span")).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"root-app\"]/div/div/div[2]/div/div[2]/div/div/div/div[2]/div/div/div[2]/div/div[2]/div[2]/div[1]/div")).getText());
        return name.getText();
    }
}
