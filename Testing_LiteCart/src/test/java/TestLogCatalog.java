import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.attributeContains;
import static org.openqa.selenium.support.ui.ExpectedConditions.attributeToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

/**
 * Задание 17. Проверьте отсутствие сообщений в логе браузера
 */
public class TestLogCatalog {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeTest
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 3);
    }
    @Test
    public void test() {
        driver.get("http://localhost/litecart/admin/");
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.xpath("//*[@id=\"box-login\"]/form/div[2]/button")).click();

        wait.until(titleIs("My Store"));

        driver.findElement(By.linkText("Catalog")).click();
        wait.until(titleIs("Catalog | My Store"));

        String footerStr = driver.findElement(By.cssSelector("table tr .footer td")).getText();
        int iProducts1=Integer.parseInt(footerStr.substring(footerStr.length()-1));
        driver.findElement(By.linkText("Rubber Ducks")).click();
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("tbody tr.row"), 2));

        List<String> listItems = Lists.newArrayList();
        List<WebElement> duckElements = driver.findElements(By.cssSelector("tbody tr.row"));
        for(WebElement duckElement : duckElements) {
            if (duckElement.findElements(By.cssSelector("td img")).size()>0) {
                listItems.add(duckElement.getText());
            }
        }
        boolean checkLogs;
        for (String item : listItems) {
            driver.findElement(By.linkText(item)).click();
            wait.until(titleIs("Edit Product: "+item+" | My Store"));
            System.out.println("Открываем страницу с продуктом "+item+": ");

            checkLogs = true;
            for (LogEntry l : driver.manage().logs().get("browser").getAll()) {
                checkLogs = false;
                System.out.println(l);
            }
            if (checkLogs) {
                    System.out.println(" -  лог пустой.");
                }
            //driver.manage().logs().get("browser").forEach(l -> System.out.println(l));
            driver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1");
            wait.until(titleIs("Catalog | My Store"));
        }
    }
    @AfterTest
    public void stop() {
        driver.quit();
        driver = null;
    }

}
