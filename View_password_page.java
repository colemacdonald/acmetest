package seng426;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import static org.junit.Assert.assertEquals;
import java.util.*;
import seng426.Helper;

public class View_password_page {
	
	private WebDriver driver;
	private String baseurl = "http://localhost:8080";
	
	
	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.gecko.driver", "/home/dylang/bin/geckodriver");
		driver = new FirefoxDriver();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);
		
		driver.get(baseurl);
		
	}
	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	//tests to make sure on password page
	@Test
	public void viewPasswordPage(){
		Helper.logIn(driver, 0);
		driver.findElement(By.linkText("ACMEPass")).click();
		List<WebElement> table = driver.findElements(By.className("table-responsive"));	
		assertEquals(1,table.size());
	}
	
	//tests to ensure unregistered user cannot access acmepass page
	@Test
	public void viewPasswordPageDenied(){
		List<WebElement> acmepassButton = driver.findElements(By.linkText("ACMEPass"));
		assertEquals(0,acmepassButton.size());
	}
	
	
	

}
