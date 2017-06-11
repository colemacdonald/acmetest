package seng426;
import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.By;

public class audit_log_tests {
	
	private WebDriver driver;
	
	private String baseUrl = "http://localhost:8080";

	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");
		driver = new FirefoxDriver();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);
		
		driver.get(baseUrl);
	}

	@After
	public void tearDown() throws Exception {
		//driver.quit();
	}

	/*
	 * Tests accessing the audit page as an Admin, the only user type with access to the page
	 * First checks to determine if the option is available in the top menu bar
	 * Then checks that when the audits option is selected, the audits page is loaded
	 */
	@Test
	public void testAuditPageAdmin() {
		logIn(driver, 0);
		
		//Check if Admin dropdown is present
		driver.findElement(By.linkText("Administration")).click();
		boolean dropdownPresent = isElementPresent(By.cssSelector("a[ui-sref='audits']"));
		if(!dropdownPresent){
			assertTrue(false);
		}
		
		//select audits
		driver.findElement(By.linkText("Audits")).click();
		boolean inputGroupPresent = isElementPresent(By.className("input-group"));
		if(!inputGroupPresent){
			assertTrue(false);
		}
		assertTrue(true);
	}
	
	/*
	 * Tests the filtering functionality of Audits page
	 */
	@Test
	public void testFilter(){
		logIn(driver, 0);
		driver.findElement(By.linkText("Administration")).click();
		driver.findElement(By.linkText("Audits")).click();
		
		//ensures that test is on the Audit page and the filter is present
		boolean inputGroupPresent = isElementPresent(By.className("input-group"));
		if(!inputGroupPresent){
			assertTrue(false);
		}
		
		//sets filter parameters to 2017-05-30, where there will always be only 1 log
		WebElement from = driver.findElement(By.name("start"));
		WebElement to = driver.findElement(By.name("end"));
		from.clear();
		from.sendKeys("2017-05-30");
		to.clear();
		to.sendKeys("2017-05-30");
		
		//tests for there being just one entry
		//Try counting entries like in class
		
	}
	
	private boolean isElementPresent(By by){
		try{
			driver.findElement(by);
		} catch (NoSuchElementException e){
			return false;
		}
		return true;
	}

	/*
	 * sets up WebDriver to log in as
	 * admin if user is set to 0
	 * manager if user is set to 1
	 * employee if user is set to 2
	 * registered user if user is set to 3
	 * Not elegant but it works
	 */
	private void logIn(WebDriver driver, int user){
		driver.findElement(By.linkText("Sign in")).click();
		
		WebElement username = driver.findElement(By.id("username"));
		WebElement password = driver.findElement(By.id("password"));
		
		if(user==0){
			//sign in as admin
			username.sendKeys("admin@acme.com");
			password.sendKeys("K-10ficile");
			WebElement modal = driver.findElement(By.className("modal-content"));
			modal.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div[2]/form/button")).click();
		} else if(user==1){
			//sign in as manager
			username.sendKeys("jo.thomas@acme.com");
			password.sendKeys("mustang");
			WebElement modal = driver.findElement(By.className("modal-content"));
			modal.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div[2]/form/button")).click();
		} else if(user==2){
			//sign in as employee
			username.sendKeys("frank.paul@acme.com");
			password.sendKeys("starwars");
			WebElement modal = driver.findElement(By.className("modal-content"));
			modal.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div[2]/form/button")).click();
		} else if(user==3){
			//sign in as registered user
			//must change log in credentials to match your own created user
			username.sendKeys("zbroitman@gmail");
			password.sendKeys("acme");
			WebElement modal = driver.findElement(By.className("modal-content"));
			modal.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div[2]/form/button")).click();
		}
		
	}
}


