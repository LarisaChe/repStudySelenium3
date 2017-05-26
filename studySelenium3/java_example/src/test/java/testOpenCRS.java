import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by admin on 27.04.2017.
 */
public class testOpenCRS {

    private WebDriver driverIE;
    private WebDriverWait wait;

    boolean isElementPresent(WebDriver driver, By locator) {
        try {
            wait.until(presenceOfElementLocated((locator)));   //By.name("q")));
            //driver.findElement(locator);
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
        catch (TimeoutException ex) {
            return false;
        }
    }

    @BeforeTest
    public void startIE() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("unexpectedAlertBehaviour", "dismiss");
        //WebDriver driverIE = new InternetExplorerDriver(caps);
        //caps.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
        //caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);

        driverIE = new InternetExplorerDriver(caps);
        //driverIE = new ChromeDriver();
        wait = new WebDriverWait(driverIE, 5);
    }

    @Test
    public void testIE() {
        //driverIE.get("http://192.128.0.65:9080/crs-web/");
        //driverIE.get("http://adminwf:adminwf@localhost:8080/crs-web/");

        //driverIE.navigate().to("http://adminwf:adminwf@localhost:8080/crs-web/");
        //driverIE.navigate().to("http://localhost:8080/crs-web/");

       // driverIE.navigate().to("http://192.128.0.65:9080/crs-web/");
        driverIE.navigate().to("http://localhost:8080/crs-web/");
        //driverIE.findElement(By.name("q")).sendKeys("webdriver");
        //driverIE.findElement(By.name("btnG")).click();
        wait.until(titleIs("ЦРС | Главная"));
       // wait.until(titleIs("CRS | Main"));

        //ожидание окна
        //WebElement element1 = wait.until(presenceOfElementLocated(By.id("_z_8")));
        //if (driverIE.findElements(By.className("z-window")).size()>0) {
        if (isElementPresent(driverIE, By.className("z-window"))) {
            System.out.println("Появилось окно перезагрузки");
            driverIE.findElement(By.cssSelector("z-window z-button#_z_5")).click();
            wait.until(titleIs("ЦРС | Главная"));
        } else {System.out.println("Не появилось окно перезагрузки");}

        //driverIE.findElement(By.id("button_19")).click();
        driverIE.findElement(By.className("z-icon-gears")).click();

        //driverIE.findElement(By.className("z-icon-plus")).click();
        wait.until(presenceOfElementLocated(By.className("z-icon-plus"))).click();

        // driverIE.findElement(By.id("button_33")).click(); //30
        wait.until(titleIs("ЦРС | Структура справочника"));
        //wait.until(titleIs("CRS | Dictionary structure"));
        /*try {
            wait(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        //String tableName = "TD"+String.format("%1$tY_%1$tm_%1$td_%1$tH_%1$tM_%1$tS", LocalDateTime.now());
        String tableName = "D_"+String.format("%1$tY%1$tm%1$td_%1$tH%1$tM%1$tS", LocalDateTime.now());
        String[] tableParams = new String[5];
        tableParams[0] = tableName;
        tableParams[1] =tableName+" русский";
        tableParams[2] =tableName+" english";
        tableParams[3] ="Комментарий к справочнику "+tableName;
        tableParams[4] ="Comment for dictionary "+tableName;

        WebElement element = driverIE.findElement(By.cssSelector(".z-grid:nth-child(1)"));
        List<WebElement> tableOptions=element.findElements(By.cssSelector(".z-row-content .z-textbox"));
        //System.out.println(tableOptions.size());
        for (int i=0; i<5; i++) {
            tableOptions.get(i).sendKeys(tableParams[i]);
        }
 //       element = driverIE.findElement(By.cssSelector(".z-tabpanel .z-grid-body"));
       // element = driverIE.findElement(By.cssSelector(".z-grid:nth-child(2)"));
        //tableOptions=element.findElements(By.cssSelector(".z-row-content input"));
        //System.out.println(tableOptions.size());

//        System.out.println(driverIE.findElements(By.cssSelector(".z-row-content input.z-textbox")).size());

//        element.findElement(By.cssSelector(".z-row-content input.z-textbox:nth-child(1)")).sendKeys("name");
        /*element.findElement(By.cssSelector(".z-row-content input.z-textbox:nth-child(2)")).sendKeys("Название");
        element.findElement(By.cssSelector(".z-row-content input.z-textbox:nth-child(3)")).sendKeys("Name en");*/

        //element.findElement(By.id("listitem_692")).sendKeys("Название");
        //element.findElement(By.id("listcell_691")).sendKeys("Name en");

        //element.findElement(By.cssSelector(".z-div input.z-textbox:nth-child(2)")).sendKeys("Название");
        //element.findElement(By.cssSelector(".z-div input.z-textbox:nth-child(3)")).sendKeys("Name en");
        //driverIE.findElement(By.cssSelector(".z-tabpanel .z-row-content input.z-textbox:nth-child(2)")).sendKeys("Название");
        //driverIE.findElement(By.cssSelector(".z-tabpanel .z-row-content input.z-textbox:nth-child(3)")).sendKeys("Name en");

        List<WebElement> textboxItems=driverIE.findElements(By.cssSelector(".z-tabpanel .z-row-content input.z-textbox"));
        System.out.println(textboxItems.size());
        //for (WebElement texboxItem : textboxItems) {
        for (int i=0; i<3; i++) {
         //   System.out.println(texboxItem.getTagName());
            switch (i) {
                case 0:
                    textboxItems.get(0).sendKeys("name");
                    break;
                case 1:
                    textboxItems.get(1).sendKeys("Название");
                    break;
                default:
                    textboxItems.get(2).sendKeys("Name en");
            }
        }

        System.out.println("выбираем тип");
        element = driverIE.findElement(By.cssSelector(".z-tabpanel .z-grid-body"));
        element.findElement(By.cssSelector(".z-row-content input.z-combobox-input")).click();
        List<WebElement> listCombobox=driverIE.findElements(By.cssSelector("ul.z-combobox-content li .z-comboitem-text"));
        System.out.println(listCombobox.size());
        for (WebElement itemCombobox : listCombobox) {
            System.out.println(itemCombobox.getText());
            if (itemCombobox.getText().equals("Строка")) {
                itemCombobox.click();
                break;
            }
        }
        System.out.println("выбрали тип");

//        driverIE.findElement(By.cssSelector("span.z-checkbox:last-child")).click();
        //driverIE.findElement(By.cssSelector("span.z-checkbox:nth-child(2)")).click();

        driverIE.findElement(By.id("zkcomp_136-cave")).findElements(By.tagName("td")).get(8).findElement(By.tagName("input")).click();

        /*List<WebElement> dopItems=driverIE.findElements(By.cssSelector("span.z-checkbox"));
        System.out.println(dopItems.size());
        //for (WebElement dopItem : dopItems) {
        for (int i=0; i<3; i++) {
              System.out.println(dopItems.get(i).getTagName());
               if (i==2) {  dopItems.get(2).click();}
            }*/
//        }
        element.findElement(By.cssSelector("span.z-spinner .z-spinner-input:first-child")).sendKeys(Keys.END + "1");

        /*dopItems=driverIE.findElements(By.cssSelector("span.z-spinner .z-spinner-input"));
        System.out.println(dopItems.size());
       // dopItems.get(0).clear();
        dopItems.get(0).sendKeys(Keys.END + "1");*/

        driverIE.findElement(By.className("z-icon-save")).click();

        //driverIE.findElement(By.className("z-notification")).click();
        WebElement element1 = wait.until(presenceOfElementLocated(By.cssSelector(".z-notification .z-notification-content")));
        element1.click();

        //driverIE.findElement(By.className(".z-toolbarbutton-content .z-icon-plus")).click();
        element1 = wait.until(presenceOfElementLocated(By.cssSelector(".z-toolbarbutton-content .z-icon-plus")));
        element1.click();

        //element.findElement(By.cssSelector("span.z-spinner:nth-child(1)")).clear();
        //element.findElement(By.cssSelector("span.z-spinner:nth-child(1)")).sendKeys("1");
        //element.findElement(By.cssSelector("span.z-spinner:first-child")).clear();

      //  element.findElement(By.cssSelector("span.z-spinner:first-child")).sendKeys("1");

        //element.findElement(By.cssSelector("span.z-checkbox:nth-child(3)")).click();
      //  driverIE.findElement(By.cssSelector("span.z-checkbox:last-child")).click();

/*        driverIE.findElement(By.id("zkcomp_109")).sendKeys(tableName);
        driverIE.findElement(By.id("zkcomp_110")).sendKeys(tableName+" русский");
        driverIE.findElement(By.id("zkcomp_115")).sendKeys(tableName+" english");
        driverIE.findElement(By.id("zkcomp_116")).sendKeys("Комментарий к справочнику "+tableName);
        driverIE.findElement(By.id("zkcomp_121")).sendKeys("Comment for dictionary "+tableName);
*/
 /*       driverIE.findElement(By.id("textbox_1364")).sendKeys("name");
        driverIE.findElement(By.id("textbox_1371")).sendKeys("Название");
        driverIE.findElement(By.id("textbox_1374")).sendKeys("Name");
        driverIE.findElement(By.id("spinner_1377-real")).sendKeys("1");
        //driverIE.findElement(By.name("combobox_1264-real")). sendKeys("1");
        driverIE.findElement(By.id("combobox_1365-btn")).click();
        // выбор типа из выпадающего списка НЕ РАБОТАЕТ!!!!
        driverIE.findElement(By.id("comboitem_1682")).click();
        driverIE.findElement(By.id("zkcomp_98")).click();
*/
  /*      driverIE.findElement(By.id("textbox_1278")).sendKeys("name");
        driverIE.findElement(By.id("textbox_1285")).sendKeys("Название");
        driverIE.findElement(By.id("textbox_1288")).sendKeys("Name");
        driverIE.findElement(By.id("spinner_1291-real")).sendKeys("1");
        //driverIE.findElement(By.name("combobox_1264-real")). sendKeys("1");
        driverIE.findElement(By.id("combobox_1279-btn")).click();
        // выбор типа из выпадающего списка НЕ РАБОТАЕТ!!!!
        driverIE.findElement(By.id("comboitem_1568")).click();
        driverIE.findElement(By.id("zkcomp_98")).click();
*/

        /*driverIE.findElement(By.id("zkcomp_298")).sendKeys("TestDict100");
        driverIE.findElement(By.id("zkcomp_303")).sendKeys("TestDict100 русский");
        driverIE.findElement(By.id("zkcomp_304")).sendKeys("TestDict100 english");
        driverIE.findElement(By.id("textbox_1263")).sendKeys("name");
        driverIE.findElement(By.id("textbox_1270")).sendKeys("Название");
        driverIE.findElement(By.id("textbox_1273")).sendKeys("Name");
        driverIE.findElement(By.id("spinner_1276-real")).sendKeys("1");
        //driverIE.findElement(By.name("combobox_1264-real")). sendKeys("1");
        driverIE.findElement(By.id("combobox_1264-btn")).click();
        // выбор типа из выпадающего списка НЕ РАБОТАЕТ!!!!
        driverIE.findElement(By.id("listcell_555")).click();
        driverIE.findElement(By.id("zkcomp_328")).click();*/
    }

    @AfterTest
    public void stopIE() {
  //      driverIE.quit();
  //      driverIE = null;
    }
}
