package seng426;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import seng426.Helper;

public class generate_password {
	
	private WebDriver 		driver;
	private String 			baseUrl = "http://localhost:8080";
	
	private WebElement 		generateModal;
	private String			generateModalPath;

	@Before
	public void setUp() throws Exception 
	{
		System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");
		
		driver = new FirefoxDriver();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);
		
		driver.get(baseUrl);
		
		//log in as admin
		Helper.logIn(driver, 0);
		
		//clicking once wasn't taking me to ACMEPass page
		driver.findElement(By.linkText("ACMEPass")).click();
		driver.findElement(By.linkText("ACMEPass")).click();
		
		//edit first password in table
		driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/table/tbody/tr[1]/td[7]/div/button[1]/span[1]")).click();
		//wanted to use but it didnt work -> think because of my (Cole) weblayout (btn is covered by home button)
		//driver.findElement(By.cssSelector("button.btn.btn-primary[ui-sref='acme-pass.new']")).click();
		
		WebElement modal = driver.findElement(By.className("modal-body"));
		
		//click generate button
		driver.findElement(By.xpath(Helper.generateXPATH(modal, "") + "/div[4]/div[2]/button")).click();
		//get new modal window
		generateModal = driver.findElement(By.className("modal-body")); 
		generateModalPath = Helper.generateXPATH(generateModal, "");
	}

	/*
	 * Will generate a password and return the string
	 */
	private String generatePassword()
	{
		driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
		return driver.findElement(By.id("field_password")).getAttribute("value");
	}
	
	/*
	 * Will toggle the option in the list specified, beginning at 1
	 * Acceptable values:		1 <= toToggle <= 5
	 */
	private boolean toggleOption(int toToggle)
	{
		if(toToggle < 1 || toToggle > 5)
		{
			return false;
		}
		try
		{
			driver.findElement(By.xpath(generateModalPath + "/div[" + Integer.toString(toToggle) + "]/input")).click();
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	
	@After
	public void tearDown() throws Exception 
	{
		driver.close();
	}

	@Test
	public void test() {
		toggleOption(1);
		toggleOption(5);
		System.out.printf("%s\n", generatePassword());
		try{
			driver.findElement(By.className("stall"));
		}
		catch(Exception e)
		{
			
		}
		fail("Not yet implemented");
	}

}
