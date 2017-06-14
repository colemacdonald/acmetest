package seng426;

import static org.junit.Assert.*;
import seng426.Helper;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.NoSuchElementException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreateUserTest {

	private WebDriver driver;
	
	private String baseUrl = "http://localhost:8080";
	
	private int getNumberOfUsers(String tablePath)
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
	
	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");
		
		driver = new FirefoxDriver();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);
		
		driver.get(baseUrl);
	
		driver.findElement(By.linkText("Sign in")).click();
		
		WebElement username = driver.findElement(By.id("username"));
		WebElement password = driver.findElement(By.id("password"));
		
		username.sendKeys("admin@acme.com");
		password.sendKeys("K-10ficile");
		
		WebElement modal = driver.findElement(By.className("modal-content"));
		modal.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div[2]/form/button")).click();
		
		try
		{
			driver.findElement(By.id("account-menu"));
		}
		catch(Exception e)
		{
			assertTrue(false);
		}
		driver.get("http://localhost:8080/#/user-management");
	}

	@After
	public void tearDown() throws Exception {
		driver.close();
	}
	
	/*
	 * Test create user functionality by attempting to register a new
	 * user with valid credentials and determining if a new user has been added
	 * to the list.
	 */
	@Test
	public void createUserSuccess()
	{
		WebElement modal, login, email, role, table;
		
		try
		{
			table = driver.findElement(By.className("table-responsive"));
			String tablePath = Helper.generateXPATH(table, "") + "/table";
			
			int numUsers = getNumberOfUsers(tablePath);
			System.out.printf("%d\n", numUsers);
			
			driver.findElement(By.cssSelector("button.btn.btn-primary[ui-sref='user-management.new']")).click();
			
			modal = driver.findElement(By.className("modal-content"));
			String modalPath = Helper.generateXPATH(modal, "");
			login = driver.findElement(By.xpath(modalPath + "/form/div[2]/div[2]/input"));
			email = driver.findElement(By.xpath(modalPath + "/form/div[2]/div[5]/input"));
			role = driver.findElement(By.xpath(modalPath + "/form/div[2]/div[7]/select"));
			
			login.sendKeys("valid_login");
			email.sendKeys("validemail@acme.com");
			role.sendKeys("User");
			
			//submit button
			driver.findElement(By.xpath(modalPath + "/form/div[3]/button[2]")).click();
			
			int newNumUsers = getNumberOfUsers(tablePath);
			
			if(newNumUsers != numUsers + 1)
			{
				fail("Failed to add user.");
			}
		}
		catch(NoSuchElementException e)
		{
			fail(e.getMessage());
		}
	}
	
	/*
	 * Tests to ensure that if an invalid email is typed in,
	 * user cannot be created.
	 */
	@Test
	public void createUserFailure()
	{
		WebElement modal, login, email, role, table;
		
		try
		{
			table = driver.findElement(By.className("table-responsive"));
			String tablePath = Helper.generateXPATH(table, "") + "/table";
			
			int numUsers = getNumberOfUsers(tablePath);
			System.out.printf("%d\n", numUsers);
			
			driver.findElement(By.cssSelector("button.btn.btn-primary[ui-sref='user-management.new']")).click();
			
			modal = driver.findElement(By.className("modal-content"));
			String modalPath = Helper.generateXPATH(modal, "");
			login = driver.findElement(By.xpath(modalPath + "/form/div[2]/div[2]/input"));
			email = driver.findElement(By.xpath(modalPath + "/form/div[2]/div[5]/input"));
			role = driver.findElement(By.xpath(modalPath + "/form/div[2]/div[7]/select"));
			
			login.sendKeys("valid_login");
			email.sendKeys("INvalidemail");
			role.sendKeys("User");
			
			//submit button
			if(driver.findElement(By.xpath(modalPath + "/form/div[3]/button[2]")).isEnabled())
			{
				fail("Submit button is enabled with invalid email in field.");
			}
		}
		catch(NoSuchElementException e)
		{
			fail(e.getMessage());
		}
	}
	
}
