package TestCases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class SideBarTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeTest
    public void beforeTest(){
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        driver.get("https://www.saucedemo.com/");

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
    }

    public void openMenu() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("bm-menu")));
        driver.findElement(By.id("react-burger-menu-btn")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("bm-menu")));
    }

    public void closeMenu() {
        driver.findElement(By.id("react-burger-cross-btn")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("bm-menu")));
    }

    @Test(priority = 1)
    public void testDropDownList(){
        openMenu();
        closeMenu();
    }

    @Test
    public void testAllItemsLink(){
        openMenu();
        driver.findElement(By.id("inventory_sidebar_link")).click();
    }

    @Test
    public void testAboutLink(){
        openMenu();
        driver.findElement(By.id("about_sidebar_link")).click();
    }

    @Test
    public void testLogOutLink(){
        openMenu();
        driver.findElement(By.id("logout_sidebar_link")).click();
    }

    @Test
    public void testResetLink(){
        openMenu();
        driver.findElement(By.id("reset_sidebar_link")).click();
    }

    @Test
    public void testCloseButton(){
        openMenu();
        closeMenu();
    }

    @AfterTest
    public void afterTest(){
        driver.quit();
    }
}
