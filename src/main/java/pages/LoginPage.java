package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import configurations.ObjectReader;
import utility.WaitUtils;

public class LoginPage {
	final String loginTextId="loginLink";
	final String userId="username";
	final String passId="password";
	final String submitId="login";
	final String profileXpath="//*[@id='root']/div/div/div[1]/div[1]/div[1]/div[1]/div[3]/span/span[1]";
	WebDriver driver=null;
	WaitUtils wait;
	ObjectReader objReader;
	public LoginPage(WebDriver driver,ObjectReader objReader, WaitUtils wait){
		this.driver=driver;
		this.objReader=objReader;
		this.wait=wait;
	}
	
	
	public String openLoginPage() {
		String acText=wait.waitForVisibility(By.id(loginTextId)).getText();
		return acText;
	}
	
	public void inputUserName(String user) {
		WebElement userName=driver.findElement(By.id(userId));
		userName.clear();
		userName.sendKeys(user);
	}
	
	public void inputPassword(String pass) {
		WebElement userName=driver.findElement(By.id(passId));
		userName.sendKeys(pass);
	}
	
	public void clickSubmit() {
		WebElement submit=wait.waitForClickable(By.id(submitId));
		submit.click();
	}
	
	public String verifySuccessfulLogin(){
		WebElement profile=driver.findElement(By.xpath(profileXpath));
		return profile.getAttribute("textContent");
	}
}
