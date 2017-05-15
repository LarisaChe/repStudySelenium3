import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

/**
 *  Задание 10. Проверить, что открывается правильная страница товара
 */
public class TestCheckProduct {
    private WebDriver driverChrome;
    private WebDriverWait wait;
    private WebDriver firefoxDriver;
    private WebDriver driverIE;

    @BeforeTest
    public void start() {
//        driverChrome = new ChromeDriver();

        //firefoxDriver = new FirefoxDriver();

        driverChrome = new FirefoxDriver();

        driverChrome.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wait = new WebDriverWait(driverChrome, 10);
    }

    float floatFontSize (String strFont) {
        String fontDop = strFont.substring(0, strFont.length()-2);
        return parseFloat(fontDop);
    }

    List<String> getListProductInfo(WebElement element, String classNameForProductName) {
        List<String> listItems = Lists.newArrayList();
        //System.out.println("s");
        listItems.add(element.findElement(By.className(classNameForProductName)).getText());
        listItems.add(element.findElement(By.className("regular-price")).getText());
        listItems.add(element.findElement(By.className("regular-price")).getCssValue("color"));
        listItems.add(element.findElement(By.className("regular-price")).getCssValue("textDecorationLine"));
        listItems.add(element.findElement(By.className("regular-price")).getCssValue("fontSize"));

        listItems.add(element.findElement(By.className("campaign-price")).getText());
        listItems.add(element.findElement(By.className("campaign-price")).getCssValue("color"));
        listItems.add(element.findElement(By.className("campaign-price")).getCssValue("textDecorationLine"));
        listItems.add(element.findElement(By.className("campaign-price")).getCssValue("fontSize"));

/*        float sFontR = floatFontSize(element.findElement(By.className("regular-price")).getCssValue("fontSize"));
        float sFontC = floatFontSize(element.findElement(By.className("campaign-price")).getCssValue("fontSize"));

        if (sFontC>sFontR) {
            listItems.add("Шрифт campaign-price больше, чем шрифт regular-price.");
        } else {
            System.out.println("Шрифт campaign-price " + Float.toString(sFontC));
            System.out.println("Шрифта regular-price "+Float.toString(sFontR));
            System.out.println(" Ошибка: Шрифт campaign-price должен быть больше шрифта regular-price.");
        }*/

        for (String item : listItems) {
            System.out.println(item);
        }
        System.out.println();

        return listItems;
    }

        @Test
    public void test() {
        driverChrome.get("http://localhost/litecart/public_html/en/");

        driverChrome.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        wait.until(titleIs("My Store | Online Store"));

        List<WebElement> duckElements = driverChrome.findElements(By.cssSelector("[id='box-campaigns'] a"));

        if (duckElements.size() > 0) {
            System.out.println("Найдено товаров: " + Integer.toString(duckElements.size()));

            List<String> productDescription1 = getListProductInfo(duckElements.get(0), "name");

            duckElements.get(0).click();

            //duckElements = driverChrome.findElements(By.cssSelector("[id='box-product']"));

            //List<String> productDescription2 = getListProductInfo(duckElements.get(0), "title");
            List<String> productDescription2 = getListProductInfo(driverChrome.findElement(By.cssSelector("[id='box-product']")), "title");

            if (productDescription1.equals(productDescription2)) {
                System.out.println("Информация по товару на главной странице и странице товара совпадает.");
            } else {
                System.out.println("Информация по товару на главной странице и странице товара не совпадает!");
            }

        } else {
            System.out.println("Ни один товар не найден.");
        }
    }

    /*@AfterTest
    public void stop() {
        driverChrome.quit();
        driverChrome = null;
    }*/
}
