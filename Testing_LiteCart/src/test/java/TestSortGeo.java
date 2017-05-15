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

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.Integer.parseInt;
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

    List<String> getListCountriesOrZones(String nameColumnCounriesOrZones, boolean withZones){
        List<String> listItems = Lists.newArrayList();
        int iName = -1, iZones = -1;

        List<WebElement> ths = driverChrome.findElements(By.cssSelector("table thead tr th"));
        for (WebElement th : ths) {
            if (th.getText().equals(nameColumnCounriesOrZones)) {  //Name or Zone
                iName = parseInt(th.getAttribute("cellIndex"));
            }
            if (th.getAttribute("textContent").equals("Zones")) {
            //if (th.getText().equals("Zones")) {
                iZones = parseInt(th.getAttribute("cellIndex"));
            }
        }

        List<WebElement> rows = driverChrome.findElements(By.cssSelector("table tbody tr"));
        List<WebElement> cells;

        if (!withZones) {
            for (WebElement row : rows) {
                cells = row.findElements(By.tagName("td"));

                if (!cells.get(iName).getText().isEmpty()) {
                    listItems.add(cells.get(iName).getText());
                }
                    else {
                    listItems.add(cells.get(iName).findElement(By.tagName("input")).getAttribute("value"));
                }
            }
        }
            else {
            for (WebElement row : rows) {
                cells = row.findElements(By.tagName("td"));

                if (parseInt(cells.get(iZones).getText()) > 0) {
                    listItems.add(cells.get(iName).getText());
                }
            }
        }
        return listItems;
        }

    boolean areElementsSortedABC(List<String> listItems) {
        List<String> listItemsDop = Lists.newArrayList();

        listItemsDop.addAll(listItems);

        Arrays.sort(new List[]{listItemsDop});
        if (listItems.equals(listItemsDop)) {
            return true;
        } else {
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

        List<String> listItems = Lists.newArrayList();

        System.out.println("Countries");
        driverChrome.findElement(By.linkText("Countries")).click();

        listItems = getListCountriesOrZones("Name", false);

        if (areElementsSortedABC(listItems))
            {System.out.println("Стран: " +listItems.size() +". Отсортированы в алфавитном порядке.");}
            else {System.out.println("Стран: " +listItems.size() +". Отсортированы не в алфавитном порядке.");}

        List<String> listCountriesWithZones = getListCountriesOrZones("Name", true);

        for (String item : listCountriesWithZones){
            driverChrome.findElement(By.linkText(item)).click();

            listItems = getListCountriesOrZones("Name", false);

            if (areElementsSortedABC(listItems))
            {System.out.println(item +", зон: "+ listItems.size() + ". Зоны отсортированы в алфавитном порядке");}
            else {System.out.println(item +", зон: "+ listItems.size() + ". Зоны отсортированы НЕ в алфавитном порядке");}

            driverChrome.findElement(By.cssSelector("button[name='cancel']")).click();
        }

        System.out.println("Geo Zones");
        driverChrome.findElement(By.linkText("Geo Zones")).click();

        listCountriesWithZones = getListCountriesOrZones("Name", true);

        for (String item : listCountriesWithZones){
            driverChrome.findElement(By.linkText(item)).click();

            listItems = getListCountriesOrZones("Zone", false);

            if (areElementsSortedABC(listItems))
            {System.out.println(item +", зон: "+ listItems.size() + ". Зоны отсортированы в алфавитном порядке");}
            else {System.out.println(item +", зон: "+ listItems.size() + ". Зоны отсортированы НЕ в алфавитном порядке");}

            driverChrome.findElement(By.cssSelector("button[name='cancel']")).click();
        }
    }

    @AfterTest
    public void stop() {
        driverChrome.quit();
        driverChrome = null;
    }
}