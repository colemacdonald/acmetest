package seng426;

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

import seng426.Helper;

public class reveal_password {

	private WebDriver driver;
	private String baseUrl = "http://localhost:8080";
	
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
	
	@Before
	public void setUp() throws Exception 
	{
		System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");
		
		driver = new FirefoxDriver();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);
		
		driver.get(baseUrl);
		
		Helper.logIn(driver, 0);
	}

	@After
	public void tearDown() throws Exception 
	{
		driver.close();
	}

	/*
	 * Test that navigates to the ACMEPass page, and attempts to toggle the password 
	 * between plain text and dots for each password stored.
	 * For each 'input', it compares the 'type' property which defines which password display method is being used.
	 */
	@Test
	public void testRevealList() {
		driver.findElement(By.linkText("ACMEPass")).click();
		driver.findElement(By.linkText("ACMEPass")).click();
		
		WebElement table = driver.findElement(By.className("table-responsive"));
		String tablePath = Helper.generateXPATH(table, "") + "/table";
		
		int numPasswords = getNumPasswordsOnPage(tablePath);
		
		//System.out.printf("%d\n", numPasswords);
		
		if(numPasswords > 0)
		{
			for(int i = 1; i <= numPasswords; i++)
			{
				String trPath = tablePath + "/tbody/tr[" + Integer.toString(i) + "]";
				String passType = driver.findElement(By.xpath(trPath + "/td[4]/div/input")).getAttribute("type");
				driver.findElement(By.xpath(trPath + "/td[4]/div/span")).click();
				String newPassType = driver.findElement(By.xpath(trPath + "/td[4]/div/input")).getAttribute("type");
				
				if(passType.equals(newPassType))
				{
					fail("Toggle failed on row " + Integer.toString(i) + ".");
				}
			}
		}
		else
		{
			fail("No passwords to attempt to reveal.");
		}
	}
	
	/*
	 * Test that navigates to the ACMEPass page, and attempts to toggle the 
	 * password display method in the create/edit menu.
	 * Takes the password 'input' field, and compares the 'type' property which defines which 
	 * password display method is being used.
	 */
	@Test
	public void testRevealEdit()
	{
		driver.findElement(By.linkText("ACMEPass")).click();
		driver.findElement(By.linkText("ACMEPass")).click();
		
		driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/table/tbody/tr[1]/td[7]/div/button[1]/span[1]")).click();
		
		//wanted to use but it didnt work -> think because of my (Cole) weblayout (btn is covered by home button)
		//driver.findElement(By.cssSelector("button.btn.btn-primary[ui-sref='acme-pass.new']")).click();
		
		WebElement modal = driver.findElement(By.cssSelector("div.modal-body"));
		String modalPath = Helper.generateXPATH(modal, "");
		
		WebElement password = driver.findElement(By.id("field_password"));
		String passType = password.getAttribute("type");
		driver.findElement(By.xpath(modalPath + "/div[4]/div[1]/div[1]/span")).click();
		String newPassType = password.getAttribute("type");
		
		if(passType.equals(newPassType))
		{
			fail("Toggle failed on Create/Edit ACME Pass");
		}

	}
}
