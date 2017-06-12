package seng426;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Helper {
	
	/*
	 * To manipulate MySQL databases, you need to download the jdbc api from 
	 * https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-installing.html
	 * and add the .jar file to the java build path
	 */
	public static void sql(String query) throws Exception{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost/acme", "acme", "acmeapp");	//adjust to your database parameters
		Statement st = con.createStatement();
		st.executeUpdate(query);
	}
	
	public static void deleteAllAcmePass() throws Exception{
		sql("delete from acmepass");
	}
	
	public static void clickOn(By locator, WebDriver driver, int timeout)
	{
	    final WebDriverWait wait = new WebDriverWait(driver, timeout);
	    wait.until(ExpectedConditions.refreshed(
	        ExpectedConditions.elementToBeClickable(locator)));
	    driver.findElement(locator).sendKeys(Keys.ENTER);
	}
	
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
			driver.findElement(By.linkText("Sign in")).sendKeys(Keys.ENTER);
			
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
			driver.findElement(By.xpath(generateXPATH(modal, "") + "/div/div[2]/form/button")).sendKeys(Keys.ENTER);
		}
}
