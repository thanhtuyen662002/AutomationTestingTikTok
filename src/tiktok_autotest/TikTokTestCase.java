package tiktok_autotest;

import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.chrome.ChromeOptions;

public class TikTokTestCase {

    private WebDriver driver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\HP Pro\\TikTok_AutoTest\\chromedriver.exe"); // Đường dẫn đến Chrome driver
        ChromeOptions opt = new ChromeOptions();
        
        opt.addArguments("--incognito");
        driver = new ChromeDriver();
        driver.manage().window().maximize();

    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testLoginSuccessful() throws InterruptedException {
        driver.get("https://www.tiktok.com");
        Thread.sleep(5000);
        WebElement loginByFace = driver.findElement(By.xpath("//p[contains(text(),'Tiếp tục với Facebook')]"));
        loginByFace.click();

        String currentWindowHandle = driver.getWindowHandle();
        Set<String> windowHandles = driver.getWindowHandles();
        
        Thread.sleep(5000);
        ExpectedConditions.numberOfWindowsToBe(windowHandles.size() + 1);

        // Chuyển sang cửa sổ mới xuất hiện
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(currentWindowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        // Đăng nhập vào Facebook
        WebElement usernameInput = driver.findElement(By.xpath("//input[@id='email']"));
        usernameInput.sendKeys("");//Nhập tài khoản Face
        Thread.sleep(3000);
        WebElement password = driver.findElement(By.id("pass"));
        password.sendKeys("");//Nhập mật khẩu
        Thread.sleep(3000);
        WebElement buttonSubmit = driver.findElement(By.name("login"));
        buttonSubmit.click();
        Thread.sleep(5000);
        driver.switchTo().window(currentWindowHandle);
        Thread.sleep(5000);
        WebElement searchInput = driver.findElement(By.xpath("//input[@placeholder='Tìm kiếm']"));
        Thread.sleep(5000);
        searchInput.sendKeys("tianyuanmengchong");
        Thread.sleep(3000);
        WebElement searchButton = driver.findElement(By.xpath("//button[@aria-label='Tìm kiếm']"));
        searchButton.click();
        Thread.sleep(5000);
        // Kiểm tra xem kết quả tìm kiếm có chứa thông tin của người dùng cần tìm hay không
        int searchResultsCount = driver.findElements(By.className("tiktok-1d5vh4i-DivLink e10wilco0")).size(); 
        assertTrue(searchResultsCount > 0);
    }

    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main(TikTokTestCase.class.getName());
    }
}
