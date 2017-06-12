import com.google.common.collect.Sets;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfWindowsToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

/**
 Задание 14. Проверьте, что ссылки открываются в новом окне
 */
public class TestLinkOpen {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeTest
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 3);
    }

    /*public ExpectedCondition<String> anyWindowOtherThan(Set<String> oldWindows) {
        return new ExpectedCondition<String>() {
            public String apply(WebDriver driver) {
                Set<String> handles = driver.getWindowHandles();
                handles.removeAll(oldWindows);
                return handles.size() > 0 ? handles.iterator().next() : null;
            }
        };
    }*/

    @Test
    public void test() {
        driver.get("http://localhost/litecart/admin/");
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.xpath("//*[@id=\"box-login\"]/form/div[2]/button")).click();

        wait.until(titleIs("My Store"));

        driver.findElement(By.linkText("Countries")).click();
        wait.until(titleIs("Countries | My Store"));

        driver.findElement(By.linkText("Add New Country")).click();

        List<WebElement> listLinks = driver.findElements(By.cssSelector("form [target='_blank']"));
        if (listLinks.size() > 0) {
            System.out.println("Найдено ссылок открывающихся в новых окнах: " + Integer.toString(listLinks.size()));

            String mainWindow = driver.getWindowHandle();
            String newWindow = "";
            Set<String> existingWindows;

            for (int i=0; i<listLinks.size(); i++) {
                existingWindows= driver.getWindowHandles();

                driver.findElements(By.cssSelector("form [target='_blank']")).get(i).click();
                // newWindow = wait.until(anyWindowOtherThan(existingWindows));

                wait.until(numberOfWindowsToBe(existingWindows.size()+1));

                Set<String > newWindowsSet = driver.getWindowHandles();
               if (newWindowsSet.removeAll(existingWindows)) {
                   Iterator it = newWindowsSet.iterator();
                   newWindow= it.next().toString();
               }
                driver.switchTo().window(newWindow);
                System.out.println("Окно "+String.valueOf(i) +": "+driver.getWindowHandle()+" заголовок: "+driver.getTitle());
                driver.close();
                driver.switchTo().window(mainWindow);
            }
           } else {
              System.out.println("Не найдено ни одной ссылки.");
           }

        }

    @AfterTest
    public void stop() {
        driver.quit();
        driver = null;
    }
}
