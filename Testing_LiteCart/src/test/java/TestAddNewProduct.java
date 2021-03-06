import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

/**
 * Задание 12. Сделайте сценарий добавления товара
 */
public class TestAddNewProduct {
    private WebDriver driverChrome;
    private WebDriverWait wait;

    boolean alertCheck(WebDriver driver, String alertString){
        WebElement element = driver.findElement(By.cssSelector(".notice.success")); //.alert.alert-success"));
        String dopString=element.getAttribute("textContent").toString();
        return (element.isDisplayed() && dopString.equals(alertString));
    }

    void openSubCatalog (String catalogName) {

        java.util.List<WebElement> listProducts = driverChrome.findElements(By.cssSelector("table.dataTable tbody tr.row")); //.findElements(By.tagName("tr"));
            for (WebElement product : listProducts) {
                if (product.findElements(By.tagName("td")).get(2).findElement(By.tagName("a")).getText().equals(catalogName)) {
                    product.findElements(By.tagName("td")).get(2).findElement(By.tagName("a")).click();
                    break;
                }
            }
    }

    String deleteProductInCatalog(String productName) {

        openSubCatalog("Rubber Ducks");

        java.util.List<WebElement> listProducts = driverChrome.findElements(By.cssSelector("table.dataTable tbody tr.row")); //.findElement(By.tagName("tr"));
        int n =0;
        for (WebElement product : listProducts) {
            if (product.findElements(By.tagName("td")).get(2).findElement(By.tagName("a")).getText().equals(productName)) {
                n++;
                product.findElements(By.tagName("td")).get(0).click();
            }
        }
        driverChrome.findElement(By.name("delete")).click();

        Alert alert = driverChrome.switchTo().alert();
        String alertText = alert.getText();
        System.out.println(alertText);
        alert.accept();

        return String.valueOf(n)+" продуктов "+productName+ " удалено из каталога";
    }

    boolean isProductInCatalog(String productName) {
        wait.until(titleIs("Catalog | My Store"));
        boolean result = false;

        openSubCatalog("Rubber Ducks");

        java.util.List<WebElement> listProducts = driverChrome.findElements(By.cssSelector("table.dataTable tbody tr.row")); //.findElement(By.tagName("tr"));
        for (WebElement product : listProducts) {
            if (product.findElements(By.tagName("td")).get(2).findElement(By.tagName("a")).getText().equals(productName)) {
                result=true;
                break;
            }
        }
    return result;
    }

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
        String [][] productData = new String[3][2];
        productData[0][0] = "code";
        productData[0][1] = "rd007";
        productData[1][0] = "name[en]";
        productData[1][1] = "Black Duck";
        //productData[2][0] = "sku";
        //productData[2][1] = "RD007";
        productData[2][0] = "quantity";
        productData[2][1] = "30";
        /*productData[4][0] = "weight";
        productData[4][1] = "1,000";
        productData[5][0] = "dim_x";
        productData[5][1] = "6,00";
        productData[6][0] = "dim_y";
        productData[6][1] = "10,00";
        productData[7][0] = "dim_z";
        productData[7][1] = "10,00";*/

        driverChrome.findElement(By.linkText("Catalog")).click();

        /*if (isProductInCatalog(productData[1][1])) {
            System.out.println(productData[1][1]+" обнаружен в каталоге и должен быть удален");
            //System.out.println(deleteProductInCatalog(productData[1][1]));
        }*/

        driverChrome.findElement(By.cssSelector(".button[href$='product']")).click();

        wait.until(titleIs("Add New Product | My Store"));

        driverChrome.findElement(By.cssSelector("label [value='1']")).click();

        WebElement element=driverChrome.findElement(By.cssSelector(".content input[data-name='Rubber Ducks']"));
        if (driverChrome.findElements(By.cssSelector(".content input[data-name='Rubber Ducks'][checked='checked']")).size()==0) {
            element.click();}

        element=driverChrome.findElement(By.cssSelector(".content input[data-name='Root']"));
        if (element.getAttribute("checked").equals("true")) {
            element.click(); }

        element=driverChrome.findElement(By.id("tab-general"));
        for (int i = 0; i <= productData.length - 1; i++) {
            element.findElement(By.name(productData[i][0])).clear();
            element.findElement(By.name(productData[i][0])).sendKeys(productData[i][1]);
        }

        element= driverChrome.findElement(By.name("sold_out_status_id"));
        element.click();
        element.sendKeys(Keys.ARROW_DOWN,Keys.ENTER);

        File imageFile= new File("src//test//resources//7-black-duck-1.png");
        element = driverChrome.findElement(By.name("new_images[]"));
        element.sendKeys(imageFile.getAbsolutePath());

        driverChrome.findElement(By.cssSelector("[href='#tab-information']")).click();

        element= driverChrome.findElement(By.name("manufacturer_id"));
        element.click();
        element.sendKeys(Keys.ARROW_DOWN,Keys.ENTER);

        element= driverChrome.findElement(By.name("short_description[en]"));
        element.sendKeys("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse sollicitudin ante massa, eget ornare libero porta congue.");

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        String str = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse sollicitudin ante massa, eget ornare libero porta congue. Cras scelerisque dui non consequat sollicitudin. Sed pretium tortor ac auctor molestie. Nulla facilisi. Maecenas pulvinar nibh vitae lectus vehicula semper. Donec et aliquet velit. Curabitur non ullamcorper mauris. In hac habitasse platea dictumst. Phasellus ut pretium justo, sit amet bibendum urna. Maecenas sit amet arcu pulvinar, facilisis quam at, viverra nisi. Morbi sit amet adipiscing ante. Integer imperdiet volutpat ante, sed venenatis urna volutpat a. Proin justo massa, convallis vitae consectetur sit amet, facilisis id libero.";
        StringSelection strSel = new StringSelection(str);
        clipboard.setContents(strSel, null);

        element= driverChrome.findElement(By.className("trumbowyg-editor"));
        element.click();
        element.sendKeys(Keys.CONTROL + "v");

  /*      str = "Colors\n" + "Body: Black\n" + "Eyes: Black\n" + "Beak: Orange\n" + "\n" + "Other\n" + "Material: Plastic";
        strSel = new StringSelection(str);
        clipboard.setContents(strSel, null);

        element= driverChrome.findElement(By.name("attributes[en]"));
        element.click();
        element.sendKeys(Keys.CONTROL + "v");
*/
        driverChrome.findElement(By.cssSelector("[href='#tab-prices']")).click();

        element = driverChrome.findElement(By.name("purchase_price"));
        element.clear();
        element.sendKeys("10,00");

        element = driverChrome.findElement(By.name("prices[USD]"));
        element.clear();
        element.sendKeys("19.0000");

        element = driverChrome.findElement(By.name("save"));
        element.click();

        if (alertCheck(driverChrome, " Changes saved")) {
            System.out.println(productData[1][1]+" добавлен");
        } else {
            System.out.println("Не удалось добавить "+productData[1][1]);
        }

        if (isProductInCatalog(productData[1][1])) {
            System.out.println(productData[1][1]+" есть в каталоге");
        } else {
            System.out.println(productData[1][1]+ " нет в каталоге");
        }
    }

    @AfterTest
    public void stop() {
        driverChrome.quit();
        driverChrome = null;
    }
}
