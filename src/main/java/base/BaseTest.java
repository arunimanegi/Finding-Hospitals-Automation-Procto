package base;
import java.time.Duration;
import java.util.Scanner;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import configurations.BrowserFactory;
import configurations.ObjectReader;
import utility.ExcelUtils;
import utility.ScreenshotUtils;
import utility.WaitUtils;

//import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

    public static WebDriver driver;
    public static WaitUtils wait;
    public static ScreenshotUtils screenShot;
    public static ExcelUtils excel;
    public static ExtentReports extent;
    public static ExtentTest test;
    public static ObjectReader objReader;
    @BeforeSuite
    public void setUp() {
        // Automatically manages the driver executable version
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the preferred browser(chrome/edge)");
        String browser=sc.nextLine();
        switch (browser.toLowerCase()){
            case "edge":
                driver=BrowserFactory.getEdgeDriver();
                break;
            case "chrome":
                driver=BrowserFactory.getChromeDriver();
                break;
            default:
                System.out.println("Enter valid browser name");
                return;
        }

        
        // Global implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
        objReader=new ObjectReader();
        driver.navigate().to(objReader.getURL());
        wait=new WaitUtils(driver,5);
        screenShot=new ScreenshotUtils();
        excel=new ExcelUtils();
        extent=new ExtentReports();
        
        ExtentSparkReporter spark = new ExtentSparkReporter(objReader.getReportsPath());
		extent.attachReporter(spark);
    }

    @AfterSuite
    public void tearDown() {
        // Write the extent report HTML file to disk
        extent.flush();
        if (driver != null) {
            // quit() closes all windows and ends the session
            driver.quit();
        }
    }
}