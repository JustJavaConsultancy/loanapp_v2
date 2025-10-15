package tech.justjava.loanapp.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoanApplicationSeleniumTest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        driver = new ChromeDriver();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void testStartProcess() {
        driver.get("http://localhost:8080/loan-application/start");

        WebElement applicantName = driver.findElement(By.id("applicantName"));
        applicantName.sendKeys("John Doe");

        WebElement loanAmount = driver.findElement(By.id("loanAmount"));
        loanAmount.sendKeys("10000");

        WebElement creditScore = driver.findElement(By.id("creditScore"));
        creditScore.sendKeys("750");

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        assertEquals("http://localhost:8080/loan-application/tasks", driver.getCurrentUrl());
    }

    @Test
    void testCompleteTask() {
        driver.get("http://localhost:8080/loan-application/tasks");

        WebElement completeLink = driver.findElement(By.linkText("Complete"));
        completeLink.click();

        WebElement decision = driver.findElement(By.id("decision"));
        decision.sendKeys("approve");

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        assertEquals("http://localhost:8080/loan-application/tasks", driver.getCurrentUrl());
    }
}
