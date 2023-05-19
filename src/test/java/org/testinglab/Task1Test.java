package org.testinglab;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testinglab.data.FormSubmissionData;

import java.util.List;

public class Task1Test {
	
	private static WebDriver driver;
	private static FormSubmissionData data;
	
	@BeforeAll
	static void setUp() {
		WebDriverManager.chromedriver()
		                .setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		driver = new ChromeDriver(options);
		data = new FormSubmissionData();
	}
	
	@BeforeEach
	void navigateToDemoCart() {
		driver.navigate()
		      .to("https://testpages.herokuapp.com/styled/basic-html-form-test.html");
	}
	
	@Test
	void formTest() {
		fillUpForm();
		testValues();
	}
	
	private static void testValues() {
		WebElement username = driver.findElement(By.id("_valueusername"));
		Assertions.assertEquals(data.username, username.getText());
		
		WebElement password = driver.findElement(By.id("_valuepassword"));
		Assertions.assertEquals(data.password, password.getText());
		
		WebElement comments = driver.findElement(By.id("_valuecomments"));
		Assertions.assertEquals(data.comments, comments.getText());
		
		WebElement checkbox = driver.findElement(By.id("_valuecheckboxes0"));
		Assertions.assertEquals(data.checkBox, checkbox.getText());
		
		WebElement radio = driver.findElement(By.id("_valueradioval"));
		Assertions.assertEquals(data.radioButton, radio.getText());
		
		WebElement multiSelect = driver.findElement(By.id("_valuemultipleselect0"));
		Assertions.assertEquals(data.multiSelect, multiSelect.getText());
		
		WebElement dropDown = driver.findElement(By.id("_valuedropdown"));
		Assertions.assertEquals(data.dropDown, dropDown.getText());
		
		WebElement fileName = driver.findElement(By.id("_valuefilename"));
		Assertions.assertEquals(data.fileName, fileName.getText());
	}
	
	private void fillUpForm() {
		// username
		WebElement username = driver.findElement(By.name("username"));
		username.sendKeys(data.username);
		
		// password
		WebElement password = driver.findElement(By.name("password"));
		password.sendKeys(data.password);
		
		// comments
		WebElement comments = driver.findElement(By.name("comments"));
		// delete all the text in the comments field
		comments.clear();
		// type in the comments
		comments.sendKeys(data.comments);
		
		// grab all checkbox by input types
		List<WebElement> checkboxes = driver.findElements(By.cssSelector("input[type='checkbox']"));
		// clear all the checkboxes
		checkboxes.forEach(checkbox -> {
			if (checkbox.isSelected()) {
				checkbox.click();
			}
		});
		// filter the checkbox by value and click it
		checkboxes.stream()
		          .filter(e -> e.getAttribute("value")
		                        .equals(data.checkBox))
		          .findFirst()
		          .ifPresent(WebElement::click);
		
		// grab a radio button by input type and value
		WebElement radio = driver.findElement(By.cssSelector("input[type='radio'][value='" + data.radioButton + "']"));
		radio.click();
		
		// grab a select by name
		WebElement select = driver.findElement(By.name("multipleselect[]"));
		// clear all the options
		select.findElements(By.tagName("option"))
		      .forEach(option -> {
			      if (option.isSelected()) {
				      option.click();
			      }
		      });
		// select the option by value
		select.findElement(By.cssSelector("option[value='" + data.multiSelect + "']"))
		      .click();
		
		// grab the dropdown by name
		WebElement dropdown = driver.findElement(By.name("dropdown"));
		// select the option by value
		dropdown.findElement(By.cssSelector("option[value='" + data.dropDown + "']"))
		        .click();
		
		// grab the file input by name
		WebElement file = driver.findElement(By.name("filename"));
		// get the absolute path of the file
		String absolutePath = getClass().getResource(data.fileName)
		                                .getPath();
		// type in the file location
		file.sendKeys(absolutePath);
		
		// submit
		WebElement submit = driver.findElement(By.cssSelector("input[type='submit']"));
		submit.click();
		
		// wait for 3 seconds
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@AfterAll
	static void tearDown() {
		driver.quit();
	}
}