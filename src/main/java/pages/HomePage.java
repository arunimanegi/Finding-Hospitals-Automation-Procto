package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import configurations.ObjectReader;
import utility.WaitUtils;

public class HomePage {
	WebDriver driver=null;
	ObjectReader objReader;
	WaitUtils wait;
	public HomePage(WebDriver driver,ObjectReader objReader, WaitUtils wait){
		this.driver=driver;
		this.objReader=objReader;
		this.wait=wait;
	}
	public boolean verifyHomePage() {
		String acTitle=driver.getTitle();
		String expTitle="Practo | Video Consultation with Doctors, Book Doctor Appointments, Order Medicine, Diagnostic Tests";
		return acTitle.equals(expTitle);
	}
	
	public void clickLoginButton() {
		wait.waitForClickable(By.name("Practo login")).click();
	}
}
