package seng426;
import static org.junit.Assert.*;


import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.By;

import seng426.Helper;

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
		driver.quit();
	}

	/*
	 * Tests accessing the audit page as an Admin, the only user type with access to the page
	 * First checks to determine if the option is available in the top menu bar
	 * Then checks that when the audits option is selected, the audits page is loaded
	 */
	@Test
	public void testAuditPageAdmin() {
		Helper.logIn(driver, 0);
		
		//Check if Admin dropdown is present
		driver.findElement(By.linkText("Administration")).sendKeys(Keys.ENTER);
		boolean dropdownPresent = isElementPresent(By.cssSelector("a[ui-sref='audits']"));
		if(!dropdownPresent){
			assertTrue(false);
		}
		
		//select audits
		driver.findElement(By.linkText("Audits")).sendKeys(Keys.ENTER);
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
		Helper.logIn(driver, 0);
		driver.findElement(By.linkText("Administration")).sendKeys(Keys.ENTER);
		driver.findElement(By.linkText("Audits")).sendKeys(Keys.ENTER);
		
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
}


