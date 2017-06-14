package seng426;

import seng426.Helper;

import static org.junit.Assert.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class PaginationTest {
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
		for(int j = 0; j < 6; j++){
			for(int i = 0; i < inputLength; i++){
				Helper.sendEnter(By.cssSelector("button.btn.btn-primary[ui-sref = 'acme-pass.new']"), driver, 5);
				driver.findElement(By.id("field_site")).sendKeys(sites[i]);
				driver.findElement(By.id("field_login")).sendKeys(logins[i]);
				driver.findElement(By.id("field_password")).sendKeys(passwords[i]);
				modal = driver.findElement(By.cssSelector("div.modal-footer"));
				Helper.clickOn(By.xpath(Helper.generateXPATH(modal, "") + "/button[2]"), driver, 5);
			}
		}
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	@Test
	public void pagenationTest() {
		int page1IDcount;
		int page2IDcount;
		int page1inputs = 20;
		int page2inputs = 10;
		
		List<WebElement> idWElist = driver.findElements(By.xpath("//tbody/tr/td[1]"));
		page1IDcount = idWElist.size();
		Helper.clickOn(By.linkText("»"), driver, 5);
		idWElist = driver.findElements(By.xpath("//tbody/tr/td[1]"));
		page2IDcount = idWElist.size();
		
		if(page1IDcount != page1inputs) fail("Incorrect number of ACME Passes displayed in first page\n");
		if(page2IDcount != page2inputs) fail("Incorrect number of ACME Passes displayed in second page\n");
		assertTrue(true);
	}

}

