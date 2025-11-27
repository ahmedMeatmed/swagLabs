package TestCases;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ItemsPageTest {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        //making sure the app is ideal
        driver.findElement(By.id("react-burger-menu-btn")).click();

    }
    @AfterMethod
    public void slowDown() throws InterruptedException {
        Thread.sleep(2000);
    }

    @Test
    public void testItemDetails() {
        // Wait for the item link to appear
        WebElement bikeLight = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.linkText("Sauce Labs Bike Light"))
        );
        Assert.assertTrue(bikeLight.isDisplayed(), "Bike Light is not visible");

        WebElement backpack = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.linkText("Sauce Labs Backpack"))
        );
        Assert.assertTrue(backpack.isDisplayed(), "Backpack is not visible");
    }

    @Test
    public void testAddToCartButton() {

        WebElement backpackAddButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("add-to-cart-sauce-labs-backpack"))
        );
        // Click the add button
        backpackAddButton.click();

        // Wait for button text to change to 'Remove'
        Boolean removeButton = wait.until(
                ExpectedConditions.textToBePresentInElementLocated(By.id("remove-sauce-labs-backpack"), "Remove")
        );

        Assert.assertEquals(
                driver.findElement(By.id("remove-sauce-labs-backpack")).getText(),
                "Remove",
                "Add to cart did not change to Remove"
        );
    }

    @Test
    public void testBackButtonWithoutAdding() {
        WebElement bikeLightLink = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.linkText("Sauce Labs Bike Light"))
        );
        bikeLightLink.click();

        WebElement backButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("back-to-products"))
        );
        backButton.click();

        Assert.assertTrue(
                driver.findElement(By.className("inventory_list")).isDisplayed(),
                "Failed to return to items page"
        );
    }
    @Test
    public void testBackButtonAfterAdding() {
        WebElement backpackLink = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.linkText("Sauce Labs Backpack"))
        );
        backpackLink.click();

        WebElement backButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("back-to-products"))
        );
        backButton.click();

        Assert.assertTrue(
                driver.findElement(By.className("inventory_list")).isDisplayed(),
                "Failed to return to items page"
        );
    }

    @Test
    public void testItemAddedToCart() {
        WebElement bikeLightAddButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("add-to-cart-sauce-labs-bike-light"))
        );
        bikeLightAddButton.click();

        // Wait until cart count updates
        WebElement cartBadge = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.className("shopping_cart_badge"))
        );
        Assert.assertEquals(cartBadge.getText(), "1", "Cart count is not correct");
    }
    @AfterClass
    public void teardown() {
        driver.findElement(By.id("react-burger-menu-btn")).click();
    driver.quit();
    }
}
