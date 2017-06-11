package seng426;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;
import java.util.*;

import seng426.Helper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
//import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.Keys;


public class password_creation_test {

	private WebDriver driver;
	
	private String baseUrl = "http://localhost:8080";
	
	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.gecko.driver", "/home/dylang/bin/geckodriver");
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
	 * Testing password creation with valid entries for all fields, logged in as registered user
	 */
	@Test
	public void Validtest() {
		//logIn(driver, 3);
		Helper.logIn(driver, 0);
		//driver.findElement(By.linkText("ACMEPass")).click();
		driver.get(baseUrl + "/#/acme-pass");
		WebElement table = driver.findElement(By.className("table-responsive"));
		String tablePath = Helper.generateXPATH(table, "") + "/table";
		driver.findElement(By.cssSelector("button[ui-sref='acme-pass.new']")).sendKeys(Keys.ENTER);
		
		WebElement site = driver.findElement(By.id("field_site"));
		WebElement log = driver.findElement(By.id("field_login"));
		WebElement password = driver.findElement(By.id("field_password"));
		
		site.sendKeys("uvic");
		log.sendKeys("zac");
		password.sendKeys("aPassword");
		driver.findElement(By.cssSelector("button[type='submit']")).sendKeys(Keys.ENTER);
		
		//get table rows
		table = driver.findElement(By.className("table-responsive"));

		
		//tablePath = Helper.generateXPATH(table, "") + "/table";
		
		String rowPath = tablePath+"/tbody/tr";
		List<WebElement> tableRows = driver.findElements(By.xpath(rowPath));
		int lastEntryNum = getNumPasswordsOnPage(tablePath);
		lastEntryNum --;
		
		//get entries from each row
		WebElement tmp = tableRows.get(lastEntryNum);
		List<WebElement> tds = driver.findElements(By.xpath(Helper.generateXPATH(tmp, "") + "/td"));
		boolean siteMatch = "uvic".equals(tds.get(1).getText());
		boolean loginMatch = "zac".equals(tds.get(2).getText());
		
		if(siteMatch && loginMatch){
			assertTrue(true);
		} else{
			assertTrue(false);
		}
		
	}
	
	/*
	 * Tests the password creation functionality, logged in as registered user but missing the site field
	 * Passes if the submit button is not clickable/doesn't not allow user to submit
	 */
	@Test
	public void noSiteTest(){
		//logIn(driver, 3);
		Helper.logIn(driver, 0);
		//driver.findElement(By.linkText("ACMEPass")).click();
		driver.get(baseUrl + "/#/acme-pass");
		driver.findElement(By.cssSelector("button[ui-sref='acme-pass.new']")).sendKeys(Keys.ENTER);
		
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
		//logIn(driver, 3);
		//driver.findElement(By.linkText("ACMEPass")).click();
		Helper.logIn(driver,0);
		driver.get(baseUrl + "/#/acme-pass");
		driver.findElement(By.cssSelector("button[ui-sref='acme-pass.new']")).sendKeys(Keys.ENTER);
		
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
		//logIn(driver, 3);
		Helper.logIn(driver, 0);
		//driver.findElement(By.linkText("ACMEPass")).click();
		driver.get(baseUrl + "/#/acme-pass");
		driver.findElement(By.cssSelector("button[ui-sref='acme-pass.new']")).sendKeys(Keys.ENTER);
		
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
			String noPasswordsStored =  "Showing 1 - 0 of 0 items.";
			if(driver.findElement(By.cssSelector("div.info")).getText().equals(noPasswordsStored)){
				return 0;
			}
			
			int i = 1;
			List<WebElement> passlist;
			String path = tablePath + "/tbody/tr[" + Integer.toString(i) + "]";
			passlist = driver.findElements(By.xpath(path));
			while(passlist.size() !=0){
				i++;
				path = tablePath + "/tbody/tr[" + Integer.toString(i) + "]";
				passlist = driver.findElements(By.xpath(path));
			}
						
			return i - 1;
			
	}
	
	


}