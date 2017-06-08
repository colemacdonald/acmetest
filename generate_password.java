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

	private void setLength(int length)
	{
		WebElement lengthField = driver.findElement(By.id("field_length"));
		
		lengthField.clear();
		lengthField.sendKeys(Integer.toString(length));
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

	/*
	 * Testing password generation with only lowercase option selected 
	 * Generating 10 passwords such that 80 characters are generated which is ~3x the size of the alphabet being tested
	 * and larger than the max alphabet size - therefore, if the functionality wasn't working, we'd expect to get an
	 * incorrect character
	 */
	@Test
	public void testLowercaseOnly()
	{
		//disable all but lower case option
		toggleOption(2);
		toggleOption(3);
		toggleOption(4);
		
		for(int i = 0; i < 10; i++)
		{
			String password = generatePassword();
			
			if(!password.matches("[a-z]+"))
			{
				fail("Password generated contained a non-lowercase letter.\nGenerated: " + password);
			}
		}
	}
	
	@Test
	public void testUppercaseOnly()
	{
		//disable all but upper case option
			toggleOption(1);
			toggleOption(3);
			toggleOption(4);
				
			for(int i = 0; i < 10; i++)
			{
				String password = generatePassword();
					
				if(!password.matches("[A-Z]+"))
				{
					fail("Password generated contained a non-uppercase letter.\nGenerated: " + password);
				}
			}
	}
	
	@Test
	public void testDigitsOnly()
	{
		//disable all but digit option
		toggleOption(1);
		toggleOption(2);
		toggleOption(4);
				
		for(int i = 0; i < 10; i++)
		{
			String password = generatePassword();
			
			if(!password.matches("[0-9]+"))
			{
				fail("Password generated contained a non-digit.\nGenerated: " + password);
			}
		}
	}
	
	@Test
	public void testSpecialCharactersOnly()
	{
		//disable all but special characters option
		toggleOption(1);
		toggleOption(2);
		toggleOption(3);
						
		for(int i = 0; i < 10; i++)
		{
			String password = generatePassword();
			
			if(!password.matches("[!@#$%-_]+"))
			{
				fail("Password generated contained a non-special character.\nGenerated: " + password);
			}
		}
	}
	
	/* 
	 * Testing length functionality. 
	 * Varies length from [4, 20), generates a password and tests whether the string
	 * is of the correct length.
	 */
	@Test
	public void testLength()
	{
		for(int i = 4; i < 20; i++)
		{
			setLength(i);
			String password = generatePassword();
			
			if(password.length() != i)
			{
				fail("Password generated was of incorrect length.\nExpected Length: "+ Integer.toString(i) +  "\nGenerated: " + password);
			}
		}
	}
	
	/*
	 * Test to see if password generated contains at least one of all of the specified types.
	 * Creates 20 passwords and ensures they have 1 of each character type.
	 */
	@Test
	public void testAll()
	{
		for(int i = 0; i < 20; i++)
		{
			String password = generatePassword();
			
			//contains at least one of each character
			if(!password.matches(".*[a-z].*") || !password.matches(".*[A-Z].*") || 
					!password.matches(".*[0-9].*") || !password.matches(".*[!@#$%-_].*"))
			{
				fail("Password generated did not contain all necessary character types.\n Generated: " + password);
			}
		}
	}
	
	@Test
	public void testPreventRepeats()
	{
		//set Prevent Repeated Characters
		toggleOption(5);
		
		for(int i = 0; i < 20; i++)
		{
			String password = generatePassword();
			
			int l = password.length();
			
			for(int j = 0; j < l; j++)
			{
				char c = password.charAt(j);
				if(j != password.lastIndexOf(c))
				{
					fail("Password generated contains a duplicate letter:" + c + ".\nGenerated: " + password);
				}
			}
		}
	}
	
//	@Test
//	public void test() {
//		toggleOption(1);
//		toggleOption(5);
//		setLength(5);
//		System.out.printf("%s\n", generatePassword());
//		try{
//			driver.findElement(By.className("stall"));
//		}
//		catch(Exception e)
//		{
//			
//		}
//		fail("Not yet implemented");
//	}

}
