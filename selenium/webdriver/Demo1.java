package webdriver;

import graphql.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.openqa.selenium.Keys;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.interactions.Actions;

public class Demo1 {
    WebDriver driver;
    WebDriverWait explicitWait;


    @BeforeClass
    public void beforeClass() {
        driver = new EdgeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(10));


        driver.get("https://staging.presight.io/login/");
        driver.findElement(By.xpath("//input[@class='chakra-input css-1qpupnb']")).
                sendKeys("wwi@presight.io");
        driver.findElement(By.xpath("//button[@type='button']")).click();
        driver.findElement(By.xpath("//input[@type='password']")).clear();
        driver.findElement(By.xpath("//input[@type='password']")).
                sendKeys("presight!@#123");
        explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Login']")));
        driver.findElement(By.xpath("//button[text()='Login']")).click();
    }
    @Test
    public void TC_01_Run_On_Firefox() throws InterruptedException {
        String search = "sales";

//        driver.get("https://staging.presight.io/login/");
//        driver.findElement(By.xpath("//input[@class='chakra-input css-1qpupnb']")).
//                sendKeys("wwi@presight.io");
//        driver.findElement(By.xpath("//button[@type='button']")).click();
//        driver.findElement(By.xpath("//input[@type='password']")).clear();
//        driver.findElement(By.xpath("//input[@type='password']")).
//                sendKeys("presight!@#123");
//        explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Login']")));
//        driver.findElement(By.xpath("//button[text()='Login']")).click();

        WebElement homeIcon = driver.findElement(By.xpath("//a[@href='/']//button[@class='chakra-button css-jik1w1']"));
        explicitWait.until(ExpectedConditions.visibilityOf(homeIcon));

//        WebElement homeIcon = driver.findElement(By.xpath("//a[@href='/']//button[@class='chakra-button css-jik1w1']"));
//        explicitWait.until(ExpectedConditions.visibilityOf(homeIcon));
//
//        WebElement homeIcon = driver.findElement(By.xpath("//a[@href='/']//button[@class='chakra-button css-jik1w1']"));
//        explicitWait.until(ExpectedConditions.visibilityOf(homeIcon));

        Assert.assertTrue(homeIcon.isDisplayed());

        driver.findElement(By.xpath("//input[@placeholder='Ask AI anything about your data']")).sendKeys(search, Keys.ENTER);
        List<WebElement> parsingLoading = driver.findElements(By.xpath("//div[text()='Parsing request...']"));
        if(parsingLoading.size() > 0) {
            explicitWait.until(ExpectedConditions.invisibilityOfAllElements(parsingLoading));
        }

        List<WebElement> questionByAI = driver.findElements(By.xpath("//p[contains(.,'select an option below')]"));
        if(questionByAI.size() > 0) {
            driver.findElement(By.xpath("//button[text()='OK, Go']")).click();
           // explicitWait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.xpath("//div[text()='Parsing request...']"))));
        }

        List<WebElement> titleClarification = driver.findElements(By.xpath("//p[@class='chakra-text css-117v7ec']"));
        if(titleClarification.size() > 0) {
            WebElement textAreaClarification = driver.findElement(By.xpath("//textarea[@class='chakra-textarea css-wq5ml3']"));
            textAreaClarification.click();
            textAreaClarification.sendKeys("sales");
            driver.findElement(By.xpath("//button[text()='OK, Go']")).click();
            explicitWait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.xpath("//div[text()='Parsing request...']"))));
        }
        WebElement titleAskAI = driver.findElement(By.xpath("//span[contains(.,'"+ search + "')]"));
        Assert.assertTrue(titleAskAI.isDisplayed());

        driver.quit();
    }

}
