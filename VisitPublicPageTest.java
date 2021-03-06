package seng426;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.*;
import java.util.NoSuchElementException;

public class VisitPublicPageTest {

	private WebDriver driver;
	private String baseurl = "http://localhost:8080";
	
	//function generated by selenium IDE
	private boolean isElementPresent(By by) {
	    try {
	      driver.findElement(by);
	      return true;
	    } catch (NoSuchElementException e) {
	      return false;
	    }
	  }
	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");
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

	//tests to make sure on homepage
	@Test
	public void testHomePage(){
		driver.get(baseurl);
		assertTrue(isElementPresent(By.cssSelector("img.img-responsive")));
		
	}
	
	//tests to make sure on Blog by number of blog posts
	@Test
	public void navigateToBlog(){
		//code taken from lab
		driver.findElement(By.linkText("Blog")).sendKeys(Keys.ENTER);
		driver.findElement(By.cssSelector("img.img-responsive"));
		
		List<WebElement> elements = driver.findElements(By.cssSelector("#media > .container a"));
		
		assertEquals(2, elements.size());
		
	}
	
	//tests to make sure on Company Page
	@Test 
	public void navigateToCompany(){
		driver.findElement(By.linkText("Company")).sendKeys(Keys.ENTER);
		assertEquals("About Us", driver.findElement(By.cssSelector("div > h1")).getText());
	}
	
	//tests to see if product on product page
	@Test
	public void navigateToProduct(){
		driver.findElement(By.linkText("Products")).sendKeys(Keys.ENTER);
	    assertEquals("POSEIDON® technology, utilizing the constructive key management standard, protects information down to the object level. It can secure any object from a field in a healthcare claim form—to a selected portion of a Microsoft® Word Document—to an entire file of real-time, streaming digital media. POSEIDON® is at the core of an advanced Role Based Access Control (RBAC) system that allows secured information to be shared among groups of users, each group having different levels of access to the secured information. POSEIDON® encrypts at the information level and manages information sharing relationships via cryptography. It is well understood that network perimeter defenses (firewalls, anti-virus, etc.) must be supplemented with the protection of the network content and ACME’s POSEIDON® products and solutions provide the protection of the content to effectively supplement network defenses.", driver.findElement(By.xpath("//section[@id='products']/div/div/p[2]")).getText());
	    //assertTrue(isElementPresent(By.cssSelector("img.img-responsive")));
	  
	}
	
	//tests to see if on technology page
	@Test
	public void navigateToTechnology(){
		driver.findElement(By.linkText("Technology")).sendKeys(Keys.ENTER);
		assertEquals("ACME has actively participated in the National Science Foundation’s call to arms – that America’s leading scientists and inventors must move toward measures that will ensure Privacy and Security in this increasingly Connected World. From the Connected Homes & Families through mobile computing and communication platforms, to Connected Cars utilizing roadside wireless infrastructures, to Connected Critical Infrastructure securing power grids and improving our ability to monitor and actively utilize airspace, ACME is providing solutions through POSEIDON, the next generation Internet of Things (IoT) security technology.", driver.findElement(By.cssSelector("div.col-md-8 > p")).getText());
	}
	
	//tests to see if you can navigate off webpage
	@Test
	public void navigateToGoogle(){
		driver.get("https://www.google.ca/");
		assertTrue(isElementPresent(By.id("hplogo")));
		/*
		 *attempt at being not on home page could not get to work
		 
		boolean test = isElementPresent(By.cssSelector("img.img-responsive"));
		assertTrue(!test);
		*/
	}
	
	//tests to see if a user can see a blog post
	@Test
	public void navigateToBlogPost(){
		driver.get(baseurl + "/index.html#/");
	    driver.findElement(By.linkText("Blog")).sendKeys(Keys.ENTER);
	    driver.findElement(By.linkText("Hashing Passwords Using MD5 vs. SHA vs. Bcrypt – by Frank Paul")).sendKeys(Keys.ENTER);
	    assertTrue(isElementPresent(By.cssSelector("div.blog-content > h1")));
	}

}
