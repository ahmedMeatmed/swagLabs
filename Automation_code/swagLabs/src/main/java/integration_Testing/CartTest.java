package TestCases;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class CartTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeTest
    public void beforeTest(){
        System.setProperty("webdriver.chrome.driver", "C:\\Drivers\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);

        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        wait.until(ExpectedConditions.urlContains("inventory.html"));
    }

    public void openCart() {
        WebElement cartIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("shopping_cart_link")));
        cartIcon.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("cart_list")));
    }

    public void addProduct(String productId) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(productId)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    public void removeProduct(String removeId) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(removeId)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    @Test(priority = 1)
    public void TC1_AddSingleProductToCart() {
        addProduct("add-to-cart-sauce-labs-backpack");
        openCart();
    }

    @Test(priority = 2)
    public void TC2_AddMultipleDifferentProducts() {
        addProduct("add-to-cart-sauce-labs-backpack");
        addProduct("add-to-cart-sauce-labs-bike-light");
        openCart();
    }

    @Test(priority = 3)
    public void TC3_RemoveProductFromCart() {
        addProduct("add-to-cart-sauce-labs-backpack");
        addProduct("add-to-cart-sauce-labs-bike-light");
        openCart();
        removeProduct("remove-sauce-labs-backpack");
    }

    @Test(priority = 4)
    public void TC4_RaiseProductQuantityInCart() {
        addProduct("add-to-cart-sauce-labs-backpack");
        addProduct("add-to-cart-sauce-labs-backpack");
        openCart();
    }

    @Test(priority = 5)
    public void TC5_VerifyTotalPriceCalculation() {
        addProduct("add-to-cart-sauce-labs-backpack");
        addProduct("add-to-cart-sauce-labs-bike-light");
        openCart();
    }

    @AfterTest
    public void afterTest(){
        if(driver != null){
            driver.quit();
        }
    }
}
