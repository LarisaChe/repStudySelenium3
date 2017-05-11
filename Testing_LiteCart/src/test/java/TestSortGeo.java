import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

/**
 * Задание 9. Проверить сортировку стран и геозон в админке
 */
public class TestSortGeo {
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
        driverChrome.get("http://localhost/litecart/admin/");
        driverChrome.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driverChrome.findElement(By.name("username")).sendKeys("admin");
        driverChrome.findElement(By.name("password")).sendKeys("admin");
        driverChrome.findElement(By.xpath("//*[@id=\"box-login\"]/form/div[2]/button")).click();
        wait.until(titleIs("My Store"));

        driverChrome.findElement(By.linkText("Countries")).click();

        //WebElement table = driverChrome.findElement(By.cssSelector("[class^='table'] tbody"));
        WebElement table = driverChrome.findElement(By.xpath("//*[@id=\'main\']/form/table/tbody"));
        List<WebElement> cells;
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        List<String> itemsCountries1 = Lists.newArrayList();
        List<String> itemsCountries2 = Lists.newArrayList();

        for (WebElement row : rows) {
            cells = row.findElements(By.tagName("td"));
            itemsCountries1.add(cells.get(4).getText());
            itemsCountries2.add(cells.get(4).getText());
        }

        //System.out.println(itemsCountries.size());
        Arrays.sort(new List[]{itemsCountries2});
        if (itemsCountries1.equals(itemsCountries2)) {
            System.out.println("Страны отсортированы в алфавитном порядке");
        } else {System.out.println("Страны отсортированы не в алфавитном порядке");}

        /*
        WebElement dopElement;
        //WebElement submenuElement;
        List<String> itemsCountries = Lists.newArrayList();
        //List<String> submenuItems = Lists.newArrayList();

        List<WebElement> listCountries = driverChrome.findElements(By.cssSelector("tbody td a:not(title)"));

        for (WebElement aCountry : listCountries) {
            itemsCountries.add(aCountry.getText());
           // System.out.println(aCountry.getText());
        }
        System.out.println(listCountries.size());*/

        /*for (String menuItem : menuItems) {
            dopElement = driverChrome.findElement(By.linkText(menuItem));
            dopElement.click();
        }*/

    }

  /*  @AfterTest
    public void stop() {
        driverChrome.quit();
        driverChrome = null;
    }*/
}