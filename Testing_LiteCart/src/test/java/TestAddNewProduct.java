import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

/**
 * Задание 12. Сделайте сценарий добавления товара
 */
public class TestAddNewProduct {
    private WebDriver driverChrome;
    private WebDriverWait wait;

    public void unhide(WebDriver driver, WebElement element) {
        //String script ="arguments[0].style.opacity=1;";
        String script = "arguments[0].style.opacity=1;"
                + "arguments[0].style['transform']='translate(0px, 0px) scale(1)';"
                + "arguments[0].style['MozTransform']='translate(0px, 0px) scale(1)';"
                + "arguments[0].style['WebkitTransform']='translate(0px, 0px) scale(1)';"
                + "arguments[0].style['msTransform']='translate(0px, 0px) scale(1)';"
                + "arguments[0].style['OTransform']='translate(0px, 0px) scale(1)';"
                + "arguments[0].style['OTransform']='translate(0px, 0px) scale(1)';"

                + "return true;";
        ((JavascriptExecutor) driver).executeScript(script, element);
    }  ////*[@id="tab-general"]/div/div[1]/div[1]/div/div/label[1]

    @BeforeTest
    public void start() {
        driverChrome = new ChromeDriver();
        driverChrome.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
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

        driverChrome.findElement(By.linkText("Catalog")).click();

        driverChrome.findElement(By.cssSelector(".btn[href$='product']")).click();

        WebElement element=driverChrome.findElement(By.cssSelector("label.btn.btn-default:nth-child(1)"));
        element.click();

        element=driverChrome.findElement(By.cssSelector(".form-control .checkbox:nth-child(2)"));
        element.click();
        element=driverChrome.findElement(By.cssSelector(".form-control .checkbox:nth-child(1)"));
        element.findElement(By.cssSelector("[data-name='Root']")).click();



        System.out.println("получилось");

    }

    @AfterTest
    public void stop() {
        //driverChrome.quit();
        //driverChrome = null;
    }
}
