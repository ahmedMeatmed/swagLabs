package integration_Testing;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class CheckOutTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeTest
    public void setup() {
        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\DELL 7520\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");


        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.password_manager_leak_detection", false);
        prefs.put("credentials_enable_service", false);
        options.setExperimentalOption("prefs",prefs);
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(9));

        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-bike-light")).click();

        WebElement cartBadge = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.className("shopping_cart_badge"))
        );

        driver.findElement(By.className("shopping_cart_link")).click();
        driver.findElement(By.id("checkout")).click();
    }
    @BeforeMethod
    public void openURL() {
        driver.get("https://www.saucedemo.com/checkout-step-one.html");

    }

    @Test(priority = 1)
    public void checkoutTest1() {
        driver.findElement(By.id("first-name")).sendKeys("Zeyad");
        driver.findElement(By.id("last-name")).sendKeys("Lotfy");
        driver.findElement(By.id("postal-code")).sendKeys("12345");
        driver.findElement(By.id("continue")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-two"));
    }


    @Test(priority = 2)
    public void checkoutTest2() {
        driver.findElement(By.id("first-name")).clear();
        driver.findElement(By.id("last-name")).clear();
        driver.findElement(By.id("postal-code")).clear();
        driver.findElement(By.id("first-name")).sendKeys("    ");
        driver.findElement(By.id("last-name")).sendKeys("   ");
        driver.findElement(By.id("postal-code")).sendKeys("    ");
        driver.findElement(By.id("continue")).click();
        Assert.assertFalse(driver.getCurrentUrl().contains("checkout-step-two"));
    }

    @Test(priority = 3)
    public void checkoutTest3() {
        driver.findElement(By.id("first-name")).clear();
        driver.findElement(By.id("last-name")).clear();
        driver.findElement(By.id("postal-code")).clear();
        driver.findElement(By.id("first-name")).sendKeys("Sara");
        driver.findElement(By.id("last-name")).sendKeys("Mohamed");
        driver.findElement(By.id("postal-code")).sendKeys("-6+7-890");
        driver.findElement(By.id("continue")).click();
        Assert.assertFalse(driver.getCurrentUrl().contains("checkout-step-two"));
        driver.navigate().back();
    }

    @Test(priority = 4)
    public void checkoutTest4() {
        driver.findElement(By.id("first-name")).clear();
        driver.findElement(By.id("last-name")).clear();
        driver.findElement(By.id("postal-code")).clear();
        driver.findElement(By.id("continue")).click();
        WebElement errorMsg = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3[data-test='error']"))
        );
        Assert.assertTrue(errorMsg.isDisplayed());
    }



    @Test(priority = 5)
    public void checkoutTest5() {
        driver.findElement(By.id("first-name")).sendKeys("Khaled  mok");
        driver.findElement(By.id("last-name")).sendKeys("Sami");
        driver.findElement(By.id("postal-code")).sendKeys("33333");
        driver.findElement(By.id("continue")).click();
        Assert.assertFalse(driver.getCurrentUrl().contains("checkout-step-two"));
        driver.navigate().back();
    }

    @Test(priority = 6)
    public void checkoutTest6() {
        driver.findElement(By.id("first-name")).sendKeys("Mona");
        driver.findElement(By.id("last-name")).sendKeys("Adel");
        driver.findElement(By.id("postal-code")).sendKeys("hghghg@");
        driver.findElement(By.id("continue")).click();
        Assert.assertFalse(driver.getCurrentUrl().contains("checkout-step-two"));
        driver.navigate().back();
    }

    @Test(priority = 7)
    public void checkoutTest7() {
        driver.findElement(By.id("first-name")).clear();
        driver.findElement(By.id("last-name")).sendKeys("Nabil");
        driver.findElement(By.id("postal-code")).sendKeys("55555");
        driver.findElement(By.id("continue")).click();
        WebElement errorMsg = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3[data-test='error']"))
        );
        Assert.assertTrue(errorMsg.isDisplayed());
    }

    @Test(priority = 8)
    public void checkoutTest8() {
        driver.findElement(By.id("first-name")).sendKeys("Hana");
        driver.findElement(By.id("last-name")).sendKeys("");
        driver.findElement(By.id("postal-code")).sendKeys("66666");
        driver.findElement(By.id("continue")).click();
        Assert.assertFalse(driver.getCurrentUrl().contains("checkout-step-two"));
    }

    @AfterClass
    public void finish() {
        driver.quit();
    }
}
