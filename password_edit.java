package seng426;

import static org.junit.Assert.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import seng426.Helper;

public class password_edit {
	
	private WebDriver 		driver;
	private String 			baseUrl = "http://localhost:8080";


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
	 * Tests a valid edit password instance
	 * All three fields are filled
	 * Password with the same id in the table should be contain the new information to pass
	 */
	@Test
	public void validEditTest() 
	{
		Helper.logIn(driver, 2);
		driver.findElement(By.linkText("ACMEPass")).sendKeys(Keys.ENTER);
		
		//Need to grab table to get row with corresponding id, and then click the corresponding edit button
		driver.findElement(By.cssSelector("button[ui-sref='acme-pass.edit({id:acmePass.id})']")).sendKeys(Keys.ENTER);

		WebElement table = driver.findElement(By.className("table-responsive"));
		String tablePath = Helper.generateXPATH(table, "") + "/table";
		driver.findElement(By.cssSelector("button[ui-sref='acme-pass.edit({id:acmePass.id})']")).click();
		
		WebElement site = driver.findElement(By.id("field_site"));
		WebElement log = driver.findElement(By.id("field_login"));
		WebElement password = driver.findElement(By.id("field_password"));

		site.clear();
		log.clear();
		password.clear();
		
		site.sendKeys("newSite");
		log.sendKeys("newLogin");
		password.sendKeys("newPassword");
		
		driver.findElement(By.cssSelector("button[type='submit']")).sendKeys(Keys.ENTER);

		//need to check correct table row was edited
		//get table rows
		table = driver.findElement(By.className("table-responsive"));
		tablePath = Helper.generateXPATH(table, "") + "/table";
		
		String rowPath = tablePath + "/tbody/tr";
		List<WebElement> tableRows = driver.findElements(By.xpath(rowPath));
		
		//get entries from each row
		//WebElement tmp = tableRows.get(0);
		List<WebElement> tds = driver.findElements(By.xpath(Helper.generateXPATH(tableRows.get(0), "") + "/td"));
		boolean siteMatch = "newSite".equals(tds.get(1).getText());
		boolean loginMatch = "newLogin".equals(tds.get(2).getText());
		
		if(siteMatch && loginMatch){
			assertTrue(true);
		} else{
			assertTrue(false);
		}
	}
	
	/*
	 * Tests an invalid edit password instance
	 * Website field is left blank
	 * The submit button should not be clickable to pass
	 */
	@Test
	public void noSiteEditTest(){
		Helper.logIn(driver, 2);
		driver.findElement(By.linkText("ACMEPass")).sendKeys(Keys.ENTER);
		
		driver.findElement(By.cssSelector("button[ui-sref='acme-pass.edit({id:acmePass.id})']")).sendKeys(Keys.ENTER);
		
		WebElement site = driver.findElement(By.id("field_site"));
		WebElement log = driver.findElement(By.id("field_login"));
		WebElement password = driver.findElement(By.id("field_password"));

		site.clear();
		log.clear();
		password.clear();
		
		log.sendKeys("shouldn't");
		password.sendKeys("work");
		
		
		WebElement submitBtn = driver.findElement(By.cssSelector("button[type='submit']"));
		if(submitBtn.isEnabled()){
			assertTrue(false);
		} else{
			assertTrue(true);
		}
	}
	
	/*
	 * Tests an invalid edit password instance
	 * user field is left blank
	 * The submit button should not be clickable to pass
	 */
	@Test
	public void noUserEditTest(){
		Helper.logIn(driver, 2);
		driver.findElement(By.linkText("ACMEPass")).sendKeys(Keys.ENTER);
		
		driver.findElement(By.cssSelector("button[ui-sref='acme-pass.edit({id:acmePass.id})']")).sendKeys(Keys.ENTER);
		
		WebElement log = driver.findElement(By.id("field_login"));
		WebElement site = driver.findElement(By.id("field_site"));
		WebElement password = driver.findElement(By.id("field_password"));
		
		site.clear();
		log.clear();
		password.clear();
		
		site.sendKeys("shouldn't");
		password.sendKeys("work");
		
		
		WebElement submitBtn = driver.findElement(By.cssSelector("button[type='submit']"));
		if(submitBtn.isEnabled()){
			assertTrue(false);
		} else{
			assertTrue(true);
		}
		
	}
	
	/*
	 * Tests an invalid edit password instance
	 * password field is left blank
	 * The submit button should not be clickable to pass
	 */
	@Test
	public void noPasswordEditTest(){
		Helper.logIn(driver, 2);
		driver.findElement(By.linkText("ACMEPass")).sendKeys(Keys.ENTER);
		
		driver.findElement(By.cssSelector("button[ui-sref='acme-pass.edit({id:acmePass.id})']")).sendKeys(Keys.ENTER);
		
		WebElement password = driver.findElement(By.id("field_password"));
		WebElement log = driver.findElement(By.id("field_login"));
		WebElement site = driver.findElement(By.id("field_site"));
		
		site.clear();
		log.clear();
		password.clear();
		
		log.sendKeys("shouldn't");
		site.sendKeys("work");
		
		
		WebElement submitBtn = driver.findElement(By.cssSelector("button[type='submit']"));
		if(submitBtn.isEnabled()){
			assertTrue(false);
		} else{
			assertTrue(true);
		}
	}

}
