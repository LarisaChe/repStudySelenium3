import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

/**
 *  [x] Задание 13. Сделайте сценарий работы с корзиной
 */
public class TestCart {
    private WebDriver driverChrome;
    private WebDriverWait wait;

    @BeforeTest
    public void start() {
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE); // ACCEPT);
        driverChrome = new ChromeDriver(capabilities);
        driverChrome.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wait = new WebDriverWait(driverChrome, 10);
    }

    @Test
    public void test() {
        int itemsInCart = 3; // столько товаров положим в корзину

        driverChrome.get("http://localhost/litecart/public_html/en/rubber-ducks-c-1/");

        driverChrome.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        wait.until(titleIs("Rubber Ducks | My Store"));

        List<WebElement> listProducts = driverChrome.findElements(By.cssSelector(".product .name")); //className("product"));
        List<String> productItems = Lists.newArrayList();

        if (listProducts.size() > 0) {
            System.out.println("Найдено товаров: " + Integer.toString(listProducts.size()));
            // добавляем товар в корзину
            for (WebElement product : listProducts) {
                productItems.add(product.getText());
            }
            Random rn = new Random();
            int irn = 0;
            int iCart = Integer.parseInt(driverChrome.findElement(By.className("quantity")).getAttribute("textContent"));
            //System.out.println(iCart);
            WebElement element;
            do {
                irn = rn.nextInt(productItems.size()-1); // % n;
                driverChrome.findElements(By.cssSelector(".product .name")).get(irn).click();

  /*              wait.until(presenceOfElementLocated(By.id("view-full-page")));
                element = driverChrome.findElement(By.id("view-full-page"));
                element.click();
                wait.until(stalenessOf(element));
*/
                String selectorString;
                if (driverChrome.findElements(By.name("options[Size]")).size()>0) {
                    String[] listOptions = {"Small","Medium","Large"};
                    irn=rn.nextInt(2);
                    driverChrome.findElement(By.name("options[Size]")).click();
                    selectorString="option[value='"+listOptions[irn]+"']";
                    //System.out.println(selectorString);
                    driverChrome.findElement(By.cssSelector(selectorString)).click();
                }
                driverChrome.findElement(By.name("add_cart_product")).click();

               wait.until(attributeContains(driverChrome.findElement(By.className("quantity")), "textContent",String.valueOf(iCart+1)));

                if (Integer.parseInt(driverChrome.findElement(By.className("quantity")).getAttribute("textContent")) == iCart + 1) {
                    iCart++;
                }

                //driverChrome.findElement(By.cssSelector(".breadcrumb [href*='rubber-ducks']")).click();
                driverChrome.findElement(By.cssSelector(".list-horizontal [href*='rubber-ducks']")).click();
            } while(iCart<itemsInCart);

            System.out.println("Положили в корзину "+String.valueOf(iCart)+" шт.");

            //удаляем из корзины
            driverChrome.findElement(By.id("cart")).click();

//            int iSize=driverChrome.findElements(By.cssSelector("[name='remove_cart_item']")).size();
           // int iSize=driverChrome.findElements(By.className("shortcut")).size();
            int iSize=driverChrome.findElements(By.cssSelector("table.dataTable td.item")).size();
            driverChrome.findElements(By.className("shortcut")).get(0).click();

            for (int i=1; i<=iSize; i++) {
               //driverChrome.findElement(By.cssSelector(".item input")).clear();
              // driverChrome.findElement(By.cssSelector(".item input")).sendKeys("0"+Keys.ENTER);

                element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".item [name='remove_cart_item']")));
                element.click();

                //wait.until(numberOfElementsToBe(By.cssSelector(".item [name='remove_cart_item']"), iSize-i));
                wait.until(numberOfElementsToBe(By.cssSelector("table.dataTable td.item"), iSize-i));
                 }

            if (iSize>4) {
                System.out.println("Удалили из корзины "+String.valueOf(iSize)+" наименований");
            }
            else {System.out.println("Удалили из корзины "+String.valueOf(iSize)+" наименования");
            }
            element = wait.until(presenceOfElementLocated(By.linkText("<< Back")));
            element.click();
            //driverChrome.findElement(By.linkText("<< Back")).click();
           // driverChrome.findElement(By.cssSelector("[title='Home']")).click();

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
