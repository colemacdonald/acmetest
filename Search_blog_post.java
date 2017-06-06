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
import org.openqa.selenium.*;

//import static org.junit.Assert.*;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.join;

import java.util.*;
import java.util.NoSuchElementException;

public class Search_blog_post {
	
	private WebDriver driver;
	private String baseurl = "http://localhost:8080/index.html#/blog-post/1";
	
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
		System.setProperty("webdriver.gecko.driver", "/home/dylang/bin/geckodriver");
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

	//tests to see if the search word is found succesfully
	//needs more testing to make sure system actually responds since search button is broken
	@Test
	public void searchFound() {
		driver.findElement(By.cssSelector("input.form-control.search_box")).clear();
	    driver.findElement(By.cssSelector("input.form-control.search_box")).sendKeys("blowfish.RETURN");
	    List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'" + "blowfish" + "')]"));
	    
	    assertEquals(1,list.size());
	}
	
	//tests to see if a word search is not found
	@Test
	public void searchNotFound() {
		driver.findElement(By.cssSelector("input.form-control.search_box")).clear();
	    driver.findElement(By.cssSelector("input.form-control.search_box")).sendKeys("notfound.RETURN");
	    List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'" + "notfound" + "')]"));
	    
	    assertEquals(0,list.size());
	}

}