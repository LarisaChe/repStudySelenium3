import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
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

    float floatFontSize(String strFont) {
        String fontDop = strFont.substring(0, strFont.length() - 2);
        return parseFloat(fontDop);
    }

    String colourName(String colourNumber) {
        int pos1 = colourNumber.indexOf("(");
        int pos2 = colourNumber.indexOf(")");

        String[] colourNumbers = (colourNumber.substring(pos1 + 1, pos2)).split(", ");
        //System.out.println(colourNumbers[0]+" "+colourNumbers[1]+" " +colourNumbers[2]);
        if ((parseInt(colourNumbers[1]) == 0) && (parseInt(colourNumbers[2]) == 0) && (parseInt(colourNumbers[0]) > 0)) {
            return "Оттенок красного";
        } else if ((parseInt(colourNumbers[0]) == parseInt(colourNumbers[1])) && (parseInt(colourNumbers[1]) == parseInt(colourNumbers[2]))) {
            return "Оттенок серого";
        } else {
            System.out.println("Ошибка: неправильный цвет: " + colourNumber);
            return "Неправильный цвет";
        }
    }

    List<String> getListProductInfo(WebDriver driver, WebElement element, String classNameForProductName) {
        List<String> listItems = Lists.newArrayList();

        String textDecoration = "text-decoration";
        if (driver.toString().substring(0, 12).equals("ChromeDriver")) {
            textDecoration = "textDecorationLine";
        }

        String fontSize = "fontSize";
        if (driver.toString().substring(0, 13).equals("FirefoxDriver")) {
            fontSize = "font-size";
        }

        listItems.add(element.findElement(By.className(classNameForProductName)).getText());
        listItems.add(element.findElement(By.className("regular-price")).getText());
        listItems.add(element.findElement(By.className("regular-price")).getCssValue("color"));
        listItems.add(colourName(element.findElement(By.className("regular-price")).getCssValue("color")));
        listItems.add(element.findElement(By.className("regular-price")).getCssValue(textDecoration));
        //listItems.add(element.findElement(By.className("regular-price")).getCssValue(fontSize));

        listItems.add(element.findElement(By.className("campaign-price")).getText());
        listItems.add(element.findElement(By.className("campaign-price")).getCssValue("color"));
        listItems.add(colourName(element.findElement(By.className("campaign-price")).getCssValue("color")));
        listItems.add(element.findElement(By.className("campaign-price")).getCssValue(textDecoration));
        //listItems.add(element.findElement(By.className("campaign-price")).getCssValue(fontSize));

        float sFontR = floatFontSize(element.findElement(By.className("regular-price")).getCssValue(fontSize));
        float sFontC = floatFontSize(element.findElement(By.className("campaign-price")).getCssValue(fontSize));

        if (sFontC > sFontR) {
            listItems.add("Шрифт campaign-price больше, чем шрифт regular-price.");
        } else {
            System.out.println("Шрифт campaign-price " + Float.toString(sFontC));
            System.out.println("Шрифта regular-price " + Float.toString(sFontR));
            System.out.println(" Ошибка: Шрифт campaign-price должен быть больше шрифта regular-price.");
        }

        /*for (String item : listItems) {
            System.out.println(item);
        }
        System.out.println();*/
        return listItems;
    }

    @Test
    public void testCheckProduct(WebDriver driver, WebDriverWait wait) {

            System.out.println(driver.toString());

            driver.get("http://localhost/litecart/public_html/en/");

            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            wait.until(titleIs("My Store | Online Store"));

            List<WebElement> duckElements = driver.findElements(By.cssSelector("[id='box-campaigns'] a"));

            if (duckElements.size() > 0) {
                System.out.println("Найдено товаров: " + Integer.toString(duckElements.size()));

                List<String> productDescription1 = getListProductInfo(driver, duckElements.get(0), "name");

                duckElements.get(0).click();

                List<String> productDescription2 = getListProductInfo(driver, driver.findElement(By.cssSelector("[id='box-product']")), "title");

                if (productDescription1.equals(productDescription2)) {
                    System.out.println("Информация по товару на главной странице и странице товара совпадает.");
                } else {
                    System.out.println("Ошибка: Информация по товару на главной странице и странице товара не совпадает!");
                }

            } else {
                System.out.println("Ни один товар не найден.");
            }
        }
}
