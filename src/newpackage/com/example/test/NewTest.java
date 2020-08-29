package newpackage.com.example.test;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

//import static org.junit.Assert.*;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.remote.RemoteWebDriver;
//import org.openqa.selenium.remote.DesiredCapabilities;
//import org.openqa.selenium.Dimension;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.interactions.Actions;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.Alert;
//import org.openqa.selenium.Keys;

import testCases.TestCase1;

public class NewTest {
	private WebDriver driver;

	JavascriptExecutor js;
	private String url = "https://testsheepnz.github.io/BasicCalculator.html";

	@Before
	public void setup() {
		js = (JavascriptExecutor) driver;
//	  	System.setProperty("webdriver.gecko.driver","C:\\Users\\Thong\\Downloads\\geckodriver.exe");
//		driver = new FirefoxDriver();
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Thong\\Downloads\\chromedriver.exe");
		driver = new ChromeDriver();

	}

	@Test
	public void ReadAndTest() {
		TestCase1 ts1 = new TestCase1();
		String data = "C:\\Users\\Thong\\Downloads\\TestData.xlsx";
		ts1.execute(url, "Prototype", driver, data);
	}

	@After
	public void afterTest() {
		// close Fire fox
		driver.close();

	}
}
