import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

/**
 * Задание 8. Сделайте сценарий, проверяющий наличие стикеров у товаров
 */
public class TestStickers {
    private WebDriver driverChrome;
    private WebDriverWait wait;

    @BeforeTest
    public void start() {
        driverChrome = new ChromeDriver();
        driverChrome.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wait = new WebDriverWait(driverChrome, 10);
    }

    @Test
    public void test() {
        driverChrome.get("http://localhost/litecart/public_html/en/rubber-ducks-c-1/");

        driverChrome.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        wait.until(titleIs("Rubber Ducks | My Store"));

        List<WebElement> stickerElements;

        List<WebElement> duckElements = driverChrome.findElements(By.className("product"));

        if (duckElements.size() > 0) {
            System.out.println("Найдено товаров: " + Integer.toString(duckElements.size()));

            for (WebElement dElement : duckElements) {

                stickerElements = dElement.findElements(By.cssSelector(".sticker"));

                switch (stickerElements.size()) {
                    case 1:
                        System.out.println("У " + dElement.findElement(By.cssSelector(".info .name")).getText() + " есть один стикер");
                        break;
                    case 0:
                        System.out.println("Нет стикера у " + dElement.findElement(By.cssSelector(".info .name")).getText());
                        break;
                    default:
                        System.out.println("У " + dElement.findElement(By.cssSelector(".info .name")).getText() + " стикеров: " + Integer.toString(stickerElements.size()));
                }
            }
        } else {
            System.out.println("Ни один товар не найден.");
        }
    }

    @AfterTest
    public void stop() {
        driverChrome.quit();
        driverChrome = null;
    }
}
