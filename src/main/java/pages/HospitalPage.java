package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utility.ExcelUtils;
import utility.WaitUtils;

public class HospitalPage {

    WebDriver driver;
    WaitUtils wait;
    ExcelUtils excel;
    By citySearchTab = By.xpath("//input[@placeholder='Search location']");
    By selectEntireBangalore = By.xpath("//div[contains(text(),'Bangalore')]");
    By hospitalSearchTab = By.xpath("//input[@placeholder='Search doctors, clinics, hospitals, etc.']");
    By hospitalSuggestion = By.xpath("//div[normalize-space()='Hospital']");
//    By stringData = By.xpath("//h1[@class=\"title\"]");
    By stringData = By.className("title");
    
    
    By hospitalName = By.className("line-1");
    By availableTime = By.className("pd-right-2px-text-green");
    By availableDoctors = By.xpath("//span[@class='u-bold']/span[3]");
    By fees = By.xpath("//p[@class='line-3']/span[1]");
    
    ArrayList<String> time;
    ArrayList<String> hospitalNames;
    ArrayList<String> cFees;
    ArrayList<String> doctors;
    List<WebElement> hospitalElements;
    public HospitalPage(WebDriver driver,WaitUtils wait,ExcelUtils excel) {
        this.driver = driver;
        this.wait = wait;
        this.excel=excel;
    }

    public void selectLocation() {
        WebElement city = wait.waitForClickable(citySearchTab);
        city.clear();
        city.sendKeys("Bangalore");
        wait.waitForClickable(selectEntireBangalore).click();
    }

    
    public void selectHospitals() {
        WebElement hospital = wait.waitForClickable(hospitalSearchTab);
        hospital.sendKeys("Hospital");
        wait.waitForClickable(hospitalSuggestion).click();
    }
    
    public String getDataForVerify(int index){
    	WebElement data1 = wait.waitForVisibility(stringData);
    	
    	String data = data1.getText();
    	String stringArr []= data.split(" ");
    	String[] num = stringArr[0].split(",");
    	stringArr[0] = num[0]+num[1];
    	return stringArr[index];
    }
    

    public ArrayList<String> getHospitalNames() {
        List<WebElement> hospitalElements = driver.findElements(hospitalName);
        hospitalNames = new ArrayList<>();
        for (WebElement hospital : hospitalElements) {
            hospitalNames.add(hospital.getText());
        }
//        System.out.println(hospitalNames);
//        System.out.println("Size : "+hospitalNames.size());
        return hospitalNames;
    }
    
    public ArrayList<String> getHospitalAvailableTime() {
        hospitalElements = driver.findElements(availableTime);
        time= new ArrayList<>();
        for (WebElement hospital : hospitalElements) {
            time.add(hospital.getText());
        }
//        System.out.println(time);
//        System.out.println("Size : "+time.size());
        return time;
    }
    
    
    public ArrayList<String> getHospitalAvailableDoctor() {
        List<WebElement> hospitalElements = driver.findElements(availableDoctors);
        doctors = new ArrayList<>();
        for (WebElement hospital : hospitalElements) {
            doctors.add(hospital.getText());
        }
//        System.out.println(doctors);
//        System.out.println("Size : "+doctors.size());
        return doctors;
    }
    
    
    public ArrayList<String> getHospitalAvailableFees() {
        hospitalElements = driver.findElements(fees);
        cFees = new ArrayList<>();
        for (WebElement hospital : hospitalElements) {
            cFees.add(hospital.getText());
        }
//        System.out.println(cFees);
//        System.out.println("Size : "+cFees.size());
        return cFees;
        
    }
    
    
}