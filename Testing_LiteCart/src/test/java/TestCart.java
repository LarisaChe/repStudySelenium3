import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
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
//        driverChrome = new ChromeDriver();
        driverChrome.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wait = new WebDriverWait(driverChrome, 10);
    }

    @Test
    public void test() {
        //DesiredCapabilities capabilities = DesiredCapabilities.firefox();
//        capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,
//                UnexpectedAlertBehaviour.ACCEPT);

        driverChrome.get("http://localhost/litecart/public_html/en/rubber-ducks-c-1/");

        driverChrome.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        wait.until(titleIs("Rubber Ducks | My Store"));

        //List<WebElement> listProducts;

        List<WebElement> listProducts = driverChrome.findElements(By.cssSelector(".product .name")); //className("product"));
        List<String> productItems = Lists.newArrayList();

        if (listProducts.size() > 0) {
            System.out.println("Найдено товаров: " + Integer.toString(listProducts.size()));
            for (WebElement product : listProducts) {
                productItems.add(product.getText());
                //System.out.println(product.getText());
            }
            Random rn = new Random();
            //int n = productItems.size() - 1;
            int irn = 0;
            int iCart = Integer.parseInt(driverChrome.findElement(By.className("quantity")).getAttribute("textContent"));
            //System.out.println(iCart);
            do {
                irn = rn.nextInt(productItems.size()-1); // % n;
                /*if (irn == 0) {
                    irn = 1;
                }
                if (irn >= productItems.size()) {
                    irn = productItems.size() - 1;
                }*/
              //  System.out.println(irn);
                driverChrome.findElements(By.cssSelector(".product .name")).get(irn).click();
                //findElement(By.linkText(productItems.get(irn))).click();

                wait.until(presenceOfElementLocated(By.id("view-full-page")));
                //System.out.println("Делаем экран большим");
                WebElement element = driverChrome.findElement(By.id("view-full-page"));
                element.click();
                //System.out.println("Ждем когда исчезнет элемент");
                wait.until(stalenessOf(element));
                //System.out.println("Добавляем в корзину");
                driverChrome.findElement(By.name("add_cart_product")).click();

                /*try {
                    driverChrome.findElement(By.name("add_cart_product")).click();
                } catch (UnhandledAlertException ex) {
                    //System.out.println("Step " + i + " - alert present: " + ex.getAlert().getText());
                    Alert alert = driverChrome.switchTo().alert();
                    alert.accept();
                }
             catch (NoAlertPresentException ex) {
                //System.out.println("Step " + i + " - alert present: " + ex.getAlert().getText());
                Alert alert = driverChrome.switchTo().alert();
                alert.accept();
            }
                catch(Exception e){
                    System.out.print("Exception caught: " + e);
                }  */
               // System.out.println("Ждем обновления корзины");
                //driverChrome.findElement(By.className("quantity")).wait();
               wait.until(attributeContains(driverChrome.findElement(By.className("quantity")), "textContent",String.valueOf(iCart+1)));

                //wait.until(elementToBeClickable(By.id("cart")));
                 //driverChrome.findElement(By.id("cart")).submit();
                if (Integer.parseInt(driverChrome.findElement(By.className("quantity")).getAttribute("textContent")) == iCart + 1) {
                    iCart++;
                    //System.out.println("обновилась");
                }
                //else {System.out.println("не обновилась");}

                driverChrome.findElement(By.cssSelector(".breadcrumb [href*='rubber-ducks']")).click(); //findElement(By.linkText("Rubber Ducks")).click();

                //} while (Integer.parseInt(driverChrome.findElement(By.className("quantity")).getAttribute("textContent"))<3);
            } while(iCart<3);
            System.out.println("Положили в корзину три товара");
            driverChrome.findElement(By.id("cart")).click();
            //System.out.println("Зашли в корзину");
            listProducts = driverChrome.findElements(By.cssSelector("[name='remove_cart_item']")); //className("product"));
            int iSize=listProducts.size();
            //System.out.println(iSize);
            for (int i=1; i<=iSize; i++) {
               driverChrome.findElement(By.cssSelector(".item input")).clear();
               driverChrome.findElement(By.cssSelector(".item input")).sendKeys("0"+Keys.ENTER);

                // wait.until(elementToBeClickable(By.className("remove_cart_item")));
               // driverChrome.findElement(By.className("remove_cart_item")).click();
               //driverChrome.findElement(By.className("update_cart_item")).click();
                wait.until(stalenessOf(driverChrome.findElement(By.cssSelector("tbody .item"))));
                //wait.until(numberOfElementsToBe(driverChrome.findElements(By.cssSelector("[name='remove_cart_item']")), iSize-i));
                //System.out.println("Ждем когда исчезнет");
                //wait.until(stalenessOf(driverChrome.findElement(By.cssSelector("tbody .item"))));  //.get(0)));
            }
            System.out.println("Удалили товары из корзины");
            //System.out.println("закончили тест");
            driverChrome.findElement(By.linkText("<< Back")).click();
                /*for (WebElement dElement : listProducts) {


                 stickerElements = dElement.findElements(By.cssSelector(".sticker"));

                switch (stickerElements.size()) {
                    case 1:
                        System.out.println("У " + dElement.findElement(By.cssSelector(".info .name")).getText() + " есть один стикер");
                        break;
                    case 0:
                        System.out.println("Нет стикера у " + dElement.findElement(By.cssSelector(".info .name")).getText());
                        break;
                    default:
                        System.out.println("У " + dElement.findElement(By.cssSelector(".info .name")).getText() + " стикеров: " + Integer.toString(stickerElements.size()));
                }
            }*/
        } else {
            System.out.println("Ни один товар не найден.");
        }
    }

    @AfterTest
    public void stop() {
        //driverChrome.quit();
        //driverChrome = null;
    }
}
