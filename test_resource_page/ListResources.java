package seng426.test_resource_page;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

/*
 *  Tests resource listing functionality for all user classes
 */
public class ListResources {

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
	 * Tests if a user signed in as an Employee can access the resources page. 
	 * An Employee user should have access to the resources page so the link to this page should exist.
	 */
	@Test
	public void listResourcesAsEmployee() {
		driver.findElement(By.linkText("Sign in")).click();
		
		WebElement username = driver.findElement(By.id("username"));
		WebElement password = driver.findElement(By.id("password"));
		
		//Sign in as frank.paul (an employee)
		username.sendKeys("frank.paul@acme.com");
		password.sendKeys("starwars");
		WebElement modal = driver.findElement(By.className("modal-content"));
		modal.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div[2]/form/button")).click();
		WebElement rmbrMeCheck = driver.findElement(By.id("rememberMe"));
		
		if( rmbrMeCheck.isSelected()){
			rmbrMeCheck.click();
		}
		
		//Check if 'Resource' link is present
		boolean resourceLinkPresent = isElementPresent(By.cssSelector("a[ui-sref = 'resource']"));
		if(!resourceLinkPresent){
			assertTrue(false);
		}
			
		//Click resource link and check if it lands on the correct web page
		driver.findElement(By.linkText("Resources")).click();
		boolean createButtonPresent = isElementPresent(By.cssSelector("button.btn.btn-primary[ui-sref = 'resource.new']"));
		if(!createButtonPresent){
			assertTrue(false);
		}
		assertTrue(true);
	}
	
	/*
	 * Tests if a user signed in as an Admin can access the resources page.
	 * An Admin user should have access to the resources page so the link to this page should exist.
	 */
	@Test
	public void listResourcesAsAdmin() {
		driver.findElement(By.linkText("Sign in")).click();
		
		WebElement username = driver.findElement(By.id("username"));
		WebElement password = driver.findElement(By.id("password"));
		
		//Sign in as admin
		username.sendKeys("admin@acme.com");
		password.sendKeys("K-10ficile");
		WebElement modal = driver.findElement(By.className("modal-content"));
		modal.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div[2]/form/button")).click();
		WebElement rmbrMeCheck = driver.findElement(By.id("rememberMe"));
		
		if( rmbrMeCheck.isSelected()){
			rmbrMeCheck.click();
		}
		
		//Check if 'Resource' link is present
		boolean resourceLinkPresent = isElementPresent(By.cssSelector("a[ui-sref = 'resource']"));
		if(!resourceLinkPresent){
			assertTrue(false);
		}
		
		//Click resource link and check if it lands on the correct web page
		driver.findElement(By.linkText("Resources")).click();
		boolean createButtonPresent = isElementPresent(By.cssSelector("button.btn.btn-primary[ui-sref = 'resource.new']"));
		if(!createButtonPresent){
			assertTrue(false);
		}
		assertTrue(true);
	}
	
	/*
	 * Tests if a user signed in as a manager can access the resources page.
	 * A Manager user should have access to the resources page so the link to this page should exist.
	 */
	@Test
	public void listResourcesAsManager() {
		driver.findElement(By.linkText("Sign in")).click();
		
		WebElement username = driver.findElement(By.id("username"));
		WebElement password = driver.findElement(By.id("password"));
		
		//Sign in as jo.thomas (a manager)
		username.sendKeys("jo.thomas@acme.com");
		password.sendKeys("mustang");
		WebElement modal = driver.findElement(By.className("modal-content"));
		modal.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div[2]/form/button")).click();
		WebElement rmbrMeCheck = driver.findElement(By.id("rememberMe"));
		
		if( rmbrMeCheck.isSelected()){
			rmbrMeCheck.click();
		}
		
		//Check if 'Resource' link is present
		boolean resourceLinkPresent = isElementPresent(By.cssSelector("a[ui-sref = 'resource']"));
		if(!resourceLinkPresent){
			assertTrue(false);
		}
		
		//Click resource link and check if it lands on the correct web page
		driver.findElement(By.linkText("Resources")).click();
		boolean createButtonPresent = isElementPresent(By.cssSelector("button.btn.btn-primary[ui-sref = 'resource.new']"));
		if(!createButtonPresent){
			assertTrue(false);
		}
		assertTrue(true);
	}
	
	/*
	 * Tests if a user not signed in can access the resources page.
	 * A non-registered user should not have access to the resources page so the link to this page should not exist.
	 */
	@Test
	public void listResourcesAsUnregistered() {
		
		boolean signInPresent = isElementPresent(By.cssSelector("a#login"));
		if(!signInPresent){
			driver.findElement(By.cssSelector("a#account-menu")).click();
			driver.findElement(By.cssSelector("a#logout")).click();
		}
		
		//Check if 'Resource' link is present
		boolean resourceLinkPresent = isElementPresent(By.cssSelector("a[ui-sref = 'resource']"));
		if(resourceLinkPresent){
			assertTrue(false);
		}
		
		assertTrue(true);
	}
	
	private boolean isElementPresent(By by) {
	    try {
	      driver.findElement(by);
	    } catch (NoSuchElementException e) {
	      return false;
	    }
	    return true;
	  }

}
