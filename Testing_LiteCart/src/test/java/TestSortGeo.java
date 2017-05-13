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

        driverChrome.findElement(By.linkText("Countries")).click();

        //WebElement table = driverChrome.findElement(By.cssSelector("[class^='table'] tbody"));
        WebElement table = driverChrome.findElement(By.xpath("//*[@id=\'main\']/form/table/tbody"));
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        List<WebElement> cells;
        List<String> listItems = Lists.newArrayList();
        List<String> listCountriesWithZones = Lists.newArrayList();

        for (WebElement row : rows) {
            cells = row.findElements(By.tagName("td"));
            listItems.add(cells.get(4).getText());

            if (Integer.parseInt(cells.get(5).getText())>0) {
                listCountriesWithZones.add(cells.get(4).getText());
                //System.out.println(cells.get(4).getText());
            }
        }

        if (areElementsSortedABC(listItems))
            {System.out.println("Стран: " +rows.size() +". Отсортированы в алфавитном порядке.");}
            else {System.out.println("Стран: " +rows.size() +". Отсортированы не в алфавитном порядке.");}

        for (String item : listCountriesWithZones){
            driverChrome.findElement(By.linkText(item)).click();
            rows = driverChrome.findElements(By.cssSelector("table tbody tr"));
            //System.out.println(rows.size());
            listItems.clear();
            for (WebElement row : rows) {
                cells = row.findElements(By.tagName("td"));
                // System.out.println(cells.get(2).findElement(By.tagName("input")).getAttribute("value"))
                listItems.add(cells.get(2).findElement(By.tagName("input")).getAttribute("value"));
                 }

            if (areElementsSortedABC(listItems))
            {System.out.println(item +", зон: "+ rows.size() + ". Зоны отсортированы в алфавитном порядке");}
            else {System.out.println(item +", зон: "+ rows.size() + ". Зоны отсортированы в алфавитном порядке");}

            driverChrome.findElement(By.cssSelector("button[name='cancel']")).click();
        }
    }

    @AfterTest
    public void stop() {
        driverChrome.quit();
        driverChrome = null;
    }
}