import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import java.awt.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;
import static org.testng.AssertJUnit.assertFalse;

/**
 * Задание 7. Сделайте сценарий, проходящий по всем разделам админки
 * */
public class TestAllMenu {
    private WebDriver driverChrome;
    private WebDriverWait wait;

    @BeforeTest
    public void start() {
        driverChrome = new ChromeDriver();
        driverChrome.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wait = new WebDriverWait(driverChrome, 10);
    }

    boolean isElementPresent(WebDriver driver, By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    boolean areElementsPresent(WebDriver driver, By locator) {
        try {
            wait.until((WebDriver d) -> d.findElements(locator));
            return driver.findElements(locator).size() > 0;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    @Test
    public void test() {
        driverChrome.get("http://localhost/litecart/admin/");
        driverChrome.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driverChrome.findElement(By.name("username")).sendKeys("admin");
        driverChrome.findElement(By.name("password")).sendKeys("admin");
        driverChrome.findElement(By.xpath("//*[@id=\"box-login\"]/form/div[2]/button")).click();
        wait.until(titleIs("My Store"));

        WebElement dopElement;
        //WebElement submenuElement;
        List<String> menuItems = Lists.newArrayList();
        List<String> submenuItems = Lists.newArrayList();

        List<WebElement> menuElements = driverChrome.findElements(By.id("app-"));

        for (WebElement menuElement : menuElements) {
            menuItems.add(menuElement.getText());
        }

        for (String menuItem : menuItems) {
            dopElement = driverChrome.findElement(By.linkText(menuItem));
            dopElement.click();

            //Проверка заголовка при активизации пункта основного меню
            //if (driverChrome.findElement(By.tagName("h1")).isDisplayed()) {
            if (isElementPresent(driverChrome, By.tagName("h1"))) {
                System.out.println("Заголовок найден для " + menuItem);
            } else {
                System.out.println("      Нет заголовка для " + menuItem);
            }

            menuElements.clear();

            // Поиск подменю у элемента
                 // возможные вариации
                   //if (isElementPresent(driverChrome, By.cssSelector("li#app-.selected ul.docs"))) {
                   //if (areElementsPresent(driverChrome, By.cssSelector("div#box-apps-menu-wrapper li#app- ul.docs li[id^=doc]"))) {
                   //if (areElementsPresent(driverChrome, By.xpath("//*[@id='app-']/ul/li"))) {

            menuElements = driverChrome.findElements(By.cssSelector("[id^=doc-]"));

                 //возможные вариации с JavaScript
                    //menuElements = (List<WebElement>) ((JavascriptExecutor) driverChrome).executeScript("return document.querySelectorAll('a:contains((WebDriver)')");
                    //menuElements = (List<WebElement>) ((JavascriptExecutor) driverChrome).executeScript("return $$('[id^=doc-]')");

                if (menuElements.size() > 0) {
                for (WebElement menuElement : menuElements) {
                    submenuItems.add(menuElement.getText());
                }
                for (String submenuItem : submenuItems) {
                    dopElement = driverChrome.findElement(By.linkText(submenuItem));
                    if (!dopElement.isSelected()) {
                        dopElement.click();

                        //Проверка заголовка при активизации пункта подменю
                        //if (driverChrome.findElement(By.tagName("h1")).isDisplayed()) {
                        if (isElementPresent(driverChrome, By.tagName("h1"))) {
                            System.out.println("Заголовок найден для подменю " + submenuItem);
                        } else {
                            System.out.println("      Нет заголовка для подменю " + submenuItem);
                        }
                    }
                }
            }
            submenuItems.clear();
        }
    }
    @AfterTest
    public void stop() {
        driverChrome.quit();
        driverChrome = null;
    }
}
