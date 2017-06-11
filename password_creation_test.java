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
		System.setProperty("webdriver.gecko.driver", "C:\\Users\\Zac\\bin\\geckodriver.exe");
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
		logIn(driver, 3);
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
		logIn(driver, 3);
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
		logIn(driver, 3);
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
		logIn(driver, 3);
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
			username.sendKeys("zbroitman@gmail.com");
			password.sendKeys("acme");
			WebElement modal = driver.findElement(By.className("modal-content"));
			modal.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div[2]/form/button")).click();
		}
		
	}

}
