package seng426;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;
import java.util.*;

import seng426.Helper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class password_creation_test {

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
	 * Testing password creation with valid entries for all fields, logged in as registered user
	 */
	@Test
	public void Validtest() {
		Helper.logIn(driver, 3);
		driver.findElement(By.linkText("ACMEPass")).click();
		
		driver.findElement(By.cssSelector("button[ui-sref='acme-pass.new']")).click();
		
		WebElement site = driver.findElement(By.id("field_site"));
		WebElement log = driver.findElement(By.id("field_login"));
		WebElement password = driver.findElement(By.id("field_password"));
		
		site.sendKeys("uvic");
		log.sendKeys("zac");
		password.sendKeys("aPassword");
		driver.findElement(By.cssSelector("button[type='submit']")).click();
		
		//get table rows
		WebElement table = driver.findElement(By.className("table-responsive"));
		String tablePath = Helper.generateXPATH(table, "") + "/table";
		String rowPath = tablePath+"/tbody/tr";
		List<WebElement> tableRows = driver.findElements(By.xpath(rowPath));
		int lastEntryNum = getNumPasswordsOnPage(rowPath);
		//get entries from each row
		List<WebElement> lastEntryAttributes = tableRows.get(lastEntryNum-1).findElements(By.xpath("/td"));
		
		System.out.println("site is " + lastEntryAttributes.get(1) +"username is "+ lastEntryAttributes.get(2));
	}
	
	/*
	 * Tests the password creation functionality, logged in as registered user but missing the site field
	 * Passes if the submit button is not clickable/doesn't not allow user to submit
	 */
	@Test
	public void noSiteTest(){
		Helper.logIn(driver, 3);
		driver.findElement(By.linkText("ACMEPass")).click();
		
		driver.findElement(By.cssSelector("button[ui-sref='acme-pass.new']")).click();
		
		WebElement log = driver.findElement(By.id("field_login"));
		WebElement password = driver.findElement(By.id("field_password"));
		
		log.sendKeys("shouldn't");
		password.sendKeys("work");
		
		
		WebElement submitBtn = driver.findElement(By.cssSelector("button[type='submit']"));
		System.out.println(submitBtn.isEnabled());
		if(submitBtn.isEnabled()){
			assertTrue(false);
		} else{
			assertTrue(true);
		}
	}
	
	/*
	 * Tests the password creation functionality, logged in as registered user but missing the username field
	 * Passes if the submit button is not clickable/doesn't not allow user to submit
	 */
	@Test
	public void noNameTest(){
		Helper.logIn(driver, 3);
		driver.findElement(By.linkText("ACMEPass")).click();
		
		driver.findElement(By.cssSelector("button[ui-sref='acme-pass.new']")).click();
		
		WebElement site = driver.findElement(By.id("field_site"));
		WebElement password = driver.findElement(By.id("field_password"));
		
		site.sendKeys("shouldn't");
		password.sendKeys("work");
		
		WebElement submitBtn = driver.findElement(By.cssSelector("button[type='submit']"));
		System.out.println(submitBtn.isEnabled());
		if(submitBtn.isEnabled()){
			assertTrue(false);
		} else{
			assertTrue(true);
		}
	}
	
	/*
	 * Tests the password creation functionality, logged in as registered user but missing the password field
	 * Passes if the submit button is not clickable/doesn't not allow user to submit
	 */
	@Test
	public void noPasswordTest(){
		Helper.logIn(driver, 3);
		driver.findElement(By.linkText("ACMEPass")).click();
		
		driver.findElement(By.cssSelector("button[ui-sref='acme-pass.new']")).click();
		
		WebElement site = driver.findElement(By.id("field_site"));
		WebElement login = driver.findElement(By.id("field_login"));
		
		site.sendKeys("shouldn't");
		login.sendKeys("work");
		
		WebElement submitBtn = driver.findElement(By.cssSelector("button[type='submit']"));
		System.out.println(submitBtn.isEnabled());
		if(submitBtn.isEnabled()){
			assertTrue(false);
		} else{
			assertTrue(true);
		}
	}
	
	private int getNumPasswordsOnPage(String tablePath)
	{
			int i = 1;
			try
			{
				//10000 is a safety constant (make sure loop isn't infinite)
				for(;i < 10000;i++)
				{
					String path = tablePath + "/tbody/tr[" + Integer.toString(i) + "]";
					driver.findElement(By.xpath(path));
				}
			}
			catch(NoSuchElementException e)
			{

			}
			return i - 1;
	}
}
