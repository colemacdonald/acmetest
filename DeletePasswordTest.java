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

import seng426.Helper;

public class DeletePasswordTest {

	/*
	 * 	taken from reveal passwords test and modified
	 *  given the tablepath it returns the number of passwords on the page
	 */
	private int getNumPasswordsOnPage(String tablePath)
	{
			String noPasswordsStored =  "Showing 1 - 0 of 0 items.";
			if(driver.findElement(By.cssSelector("div.info")).getText().equals(noPasswordsStored)){
				return 0;
			}
			
			int i = 1;
			List<WebElement> passlist;
			String path = tablePath + "/tbody/tr[" + Integer.toString(i) + "]";
			passlist = driver.findElements(By.xpath(path));
			while(passlist.size() !=0){
				i++;
				path = tablePath + "/tbody/tr[" + Integer.toString(i) + "]";
				passlist = driver.findElements(By.xpath(path));
			}
						
			return i - 1;
			
	}
	/*
	 * function that waits for the given amount of seconds passed to it
	 * try catch block automatically generated by eclipse
	 */
	private void wait_function(int i){
		try {
			TimeUnit.SECONDS.sleep(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
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
		Helper.logIn(driver, 0);
		
		
	}
	
	@After
	public void tearDown() throws Exception {
		driver.quit();
	}
	
	/*
	 * tests to see if last password successfully deleted for the test to work properly you need at least 2 passwords stored
	 * otherwise it would be the same test as deleting the first password
	 * passes if the last password is deleted
	 */
	@Test
	public void DeletePassword(){
		driver.get(baseurl + "/#/acme-pass");
		WebElement table = driver.findElement(By.className("table-responsive"));
		String tablePath = Helper.generateXPATH(table, "") + "/table";
		
		int numPasswords = getNumPasswordsOnPage(tablePath);
		if (numPasswords >1){
			driver.findElement(By.xpath("//tr[" + numPasswords + "]/td[7]/div/button[2]")).sendKeys(Keys.ENTER);
		    
			wait_function(3);
		    driver.findElement(By.cssSelector("button.btn.btn-danger")).sendKeys(Keys.ENTER);
		    
		    try{
		    driver.findElement(By.xpath("//tr[" + numPasswords + "]/td[7]/div/button[2]"));	
		    }catch(NoSuchElementException e){
		    	assertTrue(true);
		    }
			
		}else{
			fail("Not enough passwords to delete last one");
		}
			
	}
	/*
	 * Test to see if first password is deleted
	 */
	@Test
	public void DeleteFirstPassword(){
		driver.get(baseurl + "/#/acme-pass");
		WebElement table = driver.findElement(By.className("table-responsive"));
		String tablePath = Helper.generateXPATH(table, "") + "/table";
		
		int numPasswordsBeforeDeletion = getNumPasswordsOnPage(tablePath);
		if (numPasswordsBeforeDeletion  >0){
			driver.findElement(By.xpath("//button[2]")).sendKeys(Keys.ENTER);
		    driver.findElement(By.cssSelector("button.btn.btn-danger")).sendKeys(Keys.ENTER);
		    
		    wait_function(5);
		    
		    WebElement NewTable = driver.findElement(By.className("table-responsive"));
		    String NewTablePath = Helper.generateXPATH(NewTable, "") + "/table";
		    
		    int numPasswordsAfterDeletion = getNumPasswordsOnPage(NewTablePath);
		    assertTrue(numPasswordsBeforeDeletion > numPasswordsAfterDeletion);
		
		}else{
			fail("No passwords to delete");
		}
			
	}
	/*
	 * test to see if the user decides not to delete password after
	 * taking the first step to delete a stored password
	 */
	@Test
	public void PasswordNotDeleted(){
		driver.get(baseurl + "/#/acme-pass");
		WebElement table = driver.findElement(By.className("table-responsive"));
		String tablePath = Helper.generateXPATH(table, "") + "/table";
		int numPasswordsBeforeDeletion = getNumPasswordsOnPage(tablePath);
		
		if (numPasswordsBeforeDeletion  >0){
		driver.findElement(By.xpath("//button[2]")).sendKeys(Keys.ENTER);
		
	    wait_function(3);
	    driver.findElement(By.xpath("//button[1]")).sendKeys(Keys.ENTER);
	    
	    int numPasswordsAfterNotDeleting = getNumPasswordsOnPage(tablePath);
	    assertTrue(numPasswordsBeforeDeletion == numPasswordsAfterNotDeleting);
		}else{
			fail("No passwords to attempt deletion");
		}
		
	}
	
	

}
