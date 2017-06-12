package seng426;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import seng426.Helper;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Sorting {
	private WebDriver driver;
	private String baseUrl = "http://localhost:8080";
	private String[] sites = {"acme", "Facebook", "uvic", "Twitter", "Google"};
	private String[] logins = {"shinshan", "happyendings", "jdmdreams", "stuDent", "joshuajohns"};
	private String[] passwords = {"tomai123", "kokoko", "777luck", "6power", "burgers"};
	private int inputLength = 5;
	
	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");
		driver = new FirefoxDriver();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);
		
		driver.get(baseUrl);
		Helper.logIn(driver, 0);
		Helper.deleteAllAcmePass();
		
		WebElement modal;
		driver.findElement(By.cssSelector("a[ui-sref = 'acme-pass']")).sendKeys(Keys.ENTER);
		for(int i = 0; i < inputLength; i++){
			Helper.sendEnter(By.cssSelector("button.btn.btn-primary[ui-sref = 'acme-pass.new']"), driver, 5);
			driver.findElement(By.id("field_site")).sendKeys(sites[i]);
			driver.findElement(By.id("field_login")).sendKeys(logins[i]);
			driver.findElement(By.id("field_password")).sendKeys(passwords[i]);
			
			modal = driver.findElement(By.cssSelector("div.modal-footer"));
			Helper.clickOn(By.xpath(Helper.generateXPATH(modal, "") + "/button[2]"), driver, 5);
		}
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	@Test
	public void sortByID() throws Exception {
		Helper.clickOn(By.xpath("//thead/tr/th[1]"), driver, 5);
		Helper.clickOn(By.xpath("//thead/tr/th[1]"), driver, 5);
		
		List<WebElement> idWElist = driver.findElements(By.xpath("//tbody/tr/td[1]"));
		String[] idList = new String[inputLength];
		String[] idListOriginal = new String[inputLength];
		
		for(int i = 0; i < inputLength; i++){
			idList[i] = idWElist.get(i).getText();
			idListOriginal[i] = idWElist.get(i).getText();
		}
		Arrays.sort(idList, String.CASE_INSENSITIVE_ORDER);
		
		assertArrayEquals("IDs not sorted correctly ", idList, idListOriginal);
	}
	
	@Test
	public void sortBySite() throws Exception {
		Helper.clickOn(By.xpath("//thead/tr/th[2]"), driver, 5);
		
		List<WebElement> siteWElist = driver.findElements(By.xpath("//tbody/tr/td[2]"));
		String[] siteList = new String[inputLength];
		String[] siteListOriginal = new String[inputLength];
		
		for(int i = 0; i < inputLength; i++){
			siteList[i] = siteWElist.get(i).getText();
			siteListOriginal[i] = siteWElist.get(i).getText();
		}
		Arrays.sort(siteList, String.CASE_INSENSITIVE_ORDER);
		
		assertArrayEquals("Sites not sorted correctly ", siteList, siteListOriginal);
	}
	
	@Test
	public void sortByLogin() throws Exception {
		Helper.clickOn(By.xpath("//thead/tr/th[3]"), driver, 5);
		
		List<WebElement> loginWElist = driver.findElements(By.xpath("//tbody/tr/td[3]"));
		String[] loginList = new String[inputLength];
		String[] loginListOriginal = new String[inputLength];
		
		for(int i = 0; i < inputLength; i++){
			loginList[i] = loginWElist.get(i).getText();
			loginListOriginal[i] = loginWElist.get(i).getText();
		}
		Arrays.sort(loginList, String.CASE_INSENSITIVE_ORDER);
		
		assertArrayEquals("Login usernames not sorted correctly ", loginList, loginListOriginal);
	}
	
	@Test
	public void sortByPassword() throws Exception {
		Helper.clickOn(By.xpath("//thead/tr/th[4]"), driver, 5);
		
		List<WebElement> passwordWElist = driver.findElements(By.xpath("//tbody/tr/td[4]"));
		String[] passwordList = new String[inputLength];
		String[] passwordListOriginal = new String[inputLength];
		
		for(int i = 0; i < inputLength; i++){
			passwordList[i] = passwordWElist.get(i).getText();
			passwordListOriginal[i] = passwordWElist.get(i).getText();
		}
		Arrays.sort(passwordList, String.CASE_INSENSITIVE_ORDER);
		
		assertArrayEquals("Passwords not sorted correctly ", passwordList, passwordListOriginal);
	}
	
	@Test
	public void sortByCreateDate() throws Exception {
		Helper.clickOn(By.xpath("//thead/tr/th[5]"), driver, 5);
		
		List<WebElement> dateWElist = driver.findElements(By.xpath("//tbody/tr/td[5]"));
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, uuuu h:mm:ss a"); //Jun 9, 2017 11:00:39 PM
		
		String[] dateStrings = new String[inputLength];
		LocalDateTime[] dateList = new LocalDateTime[inputLength];
		LocalDateTime[] dateListOriginal = new LocalDateTime[inputLength];
		
		for(int i = 0; i < inputLength; i++){
			dateStrings[i] = dateWElist.get(i).getText();
			dateList[i] = LocalDateTime.parse(dateStrings[i], formatter);
			dateListOriginal[i] = LocalDateTime.parse(dateStrings[i], formatter);
		}
		
		
		Arrays.sort(dateList, (LocalDateTime d1, LocalDateTime d2) -> d1.compareTo(d2));
		assertArrayEquals("Dates not sorted correctly ", dateList, dateListOriginal);
	}
	
	@Test
	public void sortByModifiedDate() throws Exception {
		Helper.clickOn(By.xpath("//thead/tr/th[6]"), driver, 5);
		
		List<WebElement> dateWElist = driver.findElements(By.xpath("//tbody/tr/td[6]"));
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, uuuu h:mm:ss a"); //Jun 9, 2017 11:00:39 PM
		
		String[] dateStrings = new String[inputLength];
		LocalDateTime[] dateList = new LocalDateTime[inputLength];
		LocalDateTime[] dateListOriginal = new LocalDateTime[inputLength];
		
		for(int i = 0; i < inputLength; i++){
			dateStrings[i] = dateWElist.get(i).getText();
			dateList[i] = LocalDateTime.parse(dateStrings[i], formatter);
			dateListOriginal[i] = LocalDateTime.parse(dateStrings[i], formatter);
		}
		
		
		Arrays.sort(dateList, (LocalDateTime d1, LocalDateTime d2) -> d1.compareTo(d2));
		assertArrayEquals("Dates not sorted correctly ", dateList, dateListOriginal);
	}

}

