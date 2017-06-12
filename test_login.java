package seng426;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class test_login {

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
		driver.close();
	}
	
	@Test
	public void testLoginSuccess()
	{
		driver.findElement(By.linkText("Sign in")).sendKeys(Keys.ENTER);
		
		WebElement username = driver.findElement(By.id("username"));
		WebElement password = driver.findElement(By.id("password"));
		
		username.sendKeys("admin@acme.com");
		password.sendKeys("K-10ficile");
		
		WebElement modal = driver.findElement(By.className("modal-content"));
		modal.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div[2]/form/button")).sendKeys(Keys.ENTER);
		
		try
		{
			driver.findElement(By.id("account-menu"));
		}
		catch(Exception e)
		{
			assertTrue(false);
		}
		assertTrue(true);
	}
	
	@Test
	public void testLoginFailure()
	{
		driver.findElement(By.linkText("Sign in")).sendKeys(Keys.ENTER);
		
		WebElement username = driver.findElement(By.id("username"));
		WebElement password = driver.findElement(By.id("password"));
		
		username.sendKeys("uname");
		password.sendKeys("pword");
		
		WebElement modal = driver.findElement(By.className("modal-content"));
		modal.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div[2]/form/button")).sendKeys(Keys.ENTER);
		
		try
		{
			driver.findElement(By.id("account-menu"));
		}
		catch(Exception e)
		{
			assertTrue(true);
			return;
		}
		assertTrue(false);
	}
}
