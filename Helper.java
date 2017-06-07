package seng426;

import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Helper {
	
	public static boolean isElementPresent(WebDriver driver, By by) {
	    try 
	    {
	    	driver.findElement(by);
	    	return true;
	    } catch (NoSuchElementException e) 
	    {
	      return false;
	    }
	 }
	
	//https://stackoverflow.com/questions/18510576/find-an-element-by-text-and-get-xpath-selenium-webdriver-junit
		public static String generateXPATH(WebElement childElement, String current) {
		    String childTag = childElement.getTagName();
		    if(childTag.equals("html")) {
		        return "/html[1]"+current;
		    }
		    WebElement parentElement = childElement.findElement(By.xpath("..")); 
		    List<WebElement> childrenElements = parentElement.findElements(By.xpath("*"));
		    int count = 0;
		    for(int i=0;i<childrenElements.size(); i++) {
		        WebElement childrenElement = childrenElements.get(i);
		        String childrenElementTag = childrenElement.getTagName();
		        if(childTag.equals(childrenElementTag)) {
		            count++;
		        }
		        if(childElement.equals(childrenElement)) {
		            return generateXPATH(parentElement, "/" + childTag + "[" + count + "]"+current);
		        }
		    }
		    return null;
		}
		
		/*
		 * sets up WebDriver to log in as
		 * admin if user is set to 0
		 * manager if user is set to 1
		 * employee if user is set to 2
		 * registered user if user is set to 3
		 * Not elegant but it works
		 */
		public static void logIn(WebDriver driver, int user){
			driver.findElement(By.linkText("Sign in")).click();
			
			WebElement username = driver.findElement(By.id("username"));
			WebElement password = driver.findElement(By.id("password"));
			WebElement modal = driver.findElement(By.className("modal-body"));
			
			if(user==0){
				//sign in as admin
				username.sendKeys("admin@acme.com");
				password.sendKeys("K-10ficile");
				
			} else if(user==1){
				//sign in as manager
				username.sendKeys("jo.thomas@acme.com");
				password.sendKeys("mustang");
			} else if(user==2){
				//sign in as employee
				username.sendKeys("frank.paul@acme.com");
				password.sendKeys("starwars");
			} else if(user==3){
				//sign in as registered user
				//must change log in credentials to match your own created user
				username.sendKeys("zbroitman@gmail");
				password.sendKeys("acme");
			}
			driver.findElement(By.xpath(generateXPATH(modal, "") + "/div/div[2]/form/button")).click();
		}
}
