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
import java.util.*;

import seng426.Helper;

public class Delete_password {

	//taken from reveal passwords test and modified
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
	private WebDriver driver;
	private String baseurl = "http://localhost:8080";
	
	
	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");
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
	
	//tests to see if last password successfully deleted
	@Test
	public void DeletePassword(){
		driver.get(baseurl + "/#/acme-pass");
		WebElement table = driver.findElement(By.className("table-responsive"));
		String tablePath = Helper.generateXPATH(table, "") + "/table";
		
		int numPasswords = getNumPasswordsOnPage(tablePath);
		if (numPasswords >0){
			driver.findElement(By.xpath("//tr[" + numPasswords + "]/td[7]/div/button[2]")).click();
		    //driver.findElement(By.xpath("//tr[" + numPasswords + "]/td[7]/div/button[2]")).click();
		    driver.findElement(By.cssSelector("button.btn.btn-danger")).click();
		    //driver.findElement(By.cssSelector("button.btn.btn-danger")).click();
		    List<WebElement> lastPassword = driver.findElements(By.xpath("//tr[" + numPasswords + "]/td[7]/div/button[2]"));	
			assertEquals(0,lastPassword.size());
			
		}else{
			fail("No passwords to delete");
		}
			
	}
	
	//Test to see if first password is deleted
	@Test
	public void DeleteFirstPassword(){
		driver.get(baseurl + "/#/acme-pass");
		WebElement table = driver.findElement(By.className("table-responsive"));
		String tablePath = Helper.generateXPATH(table, "") + "/table";
		
		int numPasswordsBeforeDeletion = getNumPasswordsOnPage(tablePath);
		if (numPasswordsBeforeDeletion  >0){
			driver.findElement(By.xpath("//button[2]")).click();
		    //driver.findElement(By.xpath("//button[2]")).click();
		    driver.findElement(By.cssSelector("button.btn.btn-danger")).click();
		    //driver.findElement(By.cssSelector("button.btn.btn-danger")).click();
		    table = driver.findElement(By.className("table-responsive"));
			tablePath = Helper.generateXPATH(table, "") + "/table";
		    int numPasswordsAfterDeletion = getNumPasswordsOnPage(tablePath);
			assertTrue(numPasswordsBeforeDeletion > numPasswordsAfterDeletion);
		}else{
			fail("No passwords to delete");
		}
			
	}
	
	//test to see if the user decides not to delete password
	@Test
	public void PasswordNotDeleted(){
		driver.get(baseurl + "/#/acme-pass");
		WebElement table = driver.findElement(By.className("table-responsive"));
		String tablePath = Helper.generateXPATH(table, "") + "/table";
		int numPasswordsBeforeDeletion = getNumPasswordsOnPage(tablePath);
		
		if (numPasswordsBeforeDeletion  >0){
		driver.findElement(By.xpath("//button[2]")).click();
		
	    
	    //driver.findElement(By.xpath("//button[1]")).click();
	    driver.findElement(By.cssSelector("button.btn.btn-default")).click();
	    //driver.findElement(By.xpath("//button[1]")).click();
	    int numPasswordsAfterNotDeleting = getNumPasswordsOnPage(tablePath);
	    assertTrue(numPasswordsBeforeDeletion == numPasswordsAfterNotDeleting);
		}else{
			fail("No passwords to attempt deletion");
		}
		
	}
	
	

}
