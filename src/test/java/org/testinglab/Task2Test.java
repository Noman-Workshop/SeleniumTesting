package org.testinglab;

import com.google.gson.Gson;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testinglab.data.TableData;

import java.util.ArrayList;
import java.util.List;

import static org.testinglab.data.TableData.Person;

public class Task2Test {
	
	private static WebDriver driver;
	private static Gson gson;
	
	private static TableData data;
	
	@BeforeAll
	static void setUp() {
		WebDriverManager.chromedriver()
		                .setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		driver = new ChromeDriver(options);
		gson = new Gson();
		
		data = new TableData();
	}
	
	@BeforeEach
	void navigateToDemoCart() {
		driver.navigate()
		      .to("https://testpages.herokuapp.com/styled/tag/dynamic-table.html");
	}
	
	@Test
	void dynamicTableTest() {
		enterTableData();
		checkTableValues();
	}
	
	private static void checkTableValues() {
		// find the table
		WebElement table = driver.findElement(By.id(data.tableId));
		
		List<Person> persons = new ArrayList<>();
		
		// parse the html to json
		table.findElements(By.tagName("tr"))
		     .stream()
		     .skip(1)
		     .forEach(tr -> {
			     List<WebElement> tds = tr.findElements(By.tagName("td"));
			     if (tds.size() == Person.class.getDeclaredFields().length) {
				     String name = tds.get(0)
				                      .getText();
				     String age = tds.get(1)
				                     .getText();
				     Person person = new Person(name, age);
				     persons.add(person);
			     }
		     });
		
		// check if the table data is equal to the persons list
		Assertions.assertEquals(data.tableData, persons);
	}
	
	private void enterTableData() {
		// find tag by details
		WebElement details = driver.findElement(By.tagName("details"));
		// click on details
		details.click();
		
		// find by id jsondata
		WebElement jsonData = driver.findElement(By.id("jsondata"));
		// clear the text
		jsonData.clear();
		// enter the data
		jsonData.sendKeys(gson.toJson(data.tableData));
		
		// change the table caption
		WebElement caption = driver.findElement(By.id("caption"));
		caption.clear();
		caption.sendKeys(data.caption);
		
		// change the table id
		WebElement tableId = driver.findElement(By.id("tableid"));
		tableId.clear();
		tableId.sendKeys(data.tableId);
		
		// submit and refresh the table
		WebElement submit = driver.findElement(By.id("refreshtable"));
		submit.click();
		
		// wait for 1 seconds
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@AfterAll
	static void tearDown() {
		driver.quit();
	}
}